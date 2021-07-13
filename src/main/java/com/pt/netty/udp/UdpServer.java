package com.pt.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * @author nate-pt
 * @date 2021/7/13 10:02
 * @Since 1.8
 * @Description udp协议服务器
 */
public class UdpServer {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    /**
                     * 设置广播模式
                     */
                    .option(ChannelOption.SO_BROADCAST, true)
                    /**
                     * 设置读的缓冲区为2M
                     */
                    .option(ChannelOption.SO_RCVBUF, 2048 * 1024)
                    /**
                     * 设置写的缓冲区为1M
                     */
                    .option(ChannelOption.SO_SNDBUF, 1024 * 1024)
                    /**
                     *  指定通道的类型为udp协议
                     */
                    .channel(NioDatagramChannel.class)
                    .handler(new ChannelInitializer<NioDatagramChannel>() {
                        @Override
                        protected void initChannel(NioDatagramChannel nioDatagramChannel) throws Exception {
                            ChannelPipeline pipeline = nioDatagramChannel.pipeline();
                            pipeline.addLast(new MyUdpServerHandler());
                        }
                    });

            // udp协议进行端口绑定
            ChannelFuture sync = bootstrap.bind(7397).sync();
            sync.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
}
