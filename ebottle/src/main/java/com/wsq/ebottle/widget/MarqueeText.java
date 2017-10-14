package com.wsq.ebottle.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MarqueeText extends TextView implements Runnable{

	private int currentScrollX=0;//当前滚动位置
	private boolean isStop = false; 
	private int textWidth; 
	private boolean isMeasure = false; 
	int screenWidth;
	
	public MarqueeText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public MarqueeText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public MarqueeText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}

	
	private void init() {
		// TODO Auto-generated method stub
		DisplayMetrics metrics=new DisplayMetrics();
		WindowManager manager=(WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		manager.getDefaultDisplay().getMetrics(metrics);
		screenWidth=metrics.widthPixels;//屏幕宽度
		Log.i("wsq","屏幕宽度="+screenWidth);
		currentScrollX=-screenWidth;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		
		super.onDraw(canvas);
		if(!isMeasure)
		{
			getTextWidth();
			isMeasure = true; 
		}
		post(this); 
		
	}

	//获取文字宽度
	private void getTextWidth() {
		Paint paint = this.getPaint();
		String str = this.getText().toString(); 
		textWidth = (int) paint.measureText(str); 
	}

	@Override
	public void run() 
	{
		currentScrollX+=6;// 滚动速度
		scrollTo(currentScrollX, 0); 
		if(currentScrollX >textWidth)
		{ 
			currentScrollX =-screenWidth; 
		}
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
			
	     post(this);
		}
	}


}
