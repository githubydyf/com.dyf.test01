package OO.base;

import java.io.Serializable;

/**
 * @ Author 董云飞
 * @ Student_ID 12103990114
 * 该类表示一个用户信息
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;//提升序列化和反序列化的兼容性
    private String ID;//用户账号
    private String userPassword;//账号密码

    public User(String ID, String userPassword) {
        this.ID = ID;
        this.userPassword = userPassword;
    }

    public User() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
