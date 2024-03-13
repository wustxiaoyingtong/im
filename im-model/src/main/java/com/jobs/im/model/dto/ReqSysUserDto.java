package com.jobs.im.model.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import com.jobs.im.core.model.BaseDto;
import com.jobs.im.core.valid.SaveValid;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统用户表(SpSysUser)表实ReqDto
 *
 * @author wust
 * @since 2024-02-23 14:00:45
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReqSysUserDto extends BaseDto {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "uid")
    private Long uid;

    @NotEmpty(message = "账户不能为空", groups = SaveValid.class)
    @ApiModelProperty(value = "账户")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @NotEmpty(message = "昵称不能为空", groups = SaveValid.class)
    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "账户状态：0-启用；1-禁用；")
    private Integer isDisabled;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "创建人")
    private Integer createBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateAt;

    @ApiModelProperty(value = "更新人")
    private Integer updateBy;
}
