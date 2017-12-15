package com.salam.niki;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by raj on 14-Dec-17.
 */

class chatAdapter extends BaseAdapter {

    ArrayList<HashMap> list;
    Context context;


    public chatAdapter(ArrayList<HashMap> receivers_list, Context chats_fragment) {
        list = receivers_list;
        context = chats_fragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.custom_chat_view,null);

        TextView tvname = (TextView)v.findViewById(R.id.tv_chat_receiver_name);


        tvname.setText(list.get(i).get("receiver").toString());
        return v;
    }
}
