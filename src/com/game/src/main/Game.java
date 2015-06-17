
package com.game.src.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Denis Mahmúd 
 */
public class Game extends Canvas implements Runnable{
    
    
    public static final int WIDTH = 320;
    
    public static final int HEIGHT = WIDTH / 12*9;
    
    public static final int SCALE = 2;
    
    public final String TITLE = "2D Space Game";

    
    private boolean running = false;
    
    private Thread thread;
    
    
    
    
    private synchronized void start() {
        if(running)
            return;
        
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    
    
       private synchronized void stop() {
        if(!running)
            return;
        
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
       while(running) {
           // this would be the game loop
           System.out.println("WORKING");
       }
       stop();
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
    
    
    
    
    
}
