package com.jinyuanqian.NettyTetst;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;



public class NettyClient {


    /*
     * 服务器端口号
     */
    private int port;

    /*
     * 服务器IP
     */
    private String host;

    public NettyClient(){

    }


    public NettyClient(int port, String host) {
        this.port = port;
        this.host = host;
        start();
    }
    private void start()  {

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        System.out.println("启动服务开始");
        try {

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.group(eventLoopGroup);
            bootstrap.remoteAddress(host, port);
            bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) {
                    socketChannel.pipeline().addLast(new NettyClientHandler());
                }
            });
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            if (channelFuture.isSuccess()) {
                System.err.println("连接服务器成功");
            }else{
                System.err.println("连接服务器失败");
            }
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyClient(10086, "localhost");
    }
}
