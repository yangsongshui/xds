package com.pgt.xds.club;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pgt.xds.base.BaseFragment;
import com.pgt.xds.R;

import butterknife.OnClick;

/**
 * 俱乐部Fragment
 * Created by zheng on 2017/5/4.
 */
public class ClubFragment extends BaseFragment {


    @Override
    protected void initData(View layout, Bundle savedInstanceState) {

    }

    @Override
    protected int getContentView() {
        return R.layout.club_fragment;
    }


    @OnClick({R.id.complete_ll, R.id.riding_troops_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.complete_ll:
                startActivity(new Intent(getActivity(), CompetitionActivity.class));
                break;
            case R.id.riding_troops_ll:
                startActivity(new Intent(getActivity(), TroopsActivity.class));
                break;
        }
    }
}
