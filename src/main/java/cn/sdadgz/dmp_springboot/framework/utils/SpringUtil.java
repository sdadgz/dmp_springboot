package cn.sdadgz.dmp_springboot.framework.utils;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ClassUtil;
import cn.sdadgz.dmp_springboot.framework.annotation.MqttController;
import lombok.var;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

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
@Component
public class SpringUtil {

    // 获取springboot启动项包名
    public String getSpringApplicationPackageName() {
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation("", SpringBootApplication.class);
        List<String> collect = classes.stream().map(ClassUtil::getPackage).collect(Collectors.toList());
        return collect.size() == 1 ? collect.get(0) : "";
    }

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

}
