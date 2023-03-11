package cn.sdadgz.dmp_springboot.controller;

import cn.sdadgz.dmp_springboot.entity.Test;
import cn.sdadgz.dmp_springboot.utils.StringUtil;
import cn.sdadgz.framework.mqtt.MqttConnectionFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;

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
@cn.sdadgz.framework.annotation.MqttController
public class MqttController {

    private final MqttConnectionFactory mqttConnectionFactory;

    // 测试订阅sub
    @RequestMapping("/test")
    public void test(Test test) {
        log.info("test接收到消息：{}", test.getMsg());
    }

    /**
     * field 0：$GPRMC, 格式ID，表示该格式为建议的最低特定GPS / TRANSIT数据（RMC）推荐最低定位信息<br>
     * field 1: UTC时间, 格式hhmmss.ssss，代表时分秒.毫秒<br>
     * field 2: 状态 A:代表定位成功 V:代表定位失败 <br>
     * field 3: 纬度 ddmm.mmmmmm 度格式（如果前导位数不足，则用0填充）<br>
     * field 4: 纬度 N(北纬)  S(南纬)<br>
     * field 5: 经度 dddmm.mmmmmm 度格式（如果前导位数不足，则用0填充）<br>
     * field 6: 经度 E(东经) W(西经)<br>
     * field 7: 速度（也为1.852 km / h）<br>
     * field 8: 方位角，度（二维方向，等效于二维罗盘）<br>
     * field 9: UTC日期 DDMMYY 天月年<br>
     * field 10: 磁偏角（000-180）度，如果前导位数不足，则用0填充）<br>
     * field 11: 磁偏角方向E =东W =西<br>
     * field 12: 模式，A =自动，D =差分，E =估计，AND =无效数据（3.0协议内容）<br>
     * field 13: 校验和<br>
     *
     * @param gps 收到的gps信息
     */
    @RequestMapping("/gps")
    public void gps(String gps) {
        log.info("gps接收到消息：{}" + gps);
        String[] fields = gps.split(",");
        String latitude = StringUtil.longitudeAndLatitudeFormat(fields[3]);
        String longitude = StringUtil.longitudeAndLatitudeFormat(fields[5]);
        log.info("latitude：{}，longitude：{}",latitude,longitude);
    }

}
