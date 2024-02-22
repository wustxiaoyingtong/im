package com.jobs.im.rbac.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

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
        String[] personPropertyArr = new String[] {"createBy"};
        handlePersonMeta(metaObject, personPropertyArr);
        String[] timePropertyArr = new String[] {"createTime", "createAt"};
        handleTimeMeta(metaObject, timePropertyArr);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String[] personPropertyArr = new String[] {"updateBy", "modifyBy"};
        handlePersonMeta(metaObject, personPropertyArr);
        String[] timePropertyArr = new String[] {"updateTime", "updateAt", "modifyTime", "modifyAt"};
        handleTimeMeta(metaObject, timePropertyArr);
    }

    private void handlePersonMeta(MetaObject metaObject, String[] personPropertyArr) {
        // String personProperty = hasProperties(metaObject, personPropertyArr);
        // if (Objects.isNull(personProperty)) {
        // return;
        // }
        // Object value = metaObject.getValue(personProperty);
        // if (Objects.nonNull(value)) {
        // Class<?> propertyType = getPropertyType(metaObject, personProperty);
        // if (propertyType.equals(Integer.class)) {
        // RespSysUserDto user = rbacApiClient.selectById(Integer.valueOf(value.toString()));
        // if (Objects.isNull(user)) {
        // this.setFieldValByName(personProperty, 0, metaObject);
        // } else {
        // this.setFieldValByName(personProperty, value, metaObject);
        // }
        // } else {
        // RespSysUserDto user = rbacApiClient.selectByName(value.toString());
        // if (Objects.isNull(user)) {
        // this.setFieldValByName(personProperty, "", metaObject);
        // } else {
        // this.setFieldValByName(personProperty, value, metaObject);
        // }
        // }
        // } else {
        // Class<?> propertyType = getPropertyType(metaObject, personProperty);
        // if (propertyType.equals(Integer.class)) {
        // this.setFieldValByName(personProperty, JwtUtils.getUserIdInt(), metaObject);
        // } else {
        // this.setFieldValByName(personProperty, JwtUtils.getUsername(), metaObject);
        // }
        // }
    }

    private void handleTimeMeta(MetaObject metaObject, String[] timePropertyArr) {
        String timeProperty = hasProperties(metaObject, timePropertyArr);
        if (Objects.isNull(timeProperty)) {
            return;
        }
        if (Objects.isNull(metaObject.getValue(timeProperty))) {
            Class<?> propertyType = getPropertyType(metaObject, timeProperty);
            if (propertyType.equals(Date.class)) {
                this.setFieldValByName(timeProperty, new Date(), metaObject);
            } else {
                this.setFieldValByName(timeProperty, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                    metaObject);
            }
        }
    }

    private String hasProperties(MetaObject metaObject, String[] propertyArr) {
        for (String property : propertyArr) {
            if (hasProperty(metaObject, property)) {
                return property;
            }
        }
        return null;
    }

    private Class<?> getPropertyType(MetaObject metaObject, String name) {
        return metaObject.getGetterType(name);
    }

    private boolean hasProperty(MetaObject metaObject, String name) {
        return StringUtils.isNotEmpty(metaObject.findProperty(name, true));
    }
}
