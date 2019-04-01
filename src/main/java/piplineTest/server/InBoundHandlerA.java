package piplineTest.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class InBoundHandlerA extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("InBoundHandlerA: " + msg);
        //调用父类的的channelRead方法，会自动传递到下一个inboundHandler,其中调用的是ctx的fireChannelRead方法
        super.channelRead(ctx, msg);
    }
}
