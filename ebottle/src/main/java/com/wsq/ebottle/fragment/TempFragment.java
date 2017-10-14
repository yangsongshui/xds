package com.wsq.ebottle.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wsq.ebottle.MyApplication;
import com.wsq.ebottle.R;

/**
 * <br />
 * created by CxiaoX at 2017/5/13 20:10.
 */

public class TempFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "TempFragment";

    private MyApplication myApp;
    private TextView temperature_tv;
    private ImageView temperature_iv;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        myApp = (MyApplication) activity.getApplication();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_temp_set, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.minus_iv).setOnClickListener(this);
        view.findViewById(R.id.plus_iv).setOnClickListener(this);
        temperature_tv = (TextView) view.findViewById(R.id.temperature_tv);
        temperature_iv = (ImageView) view.findViewById(R.id.temperature_iv);
        temperature_iv.setImageResource(getPic(myApp.fTemp));
        temperature_tv.setText(myApp.fTemp + "℃");
/*
        for (int i = 0; i < spTemp.getCount(); i++) {
            String st = (String) spTemp.getItemAtPosition(i);
            int temp = Integer.parseInt(st);
            if (temp == myApp.fTemp) {
                spTemp.setSelection(i);
                break;
            }
        }*/


    }


    @Override
    public void onClick(View view) {
        //   String temp = (String) spTemp.getSelectedItem();
        //    Log.i(TAG, "onClick: tem=" + temp);
        switch (view.getId()) {
            case R.id.plus_iv:
                if (myApp.fTemp < 46) {
                    myApp.fTemp++;
                } else {
                    Toast.makeText(getActivity(), "温度太高了!", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case R.id.minus_iv:
                if (myApp.fTemp > 40) {
                    myApp.fTemp--;
                } else {
                    Toast.makeText(getActivity(), "温度太低了!", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            default:
                break;
        }


        temperature_tv.setText(myApp.fTemp + "℃");
        temperature_iv.setImageResource(getPic(myApp.fTemp));
        SharedPreferences sp = getActivity().getSharedPreferences("device_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("ftemp", myApp.fTemp);
        editor.apply();
        Log.i(TAG, "onClick: 保存成功");


        // 连接设备，写恒温
        // 发送广播

    }

    private int getPic(int temp) {
        switch (temp) {
            case 40:
                return R.drawable.temperature_40;
            case 41:
                return R.drawable.temperature_41;
            case 42:
                return R.drawable.temperature_42;
            case 43:
                return R.drawable.temperature_43;
            case 44:
                return R.drawable.temperature_44;
            case 45:
                return R.drawable.temperature_45;
            case 46:
                return R.drawable.temperature_46;
            default:
                return R.drawable.temperature_40;
        }
    }
}
