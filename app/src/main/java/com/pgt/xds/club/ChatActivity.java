package com.pgt.xds.club;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pgt.xds.R;
import com.pgt.xds.app.MyApplication;
import com.pgt.xds.base.BaseActivity;
import com.pgt.xds.club.adapter.ChatAdapter;
import com.pgt.xds.club.model.ChatItemModel;
import com.pgt.xds.club.model.ChatModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class ChatActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.group_name)
    TextView groupName;
    @BindView(R.id.chat_recycler)
    RecyclerView chatRecycler;
    @BindView(R.id.id_swipe_ly)
    SwipeRefreshLayout idSwipeLy;
    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.tvSend)
    TextView tvSend;
    private String content;
    private ChatAdapter adapter;
    List<ChatModel> data;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initView() {
        data = new ArrayList<>();
        content = "";
        chatRecycler.setHasFixedSize(true);
        adapter = new ChatAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(true);
        chatRecycler.setLayoutManager(layoutManager);
        chatRecycler.setAdapter(adapter = new ChatAdapter());
        adapter.replaceAll(getTestAdData());
    }

    @Override
    protected void initData() {
        idSwipeLy.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
    }

    @Override
    protected void initListener() {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                content = s.toString().trim();
                if (content.length() > 0) {
                    tvSend.setEnabled(true);
                    tvSend.setBackgroundResource(R.drawable.title_right_click_bg);
                } else {
                    tvSend.setEnabled(false);
                    tvSend.setBackgroundResource(R.color.btn_no_click);
                }
            }
        });
        idSwipeLy.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View view) {

    }


    @OnClick({R.id.left_layout, R.id.right_layout, R.id.tvSend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.right_layout:
                break;
            case R.id.tvSend:
                if (content.length() > 0) {
                    data.clear();
                    ChatItemModel model = new ChatItemModel();
                    model.setIcon("http://img.my.csdn.net/uploads/201508/05/1438760499_5081.jpg");
                    model.setContent(content);
                    data.add(new ChatModel(ChatModel.CHAT_B, model));
                    adapter.addAll(data);
                    //发送
                    et.setText("");

                } else {
                    MyApplication.newInstance().toastor.showSingletonToast("发送消息不能为空");
                }

                break;
        }
    }

    public static List<ChatModel> getTestAdData() {
        ArrayList<ChatModel> models = new ArrayList<>();
        ChatItemModel model = new ChatItemModel();
        model.setContent("狼人杀么？");
        model.setIcon("http://img.my.csdn.net/uploads/201508/05/1438760758_3497.jpg");
        models.add(0,new ChatModel(ChatModel.CHAT_A, model));
        ChatItemModel model2 = new ChatItemModel();
        model2.setContent("不玩");
        model2.setIcon("http://img.my.csdn.net/uploads/201508/05/1438760478_3128.jpg");
        models.add(0,new ChatModel(ChatModel.CHAT_A, model2));
        ChatItemModel model3 = new ChatItemModel();
        model3.setContent("玩什么狼人杀 还是来王者农药吧");
        model3.setIcon("http://img.my.csdn.net/uploads/201508/05/1438760498_7007.jpg");
        models.add(0,new ChatModel(ChatModel.CHAT_A, model3));
        ChatItemModel model4 = new ChatItemModel();
        model4.setContent("来来来~~~大神带你飞");
        model4.setIcon("http://img.my.csdn.net/uploads/201508/05/1438760530_5750.jpg");
        models.add(0,new ChatModel(ChatModel.CHAT_A, model4));
        ChatItemModel model5 = new ChatItemModel();
        model5.setContent("那你很棒哦");
        model5.setIcon("http://img.my.csdn.net/uploads/201508/05/1438760499_5081.jpg");
        models.add(0,new ChatModel(ChatModel.CHAT_B, model5));
        ChatItemModel model6 = new ChatItemModel();
        model6.setContent("五黑车缺一位啦");
        model6.setIcon("http://img.my.csdn.net/uploads/201508/05/1438760758_6667.jpg");
        models.add(0,new ChatModel(ChatModel.CHAT_A, model6));
        return models;
    }
    @Override
    public View[] filterViewByIds() {
        View[] views = {tvSend,et};
        return views;
    }

    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.et};
        return ids;
    }


    @Override
    public void onRefresh() {

    }
}
