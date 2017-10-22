import java.awt.*;
import java.util.Random;

public class Egg {
    int row;
    int col;
    int size = Yard.CELLSIZE;
    boolean colorFlag = true;
    static Random random = new Random();

    public Egg(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void generateNewEgg() {
        row = random.nextInt(Yard.ROWS-3)+3;
        col = random.nextInt(Yard.COLS-3)+3;
    }

    public void draw(Graphics g) {
        Color tmp = g.getColor();
        if (colorFlag) {
            g.setColor(Color.red);
            colorFlag = false;
        } else {
            g.setColor(Color.gray);
            colorFlag = true;
        }
        g.fillOval(col*size,row*size,size,size);
        g.setColor(tmp);
    }

    public Rectangle getRect() {
        return new Rectangle(col*size,row*size,size,size);
    }
}
