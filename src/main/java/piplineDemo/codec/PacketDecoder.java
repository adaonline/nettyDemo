package piplineDemo.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import packet.command.PacketCodeC;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {
    //自行进行堆内存释放
    //将解码的信息对象传递到下一个
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(PacketCodeC.INSTANCE.decode(in));
    }
}
