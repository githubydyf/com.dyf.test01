package OO.window;


import OO.ManageEffect.ButtonFeedBack;

import OO.ManageEffect.ChatBoard;
import OO.ManageEffect.RegisteredAccount;
import OO.MyUser.MyUser;
import OO.base.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * @ Author 董云飞
 * @ Student_ID 12103990114
 */
public class Main extends Application {
    private BorderPane borderPane = new BorderPane();
    private Button button1 = new Button("登录");
    private Button button2 = new Button("注册账号");
    private TextField textField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private VBox vBox = new VBox();
    private Text text1 = new Text("账户");
    private Text text2 = new Text("密码");
    private HBox hBox1 = new HBox();
    private HBox hBox2 = new HBox();
    private HBox hBox3 = new HBox();
    private HBox hBox4 = new HBox();
    private Text title = new Text("OO通讯");
    private MyUser myUser = null;//存放当前用户的User



    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initial();
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String id = textField.getText();
                String password = passwordField.getText();
                User user = new User();
                user.setID(id);
                user.setUserPassword(password);
                myUser = new MyUser(user);
                ChatBoard chatBoard = new ChatBoard(myUser);
                TextArea textArea = chatBoard.getTextArea();
                if(ButtonFeedBack.judge(id,password,myUser,textArea)){//判断账号密码是否正确
                    try {



                        chatBoard.start(new Stage());
                        primaryStage.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("用户名或密码错误");
                    alert.setHeaderText("出错");
                    alert.show();



                }

            }
        });
        borderPane.setCenter(vBox);
        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("OO通讯");
        primaryStage.show();
        double x = textField.getLayoutX();
        text1.setX(x - text1.getWrappingWidth());
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    new RegisteredAccount().start(new Stage());
                    primaryStage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }
    public void initial(){

        textField.setPrefHeight(40);
        textField.setMaxWidth(300);
        passwordField.setPrefHeight(40);
        passwordField.setMaxWidth(300);

        hBox1.setSpacing(10);
        hBox1.setPadding(new Insets(10,10,10,10));
        hBox1.setAlignment(Pos.CENTER);

        hBox2.setSpacing(10);
        hBox2.setPadding(new Insets(10,10,10,10));
        hBox2.setAlignment(Pos.CENTER);

        hBox1.getChildren().addAll(text1,textField);
        hBox2.getChildren().addAll(text2,passwordField);

        hBox3.getChildren().addAll(button1,button2);
        button1.setPrefWidth(80);
        button2.setPrefWidth(80);
        hBox3.setSpacing(20);
        hBox3.setPadding(new Insets(10,10,10,20));
        hBox3.setAlignment(Pos.CENTER);



        hBox4.getChildren().add(title);
        hBox4.setSpacing(20);
        hBox4.setPadding(new Insets(10,10,10,20));
        hBox4.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(hBox4,hBox1,hBox2,hBox3);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(30,30,30,30));


        title.setFont(Font.font("KaiTi",50));
        title.setFill(Color.BLACK);
        Image image = new Image("file:封面.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(image,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
        borderPane.setBackground(new Background(backgroundImage));

    }

}
