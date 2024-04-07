package com.jobs.im.file.config;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.jobs.im.core.context.SysUserContextHolder;

/**
 * @program: im
 * @ClassName: MetaObjectHandler
 * @description:
 * @author: Author
 * @create: 2024-02-22 17:11
 * @Version 1.0
 **/
@Component
public class ImMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        String createBy = metaObject.findProperty("createBy", true);
        if (StringUtils.isNotEmpty(createBy)) {
            this.setFieldValByName(createBy, SysUserContextHolder.getUserId(), metaObject);
        }
        String createAt = metaObject.findProperty("createAt", true);
        if (StringUtils.isNotEmpty(createAt)) {
            this.setFieldValByName(createAt, new Date(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String updateBy = metaObject.findProperty("updateBy", true);
        if (StringUtils.isNotEmpty(updateBy)) {
            this.setFieldValByName(updateBy, SysUserContextHolder.getUserId(), metaObject);
        }
        String updateAt = metaObject.findProperty("updateAt", true);
        if (StringUtils.isNotEmpty(updateAt)) {
            this.setFieldValByName(updateAt, new Date(), metaObject);
        }
    }
}
