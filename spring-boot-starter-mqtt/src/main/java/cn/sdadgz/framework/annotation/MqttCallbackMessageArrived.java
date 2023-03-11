package cn.sdadgz.framework.annotation;

import java.lang.annotation.*;

/**
 * mqtt收到订阅的消息回调
 *
 * <p>
 * 废物本物
 * </p>
 *
 * @author sdadgz
 * @since 2023/3/11 12:27:05
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface MqttCallbackMessageArrived {
    String value() default "";
}
