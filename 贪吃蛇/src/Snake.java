import javafx.scene.paint.Color;

/**
 * @ Author 董云飞
 * @ Student_ID 12103990114
 * 蛇类对象
 */
public class Snake {
    private double x;
    private double y;

    private double cellLength;//蛇体尺寸

    private Color color;



    public Snake(double x, double y, double cellLength, Color color) {
        this.x = x;
        this.y = y;
        this.cellLength = cellLength;
        this.color = color;

    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getCellLength() {
        return cellLength;
    }

    public void setCellLength(double cellLength) {
        this.cellLength = cellLength;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }


}
