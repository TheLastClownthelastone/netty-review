package com.pt.netty.springboot.controller;

import com.pt.netty.springboot.sever.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nate-pt
 * @date 2021/7/13 11:55
 * @Since 1.8
 * @Description
 */
@RequestMapping("nettyInfo")
@RestController
public class NettyInfoController {

    @Autowired
    private NettyServer nettyServer;

    @GetMapping("getNettyInfo")
    public String getNettyInfo(){
        return nettyServer.getChannel().localAddress().toString();
    }
}
