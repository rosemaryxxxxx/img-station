package com.xhh.imgdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.xhh.imgdemo.entity.SysUserEntity;
import com.xhh.imgdemo.mapper.SysUserMapper;
import com.xhh.imgdemo.req.SysUserLoginReq;
import com.xhh.imgdemo.req.SysUserSaveReq;
import com.xhh.imgdemo.resp.SysUserLoginResp;
import com.xhh.imgdemo.service.SysUserService;
import com.xhh.imgdemo.utils.CopyUtil;
import com.xhh.imgdemo.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;


import javax.annotation.Resource;
import java.util.List;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Autowired
    private SnowFlake snowFlake;


    @Override
    public void register(SysUserSaveReq req) {
        SysUserEntity user = CopyUtil.copy(req, SysUserEntity.class);
        if(ObjectUtils.isEmpty(req.getId())){
            SysUserEntity userDb = selectByLoginName(req.getLoginName());
            if(ObjectUtils.isEmpty(userDb)){
                //通过雪花算法给用户设置唯一的id
                user.setId(snowFlake.nextId());
                sysUserMapper.insert(user);
            }
        }
    }

    @Override
    public SysUserLoginResp login(SysUserLoginReq req) {
        SysUserEntity userDb = selectByLoginName(req.getLoginName());
        if(ObjectUtils.isEmpty(userDb)){
            //用户不存在
            return null;
        }else {
            //登陆成功
            SysUserLoginResp userLoginResp = CopyUtil.copy(userDb, SysUserLoginResp.class);
            return userLoginResp;
        }
    }

    //查询loginName是否正确
    public SysUserEntity selectByLoginName(String loginName){
        QueryWrapper<SysUserEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUserEntity::getLoginName,loginName);
        List<SysUserEntity> userEntityList = sysUserMapper.selectList(wrapper);
        if(CollectionUtils.isEmpty(userEntityList)){
            return  null;
        }else {
            return userEntityList.get(0);
        }
    }
}

