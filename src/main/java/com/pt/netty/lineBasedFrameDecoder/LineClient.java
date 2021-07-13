package com.pt.netty.lineBasedFrameDecoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author nate-pt
 * @date 2021/7/13 9:24
 * @Since 1.8
 * @Description
 */
public class LineClient {


    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        System.out.println("通道初始化");
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new MyClientHandler());
                    }
                });
        ChannelFuture localhost = bootstrap.connect(new InetSocketAddress("localhost", 7397)).sync();
        localhost.channel().closeFuture().sync();
    }
}
