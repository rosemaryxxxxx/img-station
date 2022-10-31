package com.xhh.imgdemo.service;

import com.xhh.imgdemo.req.SysUserLoginReq;
import com.xhh.imgdemo.req.SysUserSaveReq;
import com.xhh.imgdemo.resp.SysUserLoginResp;

public interface SysUserService {
    void register(SysUserSaveReq req);

    SysUserLoginResp login(SysUserLoginReq req);
}
