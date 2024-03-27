package com.jobs.im.model.bean;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: im
 * @ClassName: ImChatGroupRel
 * @description:
 * @author: Author
 * @create: 2024-03-22 11:51
 * @Version 1.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("im_chat_group_rel")
public class ImChatGroupRel implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 群组id
     **/
    @TableField(value = "group_id")
    private Integer groupId;

    /**
     * 用户id
     **/
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 群聊角色，0：一般角色；1：管理员
     **/
    @TableField(value = "role")
    private Integer role;

    /**
     * 是否禁言，0：正常；1：禁言
     **/
    @TableField(value = "speak")
    private Integer speak;
}
