package com.pgt.xds.my;

import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.pgt.xds.base.BaseActivity;
import com.pgt.xds.R;
import com.pgt.xds.utils.GetCity;

import butterknife.BindView;
import butterknife.OnClick;

public class CompileAddressActivity extends BaseActivity {

    @BindView(R.id.compile_city)
    TextView compileCity;
    private OptionsPickerView optionsPickerView;//地区选择
    GetCity getCity;
    String address = "";

    @Override
    protected int getContentViewId() {
        return R.layout.activity_compile_address;
    }

    @Override
    protected void initView() {
        getCity = new GetCity(this);
        optionsPickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx;
                //返回的分别是三个级别的选中位置
                String city = getCity.getOptions1Items().get(options1).getPickerViewText();
                //  如果是直辖市或者特别行政区只设置市和区/县
                if ("北京".equals(city) || "上海".equals(city) || "天津".equals(city) || "重庆".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
                    tx = city + "-" + getCity.getOptions3Items().get(options1).get(options2).get(options3).getPickerViewText();
                } else {
                    tx = getCity.getOptions1Items().get(options1).getPickerViewText() + "-" +
                            getCity.getOptions2Items().get(options1).get(options2) + "-" +
                            getCity.getOptions3Items().get(options1).get(options2).get(options3).getPickerViewText();
                }

                compileCity.setText(tx);
            }
        }).isDialog(false).build();
        optionsPickerView.setPicker(getCity.getOptions1Items(), getCity.getOptions2Items(), getCity.getOptions3Items());//三级选择器

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


    @OnClick({R.id.left_layout, R.id.search_address, R.id.compile_city_ll, R.id.city_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.search_address:
                //使用当前位置
                break;
            case R.id.compile_city_ll:
                //选择地址
                optionsPickerView.show();
                break;
            case R.id.city_btn:
                break;
        }
    }
}
