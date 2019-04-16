package singnalToSignal.message;

import packet.command.Command;
import packet.command.Packet;
//一对一聊天登录包
public class One2One_LoginRequest extends Packet {

    private String username;

    @Override
    public Byte getCommand() {
        return Command.One2OneLoginRequest;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
