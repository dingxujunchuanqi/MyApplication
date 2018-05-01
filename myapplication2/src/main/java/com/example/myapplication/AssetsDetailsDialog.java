package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by dingxujun on 2017/10/20.
 *
 * @project rt21dms_new
 */

public class AssetsDetailsDialog {
    public static void showDetailsDialog(Context context, OnOkbtnClickListener onOkbtnClickListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.inventory_results_view, null);
        EditText et_realNum = (EditText) view.findViewById(R.id.et_realNum);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("资产信息");
        builder.setView(view);
        builder.setPositiveButton("确定", null);
        builder.setNegativeButton("取消", null);
        String items[] = new String[]{
                "盘盈", "盘亏", "报废", "其他"
        };
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        System.nanoTime();
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public interface OnOkbtnClickListener {
        void onClick(String actual_info);
    }

    public  static void imageDialog(Context context){
        Dialog dialog =  new  Dialog(context, R.style.edit_AlertDialog_style);
        dialog.setContentView(R.layout.image_dialog);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.start_img);
        imageView.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_success));
        dialog.show();

        dialog.setCanceledOnTouchOutside(false); // Sets whether this dialog is
        Window w = dialog.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.x = 0;
        lp.y = 40;
        dialog.onWindowAttributesChanged(lp);


    }
}
