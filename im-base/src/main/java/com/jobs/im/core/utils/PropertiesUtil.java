package com.jobs.im.core.utils;

import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;
import com.jobs.im.core.enu.ApiCodeEnum;
import com.jobs.im.core.model.ApiCode;

/**
 * @program: im
 * @ClassName: PropertiesUtil
 * @description:
 * @author: Author
 * @create: 2024-02-22 16:05
 * @Version 1.0
 **/
public final class PropertiesUtil {
    private static ConcurrentMap<String, String> codeMap = Maps.newConcurrentMap();

    private PropertiesUtil() {}

    public static void put(String key, String value) {
        codeMap.put(key, value);
    }

    public static String get(String key) {
        return codeMap.get(key);
    }

    public static ApiCode get(ApiCodeEnum enu) {
        String code = get(enu.desc + ".code");
        if (StringUtils.isBlank(code)) {
            return new ApiCode();
        }
        return new ApiCode(Integer.valueOf(code), codeMap.getOrDefault(enu.desc + ".msg", null));
    }
}
