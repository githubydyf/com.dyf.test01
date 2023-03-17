package OO.MyUser;

import OO.base.User;

/**
 * @ Author 董云飞
 * @ Student_ID 12103990114
 * 存放客户端user
 */
public class MyUser {
    private User user = null;

    public MyUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
