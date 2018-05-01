package com.example.myapplication;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import net.lemonsoft.lemonbubble.LemonBubble;

import me.leefeng.promptlibrary.Builder;
import me.leefeng.promptlibrary.PromptDialog;

public class DialogActivity extends AppCompatActivity {

    private BottomAnimDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        //lemohelloDialog();compile 'com.github.1em0nsOft:LemonHello4Android:1.0.1' 对话框
       // promaptDialog();//   compile 'com.github.limxing:Android-PromptDialog:1.1.3' 对话框
        dialog = new BottomAnimDialog(this, "相机", "相册", "取消");
      //  LemonBubble.showRight(this, "集成成功！", 2000);
        promaptDialog();
    }

    private void promaptDialog() {
        PromptDialog promptDialog = new PromptDialog(this);
        promptDialog.showLoading("正在下载数据",false);
        promptDialog.showSuccess("下载成功",false);
        promptDialog.setViewAnimDuration(0);
    }

  /*  private void lemohelloDialog() {
        LemonHelloInfo info = LemonHello.getSuccessHello("温馨提示", "恭喜您，下载成功！");
        info.show(DialogActivity.this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LemonHelloView.defaultHelloView().hide();//隐藏对话框
            }
        }, 2000);
    }*/

/**底部弹出的带动画的对话框
*@data 创建时间 2017/11/13
*@author dingxujun
*/
  public void bottomdialog(View v){

      dialog.setClickListener(new BottomAnimDialog.BottonAnimDialogListener() {
          @Override
          public void onItem1Listener() {
              // TO_DO
             // dialog.dismiss();
          }

          @Override
          public void onItem2Listener() {
              // TO_DO
             // dialog.dismiss();
          }

          @Override
          public void onItem3Listener() {
              dialog.dismiss();
          }
      });

      dialog.show();
  }

  public void cardview_oclick(View view){
      Intent intent =new Intent(this,CardViewActivity.class);
      startActivity(intent);
  }
}
