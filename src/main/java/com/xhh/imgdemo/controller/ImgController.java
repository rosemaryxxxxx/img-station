package com.xhh.imgdemo.controller;

import com.drew.imaging.ImageProcessingException;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.xhh.imgdemo.entity.ImgEntity;
import com.xhh.imgdemo.req.ImgSaveReq;
import com.xhh.imgdemo.req.SysUserSaveReq;
import com.xhh.imgdemo.resp.CommonResp;
import com.xhh.imgdemo.service.ImgService;
import com.xhh.imgdemo.utils.FastDfsUtil;
import com.xhh.imgdemo.utils.imgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private FastDfsUtil fastdfsUtils;

    @Value("${nignx.ip}")
    private String serverIP;

    @Value("${nignx.port}")
    private String nignxPort;

    @PostMapping("imgupload")
    @ResponseBody
    //使用List<MultipartFile>实现批量上传
    public CommonResp imgUpload(@RequestParam("file") MultipartFile[] file) throws ImageProcessingException, IOException {
        //循环遍历所有接收到的文件
        for(MultipartFile mfile : file){
            //将上传的图片再上传到FASTDFS图片服务器
            StorePath storePath = fastdfsUtils.upload(mfile);
            String fullPath = storePath.getFullPath();

            //将mutipartfile转换成file
            File f = imgUtils.MultipartFileToFile(mfile);
            ImgSaveReq req = new ImgSaveReq();
            //将上传图片的信息装入req中
            req.setId(null);
            req.setTime(imgUtils.getImgTime(f));
            req.setLocation(imgUtils.getImgLocation(f));
            req.setUrl(serverIP+":"+nignxPort+"/"+fullPath);
            imgService.imgUpload(req);
        }

        CommonResp resp = new CommonResp<>();
        return resp;

    }

    /**
     * 通过图片位置信息进行模糊查询
     * @param location
     * @return
     */
    @PostMapping("imgqurey")
    public List<ImgEntity> imgQueryByLocation(@RequestParam("location") String location){
        return imgService.testQuerymohu(location);
    }

    /**
     * 通过图片id进行删除操作,要同时删除MySQL数据库中和FASTDFS中的图片信息
     * @param id
     * @return
     */
    @PostMapping("imgdelete")
    public CommonResp imgDeleteById(@RequestParam("id") Long id){
        //删除FASTDFS服务器中的图片
        ImgEntity img = imgService.queryById(id);
        String url = img.getUrl().substring(img.getUrl().indexOf("/"));
        fastdfsUtils.delete(url);
        //删除mysql数据库中的图片信息
        imgService.imgDelete(id);
        CommonResp resp = new CommonResp<>();
        return resp;
    }





}
