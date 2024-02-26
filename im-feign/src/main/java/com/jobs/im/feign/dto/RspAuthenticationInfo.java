package com.jobs.im.feign.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: im
 * @ClassName: RspAuthenticationInfo
 * @description:
 * @author: Author
 * @create: 2024-02-24 09:52
 * @Version 1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RspAuthenticationInfo implements Serializable {
    Boolean success;

    String message;
}
