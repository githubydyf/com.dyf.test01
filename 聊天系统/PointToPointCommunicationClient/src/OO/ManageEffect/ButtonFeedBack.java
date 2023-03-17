package OO.ManageEffect;

import OO.Client.Server.ClientSendFile;
import OO.Client.Server.ManageClientConnectServerThread;
import OO.Client.Server.UserClientServer;
import OO.MyUser.MyUser;
import OO.base.User;
import javafx.scene.control.TextArea;

import java.io.File;
import java.util.Date;

/**
 * @ Author 董云飞
 * @ Student_ID 12103990114
 */
public class ButtonFeedBack {

        //登陆时判断
        public static boolean judge(String id, String password, MyUser myUser, TextArea textArea){
            UserClientServer userClientServer = new UserClientServer(myUser);
            userClientServer.setTextArea(textArea);




            return userClientServer.checkUser(id, password);
        }

        //查询好友时按钮反应
        public static void sendMessageOnlineUserList(User  user){
            UserClientServer userClientServer = new UserClientServer(new MyUser(user));
            userClientServer.setUser(user);
            userClientServer.setSocket(ManageClientConnectServerThread.getClientConnectServerThread(user.getID()).getSocket());
            userClientServer.getOnlineUserList();

        }
        //退出时发送消息到服务器
        public  static void exit(User user){
            UserClientServer userClientServer = new UserClientServer(new MyUser(user));
            userClientServer.setUser(user);
            userClientServer.setSocket(ManageClientConnectServerThread.getClientConnectServerThread(user.getID()).getSocket());
            userClientServer.logout();

        }
        public static void sendFile(User user,String id,String filePath,TextArea textArea){

            ClientSendFile.sendFile(filePath,user.getID(),id,"D:/test");
            textArea.appendText("\n" + new Date().toString());
            textArea.appendText("\n" + user.getID() + "向" + id + "发送了" + new File(filePath).getName() + "文件");

        }



    }

