package com.pt.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @author nate-pt
 * @date 2021/7/12 17:15
 * @Since 1.8
 * @Description nio 客户端
 */
public class NioClient {

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open();
        // 设置非阻塞
        socketChannel.configureBlocking(false);
        boolean connect = socketChannel.connect(new InetSocketAddress("127.0.0.1", 7397));

        if (connect) {
            socketChannel.register(selector, SelectionKey.OP_READ);
        }else {
            socketChannel.register(selector,SelectionKey.OP_CONNECT);
        }
        System.out.println("客户端创建成功");
        new NioClientHandler(selector, Charset.forName("UTF-8")).start();

    }


}
