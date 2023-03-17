package OO.base;

import java.io.Serializable;

/**
 * @ Author 董云飞
 * @ Student_ID 12103990114
 * 客户端和服务端通讯时接受的消息
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;//提升序列化和反序列化的兼容性
    private String sender;//发送者
    private String getter;//接受者
    private String content;//内容
    private String sendTime;//发送时间
    private String messageType;//消息类型

    private byte[] file;//文件内容
    private String filename;//文件名
    private String fileStoreAddress;//文件存储地址

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileStoreAddress() {
        return fileStoreAddress;
    }

    public void setFileStoreAddress(String fileStoreAddress) {
        this.fileStoreAddress = fileStoreAddress;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
