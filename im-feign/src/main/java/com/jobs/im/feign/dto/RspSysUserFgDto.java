package com.jobs.im.feign.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: im
 * @ClassName: RspSysUserFgDto
 * @description:
 * @author: Author
 * @create: 2024-03-12 11:48
 * @Version 1.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RspSysUserFgDto implements Serializable {

    private Integer id;

    /**
     * uid
     **/
    private Long uid;

    /**
     * 账户
     **/
    private String username;

    /**
     * 密码
     **/
    private String password;

    /**
     * 昵称
     **/
    private String nickname;

    /**
     * 邮箱
     **/
    private String email;

    /**
     * 手机号
     **/
    private String phone;

    /**
     * 备注
     **/
    private String remark;

    /**
     * 账户状态：0-启用；1-禁用；
     **/
    private Integer isDisabled;

    /**
     * 创建时间
     **/
    private Date createAt;

    private Integer createBy;

    /**
     * 更新时间
     **/
    private Date updateAt;

    private Integer updateBy;

}
