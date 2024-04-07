package com.jobs.im.file.service.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jobs.im.core.enu.ApiCodeEnum;
import com.jobs.im.core.exception.ServerException;
import com.jobs.im.core.model.PageInfo;
import com.jobs.im.core.snowflake.SnowFlake;
import com.jobs.im.core.utils.Assert;
import com.jobs.im.core.utils.BeanMapperUtil;
import com.jobs.im.file.dao.ImFileMapper;
import com.jobs.im.file.service.IImFileService;
import com.jobs.im.model.bean.ImFile;
import com.jobs.im.model.dto.ReqImFileDto;
import com.jobs.im.model.dto.RespImFileDto;

import io.minio.MinioClient;

/**
 * 文件信息表(ImFile)表Service实现类
 *
 * @author wust
 * @since 2024-03-29 16:35:22
 */
@Service
public class ImFileServiceImpl extends BaseServiceImpl implements IImFileService {
    @Resource
    private ImFileMapper imFileMapper;

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucketName:im}")
    private String bucketName;

    @Override
    public int add(ReqImFileDto reqImFileDto) throws RuntimeException {
        return imFileMapper.insert(BeanMapperUtil.map(reqImFileDto, ImFile.class));
    }

    @Override
    public int delete(Serializable id) throws RuntimeException {
        ImFile imFile = imFileMapper.selectById(id);
        Assert.notNull(imFile, ApiCodeEnum.RECORD_NOT_EXISTS);
        return imFileMapper.deleteById(id);
    }

    @Override
    public ImFile detail(Serializable id) throws RuntimeException {
        return imFileMapper.selectById(id);
    }

    @Override
    public int update(ReqImFileDto reqImFileDto) throws RuntimeException {
        ImFile imFile = imFileMapper.selectById(reqImFileDto.getId());
        Assert.notNull(imFile, ApiCodeEnum.RECORD_NOT_EXISTS);
        return imFileMapper.updateById(BeanMapperUtil.map(reqImFileDto, ImFile.class));
    }

    @Override
    public PageInfo<ImFile> queryPage(ReqImFileDto reqImFileDto) {
        LambdaQueryWrapper<ImFile> wrapper = Wrappers.<ImFile>lambdaQuery()
            .eq(ImFile::getCreateBy, reqImFileDto.getCreateBy()).orderByDesc(ImFile::getCreateAt);
        return query(imFileMapper, wrapper, reqImFileDto);
    }

    @Override
    public List<ImFile> query(ReqImFileDto reqImFileDto) {
        return imFileMapper.selectList(Wrappers.<ImFile>lambdaQuery()
            .eq(!Objects.isNull(reqImFileDto.getCreateBy()), ImFile::getCreateBy, reqImFileDto.getCreateBy())
            .eq(!Objects.isNull(reqImFileDto.getUid()), ImFile::getUid, reqImFileDto.getUid())
            .eq(!Objects.isNull(reqImFileDto.getId()), ImFile::getId, reqImFileDto.getId())
            .eq(StringUtils.isNotBlank(reqImFileDto.getName()), ImFile::getName, reqImFileDto.getName())
            .orderByDesc(ImFile::getCreateAt));
    }

    @Override
    public ImFile queryOne(ReqImFileDto reqImFileDto) {
        List<ImFile> imFiles = imFileMapper.selectList(Wrappers.<ImFile>lambdaQuery()
            .eq(!Objects.isNull(reqImFileDto.getCreateBy()), ImFile::getCreateBy, reqImFileDto.getCreateBy())
            .eq(!Objects.isNull(reqImFileDto.getUid()), ImFile::getUid, reqImFileDto.getUid())
            .eq(!Objects.isNull(reqImFileDto.getId()), ImFile::getId, reqImFileDto.getId())
            .eq(StringUtils.isNotBlank(reqImFileDto.getName()), ImFile::getName, reqImFileDto.getName())
            .orderByDesc(ImFile::getCreateAt));
        return Optional.ofNullable(imFiles).map(Collection::stream).map(s -> s.findAny()).map(s -> s.orElse(null))
            .orElse(null);
    }

    @Override
    public RespImFileDto upload(MultipartFile file) throws Exception {
        String original = file.getOriginalFilename();
        if (StringUtils.isNotBlank(original) && original.length() > 64) {
            throw new ServerException(ApiCodeEnum.FILE_ORIGINAL_NAME_TOO_LONG);
        }
        String suffix = original.substring(original.lastIndexOf("."));
        long id = SnowFlake.next();
        String objectName = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "/" + id + suffix;
        minioClient.putObject(bucketName, objectName, file.getInputStream(), file.getContentType());
        String fileName = id + suffix;
        ReqImFileDto fileDto = ReqImFileDto.builder().uid(id).originalName(original).name(fileName).build();
        ImFile addFile = BeanMapperUtil.map(fileDto, ImFile.class);
        imFileMapper.insert(addFile);
        return BeanMapperUtil.map(addFile, RespImFileDto.class);
    }

    @Override
    public RespImFileDto signUpLogo(MultipartFile file) throws Exception {
        return upload(file);
    }
}