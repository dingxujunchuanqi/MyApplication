package com.example.myapplication.activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.example.myapplication.R;
import com.example.myapplication.adapter.GroupLvAdapter;
import com.example.myapplication.bean.GroupLvBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 分组的展示的listview界面
 * Created by dingxujun on 2018/4/30.
 *
 * @project MyApplication
 */

public class GroupLvActivity extends AppCompatActivity implements GroupLvAdapter.InnerItemOnclickListener {
    private ListView group_lv;
    private List list = null;
    private List<String> groupkey = new ArrayList<String>();
    private List<GroupLvBean.ReturnDataBean> grReturnList = new ArrayList<>();//外层集合
    private List<GroupLvBean.ReturnDataBean.OrderinfoBean> orderList;
    private GroupLvAdapter.MyHolder2 holder2;
    private ListView group_lv1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grouplv_activity);
        initView();
        initData();
        setAdapter();
    }

    private void initData() {
        GroupLvBean groupLvBean = new GroupLvBean();

        for (int i = 0; i < 6; i++) {
            orderList = new ArrayList<>();//内层集合
            GroupLvBean.ReturnDataBean reBean = new GroupLvBean.ReturnDataBean();
            reBean.setOrderid("呵呵丁丁" + i);
            for (int j = 0; j < 6; j++) {
                GroupLvBean.ReturnDataBean.OrderinfoBean bean = new GroupLvBean.ReturnDataBean.OrderinfoBean();
                bean.setShopname("北京" + j);
                bean.setVegetname("南京" + j);
                orderList.add(bean);
            }
            reBean.setOrderinfo(orderList);

            grReturnList.add(reBean);

        }
        groupLvBean.setReturnData(grReturnList);

        for (GroupLvBean.ReturnDataBean bean :
                groupLvBean.getReturnData()) {
            groupkey.add(bean.getOrderid());

        }

        list = new ArrayList<String>();

        for (GroupLvBean.ReturnDataBean bean :
                groupLvBean.getReturnData()) {
            list.add(bean.getOrderid());
            for (GroupLvBean.ReturnDataBean.OrderinfoBean bean1 :
                    bean.getOrderinfo()) {
                list.add(bean1);
            }
        }
    }

    private void initView() {
        group_lv = (ListView) findViewById(R.id.group_lv);
    }

    private void setAdapter() {
        GroupLvAdapter adapter = new GroupLvAdapter(this, list, groupkey);
        group_lv.setAdapter(adapter);
        adapter.setOnInnerItemOnClickListener(this);

        //设置整个条目的点击事件
        group_lv.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(this, "整个条目" + position, Toast.LENGTH_SHORT).show();
            Object tag = view.getTag();
            if (view.getTag() instanceof GroupLvAdapter.MyHolder2) {
                holder2 = (GroupLvAdapter.MyHolder2) tag;

                //设置条目图片的点击事件
                holder2.list_icon.setOnClickListener(v -> {
                    Toast.makeText(this, "图片" + position, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    /**
     * 设置加号 和减号的点击事件
     *
     * @param v
     */
    @Override
    public void itemClick(View v) {
        int position = (int) v.getTag();
        switch (v.getId()) {
            case R.id.add_bt:
                Toast.makeText(this, "我是加号" + position, Toast.LENGTH_SHORT).show();
                break;
            case R.id.minus_bt:
                Toast.makeText(this, "我是减号" + position, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
