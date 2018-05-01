package com.example.nicespinner.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nicespinner.R;

/**
 * Created by dingxujun on 2018/2/5.
 *
 * @project MyApplication
 */

public class BasicInfoFragment extends Fragment {
    public static BasicInfoFragment newInstance(String data) {
        Bundle args = new Bundle();
        args.putString("key", data);
        BasicInfoFragment fragment = new BasicInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.infofragment, container, false);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        tv.setText("Fragment------" + getArguments().getString("key"));
        return view;
    }
}
