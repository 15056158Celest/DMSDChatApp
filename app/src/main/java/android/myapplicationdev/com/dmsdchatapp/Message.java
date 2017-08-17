package android.myapplicationdev.com.dmsdchatapp;

import java.io.Serializable;

/**
 * Created by 15056158 on 17/8/2017.
 */

public class Message implements Serializable {
    private String id;
    private String messageText;
    private String messageTime;
    private String messageUser;

    public Message(){
    }

    public Message(String messageText, String messageTime, String messageUser) {
        this.messageText = messageText;
        this.messageTime = messageTime;
        this.messageUser = messageUser;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }





}
