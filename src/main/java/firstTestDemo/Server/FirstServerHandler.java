package firstTestDemo.Server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {
    //收到连接后被回调
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + "--服务端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));
        ctx.writeAndFlush(getByteBuf(ctx));
    }
    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "你好,我是服务端".getBytes(Charset.forName("utf-8"));

        ByteBuf buffer = ctx.alloc().buffer();

        buffer.writeBytes(bytes);

        return buffer;
    }
}
