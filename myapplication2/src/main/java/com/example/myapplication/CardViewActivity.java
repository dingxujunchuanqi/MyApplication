package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * CardView界面
 * Created by dingxujun on 2017/11/17.
 *
 * @project MyApplication
 */

public class CardViewActivity extends Activity {

    private WebView webview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardview_activity);
        CardView cv_item = (CardView) findViewById(R.id.cv_item);
        webview = (WebView) findViewById(R.id.webview);
        cv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        webview.loadUrl("file:///android_asset/jit/index.html");//加载asset文件夹下html
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js
        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        //不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);

    }
}
