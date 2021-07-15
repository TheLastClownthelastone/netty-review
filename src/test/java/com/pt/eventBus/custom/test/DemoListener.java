package com.pt.eventBus.custom.test;

import com.pt.eventBus.custom.ThreadMode;
import com.pt.eventBus.custom.WokerShecle;

/**
 * @author nate-pt
 * @date 2021/7/15 11:01
 * @Since 1.8
 * @Description
 */
public class DemoListener {

    @WokerShecle
    public void test1(Integer a){
        System.out.println(Thread.currentThread().getName());
        System.out.println(a);
    }

    @WokerShecle(threadmode = ThreadMode.ASYNC)
    public void test2(String str){
        System.out.println(Thread.currentThread().getName());
        System.out.println(str);
    }
}
