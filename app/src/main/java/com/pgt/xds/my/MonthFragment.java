package com.pgt.xds.my;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.pgt.xds.BaseFragment;
import com.pgt.xds.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonthFragment extends BaseFragment {


    public MonthFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_month;
    }

}
