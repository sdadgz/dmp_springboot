package cn.sdadgz.dmp_springboot.controller;

import cn.sdadgz.dmp_springboot.entity.Test;
import cn.sdadgz.dmp_springboot.framework.config.MqttConfig;
import cn.sdadgz.dmp_springboot.framework.mqtt.MqttConnectionFactory;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * mqtt控制者
 *
 * <p>
 * 废物本物
 * </p>
 *
 * @author sdadgz
 * @since 2023/3/9 19:51:16
 */
@Slf4j
@RequiredArgsConstructor
@cn.sdadgz.dmp_springboot.framework.annotation.MqttController
public class MqttController {

    private final MqttConnectionFactory mqttConnectionFactory;

    // 测试订阅sub
    @RequestMapping("/test")
    public void test(Test test) {
        System.out.println(test.getMsg());
    }

    // 测试订阅sub
    @RequestMapping("/test01")
    public void test01(String msg) {
        System.out.println();
    }

    // 接收gps

}
