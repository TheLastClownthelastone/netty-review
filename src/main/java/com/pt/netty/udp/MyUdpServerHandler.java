package com.pt.netty.udp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


/**
 * @author nate-pt
 * @date 2021/7/13 10:48
 * @Since 1.8
 * @Description udp服务端处理器
 */
public class MyUdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket packet) throws Exception {
        System.out.println("udp服务器开始读取数据..........");
        System.out.println(packet.content().toString(Charset.forName("UTF-8")));
        // 先生成对应bytebuf对象
        String str = "服务器收到消息返回消息给客户端\r\n";
        byte[] bytes = str.getBytes(Charset.forName("UTF-8"));
        DatagramPacket data = new DatagramPacket(Unpooled.copiedBuffer(bytes), packet.sender());
        channelHandlerContext.writeAndFlush(data);
    }
}
