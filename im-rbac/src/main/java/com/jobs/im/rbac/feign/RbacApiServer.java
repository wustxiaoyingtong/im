package com.jobs.im.rbac.feign;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobs.im.core.utils.BeanMapperUtil;
import com.jobs.im.feign.client.RbacApiClient;
import com.jobs.im.feign.dto.ReqAuthenticationInfo;
import com.jobs.im.feign.dto.ReqSysUserFgDto;
import com.jobs.im.feign.dto.RspAuthenticationInfo;
import com.jobs.im.feign.dto.RspSysUserFgDto;
import com.jobs.im.model.dto.ReqSysUserDto;
import com.jobs.im.rbac.service.ISysUserService;

/**
 * @program: im
 * @ClassName: RbacApiServer
 * @description:
 * @author: Author
 * @create: 2024-02-24 09:46
 * @Version 1.0
 **/
@RestController
@RequestMapping("/feign")
public class RbacApiServer implements RbacApiClient {
    @Autowired
    private ISysUserService sysUserService;

    @Override
    @PostMapping(value = "/getAuthenticationInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public RspAuthenticationInfo
        getAuthenticationInfo(@Valid @RequestBody ReqAuthenticationInfo reqAuthenticationInfo) {
        return sysUserService.getAuthenticationInfo(reqAuthenticationInfo);
    }

    @Override
    @PostMapping(value = "/getUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public RspSysUserFgDto getUser(ReqSysUserFgDto req) {
        return BeanMapperUtil.map(sysUserService.getUser(BeanMapperUtil.map(req, ReqSysUserDto.class)),
            RspSysUserFgDto.class);
    }
}
