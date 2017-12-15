package com.salam.niki;

import android.app.Activity;
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

class MessageAdapter extends BaseAdapter {
    ArrayList<HashMap> messagelist;
    Activity act;

    public MessageAdapter(ArrayList<HashMap> msglist, Chat_Activity chat_activity) {
        messagelist = msglist;
        act = chat_activity;
    }

    @Override
    public int getCount() {
        return messagelist.size();
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
        LayoutInflater inflater = (LayoutInflater)act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.custom_message_view,null);

        TextView tvmsg = (TextView)v.findViewById(R.id.tv_msg);
        TextView tvsender = (TextView)v.findViewById(R.id.tv_sender);
        TextView tvreceiver = (TextView)v.findViewById(R.id.tv_receiver);

        tvmsg.setText(messagelist.get(i).get("msg").toString());
        tvsender.setText(messagelist.get(i).get("sender").toString());
        tvreceiver.setText(messagelist.get(i).get("receiver").toString());

        return v;
    }
}
