package com.xhh.imgdemo.controller;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.xhh.imgdemo.utils.FastDfsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/fastdfsUtils")
public class FdfsController {

        @Autowired
        private FastDfsUtil fastdfsUtils;


        @PostMapping("/upload")
        public String upload(@RequestParam("file") MultipartFile file) throws IOException {
            StorePath storePath = fastdfsUtils.upload(file);
            String fullPath = storePath.getFullPath();
            System.out.println("fullPath = " + fullPath);
            return fullPath;
        }

        @DeleteMapping("/delete")
        public void delete(String fileUrl) {
            fastdfsUtils.delete(fileUrl);
        }

        @GetMapping("/download")
        public void downloadFile(String fileUrl, HttpServletResponse response)
                throws IOException {
            fastdfsUtils.download(fileUrl, null, response);
        }
    }


