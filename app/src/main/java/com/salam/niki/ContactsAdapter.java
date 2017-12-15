package com.salam.niki;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
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

class ContactsAdapter extends BaseAdapter {


    ArrayList<HashMap> data;
    Activity activity_local;
    public ContactsAdapter(ArrayList<HashMap> contacts_list, FragmentActivity activity) {
        data = contacts_list;


        activity_local = activity;
    }

    @Override
    public int getCount() {
        return data.size();
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
        LayoutInflater inflater = (LayoutInflater)activity_local.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.custom_contacts_view,null);

        TextView tvname = (TextView)v.findViewById(R.id.tv_contact_name);
        TextView tvno = (TextView)v.findViewById(R.id.tv_contact_no);

        tvname.setText(data.get(i).get("contact_name").toString());
        tvno.setText(data.get(i).get("contact_no").toString());

        return v;
    }
}
