package com.xhh.imgdemo.controller;

import com.drew.imaging.ImageProcessingException;
import com.xhh.imgdemo.req.ImgSaveReq;
import com.xhh.imgdemo.req.SysUserSaveReq;
import com.xhh.imgdemo.resp.CommonResp;
import com.xhh.imgdemo.service.ImgService;
import com.xhh.imgdemo.utils.imgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.xhh.imgdemo.utils.imgUtils.getImgLocation;

@RestController
@RequestMapping("/img")
public class ImgController {
    @Autowired
    private ImgService imgService;

    @PostMapping("imgupload")
    @ResponseBody
    //使用List<MultipartFile>实现批量上传
    public CommonResp imgUpload(@RequestParam("file") MultipartFile[] file) throws ImageProcessingException, IOException {

        for(MultipartFile mfile : file){
            //将mutipartfile转换成file
            File f = imgUtils.MultipartFileToFile(mfile);
            ImgSaveReq req = new ImgSaveReq();
            //将上传图片的信息装入req中
            req.setId(null);
            req.setTime(imgUtils.getImgTime(f));
            req.setLocation(imgUtils.getImgLocation(f));
            req.setUrl(imgUtils.getImgUrl(f) + mfile.getOriginalFilename());
            imgService.imgUpload(req);
        }

        CommonResp resp = new CommonResp<>();
        return resp;

    }
}
