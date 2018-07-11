package com.example.administrator.materialdesign.common;

/**
 * Created by Administrator on 2018/7/9 0009.
 */

public class MessageEvent {
    public MessageEvent(){

    }
    public MessageEvent(EventType type){
        this.type = type;
    }
    public EventType type;

    public enum EventType{
        EXIT,//退出时发送
        NO_NET,//没有网络
        HAS_NET,//有网
        SUBMIT_SUCCESS//提交任务成功
    }
}
