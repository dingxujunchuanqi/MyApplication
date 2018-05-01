package com.example.nicespinner.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.nicespinner.R;
import com.example.nicespinner.view.MySpannableTextView;
import com.example.nicespinner.view.RemarkDialog;
import com.lzy.okserver.download.DownloadManager;
import com.lzy.okserver.download.DownloadService;

/**
 * Android中SpannableString学习以及实现自定义TextView的显示更多（展开）和收起功能
 * Created by dingxujun on 2018/3/19.
 *
 * @project MyApplication
 */

public class MySpannableActivity extends Activity{
    String a="国家主席习近平签署主席令，任命了国务院副总理、国务委员、各部部长、各委员会主任、中国人民银行行长、审计长、秘书长\n" +
            "十三届全国人大一次会议举行第七次全体会议，习近平等党和国家领导人出席大会\n" +
            "大会经投票表决，决定韩正、孙春兰、胡春华、刘鹤为国务院副总理\n" +
            "大会经投票表决，决定魏凤和、王勇、王毅、肖捷、赵克志为国务委员";
    private MySpannableTextView mySpannableTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myspannable_activity);
        mySpannableTextView = (MySpannableTextView) findViewById(R.id.tv);
      //  mySpannableTextView.limitTextViewString(a,80,mySpannableTextView,null);
    }
    public void tanqi(View view){
        RemarkDialog.showDialog(this,mySpannableTextView);
        DownloadManager manger= DownloadService.getDownloadManager();
        manger.getThreadPool().setCorePoolSize(9);
       // manger.setTargetFolder();
        manger.pauseAllTask();
    }
}
