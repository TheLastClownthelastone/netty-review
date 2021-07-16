package com.pt.lang;

import org.junit.Test;

import java.io.IOException;

/**
 * @author nate-pt
 * @date 2021/7/16 11:39
 * @Since 1.8
 * @Description
 */
public class RunTimeTest {

    /**
     * exec 通过命令行的形式调用其他进程
     * @throws IOException
     */
    @Test
    public void exec1() throws IOException {
        for (int i = 0; i < 10; i++) {
            System.out.println("_______________");
        }
        String commond = "D:\\apache-zookeeper-3.5.9-bin\\bin\\zkServer.cmd";
        Process exec = Runtime.getRuntime().exec(commond);

    }

    /**
     * 获取当前java虚拟机的最大内存
     */
    @Test
    public void exec2(){
        long l = Runtime.getRuntime().maxMemory();
        System.out.println(l);

    }
}
