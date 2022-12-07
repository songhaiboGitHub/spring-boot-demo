package com.xkcoding.cache.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xkcoding.cache.redis.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * <p>
 * Redis测试
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-11-15 17:17
 */
@Slf4j
public class RedisTest extends SpringBootDemoCacheRedisApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Serializable> redisCacheTemplate;

    /**
     * 测试 Redis 操作
     */
    @Test
    public void get() {
        // 测试线程安全，程序结束查看redis中count的值是否为1000
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        IntStream.range(0, 1000).forEach(i -> executorService.execute(() -> stringRedisTemplate.opsForValue().increment("count", 1)));

        stringRedisTemplate.opsForValue().set("k1", "v1");
        String k1 = stringRedisTemplate.opsForValue().get("k1");
        log.debug("【k1】= {}", k1);

        // 以下演示整合，具体Redis命令可以参考官方文档
        String key = "xkcoding:user:1";
        redisCacheTemplate.opsForValue().set(key, new User(1L, "user1"));
        // 对应 String（字符串）
        User user = (User) redisCacheTemplate.opsForValue().get(key);
        log.debug("【user】= {}", user);
    }

    @Test
    public void getHash() {
        String a = "{\n" +
            "\t\"verify\": {\n" +
            "\t\t\"appId\": \"e58d123613614b118bf90726349c26ba\",\n" +
            "\t\t\"secretKey\": \"90fa9a2ea21441b8931e5930ff8185e0\"\n" +
            "\t},\n" +
            "\t\"dataInfo\": {\n" +
            "\t\t\"equipmentId\": \"000000001937107200\",\n" +
            "\t\t\"temperature\": \"空气温度\",\n" +
            "\t\t\"humidity\": \"空气湿度\"\n" +
            "\t}\n" +
            "}";
        JSONObject jsonObject = JSONObject.parseObject(a);
        JSONObject data = jsonObject.getJSONObject("dataInfo");
        try {
            int i = 0 / 0;
        } catch (Exception e) {
            Map<String, String> map = new HashMap<>();
            map.put("error", e.getMessage());
            map.put("dataInfo", data.toJSONString());
            String join = String.join(":", "11", "22", "34");
            redisCacheTemplate.opsForValue().set("equipment_push_data_error_msg:"+join, JSON.toJSONString(map),30, TimeUnit.DAYS);
        }
        Object v = redisCacheTemplate.opsForValue().get("equipment_push_data_error_msg:11:22:34");
        String b=(String) v;
        Map map = JSON.parseObject(b, Map.class);
        System.out.println(map);
    }
}
