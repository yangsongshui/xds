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

import com.pgt.xds.BaseActivity;
import com.pgt.xds.R;
import com.pgt.xds.view.CompilePopupWindow;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static com.pgt.xds.utils.PicUtil.hasSdcard;

/**
 * 编辑个人信息界面
 */
public class CompileInfoActivity extends BaseActivity implements View.OnFocusChangeListener {

    private static final int RESULT = 1;
    private static final int PHOTO_REQUEST_CUT = 2;
    private static final int CODE_CAMERA_REQUEST = 3;
    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";

    @BindView(R.id.my_pic)
    ImageView myPic;
    @BindView(R.id.my_name)
    TextView myName;
    @BindView(R.id.my_phone)
    TextView myPhone;
    @BindView(R.id.my_sex)
    TextView mySex;
    @BindView(R.id.compile_height_tv)
    TextView compileHeightTv;
    @BindView(R.id.compile_weight_tv)
    TextView compileWeightTv;
    @BindView(R.id.my_age)
    TextView myAge;
    @BindView(R.id.my_address)
    TextView myAddress;

    //自定义的弹出框类
    CompilePopupWindow menuWindow;
    Bitmap bitmap;
    @BindView(R.id.compile_height_et)
    EditText compileHeightEt;
    @BindView(R.id.compile_weight_et)
    EditText compileWeightEt;
    @BindView(R.id.compile_height_iv)
    ImageView compileHeightIv;
    @BindView(R.id.compile_weight_iv)
    ImageView compileWeightIv;
    @BindView(R.id.compile_weight)
    LinearLayout compileWeight;
    @BindView(R.id.compile_height)
    LinearLayout compileHeight;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_compile_info;
    }

    @Override
    protected void initView() {
        //实例化SelectPicPopupWindow
        menuWindow = new CompilePopupWindow(this, itemsOnClick);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        compileHeightEt.setOnFocusChangeListener(this);
        compileWeightEt.setOnFocusChangeListener(this);
    }

    @Override
    public void onClick(View view) {

    }


    @OnClick({R.id.left_layout, R.id.compile_pic_ll, R.id.compile_name_ll, R.id.compile_phone_ll, R.id.compile_sex_ll,
            R.id.compile_weight_iv, R.id.compile_height_iv, R.id.compile_age_ll, R.id.compile_address_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.compile_pic_ll:
                //显示窗口
                menuWindow.showAtLocation(this.findViewById(R.id.activity_compile_info), Gravity.BOTTOM, 0, 0); //设置layout在PopupWindow中显示的位;
                break;
            case R.id.compile_name_ll:
                startActivity(new Intent(this, CompileNameActivity.class));
                break;
            case R.id.compile_phone_ll:
                startActivity(new Intent(this, CompilePhoneActivity.class));
                break;
            case R.id.compile_sex_ll:
                startActivity(new Intent(this, CompileSexActivity.class));
                break;
            case R.id.compile_height_iv:
                compileHeightEt.setText("");
                break;
            case R.id.compile_weight_iv:
                compileWeightEt.setText("");
                break;
            case R.id.compile_age_ll:
                startActivity(new Intent(this, CompileAgeActivity.class));
                break;
            case R.id.compile_address_ll:
                startActivity(new Intent(this, CompileAddressActivity.class));
                break;
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
        intent.putExtra("outputX", 250);
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
            myPic.setImageBitmap(bitmap);

            // bitmap.recycle();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myPic = null;
        if (bitmap != null)
            bitmap.recycle();
    }

    //判断EditText获取焦点监听
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            if (v.getId() == R.id.compile_height_et) {
                compileHeightIv.setVisibility(View.VISIBLE);
                compileHeight.setVisibility(View.GONE);
            } else if (v.getId() == R.id.compile_weight_et) {
                compileWeightIv.setVisibility(View.VISIBLE);
                compileWeight.setVisibility(View.GONE);

            }
        } else {
            if (v.getId() == R.id.compile_height_et) {
                compileHeight.setVisibility(View.VISIBLE);
                compileHeightIv.setVisibility(View.INVISIBLE);
                String height = compileHeightEt.getText().toString();
                if (height.length() > 0)
                    compileHeightTv.setText(height);
                compileHeightEt.setText("");

            } else if (v.getId() == R.id.compile_weight_et) {
                compileWeight.setVisibility(View.VISIBLE);
                compileWeightIv.setVisibility(View.INVISIBLE);
                String weight = compileWeightEt.getText().toString();

                if (weight.length() > 0)
                    compileWeightTv.setText(weight);
                compileWeightEt.setText("");
            }
        }
    }

    @Override
    public View[] filterViewByIds() {
        View[] views = {compileHeightIv, compileWeightIv};
        return views;
    }

    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.compile_height_et, R.id.compile_weight_et};
        return ids;
    }

}
