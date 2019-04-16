package singnalToSignal.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import singnalToSignal.message.One2One_LoginResponse;
import singnalToSignal.session.Session;
import singnalToSignal.session.SessionUtil;

public class One2One_LoginResponseHandler extends SimpleChannelInboundHandler<One2One_LoginResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, One2One_LoginResponse response) throws Exception {
        String userId = response.getUserId();
        String userName = response.getUserName();

        if (response.isLoginResult()) {
            System.out.println("[" + userName + "]登录成功，userId 为: " + response.getUserId());
            //这里sessionUtil跟Server端的不是同一个，但是作用是一样的
            SessionUtil.bindSession(new Session(userId, userName), ctx.channel());
        } else {
            System.out.println("[" + userName + "]登录失败，原因：" + response.getReason());
        }
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("客户端连接被关闭!");
    }
}
