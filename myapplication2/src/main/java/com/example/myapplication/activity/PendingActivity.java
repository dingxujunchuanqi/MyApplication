package com.example.myapplication.activity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * Created by dingxujun on 2018/7/12.
 *
 * @project MyApplication
 */

public class PendingActivity extends AppCompatActivity {

    PendingIntent pendingIntent;
    Context myContext;
    TextView textIntervalo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendingintent);
        Button buttonPending = (Button) findViewById(R.id.buttonPending);
        Button buttonAlarm = (Button) findViewById(R.id.buttonAlarm);
        textIntervalo = (TextView) findViewById(R.id.textIntervalo);

        myContext = getApplicationContext();

        Intent intent = new Intent();
        intent.setClass(myContext, DetailActivity.class);
        pendingIntent = PendingIntent.getActivity(myContext, 0, intent, 0);

        buttonPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent voidIntent = new Intent();
                try {
                    pendingIntent.send(myContext, 0, voidIntent);
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                int sec = Integer.parseInt(textIntervalo.getText().toString());
                alarmManager.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 1000 * sec, pendingIntent);
            }
        });
    }


    /**
     * 通知栏案例
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Resources res = getResources();
        Intent i = new Intent();
        i.setClass(myContext, DetailActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);

//这里使用的支持库中的NotificationCompat
        Notification notification = new NotificationCompat.Builder(this)
                //设置通知对应的一些信息
                .setTicker(res.getString(R.string.new_pictures_title))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(res.getString(R.string.new_pictures_title))
                .setContentText(res.getString(R.string.new_pictures_text))
                //添加通知对应的PendingIntent
                //一旦点击通知后，PendingIntent将被处理
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        notificationManager.notify(0, notification);

    }
}
