package com.jobs.im.file.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.jobs.im.core.enu.ApiCodeEnum;
import com.jobs.im.core.model.BaseDto;
import com.jobs.im.core.model.PageInfo;
import com.jobs.im.core.utils.Assert;

/**
 * @program: im
 * @ClassName: BaseServiceImpl
 * @description:
 * @author: Author
 * @create: 2024-02-23 14:38
 * @Version 1.0
 **/
public abstract class BaseServiceImpl {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Description: 公共分页查询-单表
     *
     * @param mapper
     * @param wrapper
     * @param dto
     * @return PageInfo<T>
     * @throws
     * @author Author
     * @date 2024/2/23 14:54
     **/
    public <K extends BaseMapper<T>, T> PageInfo<T> query(K mapper, Wrapper<T> wrapper, BaseDto dto) {
        IPage<T> resultPage = mapper.selectPage(new Page<T>(dto.getPageNo(), dto.getPageSize()), wrapper);
        return PageInfo.of(dto.getPageNo(), dto.getPageSize(), resultPage.getTotal(), resultPage.getRecords());
    }

    protected <T, M, N> Map<M, N> collectionToMap(BaseMapper<T> mapper, Function<T, M> key, Function<T, N> item,
        Collection<? extends Serializable> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return Maps.newHashMap();
        }
        List<T> ts = mapper.selectBatchIds(idList);
        return Optional.ofNullable(ts.stream().collect(Collectors.toMap(t -> key.apply(t), t -> item.apply(t))))
            .orElse(Maps.newHashMap());
    }

    /**
     * 集合校验
     *
     * @param mapper
     *            Mapper
     * @param wrapper
     *            Wrapper
     * @param <K>
     * @param <T>
     */
    protected <K extends BaseMapper<T>, T> void checkCollection(K mapper, Wrapper<T> wrapper, ApiCodeEnum apiCodeEnum) {
        List<T> list = mapper.selectList(wrapper);
        Assert.isTrue(CollectionUtils.isEmpty(list), apiCodeEnum);
    }
}
