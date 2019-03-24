package packet.message;


import packet.command.Command;
import packet.command.Packet;

public class LoginRequestPacket  extends Packet {

    private Integer userId;

    private String username;

    private String password;
    @Override
    public Byte getCommand() {
        return Command.LOGIN;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
