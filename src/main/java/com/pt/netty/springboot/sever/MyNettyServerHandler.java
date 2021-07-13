package com.pt.netty.springboot.sever;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * @author nate-pt
 * @date 2021/7/13 11:38
 * @Since 1.8
 * @Description
 */
@Component
@Slf4j
public class MyNettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("【链接创建成功】....");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("【收到客户端消息为】："+((ByteBuf) msg).toString(Charset.forName("UTF-8")));
    }
}
