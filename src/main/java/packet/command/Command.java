package packet.command;

public interface Command {
    Byte LOGIN=1; //登录
    Byte LOGINRESPONSE=2; //登录返回
    Byte MESSAGEREQUEST=3; //发送消息请求
    Byte MESSAGERESPONSE=4; //发送消息


    //单对单聊天
    Byte One2OneLoginRequest=5;
    Byte One2OneLoginResponse=6;
    Byte One2OneMessageRequest=7;
    Byte One2OneMessageResponse=8;
    Byte One2OneMessageResult=9;
}
