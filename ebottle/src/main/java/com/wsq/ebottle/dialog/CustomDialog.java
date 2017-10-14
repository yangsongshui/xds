package com.wsq.ebottle.dialog;

import com.wsq.ebottle.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

public class CustomDialog extends Dialog{

	
	public CustomDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CustomDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder
	{
		private Context context;
		private String title;
		private String message;
		private String cancel_btnText;
		private String confirm_btnText;
		private View contentView;
		
		//按钮事件
		private DialogInterface.OnClickListener confirm_btnClickListener;
		private DialogInterface.OnClickListener cancel_btnClickListener;
		
		public Builder(Context context)
		{
			this.context=context;
		}
		
		/*设置对话框信息*/
		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}


		public Builder setMessage(int message) {
			this.message = (String) context.getText(message);
			return this;
		}

		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}


		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}



		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}


		public Builder setPositiveButton(int confirm_btnText,
				DialogInterface.OnClickListener listener) {
			this.confirm_btnText = (String) context.getText(confirm_btnText);
			this.confirm_btnClickListener = listener;
			return this;
		}


		public Builder setPositiveButton(String confirm_btnText,
				DialogInterface.OnClickListener listener) {
			this.confirm_btnText = confirm_btnText;
			this.confirm_btnClickListener = listener;
			return this;
		}


		public Builder setNegativeButton(int cancel_btnText,
				DialogInterface.OnClickListener listener) {
			this.cancel_btnText = (String) context.getText(cancel_btnText);
			this.cancel_btnClickListener = listener;
			return this;
		}


		public Builder setNegativeButton(String cancel_btnText,
				DialogInterface.OnClickListener listener) {
			this.cancel_btnText = cancel_btnText;
			this.cancel_btnClickListener = listener;
			return this;
		}

       public CustomDialog create()
       {
    	   LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	   final CustomDialog dialog=new CustomDialog(context, R.style.CustomDialogStyle);
    	   View layout = inflater.inflate(R.layout.customdialog, null);
    	   dialog.addContentView(layout, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			((TextView) layout.findViewById(R.id.title)).setText(title);
			((TextView) layout.findViewById(R.id.title)).getPaint().setFakeBoldText(true);
			if (title == null || title.trim().length() == 0) {
				((TextView) layout.findViewById(R.id.message)).setGravity(Gravity.CENTER);
			}
			if (message != null) 
			{
				((TextView) layout.findViewById(R.id.message)).setText(message);
			}
			//取消按钮
			if (cancel_btnText != null) {
				((Button) layout.findViewById(R.id.cancel_btn))
						.setText(cancel_btnText);
				if (cancel_btnClickListener != null) {
					((Button) layout.findViewById(R.id.cancel_btn))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) 
								{
									cancel_btnClickListener.onClick(dialog,DialogInterface.BUTTON_NEGATIVE);
								}
							});
				} else 
				{
					((Button) layout.findViewById(R.id.cancel_btn))
							.setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									dialog.dismiss();
								}
							});
				}
			}
			//确定按钮
			if (confirm_btnText != null) {
				((Button) layout.findViewById(R.id.confirm_btn))
						.setText(confirm_btnText);
				if (confirm_btnClickListener != null) {
					((Button) layout.findViewById(R.id.confirm_btn))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v)
								{
									confirm_btnClickListener.onClick(dialog,DialogInterface.BUTTON_POSITIVE);
								}
							});
				} else {
					((Button) layout.findViewById(R.id.confirm_btn))
							.setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									dialog.dismiss();
								}
							});
				}
			}			
		  dialog.setContentView(layout);
    	   
		  return dialog;
    	   
       }
	}
	
	
}
