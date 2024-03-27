package com.jobs.im.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jobs.im.chat.service.IImChatGroupRelService;
import com.jobs.im.core.model.ApiResult;
import com.jobs.im.core.valid.PageValid;
import com.jobs.im.core.valid.QueryValid;
import com.jobs.im.core.valid.SaveValid;
import com.jobs.im.core.valid.UpdateValid;
import com.jobs.im.model.dto.ReqImChatGroupRelDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 群聊-用户关联表(ImChatGroupRel)表Controller类
 *
 * @author wust
 * @since 2024-03-22 11:51:27
 */
@RestController
@RequestMapping("/chat/groupRel")
@Api(tags = "群聊-用户关联表")
public class ImChatGroupRelController {
    @Autowired
    private IImChatGroupRelService imChatGroupRelService;

    /**
     * Description: 群聊-用户关联表-新增
     * 
     * @param reqDto
     * @return ApiResult
     * @throws
     * @author wust
     * @date 2024-03-22 11:51:27
     **/
    @ApiOperation(value = "群聊-用户关联表-新增")
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult add(@Validated(SaveValid.class) @RequestBody ReqImChatGroupRelDto reqDto) {
        imChatGroupRelService.add(reqDto);
        return ApiResult.success();
    }

    /**
     * Description: 群聊-用户关联表-删除
     * 
     * @param id
     * @return ApiResult
     * @throws
     * @author wust
     * @date 2024-03-22 11:51:27
     **/
    @ApiOperation(value = "群聊-用户关联表-删除")
    @PostMapping(value = "/delete/{id}")
    public ApiResult delete(@PathVariable("id") Integer id) {
        imChatGroupRelService.delete(id);
        return ApiResult.success();
    }

    /**
     * Description: 群聊-用户关联表-详情
     * 
     * @param id
     * @return ApiResult
     * @throws
     * @author CatEyes
     * @date 2023/4/13 15:39
     **/
    @ApiOperation(value = "群聊-用户关联表-详情")
    @PostMapping(value = "/detail/{id}")
    public ApiResult detail(@PathVariable("id") Integer id) {
        return ApiResult.success(imChatGroupRelService.detail(id));
    }

    /**
     * Description: 群聊-用户关联表-修改
     * 
     * @param reqDto
     * @return ApiResult
     * @throws
     * @author wust
     * @date 2024-03-22 11:51:27
     **/
    @ApiOperation(value = "群聊-用户关联表-修改")
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult update(@Validated(UpdateValid.class) @RequestBody ReqImChatGroupRelDto reqDto) {
        imChatGroupRelService.update(reqDto);
        return ApiResult.success();
    }

    /**
     * Description: 群聊-用户关联表-查询
     * 
     * @param reqDto
     * @return ApiResult
     * @throws
     * @author wust
     * @date 2024-03-22 11:51:27
     **/
    @ApiOperation(value = "群聊-用户关联表-查询")
    @PostMapping(value = "/query", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult query(@Validated({QueryValid.class, PageValid.class}) @RequestBody ReqImChatGroupRelDto reqDto) {
        return ApiResult.success(imChatGroupRelService.queryPage(reqDto));
    }
}
