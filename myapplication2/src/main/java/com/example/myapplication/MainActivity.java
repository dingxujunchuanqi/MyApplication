package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.myapplication.activity.GroupLvActivity;
import com.example.myapplication.utils.AsyncTaskUtils;

/**
 * 1.分组的listView 及条目点击事件及条目子控件点击事件
 */
public class MainActivity extends AppCompatActivity {

    private AssetsLoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingDialog = new AssetsLoadingDialog(this);
        stockNumDialog(this);
    }


    public void stockNumDialog(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.inventory_results_view, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("资产信息");
        builder.setView(view);
        builder.setPositiveButton("确定", (dialog, which) -> {

            dialog.dismiss();

        });
        builder.setNegativeButton("取消", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.setOnCancelListener(dialog1 -> { //dialog取消监听
            finish();
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        // dialog.setCancelable(false);
        dialog.show();
    }

    public void dialog(View view) {
        AssetsDetailsDialog.showDetailsDialog(this, actual_info ->
                System.out.println("------------actual_info------" + actual_info));
    }

    /**
     * 自己设计的进度条
     *
     * @data 创建时间 2017/10/27
     * @author dingxujun
     */
    public void progress(View view) {
      loadingDialog.showLoadingDialog("加载中...");
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < 5; i++) {
                    loadingDialog.setMax(5);
                    SystemClock.sleep(2000);
                    System.out.println("--------loadingDialog--------------" + loadingDialog);
                    loadingDialog.setIncremenProgress(1);
                }
                loadingDialog.dismissDialog();
            }
        }.start();

    }

    /**
     * 酷炫对话框集成框架 使用
     *
     * @data 创建时间 2017/11/4
     * @author dingxujun
     */
    public void mydialog(View view) {
        Intent intent = new Intent(this, DialogActivity.class);
        startActivityForResult(intent,100);

    }

    /**
     *
     * 异步线程dialog Activity返回后，再进去show dialog 崩溃问题,我这个只创建一个dialog实例，在Activity
     * onDestroy时把dialog置null
    *@data 创建时间 2018/1/1
    *@author dingxujun
    */
     private  void threadText(){
         AsyncTaskUtils.doAsync(new AsyncTaskUtils.IDataCallBack<Object>() {
             @Override
             public void onTaskBefore() {
                 AssetsLoadingDialog.showProgressDialog(MainActivity.this,"9999");
             }
             @Override
             public Object onTasking(Void... params) {
                 return null;
             }

             @Override
             public void onTaskAfter(Object result) {
                 AssetsLoadingDialog.dismiss();
             }
         });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AssetsLoadingDialog.dialog=null;
    }
    /**
     * 分组的listview 及条目点击事件及条目子控件点击事件
    *@date 创建时间 2018/4/30
    *@author dingxujun
    *
    */
    public void groupLitvew(View view){
        Intent intent =new Intent(this, GroupLvActivity.class);
        startActivity(intent);

    }
}




