import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.util.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private static final int WIDTH = 640;
    private static final int HEIGHT = 640;
    private static final int UNIT_SIZE = 20;   // size of each block snake and food
    private static final int GAME_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    private static final int DELAY = 100;

    private final int[] snakeX = new int[GAME_UNITS];
    private final int[] snakeY = new int[GAME_UNITS];
    private int snakeLength = 1;

    private int foodX;
    private int foodY;

    private char direction = 'R';
    private boolean isRunning = false;

    private final Random random;
    int score=0;

    public SnakeGame() {
        random = new Random();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(this);
        startGame();
    }

    public void startGame() {
        isRunning = true;
        newFood();     // generate new food
        snakeX[0] = WIDTH / 2;     // middle se start kre ga game ko
        snakeY[0] = HEIGHT / 2;
        Timer timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {     // draw snakw and food
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (isRunning) {
            for (int i = 0; i < snakeLength; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(snakeX[i], snakeY[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(Color.yellow);
                    g.fillRect(snakeX[i], snakeY[i], UNIT_SIZE, UNIT_SIZE);
                }
            }

            g.setColor(Color.red);
            g.fillRect(foodX, foodY, UNIT_SIZE, UNIT_SIZE);
        } else {
            gameOver(g);
        }
    }

    public void newFood() {
        foodX = random.nextInt((int) (WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        foodY = random.nextInt((int) (HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        for (int i = snakeLength; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }

        switch (direction) {
            case 'U':
                snakeY[0] -= UNIT_SIZE;
                break;
            case 'D':
                snakeY[0] += UNIT_SIZE;
                break;
            case 'L':
                snakeX[0] -= UNIT_SIZE;
                break;
            case 'R':
                snakeX[0] += UNIT_SIZE;
                break;
        }

        // Wrap around logic
        if (snakeX[0] < 0) {
            snakeX[0] = WIDTH - UNIT_SIZE;
        } else if (snakeX[0] >= WIDTH) {
            snakeX[0] = 0;
        } else if (snakeY[0] < 0) {
            snakeY[0] = HEIGHT - UNIT_SIZE;
        } else if (snakeY[0] >= HEIGHT) {
            snakeY[0] = 0;
        }
    }

    public void checkFoodCollision() {
        if (snakeX[0] == foodX && snakeY[0] == foodY)
        {   score++;
            snakeLength++;
            newFood();
        }
    }

    public void checkCollision() {
        for (int i = snakeLength; i > 0; i--) {
            if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                isRunning = false;
            }
        }

//        if (!isRunning) {
//            // Game over
//        }
    }

    public void gameOver(Graphics g) {
        String msg = "Game Over";
        String ss="Score = ";
        String scoree=String.valueOf(score);
        Font font = new Font("Helvetica", Font.BOLD, 40);
        FontMetrics metrics = getFontMetrics(font);

        g.setColor(Color.red);
        g.setFont(font);
        g.drawString(msg, (WIDTH - metrics.stringWidth(msg)) / 2, HEIGHT / 2);
        g.drawString(ss,222,250);
        g.drawString(scoree,390,250);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            move();
            checkFoodCollision();
            checkCollision();
        }
        repaint();
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT && direction != 'R') {
            direction = 'L';
        } else if (key == KeyEvent.VK_RIGHT && direction != 'L') {
            direction = 'R';
        } else if (key == KeyEvent.VK_UP && direction != 'D') {
            direction = 'U';
        } else if (key == KeyEvent.VK_DOWN && direction != 'U') {
            direction = 'D';
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }


}
