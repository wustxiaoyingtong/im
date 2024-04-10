package com.jobs.im.model.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: im
 * @ClassName: ImChatGroup
 * @description:
 * @author: Author
 * @create: 2024-03-14 17:35
 * @Version 1.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("im_chat_group")
public class ImChatGroup implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableId(value = "uid")
    private String uid;

    /**
     * 群组名
     **/
    @TableField(value = "name")
    private String name;

    /**
     * 群公告
     **/
    @TableField(value = "announcement")
    private String announcement;

    /**
     * 创建时间
     **/
    @TableField(value = "create_at", fill = FieldFill.INSERT)
    private Date createAt;

    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private Integer createBy;

    /**
     * 更新时间
     **/
    @TableField(value = "update_at", fill = FieldFill.UPDATE)
    private Date updateAt;

    @TableField(value = "update_by", fill = FieldFill.UPDATE)
    private Integer updateBy;
}
