package com.pt.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author nate-pt
 * @date 2021/7/12 15:00
 * @Since 1.8
 * @Description 通道处理器
 */
public abstract class ChannelAdapter extends Thread{
    /**
     * socket对象
     */
    private Socket socket;

    /**
     * 通道处理器
     */
    private ChannelHandler channelHandler;

    /**
     * 字符集编码
     */
    private Charset charset;

    /**
     *
     * @param socket
     * @param charset
     */
    public ChannelAdapter(Socket socket,Charset charset){
        this.socket = socket;
        this.charset = charset;

        // 创建死循环等待socket进行链接
        while (!socket.isConnected()) {
            // 链接成功之后跳出循环
            break;
        }
        // 创建channelHandler对象
        this.channelHandler = new ChannelHandler(this.socket,this.charset);
        // 通道创建成功调用channelActive方法
        channelActive(channelHandler);
    }

    /**
     *  通道创建成功的时候调用改方法
     * @param ctx
     */
    public  abstract void channelActive(ChannelHandler ctx);

    /**
     * 通道读取数据的时候方法
     * @param ctx
     * @param msg
     */
    public abstract  void channelRead(ChannelHandler ctx,Object msg);

    @Override
    public void run() {
        // ctx.writeAndFlush 写完消息的时候一定要进行换行，不然会一直在while循环
        // 监听通道中是否存在消息,如果通道中存在消息的话调用channelRead方法
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),charset));
            String msg = null;
            while ((msg = reader.readLine()) != null) {
                channelRead(channelHandler,msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
