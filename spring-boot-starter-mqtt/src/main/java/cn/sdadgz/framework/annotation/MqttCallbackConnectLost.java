package cn.sdadgz.framework.annotation;

import java.lang.annotation.*;

/**
 * mqtt回调函数 断开连接
 *
 * <p>
 * 废物本物
 * </p>
 *
 * @author sdadgz
 * @since 2023/3/11 12:02:22
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface MqttCallbackConnectLost {
    String value() default "";
}
