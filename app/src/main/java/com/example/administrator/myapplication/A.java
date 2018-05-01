package com.example.administrator.myapplication;

/**
 *
 */

public class A {
   private static A a =null;

    public static void danli(){

        if (a==null){
            a=new A();
            System.out.println("------a==null----");
        }else {

        }

       // getResult(); 调用本地方法
    }
/**
 * 使用so库
*@date 创建时间 2018/3/5
*@author dingxujun
*/
    static {
        System.loadLibrary("libnative-lib");
    }
 //   private static native String getResult();  so里面用c语言写好的本地方法
}
