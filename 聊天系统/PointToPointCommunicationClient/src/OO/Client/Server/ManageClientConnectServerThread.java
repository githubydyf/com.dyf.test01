package OO.Client.Server;

import java.util.HashMap;

/**
 * @ Author 董云飞
 * @ Student_ID 12103990114
 * 管理客户端连接到服务器端线程类
 */
public class ManageClientConnectServerThread {
    //key->id value->线程
    private static HashMap<String,ClientConnectServerThread> hashMap = new HashMap<>();
    //加入线程到集合中
    public static void addClientConnectServerThread(String id,ClientConnectServerThread clientConnectServerThread){
        hashMap.put(id,clientConnectServerThread);
    }
    //获取对应id的线程
    public static ClientConnectServerThread getClientConnectServerThread(String id){
        return hashMap.get(id);
    }

}
