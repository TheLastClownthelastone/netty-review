package com.pt.eventBus;

import org.greenrobot.eventbus.EventBus;

/**
 * @author nate-pt
 * @date 2021/7/15 9:47
 * @Since 1.8
 * @Description
 */
public class Demo1 {

    public static void main(String[] args) {
        EventBus bus = EventBus.getDefault();
        BusListener busListener = new BusListener();
        bus.register(busListener);


        bus.post("23222");
        //bus.post("111");

    }
}
