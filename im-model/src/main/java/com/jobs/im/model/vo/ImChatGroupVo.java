package com.jobs.im.model.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.jobs.im.model.bean.ImChatGroupRel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: im
 * @ClassName: ImChatGroupVo
 * @description:
 * @author: Author
 * @create: 2024-03-22 16:16
 * @Version 1.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImChatGroupVo implements Serializable {
    private Integer id;

    private String uid;

    /**
     * 群组名
     **/
    private String name;

    /**
     * 群公告
     **/
    private String announcement;

    /**
     * 创建时间
     **/
    private Date createAt;

    private Integer createBy;

    /**
     * 更新时间
     **/
    private Date updateAt;

    private Integer updateBy;

    /**
     * 关联的用户集合
     */
    private List<ImChatGroupRel> rels;
}
