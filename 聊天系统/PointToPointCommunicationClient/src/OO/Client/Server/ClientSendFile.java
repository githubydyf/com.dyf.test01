package OO.Client.Server;

import OO.Client.Utilities.StreamUtils;
import OO.base.Message;
import OO.base.MessageType;

import java.io.*;

/**
 * @ Author 董云飞
 * @ Student_ID 12103990114
 * 此类用于用户之间发送文件
 */
public class ClientSendFile {
    //发送文件的方法

    /**
     *
     * @param filepath 文件路径
     * @param sender 发送者的id
     * @param getter 接受者的id
     */
    public static void sendFile(String filepath,String sender,String getter,String destPath){
        if(isFileExit(filepath)){
            File file = new File(filepath);
            byte[] bytes = getByteArrayFromFile(filepath);
            Message message = new Message();
            message.setMessageType(MessageType.MESSAGE_SEND_FILE);
            message.setFilename(file.getName());
            message.setSender(sender);
            message.setGetter(getter);
            message.setFile(bytes);
            message.setSendTime(new java.util.Date().toString());
            message.setFileStoreAddress(destPath);
            try {
                ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(sender).getSocket().getOutputStream());
                oos.writeObject(message);
                System.out.println("发送成功");
            } catch (IOException e) {
                e.printStackTrace();
            }


        }else {
            System.out.println(filepath + "文件不存在，请检查文件格式或路径");
        }

    }

    //判断要发送的文件是否存在
    public static boolean isFileExit(String filepath){
        File file = new File(filepath);
        if(file.exists()){
            if(file.isFile()){
                return true;
            }

        }
        return false;

    }
    //将文件转换为字节数组
    public static byte[] getByteArrayFromFile(String filepath){
        byte[] bytes = null;
        File file = new File(filepath);
        try {
            bytes = StreamUtils.streamToByteArray(new FileInputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;


    }


}
