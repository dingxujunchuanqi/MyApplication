package com.example.nicespinner.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;


import org.angmarch.views.NiceSpinner;


/**
 * 自定义 NiceSpinner
 * Created by dingxujun on 2018/1/10.
 *
 * @project rt21dms_new
 */

public class MyNiceSpinner extends NiceSpinner {
    private Context context;

    public MyNiceSpinner(Context context) {
        super(context);
        System.out.println("------------我没被调用-------");
    }

    public MyNiceSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        System.out.print("33334444444444444444");
        System.out.print("3333444444444448888");
        System.out.print("3333444444444448888");
        System.out.println("");
        System.out.print("33334444444444444444");
        System.out.print("3333444444444448888");
        System.out.print("3333444444444448888");
        System.out.println("");
        System.out.print("33334444444444444444");
        System.out.print("3333444444444448888");
        System.out.print("3333444444444448888");
        System.out.println("");


        init();
    }

    public MyNiceSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setGravity(Gravity.CENTER);
        setPadding(5, 5,
                5, 5);
    }
}
