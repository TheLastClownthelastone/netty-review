package com.pt.eventBus.custom.test;

import com.pt.eventBus.custom.PtBus;

/**
 * @author nate-pt
 * @date 2021/7/15 11:00
 * @Since 1.8
 * @Description 测试
 */
public class Demo1 {

    public static void main(String[] args) {
        PtBus ptBus = PtBus.getInstance();
        ptBus.register(new DemoListener());
        ptBus.send(1);
        ptBus.send("33333");
        ptBus.send(22222);
    }
}
