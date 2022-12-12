package com.xkcoding.mongodb.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Alarm的DTO
 */
@Data
@Document(collection = "equipmentImageInfo")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImagePO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 图片ID
     */
    @Id
    private String id;

    private String equipmentNum;
    /**
     * 设备编号
     */
    private String code;

    /**
     * 图片类型0:所有；1：全景；2：农机作业；9：作物长势
     */
    private String type;

    /**
     * 图片地址
     */
    private String imagePath;

    /**
     * 图片采集时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

    /**
     * 图片标签，农机作业类型名称
     */
    private String tag;

    /**
     * 图片文件大小，以Kb为单位
     */
    private Integer size;

    /**
     * 农场id
     */
    private String farmId;

    /**
     * 地块id
     */
    private String fieldIds;

}
