package com.pgt.xds.club;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pgt.xds.base.BaseActivity;
import com.pgt.xds.connector.OnItemCheckListener;
import com.pgt.xds.R;
import com.pgt.xds.club.adapter.MemberAdapter;
import com.pgt.xds.utils.Toastor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MemberActivity extends BaseActivity implements OnItemCheckListener {


    @BindView(R.id.team_pic)
    ImageView teamPic;
    @BindView(R.id.team_sex)
    TextView teamSex;
    @BindView(R.id.team_lv)
    TextView teamLv;
    @BindView(R.id.member_rv)
    RecyclerView memberRv;
    List<String> mList;
    MemberAdapter adapter;
    Toastor toastor;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_member;
    }

    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        memberRv.setLayoutManager(layoutManager);
        toastor = new Toastor(this);
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mList.add("111");
        mList.add("111");
        mList.add("111");
        adapter = new MemberAdapter(mList, this);
        memberRv.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        adapter.setOnItemCheckListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    @OnClick({R.id.left_layout, R.id.troops_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.troops_search:
                break;
        }
    }

    @Override
    public void OnItemCheck(RecyclerView.ViewHolder viewHolder, int position) {

    }
}
