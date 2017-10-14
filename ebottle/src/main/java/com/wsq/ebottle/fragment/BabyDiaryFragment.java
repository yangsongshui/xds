package com.wsq.ebottle.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.wsq.ebottle.R;
import com.wsq.ebottle.activity.AddDiaryActivity;
import com.wsq.ebottle.adapter.DiaryListAdapter;
import com.wsq.ebottle.db.dao.DiaryTableDao;

public class BabyDiaryFragment extends Fragment implements OnClickListener{
	
	private TextView txtAddDiary;
	private ListView listDiary;
	private DiaryListAdapter diaryListAdapter;
	
	private static int ADD_DIARY_RESULT=1001;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_baby_diary, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	
	private void initView() {
		// TODO Auto-generated method stub
		
		txtAddDiary=(TextView)getView().findViewById(R.id.txt_add_diary);
		txtAddDiary.setOnClickListener(this);
		
		listDiary=(ListView)getView().findViewById(R.id.list_diary);
		onRefreshList();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.txt_add_diary:
			Intent intent=new Intent(getActivity(), AddDiaryActivity.class);
			intent.putExtra("wsq","shuju");
			getActivity().startActivityForResult(intent, ADD_DIARY_RESULT);
			break;

		default:
			break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==ADD_DIARY_RESULT)
		{
			if(resultCode==1)
			{
				onRefreshList();
			}
		}
		
		
	}
	
	Handler handler=new Handler()
	{
		public void handleMessage(android.os.Message msg) {
			
			if(msg.what==1)
			{
				DiaryTableDao dao=new DiaryTableDao(getActivity());
				diaryListAdapter=new DiaryListAdapter(getActivity(),dao.getAllDiary());
				listDiary.setAdapter(diaryListAdapter);
			}
			
		};
	};



	//刷新列表
	protected void onRefreshList() {
		handler.sendEmptyMessage(1);
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub

		super.onDestroyView();
	}
	
}
