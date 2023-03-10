package cn.sdadgz.dmp_springboot;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
class DmpSpringbootApplicationTests {

    @Resource
    private Map<String, Class<?>> mqttControllerRouter;

    @Test
    void contextLoads() {
        System.out.println(mqttControllerRouter);
    }

}
