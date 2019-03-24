package packet.command;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import packet.command.Command;
import packet.command.Packet;
import packet.message.LoginRequestPacket;
import packet.serialize.JSONSerializer;
import packet.serialize.Serializer;

import java.util.HashMap;
import java.util.Map;

public class PacketCodeC {
    private static final int MAGIC_NUMBER=0x12345678;

    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte, Serializer> serializerMap;
    //初始化
    static {
        packetTypeMap = new HashMap<Byte, Class<? extends Packet>>();
        packetTypeMap.put(Command.LOGIN, LoginRequestPacket.class);

        serializerMap = new HashMap<Byte, Serializer>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }
    //编码
    public ByteBuf encode(Packet packet){
        //1.创建bytebuf对象
        ByteBuf byteBuf= ByteBufAllocator.DEFAULT.ioBuffer();
        //2.序列化java对象
        byte[] bytes=Serializer.DEFAULT.serialize(packet);
        //3.写入
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.version);
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        return byteBuf;

    }
    //解码
    public Packet decode(ByteBuf byteBuf){
        //跳过MAGIC_NUMBER
        byteBuf.skipBytes(4);

        //跳过版本号
        byteBuf.skipBytes(1);
        //序列化算法标识
        byte serializerAlgorithm=byteBuf.readByte();
        //指令id
        byte command=byteBuf.readByte();
        //数据包长度
        int length=byteBuf.readInt();

        byte[] bytes=new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> messageType=getRequestType(command);

        Serializer serializer=getSerializer(serializerAlgorithm);
        if(messageType!=null&&serializer!=null){
            return serializer.deserialize(messageType,bytes);
        }
        return null;

    }

    private Class<? extends Packet> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }

    private Serializer getSerializer(byte serializerAlgorithm){
        return serializerMap.get(serializerAlgorithm);
    }
}
