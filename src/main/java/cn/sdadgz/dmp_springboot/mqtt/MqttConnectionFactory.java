package cn.sdadgz.dmp_springboot.mqtt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * mqtt连接工厂
 *
 * <p>
 * 废物本物
 * </p>
 *
 * @author sdadgz
 * @since 2023/3/9 19:27:43
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MqttConnectionFactory {

    private final MqttDefaultCallBack mqttDefaultCallBack;
    private final MqttConfig mqttConfig;

    // mqtt客户端
    @Getter @Setter
    private static MqttClient mqttClient;

    // 连接
    public void connect() {
        connect(mqttConfig.getHost(),
                mqttConfig.getClientId(),
                mqttConfig.getUsername(),
                mqttConfig.getPassword(),
                mqttConfig.getTimeout(),
                mqttConfig.getKeepAlive());
    }

    // 连接
    private void connect(String host, String clientID, String username, String password, int timeout, int keepalive) {
        MqttClient client;
        try {
            client = new MqttClient(host, clientID, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setConnectionTimeout(timeout);
            options.setKeepAliveInterval(keepalive);
            setMqttClient(client);
            try {
                //设置回调类
                client.setCallback(mqttDefaultCallBack);
//                client.connect(options);
                IMqttToken iMqttToken = client.connectWithResult(options);
                boolean complete = iMqttToken.isComplete();
                log.info("MQTT连接" + (complete ? "成功" : "失败"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
