package com.example.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;

/**
 * Created by dingxujun on 2017/10/25.
 *
 * @project rt21dms_new
 */

public class AssetsLoadingDialog {

    private  ProgressDialog dialogProgress;
    public   static  ProgressDialog dialog;


    public AssetsLoadingDialog(Context context) {
        if (dialogProgress == null) {
            dialogProgress = new ProgressDialog(context);
        }
    }

    public void showLoadingDialog(String title) {
        dialogProgress.setTitle(title);
        dialogProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialogProgress.show();
    }

    public void dismissDialog() {
        dialogProgress.dismiss();
    }

    public void setMax(int progress) {
        dialogProgress.setMax(progress);
    }

    public void setIncremenProgress(int progress) {
        dialogProgress.incrementProgressBy(progress);
    }
    public void setProgress(int progress) {
        dialogProgress.setProgress(progress);
    }


    /**自定义关闭 对话框的同时关闭Activity
     *@data 创建时间 2017/12/15
     *@author dingxujun
     */
    public  static void showProgressDialog(Activity context,String message) {
        if (!context.isFinishing()) {
            if (dialog==null){
                dialog = new ProgressDialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            }
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(true);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage(message);
            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
                System.out.println("----------dialog--------show-----"+dialog);
            }
        }
        dialog.setOnCancelListener(dialog1 -> {
            context.finish();
        });
    }
    public static void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            System.out.println("----------dialog-------------"+dialog);
        }
    }
}
