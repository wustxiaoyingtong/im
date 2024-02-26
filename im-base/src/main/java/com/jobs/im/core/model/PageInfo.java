package com.jobs.im.core.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: im
 * @ClassName: PageInfo
 * @description:
 * @author: Author
 * @create: 2024-02-23 14:27
 * @Version 1.0
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageInfo<T> implements Serializable {
    // 当前页
    private int pageNum;
    // 每页的数量
    private int pageSize;
    // 当前页的数量
    private long total;
    // 总页数
    private int pages;
    // 结果集
    private List<T> list;

    public PageInfo(List<T> list) {
        if (list instanceof Collection) {
            this.pageNum = 1;
            this.pageSize = list.size();
            this.pages = this.pageSize > 0 ? 1 : 0;
            this.list = list;
            this.total = list.size();
        }
    }

    public static <T> PageInfo of(Integer pageNo, Integer pageSize, long total, List<T> list) {
        return PageInfo.<T>builder().pageNum(pageNo).pageSize(pageSize).total(total)
            .pages(0 == total ? 0 : (int)((total - 1) / pageSize + 1)).list(list).build();
    }
}
