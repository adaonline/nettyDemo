package singnalToSignal.client;

import frameDecoder.Spliter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import piplineDemo.codec.PacketDecoder;
import piplineDemo.codec.PacketEncoder;
import singnalToSignal.message.One2One_LoginRequest;
import singnalToSignal.message.One2One_MessageRequest;
import singnalToSignal.session.SessionUtil;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ChatClient {


    private static final int MAX_RETRY=5;
    private static final String HOST="127.0.0.1";
    private static final int port=8001;

    public static void main(String[] args) {
        NioEventLoopGroup workGroup=new NioEventLoopGroup();
        Bootstrap bootstrap=new Bootstrap();
        bootstrap.group(workGroup).channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new One2One_LoginResponseHandler());
                        ch.pipeline().addLast(new One2One_MessageResponseHandler());
                        ch.pipeline().addLast(new One2OneMessageResultHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });
        connect(bootstrap,HOST,port,MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap,String host,int port,int retry){
        bootstrap.connect(host,port).addListener(future -> {
            if(future.isSuccess()){
                System.out.println(new Date()+" 连接成功，启动控制台线程");
                Channel channel=((ChannelFuture)future).channel();
                startConsoleThread(channel);
            }else if(retry==0){
                System.err.println("重试次数已经用完");
            }else {
                int order=(MAX_RETRY-retry)+1;
                int delay=1<<order;
                System.err.println(new Date()+" 连接失败，第"+order+"次重连");
                bootstrap.config().group().schedule(()->connect(bootstrap, host, port, retry-1),delay, TimeUnit.SECONDS);
            }
        });

    }

    public static void startConsoleThread(Channel channel){
        Scanner scanner=new Scanner(System.in);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()){
                    if(!SessionUtil.hasLogin(channel)){
                        System.out.println("输入用户名登录到服务器：");

                        String line=scanner.nextLine();
                        One2One_LoginRequest request=new One2One_LoginRequest();
                        request.setUsername(line);

                        channel.writeAndFlush(request);
                        try {
                            Thread.sleep(1000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else {
                        System.out.print("输入目标用户id：");
                        String toUserId = scanner.next();
                        System.out.print("输入要发送的内容：");
                        String message = scanner.next();
                        channel.writeAndFlush(new One2One_MessageRequest(message, toUserId));
                    }
                }
            }
        }).start();
    }

}
