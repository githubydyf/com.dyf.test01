package OO.InitialFace;
import OO.server.Server.OOServer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @ Author 董云飞
 * @ Student_ID 12103990114
 */
public class Main extends Application {
    public static TextField ip = new TextField("127.0.0.1");
    public static TextField port = new TextField("8888");
    Text text1 = new Text("IP");
    Text text2 = new Text("端口");
    Button button1 = new Button("启动服务器");
    Button button2 =  new Button("关闭服务器");
    BorderPane borderPane = new BorderPane();
    VBox vBox = new VBox();
    HBox hBox1 = new HBox();
    HBox hBox2 = new HBox();
    HBox hBox3 = new HBox();



    public static void main(String[] args){
       launch(args);


    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Image image = new Image("file:封面.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
        borderPane.setBackground(new Background(backgroundImage));
        initial();
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                OOServer.loop = false;
                primaryStage.close();
            }
        });
        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public void initial(){

        borderPane.setCenter(vBox);
        vBox.getChildren().addAll(hBox1,hBox2,hBox3);
        hBox1.getChildren().addAll(text1,ip);
        hBox2.getChildren().addAll(text2,port);
        hBox3.getChildren().addAll(button1,button2);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10,10,10,10));

        hBox1.setSpacing(10);
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setPadding(new Insets(10,10,10,10));

        hBox2.setSpacing(10);
        hBox2.setAlignment(Pos.CENTER);
        hBox2.setPadding(new Insets(10,10,10,10));

        hBox3.setSpacing(10);
        hBox3.setAlignment(Pos.CENTER);
        hBox3.setPadding(new Insets(10,10,10,10));
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                OOServer ooServer = new OOServer();
                ooServer.start();

            }
        });
    }
}
