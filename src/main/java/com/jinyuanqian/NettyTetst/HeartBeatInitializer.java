package com.jinyuanqian.NettyTetst;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class HeartBeatInitializer  extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline p = socketChannel.pipeline();
        // 添加NettyServerHandler，用来处理Server端接收和处理消息的逻辑
        p.addLast(new NettyServerHandler());
    }
}
