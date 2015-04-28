package com.jiuwu.picboutique.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiuwu.picboutique.bean.RssInfo;
import com.jiuwu.picboutique.view.Item_List_View;
import com.jiuwu.picboutique.view.Item_List_View_;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Terry on 2015/4/18.
 */
public class ListAdapter extends BaseAdapter {
    private Context context;

    private List<RssInfo> list;


    private boolean isFalseData;

    public ListAdapter(Context context,List<RssInfo> list){
        this.context = context;
        this.list = list;
        if (this.list == null) {
            this.list = new ArrayList<RssInfo>();
        }
    }

    @Override
    public int getCount() {
        if (this.list == null ) {
            return 0;
        }
        return this.list.size();
    }

    @Override
    public RssInfo getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Item_List_View itemView = null;
        if (convertView == null || (convertView instanceof TextView)) {
            itemView = Item_List_View_.build(context);
            convertView = itemView;
        } else {
            itemView = (Item_List_View) convertView;
        }
//        itemView.setDoctorListInfo(getItem(position));
//        itemView.setPadding(0, 0, 0, 8);
        itemView.initViews(getItem(position));

        return convertView;
    }

    public void upData(List<RssInfo> dataList,boolean isFirst){
        if (this.list == null) {
            this.list = new ArrayList<RssInfo>();
        }
        if (isFalseData) {
            this.list.remove(this.list.size()-1);
            isFalseData = false;
        }
        if (isFirst) {
            this.list.clear();
        }
        this.list.addAll(dataList);
        notifyDataSetChanged();
    }

    public void addFalseData(RssInfo data){
        if (this.list == null) {
            this.list = new ArrayList<RssInfo>();
        }
        this.list.add(data);
        isFalseData = true;
        notifyDataSetChanged();
    }

}
