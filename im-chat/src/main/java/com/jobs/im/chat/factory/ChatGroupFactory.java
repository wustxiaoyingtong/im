package com.jobs.im.chat.factory;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.jobs.im.chat.service.impl.ImChatGroupRelServiceImpl;
import com.jobs.im.chat.service.impl.ImChatGroupServiceImpl;
import com.jobs.im.core.context.SpringContextHolder;
import com.jobs.im.core.utils.BeanMapperUtil;
import com.jobs.im.model.bean.ImChatGroup;
import com.jobs.im.model.bean.ImChatGroupRel;
import com.jobs.im.model.dto.ReqImChatGroupDto;
import com.jobs.im.model.dto.ReqImChatGroupRelDto;
import com.jobs.im.model.vo.ImChatGroupVo;

/**
 * @program: im
 * @ClassName: ChatGroupFactory
 * @description:
 * @author: Author
 * @create: 2024-03-22 15:21
 * @Version 1.0
 **/
public final class ChatGroupFactory {
    public static ImChatGroupVo getGroup(Long uid) {
        ImChatGroupServiceImpl imChatGroupService = SpringContextHolder.getBean("imChatGroupServiceImpl");
        ImChatGroup detail = Optional.ofNullable(imChatGroupService.query(ReqImChatGroupDto.builder().uid(uid).build()))
            .map(Collection::stream).map(s -> s.findFirst()).map(s -> s.orElse(null)).orElse(null);
        if (Objects.isNull(detail)) {
            return null;
        }
        ImChatGroupRelServiceImpl imChatGroupRelService = SpringContextHolder.getBean("imChatGroupRelServiceImpl");
        List<ImChatGroupRel> rels =
            imChatGroupRelService.query(ReqImChatGroupRelDto.builder().groupId(detail.getId()).build());
        ImChatGroupVo vo = BeanMapperUtil.map(detail, ImChatGroupVo.class);
        vo.setRels(rels);
        return vo;
    }
}
