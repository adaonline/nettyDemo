package frameDecoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
//粘包的实验、

public class Server {
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup=new NioEventLoopGroup();
        NioEventLoopGroup workGroup=new NioEventLoopGroup();
        ServerBootstrap serverBootstrap=new ServerBootstrap();
        serverBootstrap.group(bossGroup,workGroup).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        //新增了定长拆包，就不会出现粘包半包的情况了
                        //ch.pipeline().addLast(new FixedLengthFrameDecoder(38));
                        ch.pipeline().addLast(new ServerReadHandler());
                    }
                });

        serverBootstrap.bind(1000);
    }
}
