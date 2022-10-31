package com.xhh.imgdemo.controller;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.xhh.imgdemo.utils.imgUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;



//import static java.awt.Container.log;

@RestController
public class HelloController {

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    @ResponseBody
    public String Hello() throws Exception {

        String s = imgUtils.printImageTags(new File("C:\\Users\\xie\\Desktop\\as.jpeg"));
        return s;
    }

}
