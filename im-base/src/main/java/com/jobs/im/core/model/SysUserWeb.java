package com.jobs.im.core.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: im
 * @ClassName: SysUserWeb
 * @description:
 * @author: Author
 * @create: 2024-02-26 17:51
 * @Version 1.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUserWeb implements Serializable {
    String acccessToken;

    String userName;
}
