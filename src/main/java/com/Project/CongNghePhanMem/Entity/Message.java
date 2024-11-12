package com.Project.CongNghePhanMem.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name= "Message", schema="dbo")
public class Message {
    @EmbeddedId
    private Message_id id;
    @Column(name= "content")
    private String name;
    public Message_id getId() {
        return id;
    }
    public void setId(Message_id id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Message() {
    }
    public Message(Message_id id, String name) {
        this.id = id;
        this.name = name;
    }
    

}
