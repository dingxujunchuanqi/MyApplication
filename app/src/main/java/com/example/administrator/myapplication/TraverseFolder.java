package com.example.administrator.myapplication;

import android.content.Context;

import java.io.File;
import java.io.IOException;

/**
 * Created by dingxujun on 2017/9/30.
 */

public class TraverseFolder {
    /**
     * 遍历当前目录下所有的文件
     */
    public static boolean GetFileName(Context context, String fileAbsolutePath, String apkName) {
        //Vector<String> vecFile = new Vector<String>();
        File file = new File(fileAbsolutePath);
        File[] subFile = file.listFiles();
        if (subFile != null) {
            for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
                // 判断是否为文件夹
                if (!subFile[iFileLength].isDirectory()) {
                    String filename = subFile[iFileLength].getName();
                    System.out.println("------文件的大小---------" + subFile[iFileLength].length());
                    if (filename.equals(apkName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
