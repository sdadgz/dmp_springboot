package cn.sdadgz.dmp_springboot.framework;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.sdadgz.dmp_springboot.framework.mqtt.MqttConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    private final MqttConnectionFactory mqttConnectionFactory;

    // 订阅全部topic
    public void subscribeTopics() {
        // todo 获取带有controller注解的类并将所有带有requestMapping的存进去
    }

    // 连接mqtt
    @Bean
    public void connect() {
        mqttConnectionFactory.connect();
        subscribeTopics();
    }

    // 重新连接
    public void reConnect() {
        connect();
    }

}
