package com.yupi.usercenter.utils;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.io.File;

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
}
