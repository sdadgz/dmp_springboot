package cn.sdadgz.dmp_springboot.controller;

import cn.sdadgz.dmp_springboot.common.Result;
import cn.sdadgz.dmp_springboot.entity.MqttGps;
import cn.sdadgz.dmp_springboot.utils.RedisUtil;
import cn.sdadgz.dmp_springboot.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * mqtt回应controller
 *
 * <p>
 * 废物本物
 * </p>
 *
 * @author sdadgz
 * @since 2023/3/12 14:52:53
 */
@RestController
@RequestMapping("/mqtt")
@RequiredArgsConstructor
public class MqttRequestController {

    private final RedisUtil redisUtil;

    @GetMapping("/gps")
    public Result gps() {

        // 获取所有键
        Set<String> keys = redisUtil.getKeys(StringUtil.GPS_PREFIX);
        List<MqttGps> collect = keys.stream().map(key -> new MqttGps(redisUtil.get(key).toString())).collect(Collectors.toList());

        return Result.success(collect);
    }

}
