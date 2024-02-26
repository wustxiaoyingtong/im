package com.jobs.im.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: im
 * @ClassName: ReqSysUserLoginDto
 * @description:
 * @author: Author
 * @create: 2024-02-23 15:02
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqSysUserLoginDto implements Serializable {
    @NotEmpty(message = "账户不能为空")
    @ApiModelProperty(value = "账户")
    private String username;

    @NotEmpty(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;
}
