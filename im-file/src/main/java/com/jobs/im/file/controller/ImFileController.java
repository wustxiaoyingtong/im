package com.jobs.im.file.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.jobs.im.core.model.ApiResult;
import com.jobs.im.core.valid.PageValid;
import com.jobs.im.core.valid.QueryValid;
import com.jobs.im.core.valid.SaveValid;
import com.jobs.im.core.valid.UpdateValid;
import com.jobs.im.file.service.IImFileService;
import com.jobs.im.model.dto.ReqImFileDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 文件信息表(ImFile)表Controller类
 *
 * @author wust
 * @since 2024-03-29 16:35:23
 */
@RestController
@RequestMapping("/file")
@Api(tags = "文件信息表")
public class ImFileController {
    @Autowired
    private IImFileService imFileService;

    /**
     * Description: 文件信息表-新增
     * 
     * @param reqDto
     * @return ApiResult
     * @throws
     * @author wust
     * @date 2024-03-29 16:35:23
     **/
    @ApiOperation(value = "文件信息表-新增")
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult add(@Validated(SaveValid.class) @RequestBody ReqImFileDto reqDto) {
        imFileService.add(reqDto);
        return ApiResult.success();
    }

    /**
     * Description: 文件信息表-删除
     * 
     * @param id
     * @return ApiResult
     * @throws
     * @author wust
     * @date 2024-03-29 16:35:23
     **/
    @ApiOperation(value = "文件信息表-删除")
    @PostMapping(value = "/delete/{id}")
    public ApiResult delete(@PathVariable("id") Integer id) {
        imFileService.delete(id);
        return ApiResult.success();
    }

    /**
     * Description: 文件信息表-详情
     * 
     * @param id
     * @return ApiResult
     * @throws
     * @author CatEyes
     * @date 2023/4/13 15:39
     **/
    @ApiOperation(value = "文件信息表-详情")
    @PostMapping(value = "/detail/{id}")
    public ApiResult detail(@PathVariable("id") Integer id) {
        return ApiResult.success(imFileService.detail(id));
    }

    /**
     * Description: 文件信息表-修改
     * 
     * @param reqDto
     * @return ApiResult
     * @throws
     * @author wust
     * @date 2024-03-29 16:35:23
     **/
    @ApiOperation(value = "文件信息表-修改")
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult update(@Validated(UpdateValid.class) @RequestBody ReqImFileDto reqDto) {
        imFileService.update(reqDto);
        return ApiResult.success();
    }

    /**
     * Description: 文件信息表-查询
     * 
     * @param reqDto
     * @return ApiResult
     * @throws
     * @author wust
     * @date 2024-03-29 16:35:23
     **/
    @ApiOperation(value = "文件信息表-查询")
    @PostMapping(value = "/query", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult query(@Validated({QueryValid.class, PageValid.class}) @RequestBody ReqImFileDto reqDto) {
        return ApiResult.success(imFileService.queryPage(reqDto));
    }

    /**
     * Description: 文件信息表-根据UID查询
     *
     * @param uid
     * @return ApiResult
     * @throws
     * @author Author
     * @date 2024/3/29 16:53
     **/
    @ApiOperation(value = "文件信息表-根据UID查询")
    @PostMapping(value = "/get/{uid}")
    public ApiResult detail(@PathVariable("uid") Long uid) {
        return ApiResult.success(imFileService.queryOne(ReqImFileDto.builder().uid(uid).build()));
    }

    /**
     * Description: 注册上传头像
     * 
     * @param file
     * @return ApiResult
     * @throws
     * @author Author
     * @date 2024/3/29 17:28
     **/
    @ApiOperation(value = "注册上传头像")
    @PostMapping(value = "/signUpLogo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult signUpLogo(@ApiParam(value = "头像文件", required = true) @RequestParam("file") MultipartFile file)
        throws Exception {
        return ApiResult.success(imFileService.signUpLogo(file));
    }

    /**
     * Description: 上传文件
     * 
     * @param file
     * @return ApiResult
     * @throws
     * @author Author
     * @date 2024/3/29 17:28
     **/
    @ApiOperation(value = "上传文件")
    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult upload(@ApiParam(value = "文件", required = true) @RequestParam("file") MultipartFile file)
        throws Exception {
        return ApiResult.success(imFileService.upload(file));
    }

    @ApiOperation(value = "文件预览")
    @GetMapping(value = "/pre/{uid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void pre(@PathVariable("uid") Long uid, HttpServletResponse response) {
        imFileService.pre(uid, response);
    }
}
