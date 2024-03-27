package com.jobs.im.model.dto;

import javax.validation.constraints.NotNull;

import com.jobs.im.core.model.BaseDto;
import com.jobs.im.core.valid.SaveValid;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 群聊-用户关联表(ImChatGroupRel)表实ReqDto
 *
 * @author wust
 * @since 2024-03-22 11:51:22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReqImChatGroupRelDto extends BaseDto {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @NotNull(message = "群组id不能为空", groups = SaveValid.class)
    @ApiModelProperty(value = "群组id")
    private Integer groupId;

    @NotNull(message = "用户id不能为空", groups = SaveValid.class)
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "群聊角色，0：一般角色；1：管理员")
    private Integer role;

    @ApiModelProperty(value = "是否禁言，0：正常；1：禁言")
    private Integer speak;
}
