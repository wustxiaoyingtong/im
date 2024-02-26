package com.jobs.im.core.exception;

import com.jobs.im.core.enu.ApiCodeEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: im
 * @ClassName: ServerException
 * @description: 服务端异常
 * @author: Author
 * @create: 2024-02-23 14:34
 * @Version 1.0
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServerException extends RuntimeException {
    private ApiCodeEnum apiCodeEnum;
}
