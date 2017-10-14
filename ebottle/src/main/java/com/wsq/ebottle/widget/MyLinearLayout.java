package com.wsq.ebottle.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class MyLinearLayout extends LinearLayout{

	public MyLinearLayout(Context context) {
		super(context);
	}

	public MyLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expendSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE<<2, MeasureSpec.AT_MOST);
		super.onMeasure(expendSpec, heightMeasureSpec);
	}
}
