package com.pgt.xds;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pgt.xds.club.ClubFragment;
import com.pgt.xds.discover.DiscoverFragment;
import com.pgt.xds.my.MyFragment;
import com.pgt.xds.my.SetActivity;
import com.pgt.xds.riding.RidingFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity {

    private RidingFragment ridingFragment;//骑行Fragment
    private ClubFragment clubFragment;//俱乐部Fragment
    private DiscoverFragment discoverFragment;//发现Fragment
    private MyFragment myFragment;//我的Fragment

    private LinearLayout runLayout;//骑行Tab
    private LinearLayout clubLayout;//俱乐部Tab
    private LinearLayout discoverLayout;//发现Tab
    private LinearLayout myLayout;//我的Tab
    private List<View> views = new ArrayList<>();//底部导航View集合

    private RelativeLayout rightClick;//标题栏右边点击按钮
    private TextView findCar;//找车
    private ImageView setImage;//设置
    private int tabState;//底部导航栏点击状态

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        rightClick = (RelativeLayout) findViewById(R.id.main_title_right_click);
        findCar = (TextView) findViewById(R.id.find_car_text);
        setImage = (ImageView) findViewById(R.id.set_image);

        runLayout = (LinearLayout) findViewById(R.id.run_layout);
        clubLayout = (LinearLayout) findViewById(R.id.club_layout);
        discoverLayout = (LinearLayout) findViewById(R.id.discover_layout);
        myLayout = (LinearLayout) findViewById(R.id.my_layout);
        views.add(runLayout);
        views.add(clubLayout);
        views.add(discoverLayout);
        views.add(myLayout);
    }

    @Override
    protected void initData() {
        checkedFragment(0);
    }

    @Override
    protected void initListener() {
        rightClick.setOnClickListener(this);
        runLayout.setOnClickListener(this);
        clubLayout.setOnClickListener(this);
        discoverLayout.setOnClickListener(this);
        myLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.run_layout://骑行点击事件
                checkedFragment(0);
                break;
            case R.id.club_layout://俱乐部点击事件
                checkedFragment(1);
                break;
            case R.id.discover_layout://发现点击事件
                checkedFragment(2);
                break;
            case R.id.my_layout://我的点击事件
                checkedFragment(3);
                break;
            case R.id.main_title_right_click://标题栏右边点击事件
                if (tabState == 0) {//找车

                } else if (tabState == 3) {//设置
                    startActivity(new Intent(this, SetActivity.class));
                }
                break;
        }
    }

    /**
     * Fragment选中状态
     **/
    private void checkedFragment(int index) {
        tabState = index;
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        hideFragment(fragmentTransaction);
        tabCheckedBackground(index);
        switch (index) {
            case 0://骑行
                rightClick.setVisibility(View.VISIBLE);
                findCar.setVisibility(View.VISIBLE);
                setImage.setVisibility(View.GONE);
                if (ridingFragment == null) {
                    ridingFragment = new RidingFragment();
                    fragmentTransaction.add(R.id.main_content_layout, ridingFragment);
                } else {
                    fragmentTransaction.show(ridingFragment);
                }
                break;
            case 1://俱乐部
                rightClick.setVisibility(View.GONE);
                findCar.setVisibility(View.GONE);
                setImage.setVisibility(View.GONE);
                if (clubFragment == null) {
                    clubFragment = new ClubFragment();
                    fragmentTransaction.add(R.id.main_content_layout, clubFragment);
                } else {
                    fragmentTransaction.show(clubFragment);
                }
                break;
            case 2://发现
                rightClick.setVisibility(View.GONE);
                findCar.setVisibility(View.GONE);
                setImage.setVisibility(View.GONE);
                if (discoverFragment == null) {
                    discoverFragment = new DiscoverFragment();
                    fragmentTransaction.add(R.id.main_content_layout, discoverFragment);
                } else {
                    fragmentTransaction.show(discoverFragment);
                }
                break;
            case 3://我的
                rightClick.setVisibility(View.VISIBLE);
                findCar.setVisibility(View.GONE);
                setImage.setVisibility(View.VISIBLE);
                if (myFragment == null) {
                    myFragment = new MyFragment();
                    fragmentTransaction.add(R.id.main_content_layout, myFragment);
                } else {
                    fragmentTransaction.show(myFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    /**
     * 隐藏Fragment
     **/
    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (ridingFragment != null) {
            fragmentTransaction.hide(ridingFragment);
        }
        if (clubFragment != null) {
            fragmentTransaction.hide(clubFragment);
        }
        if (discoverFragment != null) {
            fragmentTransaction.hide(discoverFragment);
        }
        if (myFragment != null) {
            fragmentTransaction.hide(myFragment);
        }
    }

    /**
     * 底部Tab选中背景色
     **/
    private void tabCheckedBackground(int index) {
        for (int i = 0; i < views.size(); i++) {
            if (i == index) {
                views.get(i).setBackgroundResource(R.color.title_color);
            } else {
                views.get(i).setBackgroundResource(R.color.main_botm_color);
            }
        }
    }
}
