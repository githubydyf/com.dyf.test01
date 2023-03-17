package OO.ManageEffect;

import OO.base.Message;
import OO.base.MessageType;
import OO.base.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @ Author 董云飞
 * @ Student_ID 12103990114
 */
public class RegisteredAccount extends Application {
    Text text = new Text("添加账号(密码不超过10位)");
    Text text1 = new Text("输入密码");
    Text text2 = new Text("确认密码");

    PasswordField passwordField1 = new PasswordField();
    PasswordField passwordField2 = new PasswordField();
    Button button = new Button("确定");
    Button button1 = new Button("退出");

    VBox vBox = new VBox();
    HBox hBox1 = new HBox();
    HBox hBox2 = new HBox();
    HBox hBox3 = new HBox();
    HBox hBox4 = new HBox();

    BorderPane borderPane = new BorderPane();

    @Override
    public void start(Stage primaryStage) throws Exception {
        initial();
        Scene scene = new Scene(borderPane,800,600);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });



    }
    public void initial(){
        Image image = new Image("file:封面.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
        borderPane.setBackground(new Background(backgroundImage));

        hBox1.getChildren().add(text);
        hBox2.getChildren().addAll(text1,passwordField1);
        hBox3.getChildren().addAll(text2,passwordField2);
        hBox4.getChildren().addAll(button,button1);
        vBox.getChildren().addAll(hBox1,hBox2,hBox3,hBox4);
        borderPane.setCenter(vBox);
        hBox1.setSpacing(10);
        hBox1.setAlignment(Pos.CENTER);
        hBox2.setSpacing(10);
        hBox2.setAlignment(Pos.CENTER);
        hBox3.setSpacing(10);
        hBox3.setAlignment(Pos.CENTER);
        hBox4.setSpacing(10);
        hBox4.setAlignment(Pos.CENTER);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        text.setFont(Font.font("KaiTi",40));
        text.setFill(Color.GREEN);

        button.setPrefWidth(80);
        button1.setPrefWidth(80);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String password1 = passwordField1.getText();
                String password2 = passwordField2.getText();
                if(password1.equals(password2) && password1.length()<= 10 && password1.length() > 0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    try {
                        Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 8888);
                        User user = new User();
                        user.setUserPassword(password1);
                        user.setID(null);
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        oos.writeObject(user);

                            Message message = (Message)ois.readObject();
                            if(message.getMessageType().equals(MessageType.MESSAGE_RETURN_AN_ACCOUNT)){
                                alert.setContentText("账号：" + message.getGetter() + "\n" + "密码：" +  password1);
                                alert.show();

                            }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("两次密码不一致");
                    alert.setHeaderText("出错");
                    alert.show();
                }

            }
        });


    }

}
