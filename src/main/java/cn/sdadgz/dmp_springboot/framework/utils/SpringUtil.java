package cn.sdadgz.dmp_springboot.framework.utils;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ClassUtil;
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
    public Map<String, Class<?>> getMqttControllerRouter() {
        // 返回值
        HashMap<String, Class<?>> map = new HashMap<>();
        // springboot包路径
        String springApplicationPackageName = getSpringApplicationPackageName();
        // 获取所有controller
        Set<Class<?>> controllerClass = ClassUtil.scanPackageByAnnotation(springApplicationPackageName, MqttController.class);
        // 遍历
        controllerClass.forEach(clazz -> {
            // 获取一级路由
            RequestMapping annotation = clazz.getAnnotation(RequestMapping.class);
            String basePath = annotation == null ? "" :
                    Arrays.stream(annotation.value()).collect(Collectors.toList()).get(0);

            // 存在requestMapping的方法
            List<Method> methodThatHasRequestMapping = Arrays.stream(clazz.getDeclaredMethods())
                    .filter(method -> method.getAnnotation(RequestMapping.class) != null)
                    .collect(Collectors.toList());

            // 二级路由和二级路由对应的参数类
            ArrayList<String> secondPaths = new ArrayList<>();
            ArrayList<Class<?>> paramClasses = new ArrayList<>();

            for (Method method : methodThatHasRequestMapping) {
                secondPaths.add(Arrays.stream(method.getAnnotation(RequestMapping.class).value())
                        .collect(Collectors.toList()).get(0));
                paramClasses.add(Arrays.stream(method.getParameterTypes())
                        .collect(Collectors.toList()).get(0));
            }

            for (int i = 0; i < secondPaths.size(); i++) {
                map.put(basePath + secondPaths.get(i), paramClasses.get(i));
            }
        });

        return map;
    }

    // 获取springboot启动项包名
    public String getSpringApplicationPackageName() {
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation("", SpringBootApplication.class);
        List<String> collect = classes.stream().map(ClassUtil::getPackage).collect(Collectors.toList());
        return collect.size() == 1 ? collect.get(0) : "";
    }

    // 连接并 订阅所有
    public void connectAndSubscribeAll() {
        mqttConnectionFactory.connect();
        // 遗留问题，不能自依赖
        getMqttControllerRouter().keySet().forEach(url -> new MqttSubscribeClient().setTopic(url).subscribe());
    }

    // 重新连接
    public void reconnect(){
        connectAndSubscribeAll();
    }

}
