package com.pgt.xds.riding;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.pgt.xds.R;
import com.pgt.xds.utils.Toastor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends Activity implements AMap.OnMyLocationChangeListener {

    @BindView(R.id.details_date_tv)
    TextView detailsDateTv;
    @BindView(R.id.details_start_tv)
    TextView detailsStartTv;
    @BindView(R.id.details_end_tv)
    TextView detailsEndTv;
    @BindView(R.id.details_type_tv)
    TextView detailsTypeTv;
    @BindView(R.id.details_linear_tv)
    TextView detailsLinearTv;
    @BindView(R.id.details_mileage_tv)
    TextView detailsMileageTv;
    @BindView(R.id.details_time_tv)
    TextView detailsTimeTv;
    @BindView(R.id.details_cadence_tv)
    TextView detailsCadenceTv;
    @BindView(R.id.details_calorie_tv)
    TextView detailsCalorieTv;
    @BindView(R.id.details_temperature_tv)
    TextView detailsTemperatureTv;

    MapView mapView;
    AMap aMap;
    Toastor toast;
    MyLocationStyle myLocationStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        toast = new Toastor(this);

        mapView = (MapView) findViewById(R.id.myMap);
        mapView.onCreate(savedInstanceState);// 此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。
        initMap();
    }

    @OnClick({R.id.left_layout, R.id.right_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.right_layout:
                break;
        }
    }
    private void initMap() {
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mapView.getMap();
        }

        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        // 自定义定位蓝点图标
        aMap.moveCamera(CameraUpdateFactory.zoomBy(6));
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.drawable.point4));
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        //定位监听
        aMap.setOnMyLocationChangeListener(this);
    }

    @Override
    public void onMyLocationChange(Location location) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        aMap = null;
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
}
