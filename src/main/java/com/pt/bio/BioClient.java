package com.pt.bio;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author nate-pt
 * @date 2021/7/12 14:39
 * @Since 1.8
 * @Description bio客户端
 */
public class BioClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1",7397);
        System.out.println("客户端链接创建成功");
        BioClientHandler bioClientHandler = new BioClientHandler(socket, Charset.forName("utf-8"));
        bioClientHandler.start();
    }
}
