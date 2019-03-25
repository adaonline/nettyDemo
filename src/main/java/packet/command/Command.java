package packet.command;

public interface Command {
    Byte LOGIN=1;
    Byte LOGINRESPONSE=2;
    Byte MESSAGEREQUEST=3;
    Byte MESSAGERESPONSE=4;
}
