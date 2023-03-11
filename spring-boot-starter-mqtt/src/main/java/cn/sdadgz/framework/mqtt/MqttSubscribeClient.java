package cn.sdadgz.framework.mqtt;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * mqtt订阅者客户端
 *
 * <p>
 * 废物本物
 * </p>
 *
 * @author sdadgz
 * @since 2023/3/9 20:51:41
 */
@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class MqttSubscribeClient {

    private String topic = "/test";
    private int qos;

    /**
     * 订阅某个主题
     */
    public void subscribe() {
        try {
            MqttClient client = MqttConnectionFactory.getMqttClient();
            if (client == null) return;
            client.subscribe(topic, qos);
            log.info("订阅主题:{}", topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
