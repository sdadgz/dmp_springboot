package cn.sdadgz.dmp_springboot.mqtt;

import cn.sdadgz.framework.annotation.MqttCallbackConnectLost;
import cn.sdadgz.framework.annotation.MqttCallbackDeliveryComplete;
import cn.sdadgz.framework.annotation.MqttCallbackMessageArrived;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * mqtt订阅者回调
 *
 * <p>
 * 废物本物
 * </p>
 *
 * @author sdadgz
 * @since 2023/3/9 19:47:58
 */
@Slf4j
@RequiredArgsConstructor
@cn.sdadgz.framework.annotation.MqttCallback
public class MqttDefaultCallBack implements MqttCallback {

    // 断开连接之后
    @Override
    @MqttCallbackConnectLost
    public void connectionLost(Throwable throwable) {
        log.info("mqtt断开连接，正在重连");
    }

    // 接消息之后
    @Override
    @MqttCallbackMessageArrived
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // subscribe后得到的消息会执行到这里面,这里在控制台有输出
//        log.info("接收消息主题 : " + topic);
//        log.info("接收消息Qos : " + message.getQos());
//        log.info("接收消息内容 : " + new String(message.getPayload()));
    }

    // 发消息之后
    @Override
    @MqttCallbackDeliveryComplete
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
