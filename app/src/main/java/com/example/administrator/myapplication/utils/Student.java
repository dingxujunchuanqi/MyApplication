package com.example.administrator.myapplication.utils;

import java.util.List;

/**
 * Created by dingxujun on 2018/4/23.
 *
 * @project MyApplication
 */

public class Student {
    private String name;
    private int age;
    List<Animals> mlist;

    public List<Animals> getList() {
        return mlist;
    }

    public void setList(List<Animals> list) {
        this.mlist = list;
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
