package com.yupi.usercenter.entity;

/**
 * Description:
 *
 * @Author Fitz
 * @Create 2023/3/14 12:06
 */
public class TargetObject {
    private String value;

    public TargetObject() {
        value = "JavaGuide";
    }

    public void publicMethod(String s) {
        System.out.println("I love " + s);
    }

    private void privateMethod() {
        System.out.println("value is " + value);
    }
}
