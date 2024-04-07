package com.jobs.im.model.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.jobs.im.core.model.BaseDto;
import com.jobs.im.core.valid.SaveValid;
import com.jobs.im.core.valid.UpdateValid;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件信息表(ImFile)表实ReqDto
 *
 * @author wust
 * @since 2024-03-29 16:35:19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReqImFileDto extends BaseDto {
    @NotNull(message = "主键Id不能为空", groups = UpdateValid.class)
    @ApiModelProperty(value = "主键")
    private Integer id;

    @NotNull(message = "uid不能为空", groups = SaveValid.class)
    @ApiModelProperty(value = "uid")
    private Long uid;

    @ApiModelProperty(value = "文件原始名")
    private String originalName;

    @NotEmpty(message = "文件名不能为空", groups = SaveValid.class)
    @ApiModelProperty(value = "文件名")
    private String name;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "创建人")
    private Integer createBy;

}
