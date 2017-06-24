package com.pgt.xds.my;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.pgt.xds.R;
import com.pgt.xds.utils.Toastor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SeekActivity extends Activity implements AMap.OnMyLocationChangeListener, GeocodeSearch.OnGeocodeSearchListener {
    @BindView(R.id.seek_num_et)
    EditText seekNumEt;
    @BindView(R.id.seek_address)
    TextView seekAddress;
    @BindView(R.id.seek_km)
    TextView seekKm;
    @BindView(R.id.seek_min)
    TextView seekMin;
    @BindView(R.id.seek_hour)
    TextView seekHour;

    MapView mapView;
    AMap aMap;
    Toastor toast;
    MyLocationStyle myLocationStyle;
    GeocodeSearch geocoderSearch;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_seek);
        toast = new Toastor(this);
        ButterKnife.bind(this);
        mapView = (MapView) findViewById(R.id.myMap);
        mapView.onCreate(savedInstanceState);// 此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。
        initMap();
    }

    private void initMap() {
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
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

        if (location != null) {
            LatLonPoint latLonPoint = new LatLonPoint(location.getLatitude(), location.getLongitude());
            // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
            RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 100, GeocodeSearch.AMAP);
            geocoderSearch.getFromLocationAsyn(query);
        }
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

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        //解析result获取地址描述信息

        if (i == AMapException.CODE_AMAP_SUCCESS) {
            if (regeocodeResult.getRegeocodeAddress() != null
                    && regeocodeResult.getRegeocodeAddress().getFormatAddress() != null) {

                String addressName = regeocodeResult.getRegeocodeAddress().getCity()+  regeocodeResult.getRegeocodeAddress().getDistrict()
                        +regeocodeResult.getRegeocodeAddress().getStreetNumber().getStreet()+regeocodeResult.getRegeocodeAddress().getStreetNumber().getNumber();
                seekAddress.setText(addressName);
            }
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @OnClick({R.id.left_layout, R.id.seek_search_iv, R.id.login_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.seek_search_iv:
                break;
            case R.id.login_btn:
                break;
        }
    }
}
