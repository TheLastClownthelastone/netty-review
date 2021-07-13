package com.pt.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author nate-pt
 * @date 2021/7/12 14:39
 * @Since 1.8
 * @Description bio实现服务端
 */
public class BioServer extends Thread{

    private ServerSocket serverSocket = null;

    public static void main(String[] args) {
        BioServer bioServer = new BioServer();
        bioServer.start();
    }



    @Override
    public void run() {
        try {
            // 创建serverSocket
            serverSocket  = new ServerSocket();
            // 绑定端口
            serverSocket.bind(new InetSocketAddress(7397));
            System.out.println("端口绑定成功："+7397);

            // 创建死循环
            while (true) {
                // 通过ServerSocket获取Socket对象
                Socket socket = serverSocket.accept();
                BioServerHandler handler = new BioServerHandler(socket, Charset.forName("UTF-8"));
                handler.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
