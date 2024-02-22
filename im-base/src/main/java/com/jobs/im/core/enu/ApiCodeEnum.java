package com.jobs.im.core.enu;

import lombok.AllArgsConstructor;

/**
 * @program: im
 * @ClassName: ApiCode
 * @description:
 * @author: Author
 * @create: 2024-02-22 15:14
 * @Version 1.0
 **/
@AllArgsConstructor
public enum ApiCodeEnum {
    SUCCESS(200, "success"), FAIL(500, "fail");

    public int code;

    public String desc;
}
