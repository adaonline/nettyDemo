package singnalToSignal.message;

import packet.command.Command;
import packet.command.Packet;

public class One2One_MessageResponse  extends Packet{
    public String fromUseId;
    public String fromName;
    public String message;

    public String getFromUseId() {
        return fromUseId;
    }

    public void setFromUseId(String fromUseId) {
        this.fromUseId = fromUseId;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return Command.One2OneMessageResponse;
    }
}
