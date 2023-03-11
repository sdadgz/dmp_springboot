package cn.sdadgz.dmp_springboot.framework;

import cn.hutool.core.util.ClassUtil;
import cn.sdadgz.dmp_springboot.framework.mqtt.MqttConnectionFactory;
import cn.sdadgz.dmp_springboot.framework.utils.SpringUtil;
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

    private final SpringUtil springUtil;

    // 开始函数
    @Bean
    public void start(){
        springUtil.connectAndSubscribeAll();
    }

}
