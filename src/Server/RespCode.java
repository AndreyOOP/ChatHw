package Server;

public interface RespCode {

    byte AUTH_OK = 101;
    byte AUTH_ERR = 102;
    byte USER_ADDED = 103;

    int BAD_REQUEST = 400;
    int OK_RESPONSE = 200;
}
