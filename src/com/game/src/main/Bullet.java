/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.src.main;

import com.games.src.main.classes.EntityA;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet extends GameObject implements EntityA{

    private Textures tex;
    private Game game;
    public Bullet(double x, double y, Textures tex, Game game) {
        super(x,y);
        this.tex = tex;
        this.game = game;
    }

    public void tick() {
        y -= 10;
        
        if(Physics.Collision(this, game.eb)) {
            System.out.println("COLLISION DETECTED");
        }
        
    }

    public void render(Graphics g) {
        g.drawImage(tex.missile, (int) x, (int) y, null);
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
    
    

}
