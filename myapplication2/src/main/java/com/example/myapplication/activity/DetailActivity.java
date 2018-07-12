package com.example.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * Created by dingxujun on 2018/7/12.
 *
 * @project MyApplication
 */

public class DetailActivity extends AppCompatActivity {
    private TextView textDisplay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        textDisplay = (TextView) findViewById(R.id.textDisplay);
        textDisplay.setText("我是你跳转的界面......");
    }
}
