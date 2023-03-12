package cn.sdadgz.framework.utils;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.json.JSONUtil;
import cn.sdadgz.framework.annotation.MqttController;
import cn.sdadgz.framework.mqtt.MqttConnectionFactory;
import cn.sdadgz.framework.mqtt.MqttSubscribeClient;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public Map<String, Consumer<MqttMessage>> getMqttControllerRouter() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 返回值
        HashMap<String, Consumer<MqttMessage>> map = new HashMap<>();
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
                map.put(basePath + otherPath, massage -> {
                    String msg = new String(massage.getPayload());
                    try {
                        Method method = methodThatHasRequestMapping.get(finalI);
                        // 如果类型是MqttMessage，不管直接扔进去
                        method.invoke(controller, method.getParameterTypes()[0] == MqttMessage.class ? massage : method.getParameterTypes()[0] == String.class ? msg : JSONUtil.toBean(msg, method.getParameterTypes()[0]));
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
