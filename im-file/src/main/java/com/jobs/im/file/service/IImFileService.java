package com.jobs.im.file.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.jobs.im.model.bean.ImFile;
import com.jobs.im.model.dto.ReqImFileDto;
import com.jobs.im.model.dto.RespImFileDto;
import com.jobs.im.model.service.BaseService;

/**
 * 文件信息表(ImFile)表Service接口
 *
 * @author wust
 * @since 2024-03-29 16:35:21
 */
public interface IImFileService extends BaseService<ImFile, ReqImFileDto> {
    ImFile queryOne(ReqImFileDto reqImFileDto);

    RespImFileDto upload(MultipartFile file) throws Exception;

    RespImFileDto signUpLogo(MultipartFile file) throws Exception;

    void pre(Long uid, HttpServletResponse response);
}
