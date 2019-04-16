package singnalToSignal.message;

import packet.command.Command;
import packet.command.Packet;

//消息返回结果
public class One2OneMessageResult extends Packet{
    private String toUserId;
    //true 完成，false失败
    private boolean result;
    //原因，用错误码更好，这里简略
    private String reason;


    public One2OneMessageResult(String toUserId, boolean result, String reason) {
        this.toUserId = toUserId;
        this.result = result;
        this.reason = reason;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public Byte getCommand() {
        return Command.One2OneMessageResult;
    }
}
