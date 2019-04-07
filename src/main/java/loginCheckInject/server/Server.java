package loginCheckInject.server;

import frameDecoder.Spliter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import login.server.ServerHandler;
import piplineDemo.codec.PacketDecoder;
import piplineDemo.codec.PacketEncoder;
import piplineDemo.server.LoginRequestHandler;
import piplineDemo.server.MessageRequestHandler;

public class Server {
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
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginRequestHandler());
                        //此处做是否登陆的验证
                        ch.pipeline().addLast(new AuthHandler());
                        ch.pipeline().addLast(new MessageRequestHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });
        serverBootstrap.bind(1000);

    }


}
