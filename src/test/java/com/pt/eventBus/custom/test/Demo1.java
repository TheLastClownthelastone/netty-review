package com.pt.eventBus.custom.test;

import com.pt.eventBus.custom.PtBus;
import org.junit.Test;

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

    @Test
    public void exec1(){
        PtBus build = PtBus.getBuilder().build();

        build.register(new DemoListener());
        build.sendPool("1111");
    }
}
