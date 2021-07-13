package com.pt.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.Charset;

/**
 * @author nate-pt
 * @date 2021/7/12 15:50
 * @Since 1.8
 * @Description nio模式服务器
 */
public class NioServer {
    /**
     * nio基于事件选择器
     */
    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    /**
     * 绑定端口启动服务器
     * @param port
     */
    public void bind(int port){
        try {
            selector = Selector.open();
            serverSocketChannel= ServerSocketChannel.open();
            // 设置通道非阻塞，如果不设置为false 的则nio模式将会变成bio模式
            serverSocketChannel.configureBlocking(false);
            // 设置链接队列的最大长度为1024
            serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
            // 将serverSocketChannel 注册到选择器中并且绑定链接事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务器端启动成功");
            new NioServerHandler(selector, Charset.forName("UTF-8")).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new NioServer().bind(7397);
    }


}
