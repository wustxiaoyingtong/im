package com.jobs.im.rbac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jobs.im.core.model.ApiResult;
import com.jobs.im.core.valid.PageValid;
import com.jobs.im.core.valid.QueryValid;
import com.jobs.im.core.valid.SaveValid;
import com.jobs.im.core.valid.UpdateValid;
import com.jobs.im.model.dto.ReqSysUserDto;
import com.jobs.im.rbac.service.ISysUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 系统用户表(SpSysUser)表Controller类
 *
 * @author wust
 * @since 2024-02-23 14:00:45
 */
@RestController
@RequestMapping("/sys/user")
@Api(tags = "系统用户表")
public class SysUserController {
    @Autowired
    private ISysUserService sysUserService;

    /**
     * Description: 系统用户表-新增
     * 
     * @param reqDto
     * @return ApiResult
     * @throws
     * @author wust
     * @date 2024-02-23 14:00:45
     **/
    @ApiOperation(value = "系统用户表-新增")
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult add(@Validated(SaveValid.class) @RequestBody ReqSysUserDto reqDto) {
        sysUserService.add(reqDto);
        return ApiResult.success();
    }

    /**
     * Description: 系统用户表-删除
     * 
     * @param id
     * @return ApiResult
     * @throws
     * @author wust
     * @date 2024-02-23 14:00:45
     **/
    @ApiOperation(value = "系统用户表-删除")
    @PostMapping(value = "/delete/{id}")
    public ApiResult delete(@PathVariable("id") Integer id) {
        sysUserService.delete(id);
        return ApiResult.success();
    }

    /**
     * Description: 系统用户表-详情
     * 
     * @param id
     * @return ApiResult
     * @throws
     * @author CatEyes
     * @date 2023/4/13 15:39
     **/
    @ApiOperation(value = "系统用户表-详情")
    @PostMapping(value = "/detail/{id}")
    public ApiResult detail(@PathVariable("id") Integer id) {
        return ApiResult.success(sysUserService.detail(id));
    }

    /**
     * Description: 系统用户表-修改
     * 
     * @param reqDto
     * @return ApiResult
     * @throws
     * @author wust
     * @date 2024-02-23 14:00:45
     **/
    @ApiOperation(value = "系统用户表-修改")
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult update(@Validated(UpdateValid.class) @RequestBody ReqSysUserDto reqDto) {
        sysUserService.update(reqDto);
        return ApiResult.success();
    }

    /**
     * Description: 系统用户表-查询
     * 
     * @param reqDto
     * @return ApiResult
     * @throws
     * @author wust
     * @date 2024-02-23 14:00:45
     **/
    @ApiOperation(value = "系统用户表-查询")
    @PostMapping(value = "/query", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult query(@Validated({QueryValid.class, PageValid.class}) @RequestBody ReqSysUserDto reqDto) {
        return ApiResult.success(sysUserService.queryPage(reqDto));
    }

    /**
     * Description: 系统用户表-修改密码
     * 
     * @param reqDto
     * @return ApiResult
     * @throws
     * @author Author
     * @date 2024/3/13 18:10
     **/
    @ApiOperation(value = "系统用户表-修改密码")
    @PostMapping(value = "/changePwd", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult changePwd(@Validated(UpdateValid.class) @RequestBody ReqSysUserDto reqDto) {
        sysUserService.changePasswd(reqDto);
        return ApiResult.success();
    }

    /**
     * Description: 系统用户表-重置密码
     *
     * @param reqDto
     * @return ApiResult
     * @throws
     * @author Author
     * @date 2024/3/13 18:25
     **/
    @ApiOperation(value = "系统用户表-重置密码")
    @PostMapping(value = "/resetPwd", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult resetPwd(@Validated(UpdateValid.class) @RequestBody ReqSysUserDto reqDto) {
        sysUserService.resetPasswd(reqDto);
        return ApiResult.success();
    }
}
