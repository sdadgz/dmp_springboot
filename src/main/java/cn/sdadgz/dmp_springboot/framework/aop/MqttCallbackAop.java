package cn.sdadgz.dmp_springboot.framework.aop;

import cn.sdadgz.dmp_springboot.framework.utils.SpringUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

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

    // 丢失连接后
    @After("@annotation(cn.sdadgz.dmp_springboot.framework.annotation.MqttCallbackConnectLost)")
    public void connectLost(){
        springUtil.reconnect();
    }

}
