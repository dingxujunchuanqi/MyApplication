package com.example.nicespinner.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by dingxujun on 2018/1/10.
 *
 * @project MyApplication
 */

@SuppressLint("AppCompatCustomView")
public class CostomTextView  extends android.support.v7.widget.AppCompatTextView {
    public CostomTextView(Context context) {
        super(context);
    }

    public CostomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CostomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        Paint mPaint1 = new Paint();
        mPaint1.setColor(getResources().getColor(android.R.color.holo_red_dark));
        mPaint1.setStyle(Paint.Style.FILL);
        Paint mPaint2 = new Paint();
        mPaint2.setColor(Color.YELLOW);
        mPaint2.setStyle(Paint.Style.FILL);
        //绘制外层矩形
        canvas.drawRect(0, 0, getMeasuredWidth(),getMeasuredHeight(), mPaint1);
        //绘制内层矩形
        canvas.drawRect(20, 20, getMeasuredWidth()-20, getMeasuredHeight()-20, mPaint2);
        canvas.save();
        //绘制文字前平移10像素
        canvas.translate(10, 0);
        super.onDraw(canvas);
        canvas.restore();
    }
}
