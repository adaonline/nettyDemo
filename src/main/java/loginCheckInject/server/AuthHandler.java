package loginCheckInject.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import login.client.LoginUtil;

public class AuthHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //因为在pipline中，所以每次有信息接收都要经过
        System.out.println("AuthHandler进行登陆校验");
        //判断是否登陆了，不成功直接关闭连接，成功就往下一个handler走
        if (!LoginUtil.hasLogin(ctx.channel())) {

            ctx.channel().close();
        } else {
            // 验证通过就移除自身，就不用每次都登录校验
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        if (LoginUtil.hasLogin(ctx.channel())) {
            System.out.println("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
        } else {
            System.out.println("无登录验证，强制关闭连接!");
        }
    }
}
