package com.jobs.im.model.dto;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: im
 * @ClassName: RespImFileDto
 * @description:
 * @author: Author
 * @create: 2024-03-29 16:59
 * @Version 1.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespImFileDto implements Serializable {
    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "uid")
    private String uid;

    @ApiModelProperty(value = "文件原始名")
    private String originalName;

    @ApiModelProperty(value = "文件名")
    private String name;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "创建人")
    private Integer createBy;
}
