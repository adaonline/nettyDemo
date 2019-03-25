package login.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import packet.command.Packet;
import packet.command.PacketCodeC;
import packet.message.LoginRequestPacket;
import packet.message.LoginResponsePacket;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    //收到数据后会回调，channelRead
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf requestBuf=(ByteBuf)msg;

        Packet packet= PacketCodeC.INSTANCE.decode(requestBuf);

        if(packet instanceof LoginRequestPacket){
            LoginRequestPacket loginRequestPacket=(LoginRequestPacket)packet;
            LoginResponsePacket loginResponsePacket=new LoginResponsePacket();
            if(check(loginRequestPacket)){
                loginResponsePacket.setSuccess(true);
            }else {
                loginResponsePacket.setReason("验证失败");
                loginResponsePacket.setSuccess(false);
            }
            ByteBuf byteBuf=PacketCodeC.INSTANCE.encode(ctx.alloc(),loginResponsePacket);
            ctx.channel().writeAndFlush(byteBuf);
        }
    }
    public boolean check(Packet packet){
        return true;
    }
}
