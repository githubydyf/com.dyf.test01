package OO.server.Server;

import OO.base.Message;
import OO.base.MessageType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @ Author 董云飞
 * @ Student_ID 12103990114
 * 该类的对象和客户端保持通信
 */
public class ServerConnectClientThread extends Thread{
    private Socket socket;
    private String id;

    public ServerConnectClientThread(Socket socket, String id) {
        this.socket = socket;
        this.id = id;
    }

    @Override
    public void run() {//接受消息
        while (true){
            System.out.println("服务端和客户端" + id + "保持通讯");
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message)ois.readObject();
                //客户端发送获取好友列表的信息
                if(message.getMessageType().equals(MessageType.MESSAGE_GET_ONLINE_USER_LIST)){
                    String onlineUserList = ManageClientThread.getOnlineUserList();
                    System.out.println("1");
                    Message message1 = new Message();
                    message1.setMessageType(MessageType.MESSAGE_RETURN_ONLINE_USER_LIST);
                    message1.setContent(onlineUserList);
                    message1.setGetter(message.getSender());

                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message1);
                }else if(message.getMessageType().equals(MessageType.MESSAGE_CLIENT_EXIT)){
                    //客户端发送退出的消息
                    System.out.println(message.getSender() + "退出");
                    ManageClientThread.removeClientThread(message.getSender());
                    socket.close();
                    //退出线程
                    break;

                }else if(message.getMessageType().equals(MessageType.MESSAGE_COMMON_MESSAGE)){
                    //客户端发送给个好友的普通消息
                    String getter = message.getGetter();
                    ObjectOutputStream oos = new ObjectOutputStream(ManageClientThread.getServerConnectClientThread(getter).socket.getOutputStream());
                    oos.writeObject(message);

                }else if(message.getMessageType().equals(MessageType.MESSAGE_TO_ALL_MESSAGE)){
                    //客户端发送群消息
                    HashMap<String, ServerConnectClientThread> hashMap = ManageClientThread.getHashMap();
                    Iterator<String> iterator = hashMap.keySet().iterator();
                    while (iterator.hasNext()) {
                        String id = iterator.next();
                        if(!id.equals(message.getSender())){
                            ObjectOutputStream oos = new ObjectOutputStream(hashMap.get(id).socket.getOutputStream());
                            oos.writeObject(message);
                        }

                    }

                }else if(message.getMessageType().equals(MessageType.MESSAGE_SEND_FILE)){
                    //客户端发送文件
                    message.setMessageType(MessageType.MESSAGE_GET_FILE);
                    ObjectOutputStream oos = new ObjectOutputStream(ManageClientThread.getServerConnectClientThread(message.getGetter()).socket.getOutputStream());
                    oos.writeObject(message);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
