package com.pt.netty.springboot.sever;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.net.InetSocketAddress;

/**
 * @author nate-pt
 * @date 2021/7/13 11:27
 * @Since 1.8
 * @Description netty中整合springboot
 */
@Component
public class NettyServer {

    @Value("${netty.host}")
    private String host;

    @Value("${netty.port}")
    private int port;

    @Autowired
    private MyNettyServerHandler myNettyServerHandler;

    private Channel channel;


    private EventLoopGroup boss = new NioEventLoopGroup(1);

    private EventLoopGroup worker = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());


    public ChannelFuture bind() {
        ChannelFuture channelFuture = null;
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(myNettyServerHandler);
                        }
                    });
             channelFuture = serverBootstrap.bind(new InetSocketAddress(host, port)).syncUninterruptibly();
            channel = channelFuture.channel();
            return channelFuture;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return channelFuture;
    }

    public void closeNettyServer(){
        if (channel == null) {
            return;
        }
        channel.close();
        boss.shutdownGracefully();
        worker.shutdownGracefully();
    }


    public Channel getChannel(){
        return channel;
    }

}
