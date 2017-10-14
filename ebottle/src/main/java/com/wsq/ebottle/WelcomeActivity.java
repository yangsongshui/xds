package com.wsq.ebottle;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class WelcomeActivity extends Activity{
	
	private Animation animation;
	private LinearLayout linearLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		init();
		
	}
	
	private void init() {
		// TODO Auto-generated method stub
		linearLayout=(LinearLayout)findViewById(R.id.welcome);
		animation=AnimationUtils.loadAnimation(this,R.anim.welcome_anim);
		animation.setFillAfter(true);
		linearLayout.setAnimation(animation);
		
		
		animation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {

			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {

			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				Intent intent=new Intent(WelcomeActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
				
			}
		});
	}

}
