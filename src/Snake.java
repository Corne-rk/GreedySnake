import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * 此处的蛇用一个方块代替；
 * 蛇的每一节看成一个节点；
 */
public class Snake {
    Node head; //蛇头;
    private int size;
    private int score = 0; //得分；
    private boolean live = true;

    public Snake() {
        head = new Node(15,20,Direction.LEFT);
        head.next = null;
        size = 1;
    }

    public int getSize() {
        return size;
    }

    public void eatEgg(Egg egg) {
        if (head.getRect().intersects(egg.getRect())) {
            addToHead();
            egg.generateNewEgg();
            score += 5;
        }
    }

    public int getScore() {
        return score;
    }

    public void draw(Graphics g) {
        if (!live) {
            return;
        }
        Node work_next = head;
        while (work_next != null) {
            work_next.draw(g);
            work_next = work_next.next;
        }
        move();
        isDead();
    }

    public boolean isLive() {
        return live;
    }

    private void move() {
        addToHead();
        deleteFromTail();
    }

    private boolean dead(Node node) {
        if (node.row < 1 || node.col < 0 || node.row > Yard.ROWS-1 || node.col > Yard.COLS-1) {
            return true;
        }
        return false;
    }

    private void isDead() {
        if (head.row < 1 || head.col < 0 || head.row > Yard.ROWS-1 || head.col > Yard.COLS-1) {
            live = false;
            return;
        }
        Node work_next = head.next;
        while (work_next != null) {
            if (head.row == work_next.row && head.col == work_next.col) {
                live = false;
                return;
            }
            if (dead(work_next)) {
                live = false;
                return;
            }
            work_next = work_next.next;
        }
    }

    private void addToHead() {
        Node node = null;
        switch (head.direction) {
            case LEFT:
                node = new Node(head.col-1,head.row,head.direction);
                break;
            case UP:
                node = new Node(head.col,head.row-1,head.direction);
                break;
            case DOWN:
                node = new Node(head.col,head.row+1,head.direction);
                break;
            case RIGHT:
                node = new Node(head.col+1,head.row,head.direction);
                break;
        }
        node.next = head;
        head = node;
        size++;
    }

    private void deleteFromTail() {
        Node work_next = head;
        Node tmp = null;
        while (work_next.next != null) {
            tmp = work_next;
            work_next = work_next.next;
        }
        tmp.next = null;
        size--;

    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (head.direction != Direction.RIGHT) {
                    head.direction = Direction.LEFT;
                }
                break;
            case KeyEvent.VK_UP:
                if (head.direction != Direction.DOWN) {
                    head.direction = Direction.UP;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (head.direction != Direction.LEFT) {
                    head.direction = Direction.RIGHT;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (head.direction != Direction.UP) {
                    head.direction = Direction.DOWN;
                }
                break;
            case KeyEvent.VK_F2:
                if (live == false) {
                    this.head = new Node(15,20,Direction.LEFT);
                    live = true;
                    score = 0;
                    size = 1;
                }
        }
    }
}

class Node {
    int row;
    int col;
    Node next = null;
    Direction direction ; //蛇头的方向；
    int size = Yard.CELLSIZE;
    Color nodeColor = Color.yellow;

    public Node(int col, int row, Direction direction) {
        this.row = row;
        this.col = col;
        this.direction = direction;
    }

    public void draw(Graphics g) {
        Color tmp = g.getColor();
        g.setColor(nodeColor);
        g.fillRect(col*size,row*size,size,size);
        g.setColor(tmp);
    }

    public Rectangle getRect() {
        return new Rectangle(col*size,row*size,size,size);
    }
}
