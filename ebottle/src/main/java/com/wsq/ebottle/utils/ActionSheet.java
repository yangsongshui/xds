package com.wsq.ebottle.utils;



import com.wsq.ebottle.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActionSheet {

	public ActionSheet()
	{
		
	}

	public static Dialog showSheet(Context context, final OnActionSheetSelected actionSheetSelected)
	{

		final Dialog dlg=new Dialog(context, R.style.DialogSheet);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.actionsheet, null);
		final int cFullFillWidth = 10000;
		layout.setMinimumWidth(cFullFillWidth);

		TextView takePhoto = (TextView) layout.findViewById(R.id.txt_take_photo);
		TextView getPhoto = (TextView) layout.findViewById(R.id.txt_get_photo);
		TextView txtCancel = (TextView) layout.findViewById(R.id.cancel);

		takePhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				actionSheetSelected.onClick(0);
				dlg.dismiss();
			}
		});

		getPhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				actionSheetSelected.onClick(1);
				dlg.dismiss();
			}
		});
		
		txtCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				actionSheetSelected.onClick(2);
				dlg.dismiss();
			}
		});
		

		Window w = dlg.getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.x = 0;
		final int cMakeBottom = -1000;
		lp.y = cMakeBottom;
		lp.gravity = Gravity.BOTTOM;
		dlg.onWindowAttributesChanged(lp);
		dlg.setCanceledOnTouchOutside(false);
		dlg.setContentView(layout);
		dlg.show();
		return dlg;
	}
	
	public interface OnActionSheetSelected
	{
		void onClick(int action);
	}
}
