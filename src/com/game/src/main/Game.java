package com.game.src.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 * TODO # tutorial #12;
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

    public void init() {
        requestFocus();
        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            spriteSheet = loader.loadImage("/nyan_ship.png");
            background = loader.loadImage("/background.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        addKeyListener(new KeyInput(this));

        // need before player and controller
        tex = new Textures(this);

        p = new Player(200, 200, tex);
        c = new Controller(this, tex);
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
        p.tick();
        c.tick();

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

        p.render(g);
        c.render(g);
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
            c.addBullet(new Bullet(p.getX(), p.getY(), tex));
        }

    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

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
