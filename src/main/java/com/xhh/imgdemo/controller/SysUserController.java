package com.xhh.imgdemo.controller;

import com.xhh.imgdemo.req.SysUserLoginReq;
import com.xhh.imgdemo.req.SysUserSaveReq;
import com.xhh.imgdemo.resp.CommonResp;
import com.xhh.imgdemo.resp.SysUserLoginResp;
import com.xhh.imgdemo.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sys-user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("register")
    //RequestBody注解可以把前端数据转成json格式送给后端
    public CommonResp register(@RequestBody SysUserSaveReq req){
        //加密password
        System.out.println(req.getPassword()+"\n"+req.getId()+"\n"+req.getName()+"\n"+req.getLoginName());
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        System.out.println(req.getPassword());

        CommonResp resp = new CommonResp<>();
        sysUserService.register(req);
        return resp;


    }

    @PostMapping("login")
    public CommonResp login(@RequestBody SysUserLoginReq req){
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp resp = new CommonResp<>();
        System.out.println(req.getLoginName()+" "+req.getPassword());
        SysUserLoginResp userLoginResp = sysUserService.login(req);
        resp.setContent(userLoginResp);
        return resp;
    }

}
