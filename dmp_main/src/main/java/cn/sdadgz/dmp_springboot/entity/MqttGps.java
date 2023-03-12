package cn.sdadgz.dmp_springboot.entity;

import cn.sdadgz.dmp_springboot.utils.StringUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * mqtt回应用的gps
 *
 * <p>
 * 废物本物
 * </p>
 *
 * @author sdadgz
 * @since 2023/3/12 14:53:57
 */
@Getter
@Setter
@NoArgsConstructor
public class MqttGps {

    public MqttGps(String redis) {
        String[] split = redis.split(StringUtil.SPLIT);
        latitude = split[0];
        longitude = split[1];
    }

    private String longitude; // 经度
    private String latitude; // 纬度

}
