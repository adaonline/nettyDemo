package singnalToSignal.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import singnalToSignal.session.SessionUtil;

public class One2oneLoginCheckHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            // 验证通过就移除自身，就不用每次都登录校验
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        if (SessionUtil.hasLogin(ctx.channel())) {
            System.out.println("当前channnel连接登录验证完毕，无需再次验证, One2oneLoginCheckHandler 被移除");
        } else {
            System.out.println("无登录验证，强制关闭连接!");
        }
    }
}
