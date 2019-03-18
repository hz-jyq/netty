package com.jinyuanqian.NettyTetst;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

public class NettyClientHandler  extends ChannelHandlerAdapter {
    private ByteBuf firstMessage;

/*    private ImConnection imConnection = new ImConnection();*/

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte[] data = "你好，服务器".getBytes();
        firstMessage = Unpooled.buffer();
        firstMessage.writeBytes(data);
        ctx.writeAndFlush(firstMessage);
        System.err.println("客户端发送消息:你好，服务器");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        String rev = getMessage(buf);
        System.err.println("客户端收到服务器消息:" + rev);
    }

    private String getMessage(ByteBuf buf) {
        byte[] con = new byte[buf.readableBytes()];
        buf.readBytes(con);
        try {
            return new String(con, "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("掉线了...");
        final EventLoop eventLoop = ctx.channel().eventLoop();
     //   eventLoop.schedule(new Runnable(() -> ), TimeUnit.DAYS);
        super.channelInactive(ctx);
    }
}
