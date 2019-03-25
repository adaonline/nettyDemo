package login.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import packet.command.PacketCodeC;
import packet.message.MessageRequestPacket;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client {
    public static int MAX_RETRY=5;
    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                // 1.指定线程模型
                .group(workerGroup)
                // 2.指定 IO 类型为 NIO
                .channel(NioSocketChannel.class)
                // 3.IO 处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });
        // 4.建立连接
        connect(bootstrap,"127.0.0.1",8000,MAX_RETRY);
    }
    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("连接成功!");
                    Client.startConsoleThread(((ChannelFuture)future).channel());
                }else if (retry == 0) {
                    System.err.println("重试次数已用完，放弃连接！");
                } else {
                    // 第几次重连
                    int order = (MAX_RETRY - retry) + 1;
                    // 本次重连的间隔
                    int delay = 1 << order;
                    System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                    bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit
                            .SECONDS);
                }
            }
        });
    }

    private static void startConsoleThread(Channel channel){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()){
                    if(LoginUtil.hasLogin(channel)){
                        System.out.println("输入消息到服务端:");
                        Scanner sc=new Scanner(System.in);
                        String line =sc.nextLine();

                        MessageRequestPacket packet=new MessageRequestPacket();
                        packet.setMessage(line);
                        ByteBuf byteBuf= PacketCodeC.INSTANCE.encode(channel.alloc(),packet);
                        channel.writeAndFlush(byteBuf);
                    }
                }
            }
        }).start();
    }

    private static void autoSend(Channel channel){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if(LoginUtil.hasLogin(channel)){
                        MessageRequestPacket packet=new MessageRequestPacket();
                        packet.setMessage(System.currentTimeMillis()+"");
                        ByteBuf byteBuf= PacketCodeC.INSTANCE.encode(channel.alloc(),packet);
                        channel.writeAndFlush(byteBuf);
                    }
                }
            }
        }).start();
    }
}
