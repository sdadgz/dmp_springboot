package cn.sdadgz.framework;

import cn.sdadgz.framework.utils.SpringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.function.Consumer;

/**
 * mqtt主体
 *
 * <p>
 * 废物本物
 * </p>
 *
 * @author sdadgz
 * @since 2023/3/10 17:23:56
 */
@Configuration
@RequiredArgsConstructor
public class MqttFrameworkCore {

    private final SpringUtil springUtil;
    private final Map<String, Consumer<String>> mqttControllerRouter;

    // 调用controller层方法
    public void controllerHandler(String topic, String qos, String msg) {
        mqttControllerRouter.get(topic).accept(msg);
    }

    // 开始函数
    @Bean
    public void start() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        springUtil.connectAndSubscribeAll();
    }

}
