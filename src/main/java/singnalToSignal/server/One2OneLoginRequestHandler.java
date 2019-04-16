package singnalToSignal.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import singnalToSignal.message.One2One_LoginRequest;
import singnalToSignal.message.One2One_LoginResponse;
import singnalToSignal.session.Session;
import singnalToSignal.session.SessionUtil;

public class One2OneLoginRequestHandler extends SimpleChannelInboundHandler<One2One_LoginRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, One2One_LoginRequest request) throws Exception {
        One2One_LoginResponse response=new One2One_LoginResponse();
        response.setUserName(request.getUsername());
        if(vaild(request)){
            response.setLoginResult(true);
            String userid=request.getUsername();
            response.setUserId(userid);
            System.out.println(request.getUsername()+"登录成功");
            SessionUtil.bindSession(new Session(userid,request.getUsername()),ctx.channel());
        }else {
            response.setReason("账号密码匹配失败");
            response.setLoginResult(false);
            System.out.println(request.getUsername()+"登录失败");
        }
        ctx.channel().writeAndFlush(response);
    }
    //用户掉线取消绑定
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }

    public boolean vaild(One2One_LoginRequest request){
        return true;
    }
}
