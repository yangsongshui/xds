package com.wsq.ebottle.widget;

import com.wsq.ebottle.R;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class EditTextWithDel extends EditText{
	
	private Context context;
	private Drawable imgAble;
	
	private onDelListener delListener;

	public EditTextWithDel(Context context) {
		super(context);
		this.context=context;
		init();
	}

	public EditTextWithDel(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		init();
	}

	public EditTextWithDel(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context=context;
		init();
	}
	

	private void init() {
		// TODO Auto-generated method stub
		imgAble=context.getResources().getDrawable(R.drawable.delete_gray);
		addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				if(isFocused())
				    setDrawable();
			}
		});
		
		setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				// TODO Auto-generated method stub
				
				if(arg1)
				{
					if(length()>0)
					{
						setCompoundDrawablesWithIntrinsicBounds(null, null, imgAble, null);
						
					}else
					{
						setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
						
					}
				}else
				{
					
						setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
						
				}
				
			}
		});
		
	}

	
	private void setDrawable() {
		// TODO Auto-generated method stub
		if(length()>0)
		{
			setCompoundDrawablesWithIntrinsicBounds(null, null, imgAble, null);
			
		}else
		{
			setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
			
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(imgAble!=null&&event.getAction()==MotionEvent.ACTION_UP)
		{
		    int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Rect rect=new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 50;
            if(rect.contains(eventX, eventY))
            {
            	setText("");
            	requestFocus();
            	
            }
		}
		
		
		return super.onTouchEvent(event);
	}
	
	public interface onDelListener
	{
		void onClicked(boolean isClicked);
	}
	
	public void setOnDelListener(onDelListener delListener)
	{
		this.delListener=delListener;
		
	}

}
