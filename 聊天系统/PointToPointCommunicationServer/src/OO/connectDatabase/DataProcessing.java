package OO.connectDatabase;

import com.mysql.jdbc.Driver;

import java.sql.*;


/**
 * @ Author 董云飞
 * @ Student_ID 12103990114
 */
public class DataProcessing {

    //服务器退出时所有状态清空
    public void renewDataWhileServerExit() {
        Connection connection = null;
        Statement statement = null;
        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/OO通讯", "root", "dyf");
            statement = connection.createStatement();
            String sql = "update user set state = 0";
            int i = statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    //用户登录时开启登录状态
    public void startState(String id) {
        Connection connection = null;
        Statement statement = null;
        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/OO通讯", "root", "dyf");
            statement = connection.createStatement();
            String sql = "update user set state = 1 where id =" + id;
            int i = statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    //用户退出时关闭登录状态
    public void endState(String id) {
        Connection connection = null;
        Statement statement = null;
        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/OO通讯", "root", "dyf");
            statement = connection.createStatement();
            String sql = "update user set state = 0 where id =" + id;
            int i = statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }
    //创建用户返回一个新账号
    public int getANewAccount(String password){
        int num = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            DriverManager.registerDriver(new Driver());

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/OO通讯", "root", "dyf");
            statement = connection.createStatement();
            String sql = "select id,password,state from user order by id desc";
            resultSet = statement.executeQuery(sql);
            int MaxID;
            if(resultSet.next()){
                MaxID = resultSet.getInt("id");
                num = MaxID + 100;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        if(num != 0){
            storeANewAccount(num + "",password);

        }
        return num;
    }
    //存储一个新账号
    public void storeANewAccount(String id,String password){
        Connection connection = null;
        Statement statement = null;
        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/OO通讯", "root", "dyf");
            statement = connection.createStatement();
            //            //String sql = "insert into user values(1000,'123456',0)";

            String sql = "insert into user values(" + id +  "," + password +  ",0)";
            int i = statement.executeUpdate(sql);


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }



    }
    //判断账号密码是否正确且是否已登陆
    public boolean judgeIsTrueAccount(String id,String password){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        if(judgeIsAccount(id)){

        try {
            DriverManager.registerDriver(new Driver());

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/OO通讯", "root", "dyf");
            statement = connection.createStatement();
            String sql = "select state,password from user where id = " + id;
            resultSet = statement.executeQuery(sql);

            if(resultSet.next()){

                String pw = resultSet.getString("password");
                int num = resultSet.getInt("state");
                if(pw.equals(password)  && num  ==  0){
                    return true;
                }


            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        }




        return false;

    }
    //判断账号是否存在
    public boolean judgeIsAccount(String id){
        boolean flag = false;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            DriverManager.registerDriver(new Driver());

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/OO通讯", "root", "dyf");
            statement = connection.createStatement();
            String sql = "select * from user";

             resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                String ID = resultSet.getString(1);
                if(id.equals(ID)){
                    flag = true;
                    break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return flag;

    }
    //获取好友状态
    public String getAllState(){
        String list = "";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            DriverManager.registerDriver(new Driver());

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/OO通讯", "root", "dyf");
            statement = connection.createStatement();
            String sql = "select id,state from user";

            resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
               String id = resultSet.getString("id");
               int n = resultSet.getInt("state");
               if(n == 0){
                   list += id + "(离线) ";
               }else if(n == 1){
                   list +=  id + "(在线) ";
               }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;

    }
}

