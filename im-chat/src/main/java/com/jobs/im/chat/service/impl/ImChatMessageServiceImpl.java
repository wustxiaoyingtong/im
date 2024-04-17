package com.jobs.im.chat.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jobs.im.chat.dao.ImChatMessageMapper;
import com.jobs.im.chat.service.IImChatMessageService;
import com.jobs.im.core.model.PageInfo;
import com.jobs.im.core.utils.BeanMapperUtil;
import com.jobs.im.model.bean.ImChatMessage;
import com.jobs.im.model.dto.ReqImChatMessageDto;

/**
 * 聊天消息记录(ImChatMessage)表Service实现类
 *
 * @author wust
 * @since 2024-04-12 14:50:15
 */
@Service
public class ImChatMessageServiceImpl extends BaseServiceImpl implements IImChatMessageService {
    @Resource
    private ImChatMessageMapper imChatMessageMapper;

    @Override
    public int add(ReqImChatMessageDto reqImChatMessageDto) throws RuntimeException {
        List<ImChatMessage> list = imChatMessageMapper
            .selectList(new LambdaQueryWrapper<ImChatMessage>().eq(ImChatMessage::getId, reqImChatMessageDto.getId()));
        Assert.isTrue(CollectionUtils.isEmpty(list), "Id重复");
        return imChatMessageMapper.insert(BeanMapperUtil.map(reqImChatMessageDto, ImChatMessage.class));
    }

    @Override
    public int delete(Serializable id) throws RuntimeException {
        ImChatMessage imChatMessage = imChatMessageMapper.selectById(id);
        Assert.notNull(imChatMessage, "当前记录不存在");
        return imChatMessageMapper.deleteById(id);
    }

    @Override
    public ImChatMessage detail(Serializable id) throws RuntimeException {
        return imChatMessageMapper.selectById(id);
    }

    @Override
    public int update(ReqImChatMessageDto reqImChatMessageDto) throws RuntimeException {
        ImChatMessage imChatMessage = imChatMessageMapper.selectById(reqImChatMessageDto.getId());
        Assert.notNull(imChatMessage, "当前记录不存在");
        return imChatMessageMapper.updateById(BeanMapperUtil.map(reqImChatMessageDto, ImChatMessage.class));
    }

    @Override
    public PageInfo<ImChatMessage> queryPage(ReqImChatMessageDto reqImChatMessageDto) {
        LambdaQueryWrapper<ImChatMessage> wrapper = Wrappers.<ImChatMessage>lambdaQuery()
            .eq(ImChatMessage::getId, reqImChatMessageDto.getId()).orderByDesc(ImChatMessage::getId);
        return query(imChatMessageMapper, wrapper, reqImChatMessageDto);
    }

    @Override
    public List<ImChatMessage> query(ReqImChatMessageDto reqImChatMessageDto) {
        return null;
    }
}
