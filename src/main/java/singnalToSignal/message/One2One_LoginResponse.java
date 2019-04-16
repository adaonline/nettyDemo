package singnalToSignal.message;

import packet.command.Command;
import packet.command.Packet;

//单对单聊天登录返回
public class One2One_LoginResponse extends Packet {
    //生成的账号id
    private String userId;
    //角色名
    private String userName;
    //是否登录成功
    private boolean loginResult;
    //原因
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isLoginResult() {
        return loginResult;
    }

    public void setLoginResult(boolean loginResult) {
        this.loginResult = loginResult;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public Byte getCommand() {
        return Command.One2OneLoginResponse;
    }
}
