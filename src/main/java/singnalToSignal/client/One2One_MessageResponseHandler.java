package singnalToSignal.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import singnalToSignal.message.One2One_MessageResponse;

public class One2One_MessageResponseHandler extends SimpleChannelInboundHandler<One2One_MessageResponse>{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, One2One_MessageResponse msg) throws Exception {
        String fromuserid=msg.getFromUseId();
        String fromName=msg.getFromName();
        String message=msg.getMessage();
        System.out.println("["+fromuserid+":"+fromName+"] 发送给你：  "+message);
    }
}
