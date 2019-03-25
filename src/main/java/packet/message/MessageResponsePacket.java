package packet.message;

import packet.command.Command;
import packet.command.Packet;

public class MessageResponsePacket extends Packet {
    public String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return Command.MESSAGERESPONSE;
    }
}
