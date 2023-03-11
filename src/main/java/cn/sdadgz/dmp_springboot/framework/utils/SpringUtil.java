package cn.sdadgz.dmp_springboot.framework.utils;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.json.JSONUtil;
import cn.sdadgz.dmp_springboot.framework.annotation.MqttController;
import cn.sdadgz.dmp_springboot.framework.mqtt.MqttConnectionFactory;
import cn.sdadgz.dmp_springboot.framework.mqtt.MqttSubscribeClient;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * spring相关工具
 *
 * <p>
 * 废物本物
 * </p>
 *
 * @author sdadgz
 * @since 2023/3/10 19:02:21
 */
@Configuration
@RequiredArgsConstructor
public class SpringUtil {

    private final MqttConnectionFactory mqttConnectionFactory;

    // 获取mqtt路由
    @Bean
    public Map<String, Consumer<String>> getMqttControllerRouter() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 返回值
        HashMap<String, Consumer<String>> map = new HashMap<>();
        // springboot包路径
        String springApplicationPackageName = getSpringApplicationPackageName();
        // 获取所有controller
        Set<Class<?>> controllerClass = ClassUtil.scanPackageByAnnotation(springApplicationPackageName, MqttController.class);
        // 遍历
        for (Class<?> clazz : controllerClass) {
            // 创建对象
            Object controller = cn.hutool.extra.spring.SpringUtil.getBean(clazz);

            // 获取一级路由
            RequestMapping firstAnnotation = clazz.getAnnotation(RequestMapping.class);
            String basePath = firstAnnotation == null ? "" :
                    Arrays.stream(firstAnnotation.value()).collect(Collectors.toList()).get(0);

            // 存在requestMapping的方法
            List<Method> methodThatHasRequestMapping = Arrays.stream(clazz.getDeclaredMethods())
                    .filter(method -> method.getAnnotation(RequestMapping.class) != null)
                    .collect(Collectors.toList());

            // 遍历方法
            for (int i = 0; i < methodThatHasRequestMapping.size(); i++) {
                String otherPath = methodThatHasRequestMapping.get(i).getAnnotation(RequestMapping.class).value()[0];
                int finalI = i;
                map.put(basePath + otherPath, msg -> {
                    try {
                        methodThatHasRequestMapping.get(finalI).invoke(controller, JSONUtil.toBean(msg, methodThatHasRequestMapping.get(finalI).getParameterTypes()[0]));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
            }
        }

        return map;
    }

    // 获取springboot启动项包名
    public String getSpringApplicationPackageName() {
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation("", SpringBootApplication.class);
        List<String> collect = classes.stream().map(ClassUtil::getPackage).collect(Collectors.toList());
        return collect.size() == 1 ? collect.get(0) : "";
    }

    // 连接并 订阅所有
    public void connectAndSubscribeAll() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        mqttConnectionFactory.connect();
        // 遗留问题，不能自依赖
        getMqttControllerRouter().keySet().forEach(url -> new MqttSubscribeClient().setTopic(url).subscribe());
    }

    // 重新连接
    public void reconnect() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        connectAndSubscribeAll();
    }

}
