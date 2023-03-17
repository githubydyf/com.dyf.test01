package OO.Client.Server;


import OO.base.Message;
import OO.base.MessageType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;


import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @ Author 董云飞
 * @ Student_ID 12103990114
 */
public class ClientConnectServerThread extends Thread {
    public static ObservableList<String> ob1 = FXCollections.observableArrayList();
    public static ObservableList<String> ob2 = FXCollections.observableArrayList();
    private TextArea textArea = null;
    //线程持有socket
    private Socket socket;

    public ClientConnectServerThread(Socket socket, TextArea textArea) {
        this.socket = socket;
        this.textArea = textArea;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("客户端线程等待从服务器中读取数据");
            try {
                if (getSocket().isClosed()) {
                    break;
                }
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                if (message.getMessageType().equals(MessageType.MESSAGE_RETURN_ONLINE_USER_LIST)) {
                    String[] s = message.getContent().split(" ");
                  ob1.removeAll();
                   ob2.removeAll();


                    for (int i = 0; i < s.length; i++) {
                        if (s[i].contains("在线")) {
                            ob1.add(s[i]);
                        } else if (s[i].contains("离线")) {
                            ob2.add(s[i]);
                        }

                    }


                    //new ShowFriendsList();
                } else if (message.getMessageType().equals(MessageType.MESSAGE_COMMON_MESSAGE)) {//接受消息

                    textArea.appendText("\n" + message.getSendTime());
                    textArea.appendText("\n" + message.getSender() + ":" + message.getContent());

                } else if (message.getMessageType().equals(MessageType.MESSAGE_TO_ALL_MESSAGE)) {

                    textArea.appendText("\n" + message.getSendTime());
                    textArea.appendText("\n" + message.getSender() + ":" + message.getContent());

                } else if (message.getMessageType().equals(MessageType.MESSAGE_GET_FILE)) {
                    //  System.out.println(message.getSender() + " : " + "文件" + message.getFilename() );
                    textArea.appendText("\n" + message.getSendTime());
                    textArea.appendText("\n" + message.getSender() + "向你发送了文件" + message.getFilename());
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(message.getFileStoreAddress() + "/" + message.getFilename()));
                    bos.write(message.getFile());
                    bos.close();
//                    System.out.println(message.getFilename() + "已存储");
                    textArea.appendText("\n" + message.getFilename() + "已储存至" + message.getFileStoreAddress());
                }
                //若没有数据传送过来，会阻塞
            } catch (Exception e) {
                // e.printStackTrace();
            }

        }

    }
}
