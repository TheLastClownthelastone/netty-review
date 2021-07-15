package com.pt.eventBus;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

/**
 * @author nate-pt
 * @date 2021/7/15 9:43
 * @Since 1.8
 * @Description 添加监听器
 */
public class BusListener {

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void listOne(){
        System.out.println(Thread.currentThread().getName());
        System.out.println("监听无参函数");
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void listenerString(String param){
        System.out.println("方法名：listenerString");
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void listTwo(String param){
        System.out.println("方法名：listTwo");
    }
}

