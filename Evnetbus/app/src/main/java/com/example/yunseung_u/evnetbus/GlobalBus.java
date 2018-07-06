package com.example.yunseung_u.evnetbus;

import org.greenrobot.eventbus.EventBus;

public class GlobalBus {
    private static EventBus sbus;
    public static EventBus getBus(){
        if(sbus == null)
            sbus = EventBus.getDefault();
        return sbus;
    }
}
