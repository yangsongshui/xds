package com.wsq.ebottle.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.wsq.ebittle.common.Constants;
import com.wsq.ebottle.R;
import com.wsq.ebottle.utils.ActionSheet;
import com.wsq.ebottle.utils.ActionSheet.OnActionSheetSelected;
import com.wsq.ebottle.utils.ToastUtils;
import com.wsq.ebottle.widget.CircleImageView;
import com.wsq.ebottle.widget.EditTextWithDel;
import com.wsq.ebottle.widget.EditTextWithDel.onDelListener;

public class BabyDataFragment extends Fragment implements OnClickListener,OnActionSheetSelected{

	private ImageView imgTou;
	//图片的文件名称
	private String fileName;
	private File curPhotoFile;
	private static final int CAMERA_WITH_DATA = 3023;
	private static final int CROP_WITH_DATA = 3024;
	private static final int PICK_WHIT_ALBUM=3025;
	private static final int CROP_WHIT_ALBUM=3026;
	
	private Uri albumUri;
	private EditText editBirth;
	private Button btnSave;
	
 
	private CircleImageView cimgHead;
	private EditText editNurseBottleId;
	private EditText editWaterBottleId;
	private EditTextWithDel editBabyName;
	private EditTextWithDel editBabyBirth;
	private EditTextWithDel editBabyWeight;
	private int sex=-1; //性别0代表女孩，1代表男孩
	private RadioGroup sexGroup;
	private RadioButton radioMale;
	private RadioButton radioFemale;

	//共享文件保存信息
	SharedPreferences.Editor editor=null;
	SharedPreferences preferences;
	//接口
	HeadImgListener imgListener=null;

	//设置标志判断是拍照还是选择存在的照片
	int action=0;
	
	boolean isClickedDel;

	//设备地址
	String deviceName;
	String deviceAddress;
	static int UPDATE_ID=1001;

