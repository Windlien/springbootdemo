package com.example.springbootdemo.controller;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */
@Controller
public class FanXing {
    public static <T> void display(T t){
       System.out.println(t.getClass());
    }
    public static <T>  List display1(T t){
        List<T> list = new ArrayList<T>();
        list.add(0, (T) "apple");
        list.add(1, (T) "pier");
        return list;
    }
    public static void main(String[] args){
        display(111);
        List<String> list =  display1("1");
        for (int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
        for(String l:list){
            System.out.println(l.toString());
        }
    }
}
