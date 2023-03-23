package com.yupi.usercenter.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Description: 测试多线程
 *
 * @Author Fitz
 * @Create 2023/3/23 18:27
 */
@SpringBootTest
public class ThreadTest {

    /**
     * 同步代码块
     */
    @Test
    void test01() {

        for (int i = 0; i < 10; i++) {
            new Thread(() -> func1())
                    .start();
        }


    }

    void func1() {
        //未使用代码块 交替执行
       /* for (int i = 0; i < 100; i++) {
            System.out.println("i = " + i);
        }*/
        //使用了代码块 顺序执行
        synchronized (this) {
            for (int i = 0; i < 100; i++) {
                System.out.println("i = " + i);
            }
        }

    }
}
