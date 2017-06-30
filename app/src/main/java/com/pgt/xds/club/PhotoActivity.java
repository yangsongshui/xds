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
        mList.add("http://img.my.csdn.net/uploads/201508/05/1438760758_3497.jpg");
        mList.add("http://img.my.csdn.net/uploads/201508/05/1438760758_6667.jpg");
        mList.add("http://img.my.csdn.net/uploads/201508/05/1438760757_3588.jpg");
        mList.add("http://img.my.csdn.net/uploads/201508/05/1438760756_3304.jpg");
        mList.add("http://img.my.csdn.net/uploads/201508/05/1438760755_6715.jpeg");
        mList.add("http://img.my.csdn.net/uploads/201508/05/1438760726_5120.jpg");
        mList.add("http://img.my.csdn.net/uploads/201508/05/1438760726_8364.jpg");
        mList.add("http://img.my.csdn.net/uploads/201508/05/1438760725_4031.jpg");
        mList.add("http://img.my.csdn.net/uploads/201508/05/1438760724_9463.jpg");
        mList.add("http://img.my.csdn.net/uploads/201508/05/1438760724_2371.jpg");
        mList.add("http://img.my.csdn.net/uploads/201508/05/1438760707_4653.jpg");
        mList.add("http://img.my.csdn.net/uploads/201508/05/1438760706_6864.jpg");
        mList.add("http://img.my.csdn.net/uploads/201508/05/1438760706_9279.jpg");
        mList.add("http://img.my.csdn.net/uploads/201508/05/1438760704_2341.jpg");
        mList.add("http://img.my.csdn.net/uploads/201508/05/1438760704_5707.jpg");
        mList.add("http://img.my.csdn.net/uploads/201508/05/1438760685_5091.jpg");
        mList.add("http://img.my.csdn.net/uploads/201508/05/1438760685_4444.jpg");
        mList.add("http://img.my.csdn.net/uploads/201508/05/1438760684_8827.jpg");
        mList.add("http://img.my.csdn.net/uploads/201508/05/1438760683_3691.jpg");
        mList.add("http://img.my.csdn.net/uploads/201508/05/1438760683_7315.jpg");
        mList.add("http://img.my.csdn.net/uploads/201508/05/1438760663_7318.jpg");
        mList.add("http://img.my.csdn.net/uploads/201508/05/1438760662_3454.jpg");
        mList.add("http://img.my.csdn.net/uploads/201508/05/1438760662_5113.jpg");
        mList.add("http://img.my.csdn.net/uploads/201508/05/1438760661_3305.jpg");
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
