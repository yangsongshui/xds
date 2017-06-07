package com.pgt.xds.club;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pgt.xds.BaseActivity;
import com.pgt.xds.R;

import butterknife.BindView;
import butterknife.OnClick;

public class RecommendActivity extends BaseActivity {

    @BindView(R.id.recommend_pic)
    ImageView recommendPic; //队伍图片
    @BindView(R.id.recommend_name)
    TextView recommendName;//队伍名称
    @BindView(R.id.recommend_id)
    TextView recommendId;//队伍id
    @BindView(R.id.recommend_people)
    TextView recommendPeople;//队伍人数
    @BindView(R.id.recommend_lv)
    TextView recommendLv;//队伍等级
    @BindView(R.id.recommend_address)
    TextView recommendAddress;//队伍地址
    @BindView(R.id.recommend_photo1)
    ImageView recommendPhoto1;
    @BindView(R.id.recommend_photo2)
    ImageView recommendPhoto2;
    @BindView(R.id.recommend_photo3)
    ImageView recommendPhoto3;
    @BindView(R.id.one_pic)
    ImageView onePic;
    @BindView(R.id.one_name)
    TextView oneName;
    @BindView(R.id.one_km)
    TextView oneKm;
    @BindView(R.id.two_pic)
    ImageView twoPic;
    @BindView(R.id.two_name)
    TextView twoName;
    @BindView(R.id.two_km)
    TextView twoKm;
    @BindView(R.id.three_pic)
    ImageView threePic;
    @BindView(R.id.three_name)
    TextView threeName;
    @BindView(R.id.three_km)
    TextView threeKm;
    @BindView(R.id.one_ll)
    LinearLayout oneLl;
    @BindView(R.id.two_ll)
    LinearLayout twoLl;
    @BindView(R.id.three_ll)
    LinearLayout threeLl;
    @BindView(R.id.right_iv)
    ImageView rightIv;
    @BindView(R.id.recommend_join)
    TextView recommendJoin;
    int type;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_recommend;
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type", -1);
        if (type == 1) {
            rightIv.setVisibility(View.GONE);
        } else if (type == 0) {
            oneLl.setVisibility(View.GONE);
            twoLl.setVisibility(View.GONE);
            threeLl.setVisibility(View.GONE);
            recommendJoin.setBackground(getResources().getDrawable(R.drawable.title_right_click_bg));
            recommendJoin.setText((getResources().getString(R.string.troops_join2)));
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View view) {

    }


    @OnClick({R.id.troops_photo_ll, R.id.troops_top_ll, R.id.left_iv, R.id.recommend_join, R.id.right_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.troops_photo_ll://查看群相册
                break;
            case R.id.troops_top_ll://查看排行榜
                break;
            case R.id.left_iv:
                finish();
                break;
            case R.id.recommend_join://申请加入
                break;
            case R.id.right_iv://设置
                startActivity(new Intent(this,UpdateActivity.class));
                break;
        }
    }

}
