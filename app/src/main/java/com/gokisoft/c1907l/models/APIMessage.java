package com.gokisoft.c1907l.models;

/**
 * Created by Diep.Tran on 8/6/21.
 */

public class APIMessage {
    int status;
    String msg;
    int id;

    public APIMessage() {
    }

    public APIMessage(int status, String msg, int id) {
        this.status = status;
        this.msg = msg;
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "APIMessage{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", id=" + id +
                '}';
    }
}
