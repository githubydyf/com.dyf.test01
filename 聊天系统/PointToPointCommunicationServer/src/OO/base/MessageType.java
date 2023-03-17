package OO.base;

/**
 * @ Author 董云飞
 * @ Student_ID 12103990114
 */
public interface MessageType {
    String MESSAGE_LOGIN_SUCCESS = "1";//登录成功
    String MESSAGE_LOGIN_FAIL = "2";//登录失败
    String MESSAGE_COMMON_MESSAGE = "3";//普通信息包
    String MESSAGE_GET_ONLINE_USER_LIST = "4";//要求返回在线用户列表
    String MESSAGE_RETURN_ONLINE_USER_LIST = "5";//返回的在线用户类表
    String MESSAGE_CLIENT_EXIT = "6";//客户端请求退出
    String MESSAGE_TO_ALL_MESSAGE = "7";//群发消息
    String MESSAGE_SEND_FILE = "8";//发送文件
    String MESSAGE_GET_FILE = "9";//接受文件
    String MESSAGE_RETURN_AN_ACCOUNT = "11";//返回一个账号

}
