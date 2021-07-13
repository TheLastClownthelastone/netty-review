package com.pt.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author nate-pt
 * @date 2021/7/12 15:03
 * @Since 1.8
 * @Description 通道处理器
 */
public class ChannelHandler {
    /**
     * socket 对象
     */
    private Socket socket;
    /**
     * socket中通过字符集
     */
    private Charset charset;

    public ChannelHandler(Socket socket, Charset charset) {
        this.socket = socket;
        this.charset = charset;
    }

    /**
     * 写入socket并且刷新socket
     * @param message
     */
    public void writeAndFlush(Object message){
        try {
            OutputStream out = socket.getOutputStream();
            out.write(message.toString().getBytes(charset));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回socket对象
     * @return
     */
    public Socket socket(){
        return socket;
    }
}
