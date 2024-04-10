package com.jobs.im.model.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.jobs.im.core.model.BaseDto;
import com.jobs.im.core.valid.SaveValid;
import com.jobs.im.core.valid.UpdateValid;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 群聊信息表(ImChatGroup)表实ReqDto
 *
 * @author wust
 * @since 2024-03-14 16:30:53
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReqImChatGroupDto extends BaseDto {
    @NotNull(message = "主键Id不能为空", groups = UpdateValid.class)
    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "uid")
    private String uid;

    @NotEmpty(message = "群组名不能为空", groups = SaveValid.class)
    @ApiModelProperty(value = "群组名")
    private String name;

    @ApiModelProperty(value = "群公告")
    private String announcement;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "创建人")
    private Integer createBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateAt;

    @ApiModelProperty(value = "更新人")
    private Integer updateBy;

}
