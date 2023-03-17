package OO.ManageEffect;

import OO.Client.Server.ManageClientConnectServerThread;
import OO.Client.Server.ManageClientSendMessage;
import OO.MyUser.MyUser;
import OO.base.Message;
import OO.base.MessageType;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.ObjectOutputStream;
import java.util.Date;


/**
 * @ Author 董云飞
 * @ Student_ID 12103990114
 */
public class ChatBoard extends Application {
    private MyUser myUser =   null;
    private BorderPane borderPane = new BorderPane();
    private TextArea textArea = new TextArea();//显示对话框
    VBox vBox=new VBox(15);//设置间距为15
    private TextField id = new TextField("id");

    Button Quit=new Button("退出");//退出
    Button SendFile = new Button("发送文件");
    Button getFriendList = new Button("查看好友");
    TextField file = new TextField("文件名");//文件名
    TextField textField=new TextField();//构造新的文本框
    ChoiceBox choiceBox= new ChoiceBox(FXCollections.observableArrayList("私聊","群聊"));


    public static void main(String[] args) {
        launch(args);
    }


    public ChatBoard(MyUser myUser) {
        this.myUser = myUser;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    @Override
    public void start(Stage primaryStage){
        initial();

        borderPane.setBottom(textField);
        borderPane.setCenter(textArea);
        borderPane.setRight(vBox);
        Scene scene=new Scene(borderPane,800,600);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.initStyle(StageStyle.DECORATED);//设置窗口风格:白色背景，带有最小化/最大化/关闭等有操作系统平台装饰(默认设置)
        primaryStage.setTitle(myUser.getUser().getID() + " ChatRoom");
        primaryStage.show();

    }
    public void initial(){
        //文件按钮
        SendFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String text = file.getText();
                File file = new File(text);
                if(file.exists()){
                    if(!file.isFile()){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("文件名输入错误");
                        alert.show();

                    }else {
                        String getter = id.getText();
                        ButtonFeedBack.sendFile(myUser.getUser(),getter,text,textArea);

                    }
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("文件路径输入错误");
                    alert.show();

                }

            }
        });
        //发送好友请求
        getFriendList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                ButtonFeedBack.sendMessageOnlineUserList(myUser.getUser());

            }

         });
        //键盘监控
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent o) {
                if(o.getCode()== KeyCode.ENTER)//检测到Enter键，同上
                {
                    String input = "";
                    String name = choiceBox.getValue().toString();
                    if(name.equals("私聊")){
                        input += "(私聊)";
                        input += textField.getText();
                        ManageClientSendMessage.singlePersonCommunication(myUser.getUser().getID(),id.getText(),input);


                    }else if(name.equals("群聊")){
                        input += "(群聊)";
                        input += textField.getText();
                        ManageClientSendMessage.sendMessageToAll(myUser.getUser().getID(),input);

                    }
                    textField.clear();
                    textArea.appendText("\n"  + new Date().toString());
                    textArea.appendText("\n"+myUser.getUser().getID()+":"+input);
                }

            }
        });

        Quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ButtonFeedBack.exit(myUser.getUser());
            }
        });
        textArea.setWrapText(true);//实现自动换行

        Quit.setPrefWidth(55);//设置按钮宽度


        choiceBox.getSelectionModel().select(0);//默认下拉框选中第0项
        Image image = new Image("file:封面.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        vBox.setBackground(new Background(backgroundImage));

        vBox.getChildren().addAll(Quit,choiceBox,id,SendFile,file,getFriendList);
        vBox.setStyle("-fx-border-color: green;-fx-border-width:2.5;-fx-border-height:200");
        vBox.setPadding(new Insets(10,10,5,10));//设置距离上
        getFriendList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    new ShowFriendsList(myUser).start(new Stage());
                    ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(myUser.getUser().getID()).getSocket().getOutputStream());
                    Message message = new Message();
                    message.setMessageType(MessageType.MESSAGE_GET_ONLINE_USER_LIST);
                    oos.writeObject(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

}
