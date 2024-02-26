package com.jobs.im.model.dto;

import java.util.Date;

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

    @ApiModelProperty(value = "账户")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

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
    private String createBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateAt;

    @ApiModelProperty(value = "更新人")
    private String updateBy;
}
