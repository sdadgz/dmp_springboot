package cn.sdadgz.dmp_springboot.mqtt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * mqtt配置类
 *
 * <p>
 * 废物本物
 * </p>
 *
 * @author sdadgz
 * @since 2023/3/9 19:09:17
 */
@Configuration
@ConfigurationProperties(MqttConfig.PREFIX)
@Getter
@Setter
public class MqttConfig {

    // 配置文件前缀
    public static final String PREFIX = "my.mqtt";

    private String host;
    private String clientId;
    private String username;
    private String password;
    private String topic;
    private int timeout;
    private int keepAlive;

}
