package com.jobs.im.core.utils;

import java.util.Collection;
import java.util.Map;

import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.jobs.im.core.enu.ApiCodeEnum;
import com.jobs.im.core.exception.ServerException;

/**
 * @program: im
 * @ClassName: Assert
 * @description:
 * @author: Author
 * @create: 2024-02-23 14:33
 * @Version 1.0
 **/
public final class Assert extends org.springframework.util.Assert {
    public static void isTrue(boolean expression, ApiCodeEnum codeEnum) {
        if (!expression) {
            throw new ServerException(codeEnum);
        }
    }

    public static void isNull(@Nullable Object object, ApiCodeEnum codeEnum) {
        if (object != null) {
            throw new ServerException(codeEnum);
        }
    }

    public static void notNull(@Nullable Object object, ApiCodeEnum codeEnum) {
        if (object == null) {
            throw new ServerException(codeEnum);
        }
    }

    public static void hasLength(@Nullable String text, ApiCodeEnum codeEnum) {
        if (!StringUtils.hasLength(text)) {
            throw new ServerException(codeEnum);
        }
    }

    public static void notEmpty(@Nullable Collection<?> collection, ApiCodeEnum codeEnum) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new ServerException(codeEnum);
        }
    }

    public static void empty(@Nullable Collection<?> collection, ApiCodeEnum codeEnum) {
        if (!CollectionUtils.isEmpty(collection)) {
            throw new ServerException(codeEnum);
        }
    }

    public static void notEmpty(@Nullable Map<?, ?> map, ApiCodeEnum codeEnum) {
        if (CollectionUtils.isEmpty(map)) {
            throw new ServerException(codeEnum);
        }
    }

    public static void empty(@Nullable Map<?, ?> map, ApiCodeEnum codeEnum) {
        if (!CollectionUtils.isEmpty(map)) {
            throw new ServerException(codeEnum);
        }
    }
}
