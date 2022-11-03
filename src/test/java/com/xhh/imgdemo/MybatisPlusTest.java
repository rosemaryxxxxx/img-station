package com.xhh.imgdemo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xhh.imgdemo.entity.ImgEntity;
import com.xhh.imgdemo.mapper.ImgMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class MybatisPlusTest {
    @Resource
    private ImgMapper imgMapper;

    @Test
    public void testSelectList(){
        //通过条件构造器wapper查询一个list集合，若没有条件，则可以设置为null参数
        List<ImgEntity> list = imgMapper.selectList(null);
        list.forEach(System.out::println);
    }

    @Test
    public void testQuerymohu(){
        // SELECT id,location,time,url FROM img WHERE location LIKE ? 模糊查询测试
        QueryWrapper<ImgEntity> wapper = new QueryWrapper<>();
        wapper.like("location","重庆");
        imgMapper.selectList(wapper);

    }
}
