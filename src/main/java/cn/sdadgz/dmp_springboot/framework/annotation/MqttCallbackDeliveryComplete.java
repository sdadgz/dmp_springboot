package cn.sdadgz.dmp_springboot.framework.annotation;

import java.lang.annotation.*;

/**
 * mqtt消息发送之后回调
 *
 * <p>
 * 废物本物
 * </p>
 *
 * @author sdadgz
 * @since 2023/3/11 12:29:09
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface MqttCallbackDeliveryComplete {
    String value() default "";
}
