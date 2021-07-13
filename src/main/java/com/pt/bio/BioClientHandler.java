package com.pt.bio;

import java.net.Socket;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author nate-pt
 * @date 2021/7/12 15:31
 * @Since 1.8
 * @Description
 */
public class BioClientHandler extends ChannelAdapter{
    /**
     * @param socket
     * @param charset
     */
    public BioClientHandler(Socket socket, Charset charset) {
        super(socket, charset);
    }

    @Override
    public void channelActive(ChannelHandler ctx) {
        System.out.println(ctx.socket().getLocalAddress());
        //向服务端发送消息 消息写完之后一定要叫上\r\n 换行
        ctx.writeAndFlush("客户端链接成功。。。。。\r\n");
    }

    @Override
    public void channelRead(ChannelHandler ctx, Object msg) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息：" + msg);
        ctx.writeAndFlush("hi 我已经收到你的消息Success！\r\n");
    }
}
