package com.pgt.xds.riding;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pgt.xds.R;

/**
 * 骑行Fragment
 * Created by zheng on 2017/5/4.
 */
public class RidingFragment extends Fragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.riding_fragment,null);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initView(View view){
        view.findViewById(R.id.riding_begin).setOnClickListener(this);
    }

    private void initData(){

    }

    private void initListener(){

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.riding_begin://骑行点击事件
                startActivity(new Intent(getActivity(),RidingStateActivity.class));
                break;
        }
    }
}
