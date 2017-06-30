package com.pgt.xds.riding;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.pgt.xds.R;
import com.pgt.xds.utils.Toastor;
import com.pgt.xds.view.SharePopuoWindow;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends Activity implements AMap.OnMyLocationChangeListener, View.OnClickListener {

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
    @BindView(R.id.map)
    ImageView map;
    MapView mapView;
    AMap aMap;
    Toastor toastor;
    MyLocationStyle myLocationStyle;
    private SharePopuoWindow sharePopuoWindow;
    private Handler handler;
    private Runnable myRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        toastor = new Toastor(this);
        mapView = (MapView) findViewById(R.id.myMap);
        mapView.onCreate(savedInstanceState);// 此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。
        initMap();
        sharePopuoWindow = new SharePopuoWindow(this, this);
        handler = new Handler(Looper.getMainLooper());
        myRunnable = new Runnable() {
            @Override
            public void run() {
                View viewScreen = getWindow().getDecorView();
                viewScreen.setDrawingCacheEnabled(true);
                viewScreen.buildDrawingCache();
                //获取当前屏幕的大小
                int width = getWindow().getDecorView().getRootView().getWidth();
                int height = getWindow().getDecorView().getRootView().getHeight();
                Bitmap bitmap1 = Bitmap.createBitmap(viewScreen.getDrawingCache(), 0, 0, width, height);
                viewScreen.destroyDrawingCache();
                UMImage image = new UMImage(DetailsActivity.this, bitmap1);//bitmap文件
                image.compressStyle = UMImage.CompressStyle.QUALITY;
                new ShareAction(DetailsActivity.this).setPlatform(platform)
                        .withText("喜德盛")
                        .withMedia(image)
                        .setCallback(umShareListener)
                        .share();
            }
        };
    }

    @OnClick({R.id.left_layout, R.id.right_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.right_layout:
                sharePopuoWindow.showAtLocation(this.findViewById(R.id.relativeLayout), Gravity.BOTTOM, 0, 0); //设置layout在PopupWindow中显示的位;
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

    @Override
    public void onClick(View v) {
        sharePopuoWindow.dismiss();
        switch (v.getId()) {
            case R.id.qq_iv:
                UMShare(SHARE_MEDIA.QQ);
                platform = SHARE_MEDIA.QQ;
                break;
            case R.id.wechat_tv:
                //   sharePopuoWindow.dismiss();
                UMShare(SHARE_MEDIA.WEIXIN);
                platform = SHARE_MEDIA.WEIXIN;
                break;
            case R.id.moments_iv:
                UMShare(SHARE_MEDIA.WEIXIN_CIRCLE);
                platform = SHARE_MEDIA.WEIXIN_CIRCLE;
                break;
            case R.id.sina_iv:
                UMShare(SHARE_MEDIA.SINA);
                platform = SHARE_MEDIA.SINA;
                break;
        }
    }

    SHARE_MEDIA platform;

    private void UMShare(final SHARE_MEDIA platform) {
        /**
         * 对地图进行截屏
         */
        aMap.getMapScreenShot(new AMap.OnMapScreenShotListener() {
            @Override
            public void onMapScreenShot(Bitmap bitmap) {

            }

            @Override
            public void onMapScreenShot(Bitmap bitmap, int status) {
                if (null == bitmap) {
                    return;
                }
                map.setVisibility(View.VISIBLE);
                mapView.setVisibility(View.GONE);
                map.setImageBitmap(bitmap);

                handler.postDelayed(myRunnable, 500);


            }
        });
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            toastor.showSingletonToast(platform + " 分享成功");
            map.setVisibility(View.GONE);
            mapView.setVisibility(View.VISIBLE);
            map.setImageBitmap(null);
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Log.e("onError", "throw:" + t.getLocalizedMessage());
            toastor.showSingletonToast(platform + " 分享失败");
            map.setVisibility(View.GONE);
            mapView.setVisibility(View.VISIBLE);
            map.setImageBitmap(null);

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            toastor.showSingletonToast(platform + " 分享取消");
            map.setVisibility(View.GONE);
            mapView.setVisibility(View.VISIBLE);
            map.setImageBitmap(null);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
