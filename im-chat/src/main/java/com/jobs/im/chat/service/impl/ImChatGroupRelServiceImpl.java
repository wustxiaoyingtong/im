package com.jobs.im.chat.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jobs.im.chat.dao.ImChatGroupRelMapper;
import com.jobs.im.chat.service.IImChatGroupRelService;
import com.jobs.im.core.enu.ApiCodeEnum;
import com.jobs.im.core.model.PageInfo;
import com.jobs.im.core.utils.Assert;
import com.jobs.im.core.utils.BeanMapperUtil;
import com.jobs.im.model.bean.ImChatGroupRel;
import com.jobs.im.model.dto.ReqImChatGroupRelDto;

/**
 * 群聊-用户关联表(ImChatGroupRel)表Service实现类
 *
 * @author wust
 * @since 2024-03-22 11:51:26
 */
@Service
public class ImChatGroupRelServiceImpl extends BaseServiceImpl implements IImChatGroupRelService {
    @Resource
    private ImChatGroupRelMapper imChatGroupRelMapper;

    @Override
    public int add(ReqImChatGroupRelDto reqImChatGroupRelDto) throws RuntimeException {
        List<ImChatGroupRel> list = imChatGroupRelMapper.selectList(
            Wrappers.<ImChatGroupRel>lambdaQuery().eq(ImChatGroupRel::getGroupId, reqImChatGroupRelDto.getGroupId())
                .eq(ImChatGroupRel::getUserId, reqImChatGroupRelDto.getUserId()));
        Assert.isTrue(CollectionUtils.isEmpty(list), ApiCodeEnum.RECORD_EXISTS);
        return imChatGroupRelMapper.insert(BeanMapperUtil.map(reqImChatGroupRelDto, ImChatGroupRel.class));
    }

    @Override
    public int delete(Serializable id) throws RuntimeException {
        ImChatGroupRel imChatGroupRel = imChatGroupRelMapper.selectById(id);
        Assert.notNull(imChatGroupRel, ApiCodeEnum.RECORD_NOT_EXISTS);
        return imChatGroupRelMapper.deleteById(id);
    }

    @Override
    public ImChatGroupRel detail(Serializable id) throws RuntimeException {
        return imChatGroupRelMapper.selectById(id);
    }

    @Override
    public int update(ReqImChatGroupRelDto reqImChatGroupRelDto) throws RuntimeException {
        ImChatGroupRel imChatGroupRel = imChatGroupRelMapper.selectById(reqImChatGroupRelDto.getId());
        Assert.notNull(imChatGroupRel, ApiCodeEnum.RECORD_NOT_EXISTS);
        return imChatGroupRelMapper.updateById(BeanMapperUtil.map(reqImChatGroupRelDto, ImChatGroupRel.class));
    }

    @Override
    public PageInfo<ImChatGroupRel> queryPage(ReqImChatGroupRelDto reqImChatGroupRelDto) {
        LambdaQueryWrapper<
            ImChatGroupRel> wrapper =
                Wrappers
                    .<ImChatGroupRel>lambdaQuery().eq(!Objects.isNull(reqImChatGroupRelDto.getGroupId()),
                        ImChatGroupRel::getGroupId, reqImChatGroupRelDto.getGroupId())
                    .orderByDesc(ImChatGroupRel::getId);
        return query(imChatGroupRelMapper, wrapper, reqImChatGroupRelDto);
    }

    @Override
    public List<ImChatGroupRel> query(ReqImChatGroupRelDto reqImChatGroupRelDto) {
        return imChatGroupRelMapper
            .selectList(
                Wrappers
                    .<ImChatGroupRel>lambdaQuery().eq(!Objects.isNull(reqImChatGroupRelDto.getGroupId()),
                        ImChatGroupRel::getGroupId, reqImChatGroupRelDto.getGroupId())
                    .orderByDesc(ImChatGroupRel::getId));
    }
}
