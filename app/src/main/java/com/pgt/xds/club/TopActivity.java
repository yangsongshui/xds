package com.pgt.xds.club;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pgt.xds.base.BaseActivity;
import com.pgt.xds.connector.OnItemCheckListener;
import com.pgt.xds.R;
import com.pgt.xds.club.adapter.TopAdapter;
import com.pgt.xds.utils.Toastor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 排行榜界面
 */
public class TopActivity extends BaseActivity implements OnItemCheckListener {


    @BindView(R.id.top_rv)
    RecyclerView topRv;
    TopAdapter adapter;
    Toastor toastor;
    List<String> mList;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_top;
    }

    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        topRv.setLayoutManager(layoutManager);
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffxjlvinj5j20u011igri.jpg");
        mList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffxjlvinj5j20u011igri.jpg");
        mList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffxjlvinj5j20u011igri.jpg");
        mList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffxjlvinj5j20u011igri.jpg");
        mList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffxjlvinj5j20u011igri.jpg");
        mList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffxjlvinj5j20u011igri.jpg");
        mList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffxjlvinj5j20u011igri.jpg");
        mList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffxjlvinj5j20u011igri.jpg");
        adapter = new TopAdapter(mList, this);
        toastor = new Toastor(this);
        topRv.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        adapter.setOnItemCheckListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    @OnClick(R.id.left_layout)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void OnItemCheck(RecyclerView.ViewHolder viewHolder, int position) {

    }
}
