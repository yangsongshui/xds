package com.wsq.ebottle;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity{
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		init();
	}

	private void init() {

		//判断是否是第一次登陆
		//延迟两秒跳转
		Handler handler=new Handler();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent intent=new Intent(SplashActivity.this,WelcomeActivity.class);
				startActivity(intent);
				finish();
			}
		}, 2000);
	}

}
