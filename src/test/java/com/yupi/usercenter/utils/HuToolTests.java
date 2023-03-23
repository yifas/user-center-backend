package com.yupi.usercenter.utils;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Test
    void testFor() {
        boolean flag = false;
        for (int i = 0; i <= 3; i++) {
            if (i == 0) {
                System.out.println("0");
            } else if (i == 1) {
                System.out.println("1");
                continue;
            } else if (i == 2) {
                System.out.println("2");
                flag = true;
            } else if (i == 3) {
                System.out.println("3");
                break;
            } else if (i == 4) {
                System.out.println("4");
            }
            System.out.println("xixi");
        }
        if (flag) {
            System.out.println("haha");
            return;
        }
        System.out.println("heihei");
    }

    @Test
    void testMap() {
        Map map = new HashMap();

    }

    @Test
    void testReflect() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> aClass = Class.forName("com.yupi.usercenter.entity.TargetObject");
        TargetObject o = (TargetObject) aClass.newInstance();

        //获取方法

    }

    @Test
    void testSort() {
        Set set = new TreeSet();
        set.add("red");
        set.add("8");
        set.add("pink");
        set.add("5");
        set.add("black");
        set.add("晨");
        set.add("write");
        set.add("张");
        set.add("7");
        set.add("purple");
        set.add("哗哗");

        System.out.println(set);
    }

    @Test
    void testSortHash() {
        Set set = new HashSet();
        set.add("red");
        set.add("8");
        set.add("pink");
        set.add("5");
        set.add("black");
        set.add("晨");
        set.add("write");
        set.add("张");
        set.add("7");
        set.add("purple");
        set.add("哗哗");

        System.out.println(set);
    }
}
