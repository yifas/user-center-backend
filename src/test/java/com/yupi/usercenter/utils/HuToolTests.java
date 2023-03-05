package com.yupi.usercenter.utils;
import java.util.ArrayList;
import java.util.Date;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import com.yupi.usercenter.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class HuToolTests {

    @Test
    void testCovertPic(){
        String path = "c:/Users/mechrev/Desktop/wechat.png";
//        String path = "\\C:\\Users\\mechrev\\Desktop\\wechat.png";
        File f1 = FileUtil.file(path);
        File f2 = FileUtil.file("C:\\Users\\mechrev\\Desktop\\wechat2.jpg");
        ImgUtil.convert(f1,f2);
    }
    @Test
    void testDate(){
        System.err.println(LocalDate.now());
        System.err.println(LocalTime.now());
        System.err.println(LocalDateTime.now());
    }

    @Test
    void testInteger(){
        Integer i = 6;
        Integer j = 6;
//        System.out.println(i==j);
        String s = String.valueOf(new Random().nextInt(899999) + 100000);
        System.err.println(s);
    }
}
