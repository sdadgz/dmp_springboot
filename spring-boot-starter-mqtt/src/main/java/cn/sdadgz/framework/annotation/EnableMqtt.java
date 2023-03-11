package cn.sdadgz.framework.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 启用mqtt
 *
 * <p>
 * 废物本物
 * </p>
 *
 * @author sdadgz
 * @since 2023/3/11 16:25:56
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@ComponentScan
public @interface EnableMqtt {
    @AliasFor(
            annotation = ComponentScan.class,
            attribute = "basePackages"
    )
    String value() default "cn.sdadgz.framework";

}
