package singnalToSignal.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import singnalToSignal.message.One2OneMessageResult;

public class One2OneMessageResultHandler extends SimpleChannelInboundHandler<One2OneMessageResult> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, One2OneMessageResult msg) throws Exception {
        System.out.println("发送给["+msg.getToUserId()+"]结果："+""+msg.isResult()+"，原因："+msg.getReason());
    }
}
