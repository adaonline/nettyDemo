package singnalToSignal.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import login.client.LoginUtil;
import packet.message.LoginRequestPacket;
import packet.message.LoginResponsePacket;

import java.util.Date;

public class One2OneLoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        System.out.println(new Date()+"：收到客户端登录请求...");
        LoginResponsePacket loginResponsePacket=new LoginResponsePacket();
        if(valid(loginRequestPacket)){
            loginResponsePacket.setSuccess(true);
            System.out.println(new Date()+":登录成功！");
            LoginUtil.markAsLogin(ctx.channel());
        }else{
            loginResponsePacket.setReason("账号密码错误");
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date()+" 登录失败");

        }
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
