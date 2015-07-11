/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.src.main;

import com.games.src.main.classes.EntityB;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Enemy extends GameObject implements EntityB {

    

    private Textures tex;

    Random r = new Random();

    private int speed = (r.nextInt(3) + 1);

    public Enemy(double x, double y, Textures tex) {
      super(x,y);   
        this.tex = tex;
    }

    public void tick() {
        y += speed;

        if (y > (Game.HEIGHT * Game.SCALE)) {
            y = -10;
            speed = (r.nextInt(3) + 1);
            x = r.nextInt(Game.WIDTH * Game.SCALE);
        }

    }

    public void render(Graphics g) {
        g.drawImage(tex.enemy, (int) x, (int) y, null);
    }
    
       public Rectangle getBounds() {
       return new Rectangle((int)x, (int)y, 32, 32);
   }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

}
