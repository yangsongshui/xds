package com.pgt.xds.club;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pgt.xds.base.BaseActivity;
import com.pgt.xds.connector.OnItemCheckListener;
import com.pgt.xds.R;
import com.pgt.xds.club.adapter.PhotoAdapter;
import com.pgt.xds.utils.Toastor;
import com.pgt.xds.view.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 群相册界面
 */
public class PhotoActivity extends BaseActivity implements OnItemCheckListener {

    @BindView(R.id.photo_rv)
    RecyclerView photoRv;

    PhotoAdapter adapter;
    Toastor toastor;
    List<String> mList;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_photo;
    }

    @Override
    protected void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        photoRv.setLayoutManager(gridLayoutManager);
        photoRv.addItemDecoration(new DividerGridItemDecoration(this));
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
        adapter = new PhotoAdapter(mList, this);
        toastor = new Toastor(this);
        photoRv.setAdapter(adapter);

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
