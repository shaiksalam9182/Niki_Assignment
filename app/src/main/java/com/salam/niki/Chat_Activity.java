package com.salam.niki;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Chat_Activity extends AppCompatActivity {

    ListView lvmsg;
    EditText etmsg;
    Button btsend;
    String sender,receiver;
    ArrayList<HashMap> msglist;
    HashMap<String,String> map;
    MsgDatabaseHandler msghadler;
    FirebaseDatabase database;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Messages");


        msghadler = new MsgDatabaseHandler(this);
        msglist = new ArrayList<>();

        sender = getIntent().getStringExtra("sender");
        receiver = getIntent().getStringExtra("receiver");


        lvmsg = (ListView)findViewById(R.id.lv_messages);
        etmsg = (EditText)findViewById(R.id.et_msg);
        btsend=  (Button)findViewById(R.id.bt_send);

        readMessages();



        btsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = etmsg.getText().toString();
                if (msg.equals("")){
                    Toast.makeText(Chat_Activity.this,"Message Can't Be Empty",Toast.LENGTH_LONG).show();
                }else {
                    insertIntoDatabase(msg);
                }
            }


        });
    }

    private void insertIntoDatabase(String msg) {
        MsgDatabaseHandler msghandler = new MsgDatabaseHandler(this);
        msghandler.addMsg(new Messages_Info(msg,sender,receiver));
        //Toast.makeText(Chat_Activity.this,"message sent",Toast.LENGTH_LONG).show();
        sendToFirebase(msg);
        readMessages();
    }

    private void sendToFirebase(String msg) {
        String key = ref.push().getKey();
        ref.child(key).child("msg").setValue(msg);
        ref.child(key).child("sender").setValue(sender);
        ref.child(key).child("receiver").setValue(receiver);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(Chat_Activity.this,"message sent",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Chat_Activity.this,"unable to Send Message",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void readMessages() {
        List<Messages_Info> messages = msghadler.getAllMessages(receiver);
        msglist.clear();

        for (Messages_Info mi : messages){
            map = new HashMap<String, String>();
            map.put("msg",mi.get_msg());
            map.put("sender",mi.get_sender());
            map.put("receiver",mi.get_receiver());
            msglist.add(map);
        }

        Log.e("msglist",msglist.toString());

        lvmsg.setAdapter(new MessageAdapter(msglist,this));




    }



}
