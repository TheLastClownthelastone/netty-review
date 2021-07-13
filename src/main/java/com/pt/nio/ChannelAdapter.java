package com.pt.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @author nate-pt
 * @date 2021/7/12 16:03
 * @Since 1.8
 * @Description nio 通道配置器
 */
public abstract class ChannelAdapter extends Thread {
    /**
     * socket事件选择器
     */
    private Selector selector;

    /**
     * 编解码格式
     */
    private Charset charset;

    private ChannelHandler channelHandler;

    public ChannelAdapter(Selector selector, Charset charset) {
        this.selector = selector;
        this.charset = charset;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 选择器进行事件轮训
                selector.select(1000);
                // 获取该选择器是触发的事件
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()) {
                    // 获取到当前这个事件
                    key = iterator.next();
                    // 将该key从选择中去除
                    iterator.remove();
                    handleInput(key);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 得到对应的key后进行处理
     *
     * @param key
     */
    private void handleInput(SelectionKey key) throws IOException {
        // 如果该key无效则不往下执行
        if (!key.isValid()) {
            return;
        }
        // 获取到对应key绑定的channel 是SocketChannel 还是ServerSocketChannel
        Class<?> superclass = key.channel().getClass().getSuperclass();

        if (superclass == SocketChannel.class) {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            // 该key绑定的是否链接成功
            if (key.isConnectable()) {
                // 判断socketChannel时候已经完成了链接
                if (socketChannel.finishConnect()) {
                    channelHandler = new ChannelHandler(socketChannel, charset);
                    channelActive(channelHandler);
                    // 往选择器中绑定读写事件
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else {
                    System.exit(0);
                }
            }
        }

        if (superclass == ServerSocketChannel.class) {
            if (key.isAcceptable()) {
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                // 通过serverSocketChannel 获取对应的socketChannel
                SocketChannel socketChannel = serverSocketChannel.accept();
                // 设置非阻塞
                socketChannel.configureBlocking(false);
                // 往选择器中注册读写事件
                socketChannel.register(selector, SelectionKey.OP_READ);
                channelHandler = new ChannelHandler(socketChannel, charset);
                channelActive(channelHandler);

            }
        }


        if (key.isReadable()) {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            // 一次读取1024个字节
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int read = socketChannel.read(byteBuffer);

            if (read > 0) {
                // 调整flip指针位置
                byteBuffer.flip();
                byte[] bytes = new byte[byteBuffer.remaining()];
                byteBuffer.get(bytes);
                channelRead(channelHandler, new String(bytes, charset));
            } else if (read < 0){
                key.channel();
                socketChannel.close();
            }


        }


    }

    /**
     * 通道链接成功之后调用该方法
     *
     * @param ctx
     */
    public abstract void channelActive(ChannelHandler ctx);

    /**
     * 读取通道中的数据
     *
     * @param ctx
     * @param msg
     */
    public abstract void channelRead(ChannelHandler ctx, Object msg);
}
