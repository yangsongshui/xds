package com.pgt.xds.my;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.pgt.xds.BaseActivity;
import com.pgt.xds.R;
import com.pgt.xds.utils.DateUtil;
import com.pgt.xds.view.CompilePopupWindow;

import java.io.File;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

import static com.pgt.xds.utils.PicUtil.hasSdcard;

public class AddActivity extends BaseActivity implements View.OnFocusChangeListener {
    private static final int RESULT = 1;
    private static final int PHOTO_REQUEST_CUT = 2;
    private static final int CODE_CAMERA_REQUEST = 3;
    /* 自行车图片文件 */
    private static final String IMAGE_FILE_NAME = "bicycle_image.jpg";
    @BindView(R.id.bicycle_name_et)
    EditText bicycleNameEt;
    @BindView(R.id.bicycle_name_iv)
    ImageView bicycleNameIv;
    @BindView(R.id.bicycle_name_tv)
    TextView bicycleNameTv;
    @BindView(R.id.bicycle_type_et)
    EditText bicycleTypeEt;
    @BindView(R.id.bicycle_type_iv)
    ImageView bicycleTypeIv;
    @BindView(R.id.bicycle_type_tv)
    TextView bicycleTypeTv;
    @BindView(R.id.bicycle_time_tv)
    TextView bicycleTimeTv;
    @BindView(R.id.bicycle_standard_et)
    EditText bicycleStandardEt;
    @BindView(R.id.bicycle_standard_iv)
    ImageView bicycleStandardIv;
    @BindView(R.id.bicycle_standard_tv)
    TextView bicycleStandardTv;
    @BindView(R.id.bicycle_standard)
    LinearLayout bicycleStandard;
    @BindView(R.id.bicycle_pic)
    ImageView bicyclePic;
    private TimePickerView timePickerView;
    //自定义的弹出框类
    private CompilePopupWindow menuWindow;
    private Bitmap bitmap;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_add;
    }

    @Override
    protected void initView() {
        timePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                bicycleTimeTv.setText(DateUtil.dateToString(date, DateUtil.LONG_DATE_FORMAT3));

            }
        }).setType(new boolean[]{true,true,true,false,false,false})
                .build();
    }

    @Override
    protected void initData() {
        //实例化SelectPicPopupWindow
        menuWindow = new CompilePopupWindow(this, itemsOnClick);
    }

    @Override
    protected void initListener() {
        bicycleStandardEt.setOnFocusChangeListener(this);
        bicycleTypeEt.setOnFocusChangeListener(this);
        bicycleNameEt.setOnFocusChangeListener(this);
    }

    @Override
    public void onClick(View view) {

    }


    @OnClick({R.id.left_layout, R.id.bicycle_name_iv, R.id.bicycle_type_iv, R.id.bicycle_time_ll, R.id.bicycle_standard_iv, R.id.bicycle_pic_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.bicycle_name_iv:
                bicycleNameEt.setText("");
                break;
            case R.id.bicycle_type_iv:
                bicycleTypeEt.setText("");
                break;
            case R.id.bicycle_time_ll:
                timePickerView.show();
                break;
            case R.id.bicycle_standard_iv:
                bicycleStandardEt.setText("");
                break;
            case R.id.bicycle_pic_ll:
                //选择自行车图片
                menuWindow.showAtLocation(this.findViewById(R.id.activity_add), Gravity.BOTTOM, 0, 0); //设置layout在PopupWindow中显示的位;
                break;
        }
    }

    @Override
    public View[] filterViewByIds() {
        View[] views = {bicycleNameIv, bicycleTypeIv, bicycleStandardIv};
        return views;
    }

    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.bicycle_name_et, R.id.bicycle_type_et, R.id.bicycle_standard_et};
        return ids;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.bicycle_name_et:
                setMsg(bicycleNameIv, bicycleNameTv, bicycleNameEt, hasFocus);
                break;
            case R.id.bicycle_type_et:
                setMsg(bicycleTypeIv, bicycleTypeTv, bicycleTypeEt, hasFocus);
                break;
            case R.id.bicycle_standard_et:
                bicycleStandard.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
                setMsg(bicycleStandardIv, bicycleStandardTv, bicycleStandardEt, hasFocus);
                break;
        }
    }

    private void setMsg(ImageView iv, TextView tv, EditText et, boolean hasFocus) {
        iv.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        tv.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
        if (!hasFocus && et.getText().toString().length() > 0) {
            tv.setText(et.getText().toString());
            et.setText("");
        }
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.compile_photo_tv:
                    //相册
                    openGallery();
                    break;
                case R.id.compile_camera_tv:
                    //相机
                    openGamera();

                    break;
                default:
                    break;
            }
            menuWindow.dismiss();

        }

    };

    /**
     * 打开相机
     */
    private void openGamera() {
        // 跳转至拍照界面
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(Environment
                            .getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        }

        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }

    /**
     * 打开相册
     */
    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);// 打开相册
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
        intent.setType("image/*");
        startActivityForResult(intent, RESULT);
    }

    /**
     * 裁剪图片
     */
    private void crop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 450);
        intent.putExtra("outputY", 250);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);







    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT) {
            if (data != null) {
                Uri uri = data.getData();
                crop(uri);
            }
        } else if (requestCode == PHOTO_REQUEST_CUT) {
            if (data != null) {
                setImageToHeadView(data);
            }
        } else if (requestCode == CODE_CAMERA_REQUEST) {
            if (hasSdcard()) {
                File tempFile = new File(
                        Environment.getExternalStorageDirectory(),
                        IMAGE_FILE_NAME);
                crop(Uri.fromFile(tempFile));
            } else {
                Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
                        .show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            bitmap = extras.getParcelable("data");
            bicyclePic.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清除bitmap缓存
        bicyclePic = null;
        if (bitmap != null)
            bitmap.recycle();
    }
}
