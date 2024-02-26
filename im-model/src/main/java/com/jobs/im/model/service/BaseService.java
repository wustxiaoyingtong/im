package com.jobs.im.model.service;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.jobs.im.core.model.PageInfo;
import com.jobs.im.model.dto.BaseDto;

/**
 * @program: im
 * @ClassName: BaseService
 * @description:
 * @author: Author
 * @create: 2024-02-23 14:24
 * @Version 1.0
 **/
public interface BaseService<T, K extends BaseDto> {
    default int add(K k) throws RuntimeException {
        return 0;
    }

    default int delete(Serializable id) throws RuntimeException {
        return 0;
    }

    default int update(K k) throws RuntimeException {
        return 0;
    }

    default T detail(Serializable id) throws RuntimeException {
        return null;
    }

    default List<T> query(K k) {
        return Collections.EMPTY_LIST;
    }

    default PageInfo<T> queryPage(K k) {
        return null;
    }
}
