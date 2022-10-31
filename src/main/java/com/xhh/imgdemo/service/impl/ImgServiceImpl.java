package com.xhh.imgdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhh.imgdemo.entity.ImgEntity;
import com.xhh.imgdemo.entity.SysUserEntity;
import com.xhh.imgdemo.mapper.ImgMapper;
import com.xhh.imgdemo.mapper.SysUserMapper;
import com.xhh.imgdemo.req.ImgSaveReq;
import com.xhh.imgdemo.service.ImgService;
import com.xhh.imgdemo.utils.CopyUtil;
import com.xhh.imgdemo.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

@Service
public class ImgServiceImpl extends ServiceImpl<ImgMapper, ImgEntity> implements ImgService {

    @Resource
    private ImgMapper imgMapper;

    @Autowired
    private SnowFlake snowFlake;

    @Override
    public void imgUpload(ImgSaveReq req) {
        ImgEntity img = CopyUtil.copy(req, ImgEntity.class);
        if(ObjectUtils.isEmpty(req.getId())){
            img.setId(snowFlake.nextId());
            imgMapper.insert(img);
        }
    }
}
