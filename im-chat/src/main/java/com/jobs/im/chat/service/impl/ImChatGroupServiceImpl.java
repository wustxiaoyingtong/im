package com.jobs.im.chat.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jobs.im.chat.dao.ImChatGroupMapper;
import com.jobs.im.chat.dao.ImChatGroupRelMapper;
import com.jobs.im.chat.service.IImChatGroupService;
import com.jobs.im.core.context.SysUserContextHolder;
import com.jobs.im.core.enu.ApiCodeEnum;
import com.jobs.im.core.model.PageInfo;
import com.jobs.im.core.snowflake.SnowFlake;
import com.jobs.im.core.utils.Assert;
import com.jobs.im.core.utils.BeanMapperUtil;
import com.jobs.im.model.bean.ImChatGroup;
import com.jobs.im.model.bean.ImChatGroupRel;
import com.jobs.im.model.dto.ReqImChatGroupDto;
import com.jobs.im.model.enu.ImChatGroupRole;

/**
 * 群聊信息表(ImChatGroup)表Service实现类
 *
 * @author wust
 * @since 2024-03-14 16:30:54
 */
@Service
public class ImChatGroupServiceImpl extends BaseServiceImpl implements IImChatGroupService {
    @Resource
    private ImChatGroupMapper imChatGroupMapper;

    @Resource
    private ImChatGroupRelMapper imChatGroupRelMapper;

    @Override
    public int add(ReqImChatGroupDto reqImChatGroupDto) throws RuntimeException {
        List<ImChatGroup> list = imChatGroupMapper
            .selectList(Wrappers.<ImChatGroup>lambdaQuery().eq(ImChatGroup::getName, reqImChatGroupDto.getName()));
        Assert.isTrue(CollectionUtils.isEmpty(list), ApiCodeEnum.CHAT_GROUP_NAME_DUPLICATED);
        reqImChatGroupDto.setUid(SnowFlake.next());
        ImChatGroup imChatGroup = BeanMapperUtil.map(reqImChatGroupDto, ImChatGroup.class);
        int insert = imChatGroupMapper.insert(imChatGroup);
        if (insert > 0) {
            imChatGroupRelMapper.insert(ImChatGroupRel.builder().groupId(imChatGroup.getId())
                .userId(SysUserContextHolder.getUserId()).role(ImChatGroupRole.ADMIN.type).build());
        }
        return insert;
    }

    @Override
    public int delete(Serializable id) throws RuntimeException {
        ImChatGroup imChatGroup = imChatGroupMapper.selectById(id);
        Assert.notNull(imChatGroup, ApiCodeEnum.RECORD_NOT_EXISTS);
        return imChatGroupMapper.deleteById(id);
    }

    @Override
    public ImChatGroup detail(Serializable id) throws RuntimeException {
        return imChatGroupMapper.selectById(id);
    }

    @Override
    public int update(ReqImChatGroupDto reqImChatGroupDto) throws RuntimeException {
        ImChatGroup imChatGroup = imChatGroupMapper.selectById(reqImChatGroupDto.getId());
        Assert.notNull(imChatGroup, ApiCodeEnum.RECORD_NOT_EXISTS);
        return imChatGroupMapper.updateById(BeanMapperUtil.map(reqImChatGroupDto, ImChatGroup.class));
    }

    @Override
    public PageInfo<ImChatGroup> queryPage(ReqImChatGroupDto reqImChatGroupDto) {
        LambdaQueryWrapper<ImChatGroup> wrapper = Wrappers.<ImChatGroup>lambdaQuery()
            .eq(ImChatGroup::getName, reqImChatGroupDto.getName()).orderByDesc(ImChatGroup::getCreateAt);
        return query(imChatGroupMapper, wrapper, reqImChatGroupDto);
    }

    @Override
    public List<ImChatGroup> query(ReqImChatGroupDto reqImChatGroupDto) {
        return imChatGroupMapper.selectList(Wrappers.<ImChatGroup>lambdaQuery()
            .eq(StringUtils.isNotBlank(reqImChatGroupDto.getUid()), ImChatGroup::getUid, reqImChatGroupDto.getUid()));
    }
}
