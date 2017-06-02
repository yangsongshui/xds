package com.pgt.xds.riding;

import android.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.pgt.xds.BaseActivity;
import com.pgt.xds.R;

/**
 * 骑行状态界面
 * Created by zheng on 2017/5/8.
 */
public class RidingStateActivity extends BaseActivity {


    private LinearLayout runLayout;//骑行
    private LinearLayout navigationLayout;//导航
    private RelativeLayout searchLayout;//title搜索

    private RunFragment runFragment;//骑行Fragment
    private NavigationFragment navigationFragment;//导航Fragment

    @Override
    protected int getContentViewId() {
        return R.layout.riding_state_activity;
    }

    @Override
    protected void initView() {
        runLayout = (LinearLayout) findViewById(R.id.riding_state_run_layout);
        navigationLayout = (LinearLayout) findViewById(R.id.riding_state_navigation_layout);
        searchLayout = (RelativeLayout) findViewById(R.id.search_layout);
    }

    @Override
    protected void initData() {
        checkedFragment(0);
    }

    @Override
    protected void initListener() {
        findViewById(R.id.back_layout).setOnClickListener(this);

        searchLayout.setOnClickListener(this);
        runLayout.setOnClickListener(this);
        navigationLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_layout:
                finish();
                break;
            case R.id.search_layout:

                break;
            case R.id.riding_state_run_layout://骑行点击事件
                checkedFragment(0);

                break;
            case R.id.riding_state_navigation_layout://导航点击事件
                checkedFragment(1);

                break;
        }
    }

    /**
     * Fragment选中状态
     **/
    private void checkedFragment(int index) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        hideFragment(fragmentTransaction);
        tabCheckedBackground(index);
        switch (index) {
            case 0://骑行
                if (runFragment == null) {
                    runFragment = new RunFragment();
                    fragmentTransaction.add(R.id.riding_state_content, runFragment);
                } else {
                    fragmentTransaction.show(runFragment);
                }
                searchLayout.setVisibility(View.GONE);
                break;
            case 1://导航
                if (navigationFragment == null) {
                    navigationFragment = new NavigationFragment();
                    fragmentTransaction.add(R.id.riding_state_content, navigationFragment);
                } else {
                    fragmentTransaction.show(navigationFragment);
                }
                searchLayout.setVisibility(View.VISIBLE);
                break;
        }
        fragmentTransaction.commit();
    }

    /**
     * 隐藏Fragment
     **/
    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (runFragment != null) {
            fragmentTransaction.hide(runFragment);
        }
        if (navigationFragment != null) {
            fragmentTransaction.hide(navigationFragment);
        }
    }

    /**
     * 底部Tab选中背景色
     **/
    private void tabCheckedBackground(int index) {
        switch (index) {
            case 0:
                runLayout.setBackgroundResource(R.color.title_color);
                navigationLayout.setBackgroundResource(R.color.main_botm_color);
                break;
            case 1:
                runLayout.setBackgroundResource(R.color.main_botm_color);
                navigationLayout.setBackgroundResource(R.color.title_color);
                break;
        }
    }
}
