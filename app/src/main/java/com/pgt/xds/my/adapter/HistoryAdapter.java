package com.pgt.xds.my.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.pgt.xds.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by omni20170501 on 2017/6/5.
 */

public class HistoryAdapter extends BaseExpandableListAdapter {

    private Context context;
    private Map<String, List<String>> data = new HashMap<>();
    //  获得父项的数量
    @Override
    public int getGroupCount() {
        return 0;
    }

    //  获得某个父项的子项数目
    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    //  获得某个父项
    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    //  获得某个父项的某个子项
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    //  获得某个父项的id
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //  获得某个父项的某个子项的id
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //  获得父项显示的view
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
           // LayoutInflater inflater = (LayoutInflater) HistoryActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           // convertView = inflater.inflate(R.layout.history_group, null);
        }
        convertView.setTag(R.layout.history_group, parent);
        convertView.setTag(R.layout.history_child, -1);


        return convertView;
    }

    //  获得子项显示的view
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
