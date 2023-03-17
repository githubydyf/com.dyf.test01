import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.util.Optional;

/**
 * @ Author 董云飞
 * @ Student_ID 12103990114
 */
public class Main extends Application {
    public static int direction = Direction.RIGHT;//初始化时默认方向
    public static volatile  boolean isRequestClosed = false;


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("贪吃蛇大作站");
        SnakePane snakePane = new SnakePane();
        initial(snakePane);
        snakePane.drawCell();

        execute(primaryStage,snakePane);
        Scene scene = new Scene(snakePane, 850, 800);
        Image image = new Image("file:游戏背景.jpeg");
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        snakePane.setBackground(new Background(backgroundImage));
        //键盘控制蛇的方向
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.A){
                    direction = Direction.LEFT;

                }else if(event.getCode() == KeyCode.W){
                    direction = Direction.UP;

                }else if(event.getCode() == KeyCode.D){
                    direction = Direction.RIGHT;

                }else if(event.getCode() == KeyCode.S){
                    direction = Direction.DOWN;

                }

            }
        });
        primaryStage.setScene(scene);

        primaryStage.show();


    }
    //初始化蛇
    //synchronized保证了每个时刻都只有一个线程访问同步代码块
    //也就确定了线程执行同步代码块是分先后顺序的，保证了有序性
    private synchronized void initial(SnakePane snakePane){
        //清空蛇的集合
        Const.list.clear();
        //创建三个蛇体
        Snake snake1 = new Snake(50,50,Const.size, Color.RED);
        Snake snake2 = new Snake(70,50,Const.size, Color.RED);
        Snake snake3 = new Snake(90,50,Const.size, Color.RED);
        Const.list.add(snake1);
        Const.list.add(snake2);
        Const.list.add(snake3);
        snakePane.setScore(0);
        direction = Direction.RIGHT;
        snakePane.setSpeed(200);

    }
    private void execute(Stage stage,SnakePane snakePane){
        initial(snakePane);

        Snake food = new Snake(190,190,Const.size,Color.RED);
        snakePane.setFood(food);
        MyTask myTask= new MyTask(snakePane);
        myTask.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            //类的工作状态监听事件
            public void handle(WorkerStateEvent event) {
                if(myTask.isForceCancel) {
                    String message = "";
                    if (myTask.isOverBound) {
                        message = "你已出界";
                    }
                    if (myTask.isIsEatItSelf()) {
                        message = "你把自己吃了";
                    }
                    Alert alert = new Alert(Alert.AlertType.NONE, (message +"\n你的分数：" + snakePane.getScore()  +"\n是否重新开始"), new ButtonType("重新开局", ButtonBar.ButtonData.YES), new ButtonType("退出游戏", ButtonBar.ButtonData.NO));

                    Optional<ButtonType> buttonType = alert.showAndWait();//用对话框阻塞画面，并返回一个用户输入的值
                    if (buttonType.isPresent()) {//是否存在值
                        if (buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES)) {
                            myTask.isForceCancel = false;
                            myTask.reDetect();
                            execute(stage, snakePane);
                        } else {
                            exit(myTask);
                            Platform.exit();//终止javaFX应用程序
                        }
                    }
                }
            }
        });
       //启动贪吃蛇线程
        new Thread(myTask).start();
        //设置一个任务来监控是否游戏结束
        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                while (!isRequestClosed){
                    if(MyTask.isEatItSelf || MyTask.isOverBound){
                        myTask.cancel(true);
                        MyTask.isForceCancel = true;
                        break;
                    }

                }
               return null;
            }
        };
        new Thread(task).start();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                exit(myTask);
            }
        });


    }

    public void exit(MyTask myTask){
        isRequestClosed = true;
        myTask.cancel();

    }
}
