package com.jobs.im.feign.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: im
 * @ClassName: ReqAuthenticationInfo
 * @description:
 * @author: Author
 * @create: 2024-02-24 09:48
 * @Version 1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqAuthenticationInfo implements Serializable {
    @NotEmpty(message = "accessToken不能为空")
    String accessToken;
}
