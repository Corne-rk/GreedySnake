import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Yard extends Frame {
    public static int COLS = 30;
    public static int ROWS = 30;
    public static int CELLSIZE = 20;
    private Image imageBuffer = null;
    private Graphics graphicsBuffer = null;
    private Snake snake = new Snake();
    private Egg egg = new Egg(10,8);

    public void launch() {
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setBackground(Color.black);
        this.setLocation(200,200);
        this.setSize(COLS*CELLSIZE,ROWS*CELLSIZE);
        this.setVisible(true);
        this.addKeyListener(new KeyMonitor());
        new Thread(new PaintThread()).start();
    }

    @Override
    public void paint(Graphics g) {
        if (snake.isLive() == false) {
            g.setColor(Color.MAGENTA);
            Font tmp = g.getFont();
            g.setFont(new Font("宋体",Font.BOLD,50));
            g.drawString("Game Over",180,200);
            g.drawString("F2重新开始",173,250);
            g.setFont(tmp);
            return;
        }
        g.setColor(Color.MAGENTA);
        g.drawString("Score: "+snake.getScore(),10,60);
        g.drawString("当前蛇的节数: "+snake.getSize(),10,80);
        Color tmp = g.getColor();
        g.setColor(Color.green);
        for (int i = 0; i < COLS; i++) {
            g.drawLine(i*CELLSIZE,0,i*CELLSIZE,ROWS*CELLSIZE);
        }
        for (int i = 0; i < ROWS; i++) {
            g.drawLine(0,i*CELLSIZE,COLS*CELLSIZE,i*CELLSIZE);
        }
        g.setColor(tmp);
        snake.draw(g);
        egg.draw(g);
        snake.eatEgg(egg);
    }

    @Override
    public void update(Graphics g) {
        if (imageBuffer == null) {
            imageBuffer = this.createImage(COLS*CELLSIZE,ROWS*CELLSIZE);
        }
        graphicsBuffer = imageBuffer.getGraphics();
        graphicsBuffer.setColor(Color.black);
        graphicsBuffer.fillRect(0,0,COLS*CELLSIZE,ROWS*CELLSIZE);
        paint(graphicsBuffer);
        g.drawImage(imageBuffer,0,0,null);
    }

    public static void main(String[] args) {
        new Yard().launch();
    }

    private class KeyMonitor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            snake.keyPressed(e);
        }
    }

    private class PaintThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
        }
    }


}
