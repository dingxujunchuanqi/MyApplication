package com.example.nicespinner.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;

import com.example.nicespinner.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * ViewStub 延时加载控件
 * Created by dingxujun on 2018/1/10.
 *
 * @project MyApplication
 */

public class ViewStubActivity extends AppCompatActivity {
    private ViewStub pic_sub;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youhua);
        pic_sub = (ViewStub) findViewById(R.id.pic_stub);
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        }, 1000);// 延迟1秒,然后加载
    }

     Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            View pic_view = pic_sub.inflate();// ①
            //pic_sub.setVisibility(View.VISIBLE);// ②
           // ImageView iv_pic = (ImageView) pic_view.findViewById(R.id.iv_pic);
           // iv_pic.setImageResource(R.mipmap.ic_launcher);
            View view = findViewById(R.id.pic_stub);//③
            view = findViewById(R.id.pic_view_id_after_inflate);//④
        };
    };
}
