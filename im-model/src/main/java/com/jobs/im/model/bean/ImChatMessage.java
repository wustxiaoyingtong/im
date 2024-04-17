package com.jobs.im.model.bean;

import java.io.Serializable;
import java.util.Date;

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
 * @ClassName: ImChatMessage
 * @description:
 * @author: Author
 * @create: 2024-04-12 14:50
 * @Version 1.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("im_chat_message")
public class ImChatMessage implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "uid")
    private String uid;

    /**
     * 发送人用户id
     **/
    @TableField(value = "sender_id")
    private Integer senderId;

    /**
     * 发送人用户uid
     */
    @TableField(value = "sender_uid")
    private String senderUid;

    /**
     * 发送人名称
     **/
    @TableField(value = "sender_name")
    private String senderName;

    /**
     * 发送人昵称
     **/
    @TableField(value = "sender_nickname")
    private String senderNickname;

    /**
     * 消息类型；0-系统，1-私聊，2-群聊，3-通知
     **/
    @TableField(value = "type")
    private Integer type;

    /**
     * 接收人uid
     **/
    @TableField(value = "receiver_uid")
    private String receiverUid;

    /**
     * 接收人名称
     **/
    @TableField(value = "receiver_name")
    private String receiverName;

    /**
     * 接收人昵称
     **/
    @TableField(value = "receiver_nickname")
    private String receiverNickname;

    /**
     * 内容
     **/
    @TableField(value = "content")
    private String content;

    /**
     * 发送时间
     **/
    @TableField(value = "time")
    private Date time;
}
