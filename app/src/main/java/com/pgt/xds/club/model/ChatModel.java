package com.pgt.xds.club.model;

/**
 * Created by ys on 2017/6/29.
 */

public class ChatModel {
    public static final int CHAT_A = 1001;
    public static final int CHAT_B = 1002;
    public int type;
    public Object object;

    public ChatModel(int type, Object object) {
        this.type = type;
        this.object = object;
    }
}
