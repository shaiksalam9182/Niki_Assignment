package com.salam.niki;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Contacts_Fragment extends Fragment {


    TextView tvNoContacts;
    ListView lvContacts;
    ProgressBar pbContacts;
    ArrayList<HashMap> contacts_list;
    HashMap<String,String> map;
    SharedPreferences sd;
    SharedPreferences.Editor editor;
    DatabaseHandler db;




    @Override
    public void onStart() {
        super.onStart();

        sd = getActivity().getSharedPreferences("contacts_sync", Context.MODE_PRIVATE);
        editor = sd.edit();

        db = new DatabaseHandler(getContext());





        tvNoContacts = (TextView)getActivity().findViewById(R.id.tv_no_contacts);
        lvContacts = (ListView)getActivity().findViewById(R.id.lv_contacts);
        contacts_list = new ArrayList<>();
        pbContacts = (ProgressBar)getActivity().findViewById(R.id.pb_contacts);


        String sync = sd.getString("sync","null");

        if (sync.equals("yes")){
            readFromDatabase();
        }else {
            new AsyncGetContacts().execute();
        }



        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent chat = new Intent(getContext(),Chat_Activity.class);
                chat.putExtra("sender","salam");
                chat.putExtra("receiver",contacts_list.get(i).get("contact_no").toString());
                startActivity(chat);
            }
        });


        //getContacts();


    }

    private void readFromDatabase() {
        List<Contact> contacts = db.getAllContacts();

        for (Contact cn : contacts){
            map = new HashMap<String, String>();
            map.put("contact_name",cn.getName());
            map.put("contact_no",cn.getPhoneNumber());
            contacts_list.add(map);
        }

        Log.e("contacts_list",contacts_list.toString());
        Log.e("contact_count", String.valueOf(contacts_list.size()));
        Collections.sort(contacts_list, new Comparator<HashMap>() {
            @Override
            public int compare(HashMap hashMap, HashMap t1) {
                String first = hashMap.get("contact_name").toString();
                String second = t1.get("contact_name").toString();
                return first.compareTo(second);
            }
        });
        Log.e("contact_count", String.valueOf(contacts_list.size()));






        pbContacts.setVisibility(View.GONE);
        tvNoContacts.setVisibility(View.GONE);
        lvContacts.setAdapter(new ContactsAdapter(contacts_list,getActivity()));
    }

    private ArrayList<HashMap> getContacts() {

        ContentResolver cr = getActivity().getContentResolver();


        Cursor c = cr.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);

        if (c.getCount()>0){
            //tvNoContacts.setVisibility(View.GONE);

            while (c.moveToNext()){

                String contactid = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phone_no = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" = "+contactid,null,null);
                if (phone_no.getCount()>0){
                    phone_no.moveToNext();
                    map = new HashMap<String, String>();
                    map.put("contact_no",phone_no.getString(phone_no.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll("\\s","").replaceAll("\\+",""));
                    map.put("contact_id",contactid);
                    map.put("contact_name",c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));

                    contacts_list.add(map);

                }

                // while (phone_no.moveToNext()){
                  //      Log.e("cursor_phone_no", phone_no.getString(phone_no.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

                //}




            }

            Log.e("contacts_data",contacts_list.toString());
        }else {
            Log.e("Contacts_data","NO contacts Found");
            tvNoContacts.setText("No Contacts Found In This Phone");
        }

        Log.e("contat_count", String.valueOf(contacts_list.size()));


        Collections.sort(contacts_list, new Comparator<HashMap>() {
            @Override
            public int compare(HashMap hashMap, HashMap t1) {
                String first = hashMap.get("contact_name").toString();
                String second = t1.get("contact_name").toString();
                return first.compareTo(second);
            }
        });



        storeContactsInSqlite(contacts_list);


        return contacts_list;

        //


    }

    private void storeContactsInSqlite(ArrayList<HashMap> contacts_list) {
        for (int i=0;i<contacts_list.size();i++){
            db.addContact(new Contact(contacts_list.get(i).get("contact_name").toString(),contacts_list.get(i).get("contact_no").toString()));
        }
        editor.putString("sync","yes");
        editor.commit();
    }


    public Contacts_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts_, container, false);
    }

    private class AsyncGetContacts extends AsyncTask<Void,Void,ArrayList<HashMap>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbContacts.setVisibility(View.VISIBLE);
            tvNoContacts.setVisibility(View.GONE);
        }

        @Override
        protected ArrayList<HashMap> doInBackground(Void... voids) {
            return getContacts();
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap> hashMaps) {
            super.onPostExecute(hashMaps);
            pbContacts.setVisibility(View.GONE);

            lvContacts.setAdapter(new ContactsAdapter(hashMaps,getActivity()));
        }
    }
}
