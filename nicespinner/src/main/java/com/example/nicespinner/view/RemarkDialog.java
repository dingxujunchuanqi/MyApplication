package com.example.nicespinner.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.nicespinner.R;

/**
 * Created by dingxujun on 2018/3/19.
 *
 * @project rt21dms_new
 */

public class RemarkDialog {
    public static void showDialog(Activity context, MySpannableTextView textView) {
        View view = LayoutInflater.from(context).inflate(R.layout.assetremariks_view, null);

        EditText remark_et = (EditText) view.findViewById(R.id.remark_et);
        remark_et.setHorizontallyScrolling(false);//EditText多行显示并且支持imeOptions设置
        remark_et.setMaxLines(4);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("备注");
        builder.setView(view);

        builder.setPositiveButton("确定", null);
        builder.setNegativeButton("取消", (dialog, witch) -> {

        });
        AlertDialog dialog = builder.create();
        //对话框中有EditText时，当对话框显示时让其弹出软键盘
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener((v) -> {
            textView.limitTextViewString(remark_et.getText().toString().trim(),40,textView,null);
                dialog.dismiss();

        });
    }
}
