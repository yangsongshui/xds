package com.wsq.ebottle.widget;

import com.wsq.ebottle.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PathEffect;
import android.graphics.drawable.Drawable;
import android.nfc.cardemulation.OffHostApduService;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class RecordBarViewWeek extends View{

	
	private Paint paint;
	private Paint redPaint;
	private Paint bluePaint;
	private Paint txtPaint;
	private Paint txtPaint1;
	private Paint txtPaint2;
	private static int BROWN=Color.rgb(189,149,46);
	private static int RED=Color.rgb(239,79,124);
	private static int GREEN=Color.rgb(94,239,79);
	private static int GRAY=Color.rgb(166,166,166);
	private static int BLACK=Color.rgb(0,0,0);

	static int barSize=30;//bar的大小
	static int offset=65;//初始偏移
	static int gap=60;//bar的间隔

	//虚线画笔
	private Paint redDottedPaint;
	private Paint blueDottedPaint;

	String date;//日期
	String week; //星期
	int nurse_ml; //喝奶毫升
	int water_ml;//喝水毫升
	public RecordBarViewWeek(Context context) {
		super(context);
		init();
	}
	public RecordBarViewWeek(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public RecordBarViewWeek(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	//初始化
	private void init() {
		// TODO Auto-generated method stub
		//硬件加速
		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		
		paint=new Paint();
		paint.setStrokeWidth(3);
		paint.setColor(BROWN);
		paint.setStyle(Style.FILL);
		setBackground(null);
		
		redPaint=new Paint();
		redPaint.setColor(RED);
		redPaint.setAntiAlias(true);
		redPaint.setStrokeWidth(5);
		redPaint.setTextSize(getContext().getResources().getDimension(R.dimen.text_size_12));
		
		bluePaint=new Paint();
		bluePaint.setColor(GREEN);
		
		txtPaint=new Paint();
		txtPaint.setAntiAlias(true);
		txtPaint.setTextSize(getContext().getResources().getDimension(R.dimen.text_size_12));
		txtPaint.setColor(BLACK);
		
		txtPaint1=new Paint();
		txtPaint1.setAntiAlias(true);
		txtPaint1.setTextSize(getContext().getResources().getDimension(R.dimen.text_size_14));
		txtPaint1.setColor(GRAY);
		
		txtPaint2=new Paint();
		txtPaint2.setAntiAlias(true);
		txtPaint2.setTextSize(getContext().getResources().getDimension(R.dimen.text_size_8));
		txtPaint2.setColor(BLACK);
		
		redDottedPaint=new Paint();
		redDottedPaint.setAntiAlias(true);
		redDottedPaint.setStrokeWidth(2);
		redDottedPaint.setStyle(Style.STROKE);  
		redDottedPaint.setColor(RED);
		PathEffect effects = new DashPathEffect(new float[] {4,4,4,4}, 1);  
		redDottedPaint.setPathEffect(effects); 
		
		blueDottedPaint=new Paint();
		blueDottedPaint.setAntiAlias(true);
		blueDottedPaint.setStrokeWidth(2);
		blueDottedPaint.setStyle(Style.STROKE);
		blueDottedPaint.setColor(GREEN);
		PathEffect effects2 = new DashPathEffect(new float[] {4,4,4,4}, 1);
		blueDottedPaint.setPathEffect(effects2);

		//计算屏幕的大小
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		int width=(int) (wm.getDefaultDisplay().getWidth()-getResources().getDimension(R.dimen.margin_20));
		//设置View的大小
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.FILL_PARENT);
		lp.setMargins(20, 0, 20, 0);
		this.setLayoutParams(lp);
	}
	
	

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		drawLeftTop(canvas);
		drawDegree(canvas);
		drawDate(canvas);
		//画虚线
		drawDottedLine(canvas);
		//画bar
		drawBar(canvas);
	}



	//画柱状图
	private void drawBar(Canvas canvas) {
		// TODO Auto-generated method stub
		float avr=getContext().getResources().getDimension(R.dimen.margin_30)/100;
		int i=-1;
		if(week.equals("星期一"))
		{
			i=0;
		}else if(week.equals("星期二"))
		{
			i=1;
		}else if(week.equals("星期三"))
		{
			i=2;
		}else if(week.equals("星期四"))
		{
			i=3;
		}else if(week.equals("星期五"))
		{
			i=4;
		}else if(week.equals("星期六"))
		{
			i=5;
		}else if(week.equals("星期天"))
		{
			i=6;
		}
		if(nurse_ml!=0)
		 canvas.drawRect(offset+gap*i+2, getHeight()-nurse_ml*avr, offset+barSize+gap*i, getHeight()-2, redPaint);
		if(water_ml!=0)
		 canvas.drawRect(offset+gap*i+barSize, getHeight()-water_ml*avr, offset+barSize+gap*i+barSize, getHeight()-2,bluePaint);
		     
		 canvas.drawText(week, offset+gap*i+i, 
		    		 getHeight()-getContext().getResources().getDimension(R.dimen.margin_30)*10-20, txtPaint1);
	
		
	}
	//画虚线
	private void drawDottedLine(Canvas canvas) {
		// TODO Auto-generated method stub
		float avr=getContext().getResources().getDimension(R.dimen.margin_30)/100;
		canvas.drawLine(offset, getHeight()-800*avr, getWidth()-2, getHeight()-800*avr, redDottedPaint);
		canvas.drawLine(offset, getHeight()-300*avr, getWidth()-2, getHeight()-300*avr, blueDottedPaint);
	}
	
	private void drawDate(Canvas canvas) {

		String strDate=date;
		canvas.drawText(strDate, getWidth()/2-txtPaint1.measureText(strDate)/2,
				getContext().getResources().getDimension(R.dimen.margin_100), txtPaint1);
		
	}
	// 画左上角
	private void drawLeftTop(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawRect(30, 30, 90, 50, redPaint);
		canvas.drawText(getContext().getString(R.string.nurse), 100, 45,
				txtPaint);

		canvas.drawRect(30, 60, 90, 80, bluePaint);
		canvas.drawText(getContext().getString(R.string.water), 100, 75,
				txtPaint);
	}

	// 画刻度
	private void drawDegree(Canvas canvas) {
		// TODO Auto-generated method stub
		int value = 100;
		int offset = (int) getContext().getResources().getDimension(R.dimen.margin_30);
		for (int i = 1; i <=10; i++) {
			canvas.drawLine(2, getHeight() - offset, 15, getHeight() - offset,
					redPaint);
			if (i == 10) {
				canvas.drawText("MAX", 17, getHeight() - offset + 5, redPaint);
			} else {
				canvas.drawText("" + value, 17, getHeight() - offset + 5,
						redPaint);
			}
			value += 100;
			offset += getContext().getResources().getDimension(R.dimen.margin_30);
		}
	}
		
	@SuppressLint("NewApi") @Override
	public void setBackground(Drawable background) {
		// TODO Auto-generated method stub
		Drawable drawable=getResources().getDrawable(R.drawable.brown_frame_bg);
		super.setBackground(drawable);
	}

	
	public void setParam(String date,String week,int nurse_ml,int water_ml)
	{
		this.date=date;
		this.week=week;
		this.nurse_ml=nurse_ml;
		this.water_ml=water_ml;
		invalidate();
	}
	
}
