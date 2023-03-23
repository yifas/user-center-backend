package com.yupi.usercenter.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;

/**
 * Description:
 *
 * @Author Fitz
 * @Create 2023/3/7 21:06
 */
@SpringBootTest
public class PositionSelectTest {


    @Test
    void testPositionSelect() {
        // 省市区三级联动
        // province     city    area

        List list = new ArrayList<>();
        list.add("浙江");
        list.add("江西");
        list.add("江苏");
        System.out.println(list);

        Map<String, List> provinceMap = new HashMap<>();
        List cityList = new ArrayList<>();
        Map<String, List> areaMap = new HashMap<>();
        List areaList = new ArrayList<>();
        areaList.add("桐乡");
        areaList.add("海宁");
        areaMap.put("嘉兴市", areaList);
        cityList.add(areaMap);
        provinceMap.put("浙江", cityList);

        System.out.println(provinceMap);
    }

    @Test
    void testPositionSelect2() {
        // 省市区三级联动
        // province     city    area
        File file = new File("C:\\Users\\91459\\Desktop\\pca.json");
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[10];
            fileInputStream.read(buffer);
            System.out.println(Arrays.toString(buffer));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    void testLambda() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("你好lambda");
            }
        });
        new Thread(() -> {
            System.out.println("你好lambda2");
        }).start();





        test11(new IntBinaryOperator() {
            @Override
            public int applyAsInt(int left, int right) {
                return left + right;
            }
        });
        int i = test11((left, right) -> {
            return left + right;
        });
        System.out.println("i = " + i);
        int j = test11((left, right) -> left + right);






        printNum(new IntPredicate() {
            @Override
            public boolean test(int value) {
                return value%2==0;
            };
        });
        printNum((value)-> {
          return   value%2==0;
        });

        //外层方法printNum()  参数是接口   外层方法中调用了接口中的某个实现类   就可以用lambda
        printNum((value) -> value%2==0);
        printNum(value -> value%2==0);





        typeConver(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.parseInt(s);
            }
        });
        typeConver((s) -> Integer.parseInt(s));
        typeConver(Integer::parseInt);





    }

    @Test
    void testlambda2(){
        foreachArr((val) -> System.out.println(val>5));
        foreachArr(val -> System.out.println(val>5));

    }

    static int test11(IntBinaryOperator intBinaryOperator) {
        int a = 10;
        int b = 20;
        return intBinaryOperator.applyAsInt(a, b);
    }


    static void printNum(IntPredicate predicate) {
        int[] arr={1,2,3,4,5,6,7,8,9,10};
        for (int i : arr) {
            if (predicate.test(i)){
                System.out.println(i);
            }
        }
    }

    static <R> R typeConver(Function<String,R> function) {
        String str = "1231";
        R apply = function.apply(str);

        return apply;
    }


    static void foreachArr(IntConsumer consumer) {
        int[] arr={1,2,3,4,5,6,7,8,9,10};
        for (int i : arr) {
           consumer.accept(i);
        }
    }
}
