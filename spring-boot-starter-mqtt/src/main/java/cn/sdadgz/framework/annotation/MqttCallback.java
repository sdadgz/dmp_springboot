package cn.sdadgz.framework.annotation;

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
}
