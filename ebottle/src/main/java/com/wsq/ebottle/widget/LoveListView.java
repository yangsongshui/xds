package com.wsq.ebottle.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class LoveListView extends ListView{

	public LoveListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public LoveListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public LoveListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int expendSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE<<2,MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expendSpec);
	}

}
