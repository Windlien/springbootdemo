package com.example.springbootdemo.utils.my;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestArrayToList {
    public static void main(String[] args) {
        //方法一  Arrays.asList(array)，返回的List是具有固定长度的私有静态内部类  。不支持对list操作
        String[] array = {"111","222","333"};
        List<String> list = Arrays.asList(array);
        list.add("444");
        list.remove(0);

        //方法二  推荐
        String[] array2 = {"111","222","333"};
        List<String> list2 = new ArrayList<>(Arrays.asList(array2));
        list2.add("444");
        list2.remove(0);
        System.out.println(list2);
    }

}
