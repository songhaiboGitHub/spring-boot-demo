package com.xkcoding.cache.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Hello Controller
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-11-29 14:58
 */
@RestController
@RequestMapping
public class HelloController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/hello")
    public String hello() {
        Long count = stringRedisTemplate.opsForValue().increment("count", 1);
        return "Hello,From Docker! count: " + count;
    }
}
