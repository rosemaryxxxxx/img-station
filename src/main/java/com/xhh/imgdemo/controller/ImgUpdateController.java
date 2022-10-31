package com.xhh.imgdemo.controller;

import com.xhh.imgdemo.resp.CommonResp;
import com.xhh.imgdemo.utils.imgUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Console;
import java.io.File;
import java.io.IOException;

@RestController
public class ImgUpdateController {

    @RequestMapping(value = "/imgupdate",method = RequestMethod.POST)
//    @PostMapping(path = "/imgupdate")
    @ResponseBody
    public String ImgUpdate(@RequestParam("file") MultipartFile file) throws Exception {
        if (null == file) {
            System.err.println("上传失败，无法找到文件！");
        }
        System.out.println(file.getOriginalFilename());
        String s = imgUtils.printImageTags(imgUtils.MultipartFileToFile(file));
        //return imgUtils.printImageTags(imgUtils.mutipartFileToFile(multipartFile));
        System.out.println(s);
        return s;
    }


}
