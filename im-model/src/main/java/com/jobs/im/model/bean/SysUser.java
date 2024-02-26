package com.jobs.im.model.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统用户表(SpSysUser)表实体类
 *
 * @author wust
 * @since 2024-02-23 14:00:45
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("sp_sys_user")
public class SysUser implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 账户
     **/
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     **/
    @TableField(value = "password")
    private String password;

    /**
     * 昵称
     **/
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 邮箱
     **/
    @TableField(value = "email")
    private String email;

    /**
     * 手机号
     **/
    @TableField(value = "phone")
    private String phone;

    /**
     * 备注
     **/
    @TableField(value = "remark")
    private String remark;

    /**
     * 账户状态：0-启用；1-禁用；
     **/
    @TableField(value = "is_disabled")
    private Integer isDisabled;

    /**
     * 创建时间
     **/
    @TableField(value = "create_at")
    private Date createAt;

    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 更新时间
     **/
    @TableField(value = "update_at")
    private Date updateAt;

    @TableField(value = "update_by", fill = FieldFill.INSERT)
    private String updateBy;
}
