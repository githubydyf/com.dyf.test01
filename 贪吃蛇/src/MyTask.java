import javafx.concurrent.Task;
import javafx.scene.paint.Color;

import java.util.Random;

/**
 * @ Author 董云飞
 * @ Student_ID 12103990114
 */

public class MyTask extends Task<Integer> {
    private SnakePane snakePane;
    private Random random = new Random();
    private int currentSize = 0;
    //volatile关键字的作用 一个数据被一个线程修改之后，会立即刷新到内存之中，保证其他的线程得到正确的数据
    //几种状态
    public volatile static boolean isForceCancel = false;//是否强制退出
    public volatile static boolean isOverBound = false;//蛇是否越界
    public volatile static boolean isEatItSelf = false;//蛇是否吃到他自己


    public MyTask(SnakePane snakePane) {
        this.snakePane = snakePane;
    }

    @Override
    protected Integer call() throws Exception {
        while (!isForceCancel) {
            //判断是否越界
            if (Const.list.getFirst().getX() < 50 || Const.list.getFirst().getY() < 50 || Const.list.getLast().getX() > 770 || Const.list.getLast().getY() > 770) {
                isOverBound = true;
                continue;
            }
            //判断是否咬到自己
            if (Const.list.size() > 3 && isIsEatItSelf()) {
                isEatItSelf = true;
                continue;

            }
            //蛇跟随方向移动
            switch (Main.direction) {
                case Direction.UP:
                    Snake snake = Const.list.removeLast();
                    snake.setY(Const.list.getFirst().getY() - Const.size);
                    snake.setX(Const.list.getFirst().getX());
                    Const.list.addFirst(snake);
                    if (isEat(snake)) {
                        controlFood(Direction.UP);
                    }
                    break;
                case Direction.DOWN:
                    Snake snake1 = Const.list.removeLast();
                    snake1.setX(Const.list.getFirst().getX());
                    snake1.setY(Const.list.getFirst().getY() + Const.size);
                    Const.list.addFirst(snake1);
                    if(isEat(snake1)){
                        controlFood(Direction.DOWN);
                    }
                    break;
                case Direction.RIGHT:
                    Snake snake2 = Const.list.removeLast();
                    snake2.setX(Const.list.getFirst().getX() + Const.size);
                    snake2.setY(Const.list.getFirst().getY());
                    Const.list.addFirst(snake2);
                    if(isEat(snake2)){
                        controlFood(Direction.RIGHT);
                    }
                    break;
                case Direction.LEFT:
                    Snake snake3 = Const.list.removeLast();
                    snake3.setX(Const.list.getFirst().getX() - Const.size);
                    snake3.setY(Const.list.getFirst().getY());
                    Const.list.addFirst(snake3);
                    if(isEat(snake3)){
                        controlFood(Direction.LEFT);
                    }
                    break;
            }
            if(Const.list.size() != currentSize && Const.list.size() % 6 == 0){
                currentSize = Const.list.size();
                snakePane.setSpeed(snakePane.getSpeed() - 20);
            }
            snakePane.repaint();
            Thread.sleep(snakePane.getSpeed());//休眠事件

        }

        return null;
    }

    //重新检测
    public void reDetect() {
        isOverBound = false;
        isEatItSelf = false;

    }

    //判断是否吃到食物
    public boolean isEat(Snake snake) {
        if (snake.getX() == snakePane.getFood().getX() && snake.getY() == snakePane.getFood().getY()) {
            snakePane.setScore(snakePane.getScore() + 1);
            return true;
        }
        return false;

    }

    //判断是否咬到自己
    public boolean isIsEatItSelf() {
        Snake head = Const.list.getFirst();
        for (int i = 1; i < Const.list.size(); i++) {
            if (head.getX() == Const.list.get(i).getX() && head.getY() == Const.list.get(i).getY()) {
                return true;
            }

        }
        return false;

    }

    public void controlFood(int direction) {
        Snake food = snakePane.getFood();

        switch (direction) {//将食物添加到蛇体内
            case Direction.UP:
                food.setY(Const.list.getLast().getY() + Const.size);
                food.setX(Const.list.getLast().getX());
                break;
            case Direction.DOWN:
                food.setY(Const.list.getLast().getY() - Const.size);
                food.setX(Const.list.getLast().getX());
                break;

            case Direction.RIGHT:
                food.setX(Const.list.getLast().getX() - Const.size);
                food.setY(Const.list.getLast().getY());
                break;
            case Direction.LEFT:
                food.setX(Const.list.getLast().getX() + Const.size);
                food.setY(Const.list.getLast().getY());
                break;
        }
        food.setColor(Color.BLACK);
        Const.list.addLast(food);
        Snake newFood = createFood();
        while (checkFood(newFood)) {
            newFood = createFood();

        }
        snakePane.setFood(newFood);

    }

    //判断食物是否在蛇体内
    public boolean checkFood(Snake food) {
        for (Snake snake : Const.list) {
            if (food.getX() == snake.getX() && food.getY() == snake.getY()) {
                return true;
            }
        }
        return false;
    }

    //创建食物
    public Snake createFood() {
        int size = (int)Const.size;

        int x = random.nextInt(37);
        int y = random.nextInt(37);;
        x = x * size + 50;
        y = y * size + 50;

        return new Snake(x, y, Const.size, Color.DEEPPINK);


    }
}


