package singnalToSignal.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import singnalToSignal.message.One2OneMessageResult;
import singnalToSignal.message.One2One_MessageRequest;
import singnalToSignal.message.One2One_MessageResponse;
import singnalToSignal.session.Session;
import singnalToSignal.session.SessionUtil;

public class One2OneMessageRequestHandler extends SimpleChannelInboundHandler<One2One_MessageRequest> {
    //抽象方法必须实现
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, One2One_MessageRequest msg) throws Exception {
        //1.拿到发送方的会话
        Session session= SessionUtil.getSession(ctx.channel());
        //2.通过发送方的会话信息构造消息

        One2One_MessageResponse response=new One2One_MessageResponse();
        response.setFromName(session.getUserName());
        response.setFromUseId(session.getUserId());
        response.setMessage(msg.getMessage());
        //3.获取消息接收方
        Channel tochannel=SessionUtil.getChannel(msg.getToUserId());
        //4.做出判断并且发送消息
        if(tochannel!=null&&SessionUtil.hasLogin(tochannel)){
            tochannel.writeAndFlush(response);
            One2OneMessageResult result=new One2OneMessageResult(msg.getToUserId(),true,"发送成功");
            ctx.channel().writeAndFlush(result);
        }else {
            System.out.println(msg.getToUserId()+"不在线不发送");
            One2OneMessageResult result=new One2OneMessageResult(msg.getToUserId(),false,"对方不在线");
            ctx.channel().writeAndFlush(result);
        }

    }

}
