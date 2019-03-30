package piplineTest.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import login.server.ServerHandler;

public class PiplineServer {
    public static void main(String[] args) {
        //监听
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //处理
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        //引导
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)//IO模型
                .childHandler(new ChannelInitializer<NioSocketChannel>() {//业务处理逻辑
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new InBoundHandlerA()).addLast(new InBoundHandlerB()).addLast(new InBoundHandlerC())
                        .addLast(new OutBoundHandlerA()).addLast(new OutBoundHandlerB()).addLast(new OutBoundHandlerC()).addLast(new ServerHandler());
                    }
                });
        //异步方法，可以添加监视器
//        serverBootstrap.bind(8000);
        bind(serverBootstrap,8000);

    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            public void operationComplete(Future<? super Void> future) {
                if (future.isSuccess()) {
                    System.out.println("端口[" + port + "]绑定成功!");
                } else {
                    System.err.println("端口[" + port + "]绑定失败!");
                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }
}
