package com.pgt.xds.club.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pgt.xds.R;
import com.pgt.xds.app.MyApplication;
import com.pgt.xds.club.model.ChatItemModel;
import com.pgt.xds.club.model.ChatModel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.BaseAdapter> {

    private List<ChatModel> dataList = new ArrayList<>();

    public void replaceAll(List<ChatModel> list) {
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void addAll(List<ChatModel> list) {
        if (dataList != null && list != null) {
            dataList.addAll(0,list);
         notifyDataSetChanged();
        }

    }

    @Override
    public ChatAdapter.BaseAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ChatModel.CHAT_A:
                return new ChatAViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_a, parent, false));
            case ChatModel.CHAT_B:
                return new ChatBViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_b, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ChatAdapter.BaseAdapter holder, int position) {
        holder.setData(dataList.get(position).object);
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).type;
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public class BaseAdapter extends RecyclerView.ViewHolder {

        public BaseAdapter(View itemView) {
            super(itemView);
        }

        void setData(Object object) {

        }
    }

    private class ChatAViewHolder extends BaseAdapter {
        private ImageView ic_user;
        private TextView tv;

        public ChatAViewHolder(View view) {
            super(view);
            ic_user = (ImageView) itemView.findViewById(R.id.ic_user);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }

        @Override
        void setData(Object object) {
            super.setData(object);
            ChatItemModel model = (ChatItemModel) object;
            //设置头像
            MyApplication.newInstance().getGlide().load(model.getIcon()).centerCrop().diskCacheStrategy(DiskCacheStrategy.RESULT).into(ic_user);
            //设置聊天内容
            tv.setText(model.getContent());
        }
    }

    private class ChatBViewHolder extends BaseAdapter {
        private ImageView ic_user;
        private TextView tv;

        public ChatBViewHolder(View view) {
            super(view);
            ic_user = (ImageView) itemView.findViewById(R.id.ic_user);
            tv = (TextView) itemView.findViewById(R.id.tv);

        }

        @Override
        void setData(Object object) {
            super.setData(object);
            ChatItemModel model = (ChatItemModel) object;
            //设置头像
            MyApplication.newInstance().getGlide().load(model.getIcon()).centerCrop().diskCacheStrategy(DiskCacheStrategy.RESULT).into(ic_user);
            //设置聊天内容
            tv.setText(model.getContent());
        }
    }
}
