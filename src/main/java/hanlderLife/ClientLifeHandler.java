package hanlderLife;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

public class ClientLifeHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        ByteBuf buffer = getByteBuf(ctx);
        ctx.channel().writeAndFlush(buffer);

    }
    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "这是一个关于channelhandler生命周期的实验".getBytes(Charset.forName("utf-8"));
        System.out.println("发送长度"+bytes.length);
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes(bytes);

        return buffer;
    }
}
