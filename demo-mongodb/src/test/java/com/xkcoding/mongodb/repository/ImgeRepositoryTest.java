package com.xkcoding.mongodb.repository;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.xkcoding.mongodb.SpringBootDemoMongodbApplicationTests;
import com.xkcoding.mongodb.model.Article;
import com.xkcoding.mongodb.model.ImagePO;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 测试操作 MongoDb
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-28 16:35
 */
@Slf4j
public class ImgeRepositoryTest extends SpringBootDemoMongodbApplicationTests {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private Snowflake snowflake;

    /**
     * 测试新增
     */
    @Test
    public void testSave() {
        ImagePO imagePO = new ImagePO();
        imagePO.setId("2");
        imagePO.setEquipmentNum("G28206747");
        imagePO.setCode("G28206747");
        imagePO.setType("0");
        imagePO.setImagePath("http://www.baidu.com");
        String format_DateTime = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format_DateTime);
        LocalDateTime localDateTime = LocalDateTime.parse("2022-11-23 12:13:08", df);
        imagePO.setTime(localDateTime);
        imagePO.setTag("tag");
        imagePO.setSize(10);
        imagePO.setFarmId("农场id");
        imagePO.setFieldIds("地块id");
        ImagePO insert = mongoTemplate.insert(imagePO,"equipmentImageInfo");
        log.info("【article】= {}", JSONUtil.toJsonStr(insert));
    }

    /**
     * 测试新增列表
     */
    @Test
    public void testSaveList() {
        List<ImagePO> imagePOS = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            ImagePO imagePO = new ImagePO();
            imagePO.setId(String.valueOf(snowflake.nextId()));
            imagePO.setEquipmentNum("G28206747");
            imagePO.setCode("G28206747");
            imagePO.setType("0");
            imagePO.setImagePath("http://www.baidu.com");
            String format_DateTime = "yyyy-MM-dd HH:mm:ss";
            DateTimeFormatter df = DateTimeFormatter.ofPattern(format_DateTime);
            LocalDateTime localDateTime = LocalDateTime.parse("2022-11-23 12:13:08", df);
            imagePO.setTime(localDateTime);
            imagePO.setTag("tag");
            imagePO.setSize(10);
            imagePO.setFarmId("农场id");
            imagePO.setFieldIds("地块id");
            imagePOS.add(imagePO);
        }
        mongoTemplate.insertAll(imagePOS);
        log.info("【articles】= {}", JSONUtil.toJsonStr(imagePOS.stream().map(ImagePO::getId).collect(Collectors.toList())));
    }


}
