package com.jobs.im.model.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件信息表(ImFile)表实体类
 *
 * @author wust
 * @since 2024-03-29 16:35:19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("im_file")
public class ImFile implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableId(value = "uid", type = IdType.AUTO)
    private Long uid;

    /**
     * 文件原始名
     **/
    @TableField(value = "original_name")
    private String originalName;

    /**
     * 文件名
     **/
    @TableField(value = "name")
    private String name;

    /**
     * 创建时间
     **/
    @TableField(value = "create_at")
    private Date createAt;

    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private Integer createBy;

}
