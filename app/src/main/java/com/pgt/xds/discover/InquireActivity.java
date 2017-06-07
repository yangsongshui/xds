package com.pgt.xds.discover;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.pgt.xds.BaseActivity;
import com.pgt.xds.OnItemCheckListener;
import com.pgt.xds.R;
import com.pgt.xds.discover.adapter.ShopAdapter;
import com.pgt.xds.utils.GetCity;
import com.pgt.xds.utils.Toastor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 门市查询界面
 */
public class InquireActivity extends BaseActivity implements OnItemCheckListener {

    @BindView(R.id.inquire_province)
    TextView inquireProvince;
    @BindView(R.id.inquire_city)
    TextView inquireCity;
    @BindView(R.id.inquire_rv)
    RecyclerView inquireRv;
    ShopAdapter adapter;
    GetCity getCity;
    List<String> mList;
    Toastor toastor;
    private OptionsPickerView optionsPickerView;//地区选择

    @Override
    protected int getContentViewId() {
        return R.layout.activity_inquire;
    }

    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        inquireRv.setLayoutManager(layoutManager);
        getCity = new GetCity(this);
        optionsPickerView = new OptionsPickerView.Builder(this, onOptionsSelectListener).build();
        optionsPickerView.setPicker(getCity.getOptions1Items(), getCity.getOptions2Items());//二级选择器
        toastor = new Toastor(this);
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        adapter = new ShopAdapter(mList, this);
        inquireRv.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        adapter.setOnItemCheckListener(this);
    }

    @Override
    public void onClick(View view) {

    }


    @OnClick({R.id.left_layout, R.id.inquire_province_ll, R.id.inquire_city_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            default:
                optionsPickerView.show();
                break;
        }
    }

    OptionsPickerView.OnOptionsSelectListener onOptionsSelectListener = new OptionsPickerView.OnOptionsSelectListener() {
        @Override
        public void onOptionsSelect(int options1, int options2, int options3, View v) {
            //返回的分别是两个级别的选中位置
            String province = getCity.getOptions1Items().get(options1).getPickerViewText();
            String city = getCity.getOptions2Items().get(options1).get(options2);
            inquireCity.setTextColor(getResources().getColor(R.color.rangoon_green));
            inquireProvince.setTextColor(getResources().getColor(R.color.rangoon_green));
            inquireProvince.setText(province);
            inquireCity.setText(city);
            mList.add("123");
            mList.add("123");
            mList.add("123");
            adapter.setItems(mList);
        }
    };

    @Override
    public void OnItemCheck(RecyclerView.ViewHolder viewHolder, int position) {
        toastor.showSingletonToast("点击了" + position);
    }
}
