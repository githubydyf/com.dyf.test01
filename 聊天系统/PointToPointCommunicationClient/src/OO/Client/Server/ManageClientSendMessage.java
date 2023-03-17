package OO.Client.Server;

import OO.base.Message;
import OO.base.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @ Author 董云飞
 * @ Student_ID 12103990114
 */
public class ManageClientSendMessage {
    /**
     * 单人消息发送
     * @param sender 发送者
     * @param getter 接收者
     * @param content 信息
     */
    public static void singlePersonCommunication(String sender,String getter,String content){
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_COMMON_MESSAGE);
        message.setSender(sender);
        message.setGetter(getter);
        message.setContent(content);
        message.setSendTime(new java.util.Date().toString());
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(sender).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public static void sendMessageToAll(String sender,String content){
        Message message = new Message();
        message.setContent(content);
        message.setSender(sender);
        message.setMessageType(MessageType.MESSAGE_TO_ALL_MESSAGE);
        message.setSendTime(new java.util.Date().toString());
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(sender).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
