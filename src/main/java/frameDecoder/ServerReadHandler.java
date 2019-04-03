package frameDecoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;
//收到数据就打印
public class ServerReadHandler extends ChannelInboundHandlerAdapter {
    //收到连接后被回调
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + "--服务端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));
    }

}
