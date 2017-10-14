package com.wsq.ebottle.widget;

import java.util.ArrayList;

import com.wsq.ebottle.R;
import com.wsq.ebottle.bean.RecordData;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class RecordBarViewDay extends View{

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
	
	private String date;
	private String week;

	//一些参数
	int ml;//毫升
	String time;//时间
	float temp; //温度
	static int barXOffset=50;
	int count=0;

	int select;//如果是1就是喂奶，如果是2就是饮水
	ArrayList<RecordData> datas;

	int nurseTime=0;//喝奶次数
	int totalInatke=0;//喝奶总量
	int aveIntake=0;//平均喝奶量
	String milkName="";//奶粉名称

	
	public RecordBarViewDay(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public RecordBarViewDay(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public RecordBarViewDay(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub\
		init();
	}
	//初始化
	@SuppressLint("ResourceAsColor") 
	private void init() {
		// TODO Auto-generated method stub
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

		//设置View的大小
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		int width=(int) (wm.getDefaultDisplay().getWidth()-getResources().getDimension(R.dimen.margin_20));
		//Log.i("wsq",width+"");
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.FILL_PARENT);
		lp.setMargins(20, 0, 20, 0);
		this.setLayoutParams(lp);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		//画左上角
		drawLeftTop(canvas);
		//画右上角日期
		drawDate(canvas);
		//中间的文字
		drawDes(canvas);
		//画刻度
		drawDegree(canvas);

		//画红色bar
	    if(datas!=null&&datas.size()>0)
	             drawBar(canvas);
		
	}

	//画红色柱状条
	private void drawBar(final Canvas canvas) {
		// TODO Auto-generated method stub 
		for(int i=0;i<datas.size();i++)
		{
			ml=datas.get(i).getMl();
			time=datas.get(i).getTime();
			temp=datas.get(i).getTemp();
			select=datas.get(i).getSelect();		
			if(select==1)
			{
				canvas.drawRect(80+barXOffset*i+1, getHeight()-(ml*2.2f),130+barXOffset*i,getHeight()-2,redPaint);
				canvas.drawText(time,80+barXOffset*i, getHeight()-(ml*2.2f)+txtPaint2.measureText("10"), txtPaint2);
				canvas.drawText(ml+"ML",80+barXOffset*i, getHeight()-(ml*2.2f)+txtPaint2.measureText("10")*2, txtPaint2);
				canvas.drawText(temp+"℃",80+barXOffset*i, getHeight()-(ml*2.2f)+txtPaint2.measureText("10")*3, txtPaint2);
			   
			}
			else if(select==2)
			{
				canvas.drawRect(80+barXOffset*i+1, getHeight()-(ml*2.2f),130+barXOffset*i,getHeight()-2,bluePaint);
				canvas.drawText(time,80+barXOffset*i, getHeight()-(ml*2.2f)+txtPaint2.measureText("10"), txtPaint2);
				canvas.drawText(ml+"ML",80+barXOffset*i, getHeight()-(ml*2.2f)+txtPaint2.measureText("10")*2, txtPaint2);
				canvas.drawText(temp+"℃",80+barXOffset*i, getHeight()-(ml*2.2f)+txtPaint2.measureText("10")*3, txtPaint2);
			}
		}
	}

//设置柱状条

	public void setDegree(ArrayList<RecordData> datas,String date,String week,int nurseTime,int totalIntake,String milkName)
	{
		this.datas=datas;
		this.date=date;
		this.week=week;
		this.nurseTime=nurseTime;
		this.totalInatke=totalIntake;
		this.aveIntake=totalIntake/nurseTime;
		this.milkName=milkName;
		if(datas.size()>0)
		      invalidate();
	}


	//画刻度
	private void drawDegree(Canvas canvas) {
		// TODO Auto-generated method stub
		int value=10;
		int offset=22;
		for(int i=1;i<=25;i++)
		{
			canvas.drawLine(2, getHeight()-offset, 15, getHeight()-offset, redPaint);
			if(i==25)
			{
			     canvas.drawText("MAX", 17,getHeight()-offset+5,redPaint);
			}else
			{
				canvas.drawText(""+value, 17,getHeight()-offset+5,redPaint);
			}
			value+=10;
			offset+=22;
		}
	}

	private void drawDes(Canvas canvas) {
		canvas.drawText("雅培", getWidth()-getContext().getResources().getDimension(R.dimen.margin_120),230,txtPaint);
		canvas.drawText("喝奶次数："+nurseTime+"次", getWidth()-getContext().getResources().getDimension(R.dimen.margin_120),260,txtPaint1);
		canvas.drawText("喝奶总量："+totalInatke+"ML", getWidth()-getContext().getResources().getDimension(R.dimen.margin_120),290,txtPaint1);
		canvas.drawText("平均每次："+aveIntake+"ML", getWidth()-getContext().getResources().getDimension(R.dimen.margin_120),320,txtPaint1);
	}

	//画日期
	private void drawDate(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawText(date, getWidth()-getContext().getResources().getDimension(R.dimen.margin_120),30,txtPaint1);
		canvas.drawText(week, getWidth()-getContext().getResources().getDimension(R.dimen.margin_120),60,txtPaint1);
	}



	//画左上角
	private void drawLeftTop(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawRect(30, 30, 90, 50, redPaint);
		canvas.drawText(getContext().getString(R.string.nurse), 100, 45,
				txtPaint);

		canvas.drawRect(30, 60, 90, 80, bluePaint);
		canvas.drawText(getContext().getString(R.string.water), 100, 75,
				txtPaint);
	}

	@SuppressLint("NewApi") @Override
	public void setBackground(Drawable background) {
		// TODO Auto-generated method stub
		Drawable drawable=getResources().getDrawable(R.drawable.brown_frame_bg);
		super.setBackground(drawable);
	}
}
