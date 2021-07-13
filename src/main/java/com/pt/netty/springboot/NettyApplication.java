package com.pt.netty.springboot;

import com.pt.netty.springboot.sever.NettyServer;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author nate-pt
 * @date 2021/7/13 11:28
 * @Since 1.8
 * @Description
 */
@SpringBootApplication
public class NettyApplication implements CommandLineRunner {

    @Autowired
    private NettyServer nettyServer;

    public static void main(String[] args) {

        SpringApplication.run(NettyApplication.class,args);
    }

    /**
     * 在spring容器加载完毕之后启动netty服务
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        ChannelFuture bind = nettyServer.bind();
        Runtime.getRuntime().addShutdownHook(new Thread(()-> nettyServer.closeNettyServer()));
        bind.channel().closeFuture().syncUninterruptibly();
    }


}
