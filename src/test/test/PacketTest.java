import io.netty.buffer.ByteBuf;
import org.junit.Assert;
import org.junit.Test;
import packet.command.Packet;
import packet.command.PacketCodeC;
import packet.message.LoginRequestPacket;
import packet.serialize.JSONSerializer;
import packet.serialize.Serializer;

public class PacketTest {

    @Test
    public void encode() {

        Serializer serializer = new JSONSerializer();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setUserId(123);
        loginRequestPacket.setUsername("zhangsan");
        loginRequestPacket.setPassword("password");

        PacketCodeC packetCodeC = new PacketCodeC();
        ByteBuf byteBuf = packetCodeC.encode(loginRequestPacket);
        Packet decodedPacket = packetCodeC.decode(byteBuf);

        Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));

    }
}
