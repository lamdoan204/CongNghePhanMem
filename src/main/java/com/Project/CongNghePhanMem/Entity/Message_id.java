package com.Project.CongNghePhanMem.Entity;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Message_id implements Serializable
 {
    @Column(name= "sender_id" )
    private int sender_id;
    @Column(name="receiver_id")
    private int receiver_id;
    @Column(name= "time_sent")
    private Date time_sent;
    public int getSender_id() {
        return sender_id;
    }
    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }
    public int getReceiver_id() {
        return receiver_id;
    }
    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }
    public Date getTime_sent() {
        return time_sent;
    }
    public void setTime_sent(Date time_sent) {
        this.time_sent = time_sent;
    }
    public Message_id() {
    }
    public Message_id(int sender_id, int receiver_id, Date time_sent) {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.time_sent = time_sent;
    }


}
