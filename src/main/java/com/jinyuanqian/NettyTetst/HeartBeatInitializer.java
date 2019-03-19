package com.jinyuanqian.NettyTetst;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class HeartBeatInitializer  extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline p = socketChannel.pipeline();
        p.addLast(new NettyServerHandler());
    }
}
