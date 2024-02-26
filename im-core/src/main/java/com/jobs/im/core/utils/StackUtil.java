package com.jobs.im.core.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * @program: im
 * @ClassName: StackUtil
 * @description:
 * @author: Author
 * @create: 2024-02-23 15:37
 * @Version 1.0
 **/
public final class StackUtil {
    /**
     * Description: 打印错误栈并返回
     * 
     * @param throwable
     * @return String
     * @throws
     * @author Author
     * @date 2024/2/23 15:38
     **/
    public static String getStackTrace(Throwable throwable) {
        try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        } catch (IOException e) {
            return Strings.EMPTY;
        }
    }

    /**
     * Description: 获取异常中打印的错误
     *
     * @param ex
     * @return String
     * @throws
     * @author Author
     * @date 2024/2/23 17:14
     **/
    public static String getMessage(MethodArgumentNotValidException ex) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        if (CollectionUtils.isEmpty(allErrors)) {
            return "";
        }
        return allErrors.stream().map(s -> s.getDefaultMessage()).collect(Collectors.joining(";"));
    }
}
