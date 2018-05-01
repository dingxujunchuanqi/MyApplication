package com.example.administrator.myapplication.utils;

/**
 * Created by dingxujun on 2018/4/23.
 *
 * @project MyApplication
 */

 public  class Animals {
    private String name;
    private int age;

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

    public Animals(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
