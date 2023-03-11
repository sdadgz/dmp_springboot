package cn.sdadgz.framework.aop;

import cn.sdadgz.framework.MqttFrameworkCore;
import cn.sdadgz.framework.utils.SpringUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

/**
 * mqtt回调类aop
 *
 * <p>
 * 废物本物
 * </p>
 *
 * @author sdadgz
 * @since 2023/3/11 11:58:33
 */
@Aspect
@Component
@RequiredArgsConstructor
public class MqttCallbackAop {

    private final SpringUtil springUtil;
    private final MqttFrameworkCore mqttFrameworkCore;

    // 丢失连接后
    @After("@annotation(cn.sdadgz.framework.annotation.MqttCallbackConnectLost)")
    public void connectLost() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        springUtil.reconnect();
    }

    // 消息到达后
    @After(value = "@annotation(cn.sdadgz.framework.annotation.MqttCallbackMessageArrived) && args(topic, message)",
            argNames = "topic, message")
    public void messageArrived(String topic, MqttMessage message) {
        mqttFrameworkCore.controllerHandler(topic,
                String.valueOf(message.getQos()),
                new String(message.getPayload()));
    }

    // 消息发送后
    @After("@annotation(cn.sdadgz.framework.annotation.MqttCallbackDeliveryComplete)")
    public void deliveryComplete() {

    }
}
