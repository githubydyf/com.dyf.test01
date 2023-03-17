package OO.Client.Server;


import OO.MyUser.MyUser;
import OO.base.Message;
import OO.base.MessageType;
import OO.base.User;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @ Author 董云飞
 * @ Student_ID 12103990114
 * 该类完成用户登录和用户注册功能
 */
public class UserClientServer {
    private MyUser myUser = null;
    private TextArea textArea = null;

    public TextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }

    public UserClientServer(MyUser myUser) {
        this.myUser = myUser;
        setUser(myUser.getUser());
    }

    private User user = new User();
    private Socket socket = null;

    public void setUser(User user) {
        this.user = user;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    //根据id和password到服务器验证是否合法
    public  boolean checkUser(String id,String password){
        boolean result = false;
        user.setID(id);
        user.setUserPassword(password);
        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 8888);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(user);//发送User对象
            //从服务器读取数据
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message message = (Message)ois.readObject();
            //判断是否登陆成功
            if(message.getMessageType().equals(MessageType.MESSAGE_LOGIN_SUCCESS)){
                //创建一个和服务器端保持通信的线程
                ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(socket,textArea);
                clientConnectServerThread.start();
                ManageClientConnectServerThread.addClientConnectServerThread(id,clientConnectServerThread);
                result = true;

            }else {
                //登陆失败，关闭socket
                socket.close();


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    //请求获取在线用户列表
    public void getOnlineUserList(){
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_GET_ONLINE_USER_LIST);
        message.setSender(user.getID());
        try {
            //得到用户线程
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(user.getID()).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    //退出客户端，并发送给服务端退出的消息
    public void logout(){
        Message message = new Message();
        message.setSender(user.getID());
        message.setMessageType(MessageType.MESSAGE_CLIENT_EXIT);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
            System.out.println(user.getID() + "退出程序");
            socket.close();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public User getUser() {
        return user;
    }
}
