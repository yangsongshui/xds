package com.pgt.xds.club;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pgt.xds.BaseActivity;
import com.pgt.xds.R;
import com.pgt.xds.view.CompilePopupWindow;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static com.pgt.xds.utils.PicUtil.hasSdcard;

public class NewTroopsActivity extends BaseActivity {
    private static final int RESULT2 = 2;
    private static final int RESULT = 1;
    private static final int PHOTO_REQUEST_CUT = 2;
    private static final int CODE_CAMERA_REQUEST = 3;
    private static final String IMAGE_FILE_NAME = "troops_head_image.jpg";
    @BindView(R.id.next_btn)
    TextView nextBtn;
    @BindView(R.id.add_pic_iv)
    ImageView addPicIv;
    //自定义的弹出框类
    CompilePopupWindow menuWindow;
    Bitmap bitmap;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_new_troops;
    }

    @Override
    protected void initView() {
        nextBtn.setEnabled(false);
        //实例化SelectPicPopupWindow
        menuWindow = new CompilePopupWindow(this, itemsOnClick);
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

    @OnClick({R.id.left_layout, R.id.add_pic_iv, R.id.next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.add_pic_iv:
                //显示窗口
                menuWindow.showAtLocation(this.findViewById(R.id.activity_new_troops), Gravity.BOTTOM, 0, 0); //设置layout在PopupWindow中显示的位;
                break;
            case R.id.next_btn:
                startActivityForResult(new Intent(this, SetTroopsActivity.class), RESULT2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT2) {
            startActivity(new Intent(this, RidingTroopsActivity.class));
            finish();
        } else if (requestCode == RESULT) {
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

    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            bitmap = extras.getParcelable("data");
            addPicIv.setImageBitmap(bitmap);
            nextBtn.setEnabled(true);
            nextBtn.setTextColor(getResources().getColor(R.color.white));
            nextBtn.setBackgroundResource(R.drawable.title_right_click_bg);
            // bitmap.recycle();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        addPicIv = null;
        if (bitmap != null)
            bitmap.recycle();
    }
}
