package OO.ManageEffect;

import OO.Client.Server.ClientConnectServerThread;
import OO.Client.Server.ManageClientConnectServerThread;
import OO.MyUser.MyUser;
import javafx.application.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * @ Author 董云飞
 * @ Student_ID 12103990114
 * 展示好友的屏幕
 */
public class ShowFriendsList extends Application{
    MyUser myUser = null;
    ListView<String> listView1 = new ListView<>();//在线
    ListView<String> listView2 = new ListView<>();//离线
    ObservableList<String> ob1 = FXCollections.observableArrayList();
    ObservableList<String> ob2 = FXCollections.observableArrayList();
    TitledPane titledPane1;//声明标题面板
    TitledPane titledPane2;
    Accordion accordion = new Accordion();//创建折叠面板





    public ShowFriendsList(MyUser myUser) {
      this.myUser  = myUser;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Image image = new Image("file:封面.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        accordion.setBackground(new Background(backgroundImage));

        ClientConnectServerThread.ob1 = ob1;
        ClientConnectServerThread.ob2 = ob2;

        listView1.setItems(ob1);
        listView2.setItems(ob2);
        titledPane1 = new TitledPane("在线",listView1);
        titledPane2 = new TitledPane("离线",listView2);
        accordion.getPanes().addAll(titledPane1,titledPane2);
        Scene scene = new Scene(accordion,300,700);
        primaryStage.setScene(scene);
        primaryStage.show();


    }


}
