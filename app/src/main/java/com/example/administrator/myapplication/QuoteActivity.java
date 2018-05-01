package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.administrator.myapplication.utils.Animals;
import com.example.administrator.myapplication.utils.Student;
import com.example.administrator.myapplication.zipfile.IPUtil;
import com.google.gson.Gson;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * DEMO 功能列表
 *1. 9老安卓混合动画 使用范例
 *2.字符串截取
 *3.下载文件  并带进度条
 *4. 解压读取文件测试
 *5. 创建文件测试、解压文件
 *6.遍历当前目录下所有的文件工具类
 *7.操作文件的工具类 包括图片，系统时间、json等
 *8.ping ip的工具类
 *9.监听apk安卓卸载的广播
 * Created by dingxujun on 2017/10/10.
 *
 * @project MyApplication
 */

public class QuoteActivity extends Activity {
    int i = 2;
    private OkHttpClient mOkHttpClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
        animation();
        mOkHttpClient = new OkHttpClient();
        mProgressBar = (ProgressBar) findViewById(R.id.firstBar);
        Environment.getExternalStorageDirectory();

        findViewById(R.id.quoe_but).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mIntent);
            }
        });
        /*调到系统界面*/
        findViewById(R.id.system_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);

            }
        });
        findViewById(R.id.swich_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (i) {
                    case 0:
                        System.out.println("---------i=0-----------");
                    case 1:
                        System.out.println("---------i=1-----------");
                    case 2:
                        System.out.println("---------i=2----------");
                    case 3:
                        System.out.println("---------i=3-----------");
                        break;
                    default:
                        break;

                }
            }
        });

    }

    /**
     * 9老安卓混合动画 使用范例，values都是可变参数
     */
    private void animation() {
        Button myView = (Button) findViewById(R.id.system_btn);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(myView, "rotationX", 0, 0),
                ObjectAnimator.ofFloat(myView, "rotationY", 0, 20,90,10),//旋转Y轴偏移的角度
                ObjectAnimator.ofFloat(myView, "rotation", 0, 720),
                ObjectAnimator.ofFloat(myView, "translationX", 0, 0),//X轴平移
                ObjectAnimator.ofFloat(myView, "translationY", 0, -300),//Y轴平移
                ObjectAnimator.ofFloat(myView, "scaleX", 1, 1.5f),//缩放
                ObjectAnimator.ofFloat(myView, "scaleY", 1, 0.5f),
                ObjectAnimator.ofFloat(myView, "alpha", 1, 0.25f, 1)//透明度
        );
        set.setDuration(5 * 1000).start();
    }

    /**
     * 截取字符串测试01
     *
     * @data 创建时间 2017/12/4
     * @author dingxujun
     */
    public void jiequ01(View view) {
        String scanCode = "资产名称:台式计算机规格型号:ThinkCentreM4600t资产编号:92-990101010001";
        int i = scanCode.indexOf("资产编号:");

        try {
            System.out.println("---------资产编号----------" + scanCode.substring(i + 5, i + 20));
        } catch (Exception e) {
            Toast.makeText(this, "编码格式不对", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        System.out.println("-----------------编码格式不对------------------");
    }

/**
 * 截取字符串03
*@date 创建时间 2018/3/13
*@author dingxujun*
*
*/
    public void jiequ03(View view) {
        String string = "2018-03-13 10:19:34";
        try {
            System.out.println("-----------substring--------------"+getYmdDate(string));
            System.out.println("-----------substring--------------"+(string.substring("2018-03-13".length(),string.length())).trim());

            Student student=new Student("小明",23);

          Animals animals =new Animals("hehh",45);
            List<Animals> list =new ArrayList();
            list.add(animals);
            student.setList(list);
            Gson gson =new Gson();
            Map <String,Student> map =new HashMap<>();
            map.put("aa",student);
            System.out.println("----------Student----------"+gson.toJson(map));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("-----substring--------异常了-------");
        }
    }

    private static final SimpleDateFormat ymDay = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 传个时间字符串 转换成年-月-日  例  "2018-03-13 10:19:34"
     * @param timeStr 时间字符串
     * @return
     */
    public  String getYmdDate(String timeStr) {
        String formatDate=null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
            long time = simpleDateFormat.parse(timeStr).getTime();
            formatDate = ymDay.format(new Date(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatDate;
    }
    /**
     * 截取字符串测试02区间截取
     *
     * @data 创建时间 2017/12/26
     * @author dingxujun
     */
    public void jiequ02(View view) {
        String scanCode = "设备设施编码：92-09030200-0017设备设施名称：曳引电梯规格型号:9000-WT-10-10-2-XS";
        String substring = scanCode.trim().substring(scanCode.indexOf("码") + 2, scanCode.lastIndexOf("备") - 1);
        System.out.println("--------资产移交字符截取---------" + substring + (scanCode.indexOf("码") + 2) + "----" + scanCode.lastIndexOf("设") + scanCode.length());
    }

    /**
     * okhttp 下载文件  并带进度条
     *
     * @data 创建时间 2017/12/12
     * @author dingxujun
     */
    public void loadfile(View view) {
        boolean b = IPUtil.ping("192.168.1.109");
        System.out.println("------------ip可用吗-----------------" + b);
        // mProgressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        // mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        /**
         * 设置连接的超时时间
         */
        mOkHttpClient.setConnectTimeout(20, TimeUnit.SECONDS);

        /**
         * 设置响应的超时时间
         */
        mOkHttpClient.setWriteTimeout(20, TimeUnit.SECONDS);
        /**
         * 请求的超时时间
         */
        mOkHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        /**
         * 允许使用Cookie
         */
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        Request request = new Request.Builder().tag(this)
                .url("http://192.168.1.104:8080/apk/newapk.apk").build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[6144];
                int len = 0;
                FileOutputStream fos = null;
                String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(SDPath, "南瑞.apk");
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        System.out.println("------progress------------------" + progress + "---" + sum * 1.0f + "----" + total);
                        Log.d("h_bl", "progress=" + progress);
                        Message msg = mHandler.obtainMessage();
                        msg.what = 1;
                        msg.arg1 = progress;
                        mHandler.sendMessage(msg);
                    }
                    fos.flush();
                    Log.d("h_bl", "文件下载成功");
                } catch (Exception e) {
                    Log.d("h_bl", "文件下载失败");
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

    ProgressBar mProgressBar;
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    int progress = msg.arg1;
                    mProgressBar.setProgress(progress);
                    if (progress == 100) {
                        mProgressBar.setVisibility(View.GONE);
                    }
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOkHttpClient.cancel(this);
    }
}
