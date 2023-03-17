import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * @ Author 董云飞
 * @ Student_ID 12103990114
 */
public class InitialFace extends Application {
    public Button button = new Button("开始游戏");

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    new Main().start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                primaryStage.close();
            }
        });


        button.setFont(new Font("KaiTi",50));
        button.setTextFill(Color.RED);
        button.setPrefWidth(280);
        button.setPrefHeight(140);
        HBox hBox = new HBox(button);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(20,20,20,20));
        BorderPane borderPane = new BorderPane();
        borderPane.setBottom(hBox);
        Scene scene = new Scene(borderPane, 800, 850);
        primaryStage.setScene(scene);
        primaryStage.show();
        Image image = new Image("file:贪吃蛇封面.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        borderPane.setBackground(new Background(backgroundImage));
        primaryStage.setResizable(false);
        BackgroundFill backgroundFill = new BackgroundFill(Color.BLACK, new CornerRadii(20), Insets.EMPTY);
        Background background = new Background(backgroundFill);
        button.setBackground(background);
        BorderStroke borderStroke = new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(20), new BorderWidths(5));
        Border border = new Border(borderStroke);
        button.setBorder(border);


    }
}
