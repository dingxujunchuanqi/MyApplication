package com.example.nicespinner.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nicespinner.R;
import com.example.nicespinner.view.MyNiceSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

/*
* *****************************************************
*                      功能列表
* 1.NiceSpinners三方框架下拉框的使用
* 2.延时加载ViewStub控件
* 3.自定义TextView
* 4.TabLayout使用
* 5.Android中SpannableString学习以及实现自定义TextView的显示更多（展开）和收起功能
* 6.OkGo多任务列表下载
* 7.状态选择器实现选中和未选中
* 8.字符串转数组； 数组转字符串
*
* *****************************************************
* */
public class MainActivity extends AppCompatActivity {
    private String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest
            .permission.WRITE_SETTINGS, Manifest.permission.READ_SYNC_SETTINGS};
    private int REQUEST_CODE_PERMISSIONS = 1;
    private TextView select;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //   Settings.System.putInt(getContentResolver(),"sys.home_key_to_app", 1);屏蔽home键操作
        final MyNiceSpinner niceSpinner = findViewById(R.id.nice_spinner);
        niceSpinner.setTextColor(Color.GREEN);//设置字体颜色
        //  niceSpinner.setTextSize(15f);//设置字体大小
        niceSpinner.setBackgroundColor(getResources().getColor(R.color.colorAccent));//设置背景颜色
        niceSpinner.setText("9999999");//单独设置字体
        final List<String> data = new ArrayList<>();
        select = (TextView) findViewById(R.id.select);

        select.setSelected(false);
        data.add("语文");
        data.add("数学");
        data.add("英语");
        data.add("政治");
        data.add("语文");
        data.add("数学");
        data.add("英语");
        data.add("政治");
        data.add("语文");
        data.add("数学");
        data.add("英语");
        data.add("政治");
        data.add("语文");
        data.add("数学");
        data.add("英语");
        //  Settings.System.putInt(getContentResolver(),"sys.home_key_to_app", 0);
        //int isHomeKeyToUser = Settings.System.getInt(getContentResolver(),"sys.home_key_to_app", 0);
        final List<String> dataset = new LinkedList<>(Arrays.asList("三天", "一周", "二周", "一个月", "三个月", "六个月", "一年"
                , "一周", "二周", "一个月", "三个月", "六个月", "一年", "领用日期"));
        // niceSpinner.attachDataSource(dataset);//设置数据的第一种样式


        niceSpinner.setAdapter(new myAdpter(data));
        niceSpinner.setSelectedIndex(0);//设置默认选择值
        // niceSpinner.setDropDownListPaddingBottom(400);//动态设置下拉菜单据底部的高度
        /*设置数据的第二种样式*/
        //   niceSpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data));
        //niceSpinner.setSelectedIndex(2);//设置初始显示哪个条目的数据
        /*条目点击监听方法*/
        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //  System.out.println("-----------parent--------------" + dataset.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    class myAdpter extends BaseAdapter {
        private final List<String> list;

        public myAdpter(List<String> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            myviewHold myviewHold = null;
            if (convertView == null) {
                myviewHold = new myviewHold();
                //如果当前的convertView为null，则通过inflate产生一个view
                convertView = View.inflate(MainActivity.this, R.layout.spinner_item, null);
                myviewHold.textView = (TextView) convertView.findViewById(R.id.tv_spinner);
                convertView.setTag(myviewHold);
            } else {
                myviewHold = (myviewHold) convertView.getTag();
            }

            // myviewHold.textView.setText(list.get(position));

            return convertView;
        }

    }

    class myviewHold {
        TextView textView;
        ImageView imageView;
    }

    /********************自定义textView******************/

    public void constomTv(View v) {
        Intent intent = new Intent(this, CostomViewActivity.class);
        startActivity(intent);
    }

    /********************延时加载ViewStub控件******************/
    public void viewStub(View v) {
        Intent intent = new Intent(this, ViewStubActivity.class);
        startActivity(intent);
    }

    /********************TabLayout使用******************/
    public void tabLayout(View v) {
        Intent intent = new Intent(this, TabLayoutActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();

    }

    /**
     * 实现自定义TextView的显示更多（展开）和收起功能
     *
     * @date 创建时间 2018/3/19
     * @author dingxujun
     */
    public void spannableString(View v) {
        Intent intent = new Intent(this, MySpannableActivity.class);
        startActivity(intent);
    }

    /**
     * Okgo 多任务列表下载
     *
     * @param v
     */
    public void oKgoDownload(View v) {
        Intent intent = new Intent(this, DownLoadActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Settings.System.putInt(getContentResolver(),"sys.home_key_to_app", 0);//解除屏蔽home键操作
    }

    /**
     * 状态选择器实现选中和未选中
     *
     * @date 创建时间 2018/4/11
     * @author dingxujun
     */
    public void select(View v) {
        select.setSelected(!select.isSelected());
        select.setText(null);
    }

    // 获得本周一0点时间

    public String getWeekEnd() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//24小时制
        // 获得当前周- 周日  的日期
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
        Date monday = currentDate.getTime();
        String weekLastDay = simpleDateFormat.format(monday);
        return weekLastDay;
    }


    // 获得本周一与当前日期相差的天数
    public static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }

    // 获得当前周- 周一的日期
    public static String getWeekStart() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//24小时制
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        String weekOneDay = simpleDateFormat.format(monday);
        return weekOneDay;
    }

    /**
     * 字符串转数组
     */
    public static void stringToshuzu(View view) {
        String am = "0123,poi,klk";
        System.out.println("-----substring---------------"+am.substring(1,am.length()-1));
        String[] split = am.split(",");
        for (int i = 0; i < split.length; i++) {
            System.out.println("------split---------"+split[i]);
        }
    }

    /**
     * 数组转字符串
     */
    public static void shuzuTostring(View view) {
        String[] arr = {"0123", "sb", "12f"};
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);        //append String并不拥有该方法，所以借助StringBuffer
        }
        String sb1 = sb.toString().trim();
        System.out.println(sb1);    //0123sb12
    }
}