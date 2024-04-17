package com.jobs.im.model.dto;

import java.util.Date;

import com.jobs.im.core.model.BaseDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 聊天消息记录(ImChatMessage)表实ReqDto
 *
 * @author wust
 * @since 2024-04-12 14:50:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqImChatMessageDto extends BaseDto {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "uid")
    private String uid;

    @ApiModelProperty(value = "发送人用户id")
    private Integer senderId;

    @ApiModelProperty(value = "发送人uid")
    private String senderUid;

    @ApiModelProperty(value = "发送人名称")
    private String senderName;

    @ApiModelProperty(value = "发送人昵称")
    private String senderNickname;

    @ApiModelProperty(value = "消息类型；0-系统，1-私聊，2-群聊，3-通知")
    private Integer type;

    @ApiModelProperty(value = "接收人uid")
    private String receiverUid;

    @ApiModelProperty(value = "接收人名称")
    private String receiverName;

    @ApiModelProperty(value = "接收人昵称")
    private String receiverNickname;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "发送时间")
    private Date time;

}
