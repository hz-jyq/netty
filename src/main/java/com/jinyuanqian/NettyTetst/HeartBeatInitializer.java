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

    public static void main(String[] args) {
        int[] arg = new int[]{3,2,1,3,5,6,7,1,2,3,5,6,12,4,12,12,1,2,4};


                int startLength = 0;
                int endLength = arg.length-1;
                while(arg[endLength] > arg[startLength]){
                  /*  int j = arg[startLength];
                    arg[startLength] = arg[endLength];
                    arg[endLength] = j;*/
                    endLength++;
                }

    }
}
