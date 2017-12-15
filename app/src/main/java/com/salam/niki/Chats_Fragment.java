package com.salam.niki;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Chats_Fragment extends Fragment {


    public TextView tvnochat;
    public ListView lschats;
    ArrayList<HashMap> receivers_list;
    HashMap<String,String> map;
    public  MsgDatabaseHandler msgDatabaseHandler;


    @Override
    public void onStart() {
        super.onStart();

        tvnochat = (TextView)getActivity().findViewById(R.id.tv_no_chat);
        lschats = (ListView)getActivity().findViewById(R.id.ls_chat);
        msgDatabaseHandler = new MsgDatabaseHandler(getContext());
        receivers_list = new ArrayList<>();

        getChatData();

        lschats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(),Chat_Activity.class);
                intent.putExtra("sender","salam");
                intent.putExtra("receiver",receivers_list.get(i).get("receiver").toString());
                startActivity(intent);
            }
        });

        //lschats.setAdapter(new chatAdapter());
    }

    private void getChatData() {
        List<Messages_Info> receivers = msgDatabaseHandler.getAllReceiversNames();
        receivers_list.clear();
        for (Messages_Info mi : receivers){
            map = new HashMap<String, String>();
            map.put("receiver",mi.get_receiver());
            receivers_list.add(map);
        }

        if (receivers_list.size()>0){
            tvnochat.setVisibility(View.GONE);
        }
        Log.e("receiver_list",receivers_list.toString());
        lschats.setAdapter(new chatAdapter(receivers_list,getContext()));

    }

    public Chats_Fragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats__fragments, container, false);
    }





}
