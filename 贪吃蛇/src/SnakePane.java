import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.LinkedList;

/**
 * @ Author 董云飞
 * @ Student_ID 12103990114
 * 蛇的面板
 */
public class SnakePane extends Pane {
    private LinkedList<Snake> list = Const.list;
    private Snake food;
    private Canvas canvas;
    private GraphicsContext context;
    private int score = 0;//得分

    private int speed = 200;//速度
    public void setScore(int score){
        this.score = score;
    }
    public int getScore(){
        return score;
    }
    public void setFood(Snake food){
        this.food = food;
    }

    public Snake getFood(){
        return food;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public SnakePane() {
        canvas = new Canvas(800, 800);//创建画布
        context = canvas.getGraphicsContext2D();
        //在画布canvas中清空清空区域
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        context.setStroke(Color.WHITE);//游戏区域
        context.strokeRect(50, 50, canvas.getWidth() - 60, canvas.getHeight() - 60);
        context.setStroke(Color.WHITE);
        for (int i = 50; i <= canvas.getWidth() -  30; i += Const.size) {
            for (int j = 50; j <= canvas.getHeight() -30; j += Const.size) {
                context.strokeRect(i,j,Const.size,Const.size);

            }

        }
        context.setStroke(Color.WHITE);

        context.strokeText("当前分数为：" + score + " 分",20, 20, 100);
        this.getChildren().add(canvas);

    }
    //增强for遍历画蛇身
    public void drawCell(){
        Snake first = list.getFirst();
        context.setFill(Color.RED);
        context.fillRect(first.getX(),first.getY(),first.getCellLength(),first.getCellLength());
        for (int i = 1; i < list.size(); i++) {
            context.setFill(Color.WHITE);
            context.fillRect(list.get(i).getX(),list.get(i).getY(),list.get(i).getCellLength(),list.get(i).getCellLength());
        }


    }
    public void repaint(){
        //给定一个区域清空一个矩形
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        context.setStroke(Color.WHITE);
        context.strokeRect(50, 50, canvas.getWidth() - 60, canvas.getHeight() -60);
        context.setStroke(Color.WHITE);
        for (int i = 50; i <= canvas.getWidth() - 30; i += Const.size) {
            for (int j = 50; j <= canvas.getHeight() - 30; j += Const.size) {
                context.strokeRect(i,j,Const.size,Const.size);

            }

        }
        context.setStroke(Color.WHITE);
        context.strokeText("当前分数为：" + score + " 分",50, 20, 200);
        drawCell();
        context.setFill(food.getColor());
        context.fillRect(food.getX(), food.getY(), food.getCellLength(), food.getCellLength());

    }


}
