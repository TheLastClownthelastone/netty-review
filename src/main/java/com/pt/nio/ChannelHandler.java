package com.pt.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author nate-pt
 * @date 2021/7/12 16:26
 * @Since 1.8
 * @Description
 */
public class ChannelHandler {


    private SocketChannel socketChannel;

    private Charset charset;

    public ChannelHandler(SocketChannel socketChannel, Charset charset) {
        this.socketChannel = socketChannel;
        this.charset = charset;
    }

    /**
     * 往通道中写入数据
     * @param msg
     */
    public void writeAndFlush(Object msg){

        try {
            byte[] bytes = msg.toString().getBytes(charset);
            // 创建byteBuffer对象
            ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
            byteBuffer.put(bytes);
            // 将flip指针调0
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取通道
      * @return
     */
    public SocketChannel channel(){
        return socketChannel;
    }
}
