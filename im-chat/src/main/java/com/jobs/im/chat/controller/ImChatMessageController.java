package com.jobs.im.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jobs.im.chat.service.IImChatMessageService;
import com.jobs.im.core.model.ApiResult;
import com.jobs.im.core.valid.PageValid;
import com.jobs.im.core.valid.QueryValid;
import com.jobs.im.core.valid.SaveValid;
import com.jobs.im.core.valid.UpdateValid;
import com.jobs.im.model.dto.ReqImChatMessageDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 聊天消息记录(ImChatMessage)表Controller类
 *
 * @author wust
 * @since 2024-04-12 14:50:15
 */
@RestController
@RequestMapping("/chat/message")
@Api(tags = "聊天消息记录")
public class ImChatMessageController {
    @Autowired
    private IImChatMessageService imChatMessageService;

    /**
     * Description: 聊天消息记录-新增
     * 
     * @param reqDto
     * @return ApiResult
     * @throws
     * @author wust
     * @date 2024-04-12 14:50:15
     **/
    @ApiOperation(value = "聊天消息记录-新增")
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult add(@Validated(SaveValid.class) @RequestBody ReqImChatMessageDto reqDto) {
        imChatMessageService.add(reqDto);
        return ApiResult.success();
    }

    /**
     * Description: 聊天消息记录-删除
     * 
     * @param id
     * @return ApiResult
     * @throws
     * @author wust
     * @date 2024-04-12 14:50:15
     **/
    @ApiOperation(value = "聊天消息记录-删除")
    @PostMapping(value = "/delete/{id}")
    public ApiResult delete(@PathVariable("id") Integer id) {
        imChatMessageService.delete(id);
        return ApiResult.success();
    }

    /**
     * Description: 聊天消息记录-详情
     * 
     * @param id
     * @return ApiResult
     * @throws
     * @author CatEyes
     * @date 2023/4/13 15:39
     **/
    @ApiOperation(value = "聊天消息记录-详情")
    @PostMapping(value = "/detail/{id}")
    public ApiResult detail(@PathVariable("id") Integer id) {
        return ApiResult.success(imChatMessageService.detail(id));
    }

    /**
     * Description: 聊天消息记录-修改
     * 
     * @param reqDto
     * @return ApiResult
     * @throws
     * @author wust
     * @date 2024-04-12 14:50:15
     **/
    @ApiOperation(value = "聊天消息记录-修改")
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult update(@Validated(UpdateValid.class) @RequestBody ReqImChatMessageDto reqDto) {
        imChatMessageService.update(reqDto);
        return ApiResult.success();
    }

    /**
     * Description: 聊天消息记录-查询
     * 
     * @param reqDto
     * @return ApiResult
     * @throws
     * @author wust
     * @date 2024-04-12 14:50:15
     **/
    @ApiOperation(value = "聊天消息记录-查询")
    @PostMapping(value = "/query", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult query(@Validated({QueryValid.class, PageValid.class}) @RequestBody ReqImChatMessageDto reqDto) {
        return ApiResult.success(imChatMessageService.queryPage(reqDto));
    }
}
