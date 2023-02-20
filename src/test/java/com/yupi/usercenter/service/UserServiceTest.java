package com.yupi.usercenter.service;

import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.request.UserRegisterRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 用户服务测试
 *
 * @author yupi
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    /**
     * 百万数据插入————mybatisplus之批处理
     */
    @Test
    void testMybatisPlusBatch() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int INSERT_NUM = 10000;
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < INSERT_NUM; i++) {
            User user = new User();
            user.setUsername("yupi");
            user.setUserAccount("yupi" + i);
            user.setGender(0);
            user.setUserPassword("b0dd3697a192885d7c055db46155b26a");
            user.setPhone("18812345678");
            user.setEmail("abc@qq.com");
            userList.add(user);
        }
        userService.saveBatch(userList, 1000);

        stopWatch.stop();
        System.out.println(stopWatch.getLastTaskTimeMillis());


    }

    /**
     * 百万数据插入————多线程加批处理插入
     */
    @Test
    void testThroadInsert() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int INSERT_NUM = 10000;
        int j = 0;

        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        //线程安全的集合
//        List<CompletableFuture<Void>> futureList = Collections.synchronizedList(new ArrayList<>());
//        List list = new CopyOnWriteArrayList();
            for (int i = 0; i < 10; i++) {
            List<User> userList = new ArrayList<>();
            while (true) {
                j++;
                User user = new User();
                user.setUsername("yupi");
                user.setUserAccount("yupi" + i);
                user.setGender(0);
                user.setUserPassword("b0dd3697a192885d7c055db46155b26a");
                user.setPhone("18812345678");
                user.setEmail("abc@qq.com");
                userList.add(user);
                if (j%10000 ==0){
                    break;
                }
            }
            //异步任务
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                userService.saveBatch(userList, 10000);
            });
            futureList.add(future);
        }

        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();

        stopWatch.stop();
        System.out.println(stopWatch.getLastTaskTimeMillis());


    }

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUsername("dogYupi");
        user.setUserAccount("123");
        user.setAvatarUrl("https://636f-codenav-8grj8px727565176-1256524210.tcb.qcloud.la/img/logo.png");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("123");
        user.setEmail("456");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("dogYupi");
        user.setUserAccount("123");
        user.setAvatarUrl("https://636f-codenav-8grj8px727565176-1256524210.tcb.qcloud.la/img/logo.png");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("123");
        user.setEmail("456");
        boolean result = userService.updateById(user);
        Assertions.assertTrue(result);
    }

    @Test
    public void testDeleteUser() {
        boolean result = userService.removeById(1L);
        Assertions.assertTrue(result);
    }

    @Test
    public void testGetUser() {
        User user = userService.getById(1L);
        Assertions.assertNotNull(user);
    }

    @Test
    void userRegister() {
        String userAccount = "yupi";
        String userPassword = "";
        String checkPassword = "123456";
        String planetCode = "1";
        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "yu";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "yupi";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "yu pi";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        checkPassword = "123456789";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "dogYupi";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "yupi";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
    }

    @Test
    void testUserRegister() {
        UserRegisterRequest register = new UserRegisterRequest();
        register.setUserAccount("dogbin");
        register.setUserPassword("12345678");
        register.setCheckPassword("12345678");
        register.setPlanetCode("10000");

        userService.userRegister(register.getUserAccount(), register.getUserPassword(),
                register.getCheckPassword(), register.getPlanetCode());
    }
}