	//广播
	BroadcastReceiver receiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			String action=arg1.getAction();
			if(Constants.BROADCAST_BABY_UPDATA.equals(action))
			{
				handler.sendEmptyMessage(UPDATE_ID);
				Log.i("wsq","在baby——data");
			}
		}
	};
	
	
	OnCheckedChangeListener changeListener=new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup arg0, int arg1) {
			// TODO Auto-generated method stub
			switch (arg1) {
			case R.id.radio_female:
				sex=0;
				break;

			case R.id.radio_male:
				sex=1;
				break;
			}
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_data_baby, container,false);
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		getActivity().registerReceiver(receiver, makeGattUpdateIntentFilter());
		
		initView();
	}
	
	private static IntentFilter makeGattUpdateIntentFilter() 
	{
		IntentFilter filter=new IntentFilter();
		filter.addAction(Constants.BROADCAST_BABY_UPDATA);
		return filter;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getActivity().unregisterReceiver(receiver);
	}

	//初始化
	private void initView() {
		// TODO Auto-generated method stub
		imgTou=(ImageView)getView().findViewById(R.id.img_tou);
		imgTou.setOnClickListener(this);
		editBirth=(EditText)getView().findViewById(R.id.edit_baby_birth);
		editBirth.setOnClickListener(this);
		btnSave=(Button)getView().findViewById(R.id.btn_save);
		btnSave.setOnClickListener(this);
		
		editNurseBottleId=(EditText)getView().findViewById(R.id.edit_nurse_bottle_id);	
		editWaterBottleId=(EditText)getView().findViewById(R.id.edit_water_bottle_id);
		editBabyName=(EditTextWithDel)getView().findViewById(R.id.edit_baby_name);
		editBabyBirth=(EditTextWithDel)getView().findViewById(R.id.edit_baby_birth);
		
		editBabyBirth.setOnDelListener(new onDelListener() {
			
			@Override
			public void onClicked(boolean isClicked) {
				// TODO Auto-generated method stub
				isClickedDel=isClicked;
			}
		});
		
		editBabyWeight=(EditTextWithDel)getView().findViewById(R.id.edit_baby_weight);
		sexGroup=(RadioGroup)getView().findViewById(R.id.radio_group_sex);
		sexGroup.setOnCheckedChangeListener(changeListener);
		
		cimgHead=(CircleImageView)getView().findViewById(R.id.img_tou);
		radioMale=(RadioButton)getView().findViewById(R.id.radio_male);
		radioFemale=(RadioButton)getView().findViewById(R.id.radio_female);
		
		editor=getActivity().getSharedPreferences("baby_data", Context.MODE_PRIVATE).edit();
		preferences=getActivity().getSharedPreferences("baby_data",Context.MODE_PRIVATE);
		
		Bitmap bitmap=BitmapFactory.decodeFile(preferences.getString("img_head_path", ""));
		cimgHead.setImageBitmap(bitmap);
		editNurseBottleId.setText(preferences.getString("nurse_bottle_id",""));
		editWaterBottleId.setText(preferences.getString("water_bottle_id",""));
		editBabyName.setText(preferences.getString("baby_name",""));
		editBabyBirth.setText(preferences.getString("baby_birth",""));
		editBabyWeight.setText(preferences.getString("baby_weight",""));
		
		if(preferences.getInt("baby_sex",-1)==0)
		{
			radioFemale.setChecked(true);
			
		}else if(preferences.getInt("baby_sex",-1)==1)
		{
			radioMale.setChecked(true);
		}
			
		
		
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) 
		{
		case R.id.img_tou:
			ActionSheet.showSheet(getActivity(), this);
			break;

		case R.id.edit_baby_birth:
			onPickDate();
			break;
		case R.id.btn_save:
			onSaveData();
			break;
		}
	}


	//设置日期
	private void onPickDate() {
		// TODO Auto-generated method stub
		Calendar calendar=Calendar.getInstance();
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH);
		int day=calendar.get(Calendar.DAY_OF_MONTH);
		DatePickerDialog pickerDialog=new DatePickerDialog(getActivity(), dateSetListener,year,month, day);
		pickerDialog.setCanceledOnTouchOutside(true);
	
		pickerDialog.show();		  
		
		
	}
	
	private DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() 
	{
		
		@Override
		public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3)
		{
			// TODO Auto-generated method stub
			String strDate="";
			String str=strDate.format("%d年%d月%d日",arg1,arg2+1,arg3);
			editBirth.setText(str);
		}
	};

	//底部对话框点击事件
	@Override
	public void onClick(int action) {
		// TODO Auto-generated method stub
		switch (action) {
		case 0:
			onTakePhoto();
			break;

		case 1:
			onPickAlbum();
			break;
		}
	}


	//拍照
	private void onTakePhoto() {
		// TODO Auto-generated method stub
		fileName=System.currentTimeMillis()+".jpg";
		curPhotoFile=new File(Environment.getExternalStorageDirectory(),fileName);
		Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(curPhotoFile));
		getActivity().startActivityForResult(intent, CAMERA_WITH_DATA);
	}

	//从相册获取
	private void onPickAlbum() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(Intent.ACTION_PICK,null);
		intent.setType("image/*");
		startActivityForResult(intent, PICK_WHIT_ALBUM);
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		if(resultCode!=getActivity().RESULT_OK)
		{
			//Log.i("wsq","fragment--onActityResult="+requestCode);
			return;
		}
		switch (requestCode)
		{
		case CAMERA_WITH_DATA:
			Log.i("wsq","result");
			cropImageUri(Uri.fromFile(curPhotoFile), 600, 600, CROP_WITH_DATA);
			break;

		case CROP_WITH_DATA:
			action=1;
			Uri uri=Uri.fromFile(curPhotoFile);
			if(uri!=null)
			{
				try {
					Bitmap bitmap=BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
					imgTou.setImageBitmap(bitmap);
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
			
		case PICK_WHIT_ALBUM:
			albumUri=data.getData();
			if(albumUri!=null)
				 Log.i("wsq",albumUri.getPath());
			     cropImageUri(albumUri, 600, 600, CROP_WHIT_ALBUM);
			break;
		case CROP_WHIT_ALBUM:
			action=2;
			try {
				Bitmap bitmap=BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(albumUri));
				imgTou.setImageBitmap(bitmap);
				
				
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		
	}

	//剪切
	public void cropImageUri(Uri uri,int outputX, int outputY, int requestCode)
	{
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", true);
		intent.putExtra("aspectX", 2);
		intent.putExtra("aspectY", 2);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		startActivityForResult(intent, requestCode);
	}

	//保存数据到数据库
	private void onSaveData()
	{
		// TODO Auto-generated method stub
		if(TextUtils.isEmpty(editBabyName.getText().toString().trim()))
		{
			ToastUtils.show(getActivity().getString(R.string.ERROR_00001), Gravity.CENTER);
			return;
		}else if(TextUtils.isEmpty(editBabyBirth.getText().toString().trim()))
		{
			ToastUtils.show(getActivity().getString(R.string.ERROR_00001), Gravity.CENTER);
			return;
			
		}else if(TextUtils.isEmpty(editBabyWeight.getText().toString().trim()))
		{
			ToastUtils.show(getActivity().getString(R.string.ERROR_00001), Gravity.CENTER);
			return;
		}else if(sex==-1)
		{
			ToastUtils.show(getActivity().getString(R.string.ERROR_00001), Gravity.CENTER);
			return;
		}
		
		if(!TextUtils.isEmpty(editNurseBottleId.getText().toString().trim()))
	    {
		    editor.putString("nurse_bottle_id", editNurseBottleId.getText().toString());
	    }
		if(!TextUtils.isEmpty(editWaterBottleId.getText().toString().trim()))
		{
			editor.putString("water_bottle_id", editWaterBottleId.getText().toString());
		}
		if(action==1)
		{
			if(curPhotoFile!=null)
			{
			    editor.putString("img_head_path",curPhotoFile.getPath());

				//传递数据
			    if(imgListener!=null)
			    {
			        imgListener.onImgPath(curPhotoFile.getPath());
			        //Log.i("wsq","babydata");
			    }
			    
			}
		}else if(action==2)
		{
			if(albumUri!=null)
			{
				 editor.putString("img_head_path",getPath(albumUri));
				//传递数据
				  if(imgListener!=null)
				  {
				        imgListener.onImgPath(getPath(albumUri));
				        //Log.i("wsq","babydata");
				  }
			}
		}
		
		editor.putString("baby_name", editBabyName.getText().toString());
		editor.putInt("baby_sex", sex);
		editor.putString("baby_birth", editBabyBirth.getText().toString());
		editor.putString("baby_weight",editBabyWeight.getText().toString());
		
		if(editor.commit())
		{
			ToastUtils.show(getActivity().getString(R.string.save_success), Gravity.TOP);
		}
		
	}
	 /**
     *从相册得到的url转换为SD卡中图片路径
     */
	private String getPath(Uri uri) {
		
		if(TextUtils.isEmpty(uri.getAuthority()))
		{
			return null;
		}
		String[] projection={MediaStore.Images.Media.DATA};
		@SuppressWarnings("deprecation")
		Cursor cursor=getActivity().managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String path = cursor.getString(column_index);
		return path;
	}
	
	
	//接口传递图像路径
      public interface HeadImgListener 
      {
    	  public void onImgPath(String path);
      }
      
      public void setHeadImgListener(HeadImgListener imgListener)
      {
    	  this.imgListener=imgListener;
    	  //Log.i("wsq","setListener");
      }


      //更新UI
      Handler handler=new Handler()
      {
    	public void handleMessage(android.os.Message msg) {
    		
    		if(msg.what==UPDATE_ID)
    		{
    			editNurseBottleId.setText(preferences.getString("nurse_bottle_id",""));
    			editWaterBottleId.setText(preferences.getString("water_bottle_id",""));
    		}	
    	};  
      };
      
      
      public void onHiddenChanged(boolean hidden) {
    	  
    	  if(!hidden)
    	  {
    		  editNurseBottleId.setText(preferences.getString("nurse_bottle_id",""));
  			  editWaterBottleId.setText(preferences.getString("water_bottle_id",""));
    	  }
    	  
      };
}
