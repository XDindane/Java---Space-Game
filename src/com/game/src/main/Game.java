package com.game.src.main;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Denis Mahmúd
 */
public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 320;

    public static final int HEIGHT = WIDTH / 12 * 9;

    public static final int SCALE = 2;

    public final String TITLE = "2D Space Game";

    private boolean running = false;

    private Thread thread;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private BufferedImage spriteSheet = null;
    private BufferedImage background = null;

    private boolean is_shooting = false;
    private Player p;
    private Controller c;
    private Textures tex;
    private Menu menu;

    public LinkedList<EntityA> ea;
    public LinkedList<EntityB> eb;

    public static int HEALTH = 100;

    private int enemy_count = 5; // how many spawn
    private int enemy_killed = 0; // how many killed
    private int highscore = 0;
    private int score = 0;

    Random r = new Random();
    public static enum STATE {

        MENU,
        GAME
    };

    public static STATE State = STATE.MENU;

    public void init() {
        requestFocus();

        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            spriteSheet = loader.loadImage("/nyan_ship.png");
            background = loader.loadImage("/background_" + (r.nextInt(6) + 1) +".png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.addKeyListener(new KeyInput(this));
        this.addMouseListener(new MouseInput(this));
        // need before player and controller
        tex = new Textures(this);
        c = new Controller(tex, this);
        p = new Player(300, 400, tex, this, c);
        menu = new Menu(this);
        ea = c.getEntityA();
        eb = c.getEntityB();
        c.createEnemy(enemy_count);
    }

    ;
    
    
    private synchronized void start() {
        if (running) {
            return;
        }

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private synchronized void stop() {
        if (!running) {
            return;
        }

        running = false;
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.exit(1);
    }

    @Override
    public void run() {
        init();
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(updates + " Ticks, Fps " + frames);
                updates = 0;
                frames = 0;
            }

        }
        stop();
    }

    private void tick() {
        if (State == STATE.GAME) {

            p.tick();
            c.tick();
            if (enemy_killed >= enemy_count) {
                enemy_count += 2;
                enemy_killed = 0;
                c.createEnemy(enemy_count);
            }
        }

        if (HEALTH <= 0) {
            HEALTH = 100 * 2;
            Game.State = Game.STATE.MENU;
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy(); // this from canvas

        if (bs == null) {
            createBufferStrategy(3); // 3 buffers
            return;
        }
        Graphics g = bs.getDrawGraphics();
        ////////////////////////////////
        //////// drawing stuff ////////

        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(background, 0, 0, null);

        if (State == STATE.GAME) {
            p.render(g);
            c.render(g);

            g.setColor(Color.gray);
            g.fillRect(5, 5, 200, 50);
            g.setColor(Color.green);
            g.fillRect(5, 5, HEALTH, 50);
            g.setColor(Color.white);
            g.drawRect(5, 5, 200, 50);

            Font fnt1 = new Font("arial", Font.BOLD, 25);
            g.setFont(fnt1);
            g.drawString("Score: " + this.getScore(), 500, 30);

        } else if (State == STATE.MENU) {
            menu.render(g);

        }
        ////////////////////////////////
        g.dispose();
        bs.show();
    }

    public static void main(String args[]) {
        Game game = new Game();

        game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        JFrame frame = new JFrame(game.TITLE);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // creating eg3
        frame.setResizable(false); // disable window resize
        frame.setLocationRelativeTo(null); // sets to normal
        frame.setVisible(true);

        game.start();
    }

    public BufferedImage getSpriteSheet() {
        return spriteSheet;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (State == STATE.GAME) {
            if (key == KeyEvent.VK_RIGHT) {
                p.setVelX(5);
            } else if (key == KeyEvent.VK_LEFT) {
                p.setVelX(-5);
            } else if (key == KeyEvent.VK_DOWN) {
                p.setVelY(5);
            } else if (key == KeyEvent.VK_UP) {
                p.setVelY(- 5);
            } else if (key == KeyEvent.VK_SPACE && !is_shooting) {
                is_shooting = true;
                c.addEntity(new Bullet(p.getX(), p.getY(), tex, this));
            }
        }

    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();
        if (State == STATE.GAME) {
            if (key == KeyEvent.VK_RIGHT) {
                p.setVelX(0);
            } else if (key == KeyEvent.VK_LEFT) {
                p.setVelX(0);
            } else if (key == KeyEvent.VK_DOWN) {
                p.setVelY(0);
            } else if (key == KeyEvent.VK_UP) {
                p.setVelY(0);
            } else if (key == KeyEvent.VK_SPACE) {
                is_shooting = false;
            }
        }

    }

    public int getEnemy_count() {
        return enemy_count;
    }

    public void setEnemy_count(int enemy_count) {
        this.enemy_count = enemy_count;
    }

    public int getEnemy_killed() {
        return enemy_killed;
    }

    public void setEnemy_killed(int enemy_killed) {
        this.enemy_killed = enemy_killed;
    }

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
