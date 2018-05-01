package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.bean.GroupLvBean;

import java.util.List;

/**
 * Created by dingxujun on 2018/4/30.
 *
 * @project MyApplication
 */

public class GroupLvAdapter extends BaseAdapter implements View.OnClickListener {
    private final List list;
    private final Context context;
    private final List<String> keyList;
    private static final int typeOne = 0;
    private static final int typeTwo = 1;
    private InnerItemOnclickListener mListener;

    public GroupLvAdapter(Context context, List list, List<String> keyList) {
        this.list = list;
        this.context = context;
        this.keyList = keyList;
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
    public boolean isEnabled(int position) {  //判断条目是否有焦点
        if (keyList.contains(getItem(position))) {
            return false;
        }

        return super.isEnabled(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHolder1 myHolder1 = null;
        MyHolder2 myHolder2 = null;
        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type) {
                case typeOne:
                    myHolder1 = new MyHolder1();
                    convertView = LayoutInflater.from(context).inflate(R.layout.group_titile, parent, false);//父布局view生效
//                    convertView = View.inflate(context, R.layout.group_titile, null);//父布局view不生效
                    myHolder1.order_num = convertView.findViewById(R.id.order_num);
                    convertView.setTag(myHolder1);
                    break;
                case typeTwo:
                    myHolder2 = new MyHolder2();
                    convertView = LayoutInflater.from(context).inflate(R.layout.grouplv_item, parent, false);
//                    convertView = View.inflate(context, R.layout.grouplv_item, parent);
                    myHolder2.city1 = convertView.findViewById(R.id.city1);
                    myHolder2.city2 = convertView.findViewById(R.id.city2);
                    myHolder2.list_icon = convertView.findViewById(R.id.list_icon);
                    myHolder2.add_bt = convertView.findViewById(R.id.add_bt);
                    myHolder2.minus_bt = convertView.findViewById(R.id.minus_bt);
                    convertView.setTag(myHolder2);
                    break;
            }
        }
        switch (type) {
            case typeOne:
                myHolder1 = (MyHolder1) convertView.getTag();
                myHolder1.order_num.setText((String) (list.get(position)));
                break;
            case typeTwo:
                myHolder2 = (MyHolder2) convertView.getTag();
                GroupLvBean.ReturnDataBean.OrderinfoBean bean = (GroupLvBean.ReturnDataBean.OrderinfoBean) list.get(position);
                myHolder2.city2.setText(bean.getShopname());
                myHolder2.city1.setText(bean.getVegetname());
                myHolder2.add_bt.setOnClickListener(this);
                myHolder2.minus_bt.setOnClickListener(this);
                myHolder2.add_bt.setTag(position);
                myHolder2.minus_bt.setTag(position);
                break;
        }
        return convertView;
    }


    static class MyHolder1 {
        private TextView order_num;
    }

    public static class MyHolder2 {
        public TextView city1;
        public TextView city2;
        public ImageView list_icon;
        public TextView add_bt;
        public TextView minus_bt;
    }

    @Override
    public int getItemViewType(int position) {
        if (keyList.contains(getItem(position))) {
            return typeOne;
        } else {
            return typeTwo;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public interface InnerItemOnclickListener {
        void itemClick(View v);
    }

    public void setOnInnerItemOnClickListener(InnerItemOnclickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        mListener.itemClick(v);
    }
}
