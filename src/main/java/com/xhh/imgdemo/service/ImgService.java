package com.xhh.imgdemo.service;

import com.xhh.imgdemo.entity.ImgEntity;
import com.xhh.imgdemo.req.ImgSaveReq;

import java.util.List;

public interface ImgService {
    void imgUpload(ImgSaveReq req);

    List<ImgEntity> testQuerymohu(String location);

    void imgDelete(Long id);
}
