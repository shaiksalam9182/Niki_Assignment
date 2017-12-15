package com.salam.niki;

/**
 * Created by raj on 14-Dec-17.
 */

public class Messages_Info {

    //private variables
    String _msg;
    String _sender;
    String _receiver;

    // Empty constructor
    public Messages_Info(){

    }
    // constructor
    public Messages_Info(String msg, String sender, String receiver){
        this._msg = msg;
        this._sender = sender;
        this._receiver = receiver;
    }

    // constructor
    public Messages_Info(String sender, String receiver){
        this._sender = sender;
        this._receiver = receiver;
    }
    // getting ID
    public String get_msg(){
        return this._msg;
    }

    // setting id
    public void set_msg(String msg){
        this._msg = msg;
    }

    // getting name
    public String get_sender(){
        return this._sender;
    }

    // setting name
    public void set_sender(String sender){
        this._sender = sender;
    }

    // getting phone number
    public String get_receiver(){
        return this._receiver;
    }

    // setting phone number
    public void set_receiver(String receiver){
        this._receiver = receiver;
    }
}
