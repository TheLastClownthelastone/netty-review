package com.pt.netty.lineBasedFrameDecoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.charset.Charset;

/**
 * @author nate-pt
 * @date 2021/7/13 9:26
 * @Since 1.8
 * @Description
 */
public class MyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端链接成功");
        String str = "客户端链接成功了\r\n";
        ByteBuf byteBuf = Unpooled.copiedBuffer(str.getBytes(Charset.forName("UTF-8")));
        ctx.writeAndFlush(byteBuf);
    }
}
