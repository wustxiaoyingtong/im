package com.jobs.im.core.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: im
 * @ClassName: ApiCode
 * @description:
 * @author: Author
 * @create: 2024-02-22 15:24
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiCode implements Serializable {
    public int code;

    public String msg;
}
