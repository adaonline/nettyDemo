package packet.message;

import packet.command.Command;
import packet.command.Packet;

public class MessageRequestPacket extends Packet {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return Command.MESSAGEREQUEST;
    }
}
