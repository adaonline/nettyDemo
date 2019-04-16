package singnalToSignal.message;

import packet.command.Command;
import packet.command.Packet;
//单对单聊天的聊天包
public class One2One_MessageRequest extends Packet{
    private String message;

    private String toUserId;

    public One2One_MessageRequest() {
    }

    public One2One_MessageRequest(String message, String toUserId) {
        this.message = message;
        this.toUserId = toUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return Command.One2OneMessageRequest;
    }
}
