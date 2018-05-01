package com.example.administrator.myapplication;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.myapplication.zipfile.DataReadManager;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dianji(View view) {
        /*********************************解压读取文件测试***********************************/
        String datazipFolder = FileUtils.gainSDPath(MainActivity.this, "nari.dms.sqlite");
        String datazipFile = datazipFolder + File.separator + "newData.zip";
        String targetFolder = FileUtils.gainSDPath(MainActivity.this, "nari.dms.sqliteCy");
        try {
            DataReadManager.unZipFiles(MainActivity.this, datazipFile, targetFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("----------------------");
        A.danli();
        /*********************************解压读取文件测试***********************************/


        /*********************************创建文件测试***********************************/
        // String apkPath = FileUtils.gainSystemPath(this, Environment.DIRECTORY_DOWNLOADS);
        //boolean GetFileBoolean = TraverseFolder.GetFileName(this, apkPath, "南瑞-测试版.apk");
        // main();
        /*********************************创建文件测试***********************************/
    }

    public void main() {
        String s = Environment.getExternalStorageDirectory().getPath() + "/aa";
        File file = new File(s);

        if (!file.exists()) {
            try {
                file.mkdirs();
                //file is create
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
