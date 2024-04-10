package com.jobs.im.core.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: im
 * @ClassName: SysUserLogin
 * @description:
 * @author: Author
 * @create: 2024-02-23 16:37
 * @Version 1.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUserLogin implements Serializable {
    @ApiModelProperty(value = "令牌")
    private String accessToken;

    @ApiModelProperty(value = "用户ID")
    private Integer id;

    @ApiModelProperty(value = "uid")
    private String uid;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private Long pic;

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色编码")
    private String roleCode;
}
