package cn.sdadgz.dmp_springboot.mqtt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.function.Function;

/**
 * mqtt控制者
 *
 * <p>
 * 废物本物
 * </p>
 *
 * @author sdadgz
 * @since 2023/3/9 19:51:16
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MqttController {

    private final MqttConnectionFactory mqttConnectionFactory;

    // 测试订阅sub
    public static void testSub() {
        new MqttSubscribeClient().subscribe();
    }

    // 容器创建时启动
    @Bean
    public void startAll() {
        mqttConnectionFactory.connect();
        testSub();
    }

}
