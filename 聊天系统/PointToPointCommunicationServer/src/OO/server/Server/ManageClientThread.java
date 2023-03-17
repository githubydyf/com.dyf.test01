package OO.server.Server;
import OO.connectDatabase.DataProcessing;
import java.util.HashMap;

/**
 * @ Author 董云飞
 * @ Student_ID 12103990114
 * 用于管理和客户端通信的线程
 */
public class ManageClientThread {
    private static HashMap<String,ServerConnectClientThread> hashMap = new HashMap<>();

    public static HashMap<String, ServerConnectClientThread> getHashMap() {
        return hashMap;
    }

    public static void addServerConnectClientThread(String id, ServerConnectClientThread serverConnectClientThread){
        hashMap.put(id,serverConnectClientThread);

    }
    public static ServerConnectClientThread getServerConnectClientThread(String id){
        return hashMap.get(id);
    }
    //返回在线用户列表
    public static String getOnlineUserList(){

        return new DataProcessing().getAllState();
    }
    //移除某个线程对象
    public static void removeClientThread(String id){
        hashMap.remove(id);
        new DataProcessing().endState(id);//改变状态

    }
}
