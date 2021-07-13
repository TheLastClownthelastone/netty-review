package com.pt.bio;

import java.net.Socket;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author nate-pt
 * @date 2021/7/12 14:59
 * @Since 1.8
 * @Description bioServer处理器
 */
public class BioServerHandler extends ChannelAdapter{

    /**
     * @param socket
     * @param charset
     */
    public BioServerHandler(Socket socket, Charset charset) {
        super(socket, charset);
    }

    @Override
    public void channelActive(ChannelHandler ctx) {
        System.out.println(ctx.socket().getInetAddress());
        ctx.writeAndFlush("BioServer链接成功返回消息....");
    }

    @Override
    public void channelRead(ChannelHandler ctx,Object msg) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息：" + msg);
        // 接受到消息之后同志客户端
        ctx.writeAndFlush("hi 我已经收到你的消息Success！\r\n");
    }
}
