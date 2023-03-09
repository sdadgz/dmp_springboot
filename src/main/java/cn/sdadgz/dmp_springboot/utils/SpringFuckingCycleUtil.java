package cn.sdadgz.dmp_springboot.utils;

import cn.hutool.extra.spring.SpringUtil;
import cn.sdadgz.dmp_springboot.mqtt.MqttController;
import org.springframework.stereotype.Component;

/**
 * spring循环依赖，我的锅
 *
 * <p>
 * 废物本物
 * </p>
 *
 * @author sdadgz
 * @since 2023/3/9 20:47:07
 */
@Component
public class SpringFuckingCycleUtil {

    public static MqttController mqttController = SpringUtil.getBean("mqttController");

}
