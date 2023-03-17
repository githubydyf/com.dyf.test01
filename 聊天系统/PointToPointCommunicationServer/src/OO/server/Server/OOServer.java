package OO.server.Server;

import OO.base.Message;
import OO.base.MessageType;
import OO.base.User;
import OO.connectDatabase.DataProcessing;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ Author 董云飞
 * @ Student_ID 12103990114
 * 服务器在9999端口监听，等待客户端的连接，并保持通信
 */
public class OOServer extends Thread{
    public static boolean loop = true;
    private ServerSocket serverSocket = null;

    //判断是否存在帐户且账户是否在线
    private boolean checkUser(String id, String password) {
        DataProcessing dataProcessing = new DataProcessing();
        return dataProcessing.judgeIsTrueAccount(id, password);

    }

    public OOServer() {


    }

    @Override
    public void run() {
        try {

            serverSocket = new ServerSocket(8888);
            System.out.println("服务器在9999端口监听...");
            while (true) {//当和某个客户端建立连接后，继续监听
                if(!loop){
                    break;

                }
                Socket socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                User user = (User) ois.readObject();
                Message message = new Message();
                if (user.getUserPassword() != null && user.getID() == null) {
                    DataProcessing dataProcessing = new DataProcessing();
                    String id = dataProcessing.getANewAccount(user.getUserPassword()) + "";
                    message.setMessageType(MessageType.MESSAGE_RETURN_AN_ACCOUNT);
                    message.setGetter(id);
                    oos.writeObject(message);
                } else {
                    if (checkUser(user.getID(), user.getUserPassword())) {
                        new DataProcessing().startState(user.getID());
                        message.setMessageType(MessageType.MESSAGE_LOGIN_SUCCESS);
                        oos.writeObject(message);
                        ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(socket, user.getID());
                        serverConnectClientThread.start();
                        ManageClientThread.addServerConnectClientThread(user.getID(), serverConnectClientThread);


                    } else {
                        System.out.println("id" + user.getID() + "密码" + user.getUserPassword() + "验证失败");
                        message.setMessageType(MessageType.MESSAGE_LOGIN_FAIL);
                        oos.writeObject(message);
                        socket.close();
                    }
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //如果服务器退出
            try {

                serverSocket.close();
                new DataProcessing().renewDataWhileServerExit();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }
}
