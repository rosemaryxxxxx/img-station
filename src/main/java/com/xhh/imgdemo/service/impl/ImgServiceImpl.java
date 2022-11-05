package com.xhh.imgdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhh.imgdemo.entity.ImgEntity;
import com.xhh.imgdemo.mapper.ImgMapper;
import com.xhh.imgdemo.req.ImgSaveReq;
import com.xhh.imgdemo.service.ImgService;
import com.xhh.imgdemo.utils.CopyUtil;
import com.xhh.imgdemo.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public List<ImgEntity> testQuerymohu(String location){
        // SELECT id,location,time,url FROM img WHERE location LIKE ? 模糊查询测试
        QueryWrapper<ImgEntity> wapper = new QueryWrapper<>();
        wapper.like("location",location);
        return  imgMapper.selectList(wapper);
    }

    @Override
    public void imgDelete(Long id) {
        imgMapper.deleteById(id);
    }
    @Override
    public ImgEntity queryById(Long id){
        return imgMapper.selectById(id);
    }


}
