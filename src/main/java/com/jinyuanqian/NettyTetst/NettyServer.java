package com.jinyuanqian.NettyTetst;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    private  int  port;

    public  NettyServer(int port){
        this.port = port;
        bind();
    }
    private void bind() {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker).channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.childHandler(new HeartBeatInitializer());
        ChannelFuture channelFuture = null;
        try {
            channelFuture = bootstrap.bind(port).sync();
            if (channelFuture.isSuccess()) {
                System.err.println("启动Netty服务成功，端口号：" + this.port);
            }
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //channelFuture.channel().closeFuture().sync();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        new NettyServer(10086);
        System.err.println("Netty服务关闭");
    }
}
