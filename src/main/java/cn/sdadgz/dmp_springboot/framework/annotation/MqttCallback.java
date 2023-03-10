package cn.sdadgz.dmp_springboot.framework.annotation;

import cn.hutool.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * mqtt回调函数注解 标注在类上
 *
 * <p>
 * 废物本物
 * </p>
 *
 * @author sdadgz
 * @since 2023/3/10 17:15:32
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface MqttCallback {
    @AliasFor(annotation = Component.class)
    String value() default "";

    // 丢失连接回调函数方法名
    String connectionLostMethodName() default "connectionLost";
    // 收到订阅的消息回调函数方法名
    String messageArrivedMethodName() default "messageArrived";
    // 发送消息之后回调函数方法名
    String deliveryCompleteMethodName() default "deliveryComplete";
}
