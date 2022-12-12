package com.xkcoding.mongodb.repository;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.xkcoding.mongodb.SpringBootDemoMongodbApplicationTests;
import com.xkcoding.mongodb.model.ImagePO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
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


    /**
     * 测试分页排序查询
     */
    @Test
    public void testQuery() {

        Criteria criteria = Criteria.where("equipmentNum").in(Arrays.asList("G28206747"));
            criteria.and("type").is("0");

        String format_DateTime = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format_DateTime);
        LocalDateTime localDateTime = LocalDateTime.parse("2023-11-23 12:13:08", df);
        //创建时间  开始时间  结束时间
            criteria = criteria.and("time").lte(localDateTime);
//        if (dto.getTimeBeg() != null && dto.getTimeEnd() != null) {
//            criteria.andOperator(
//                Criteria.where("time").gte("2021-01-01 12:12:12"),
//                Criteria.where("time").lte("2022-12-29 12:12:12"));
//        } else {
//            if (dto.getTimeBeg() != null) {
//        criteria.and("time").gte("2021-01-01 12:12:12.000").lte("2023-12-29 12:12:12.000");
//        criteria= criteria.and("time").gte("2021-01-01 12:12:12");
//            }
//            if (dto.getTimeEnd() != null) {
//                criteria.and("time").lte(DateUtils.formatDateTime(dto.getTimeEnd()));
//            }
//        }
        Query query = Query.query(criteria).with(Sort.by(Sort.Direction.DESC, "time"));
        long total = mongoTemplate.count(query, ImagePO.class);
        query.skip((1 - 1) * 10).limit(10);
        List<ImagePO> list = mongoTemplate.find(
            query, ImagePO.class
        );
        log.info("【总页数】= {}", list);
    }



}
