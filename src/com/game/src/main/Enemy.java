/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.src.main;

import com.game.src.libs.Animation;
import com.games.src.main.classes.EntityA;
import com.games.src.main.classes.EntityB;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Enemy extends GameObject implements EntityB {
    
    private Textures tex;
    
    Random r = new Random();
    
    Animation anim;
    
    private Game game;
    private Controller c;
    
    private int speed = (r.nextInt(3) + 1);
    
    public Enemy(double x, double y, Textures tex, Controller c, Game game) {
        super(x, y);        
        this.tex = tex;
        this.c = c;
        this.game = game;
        anim = new Animation(10, tex.enemy[0], tex.enemy[1], tex.enemy[2]);
    }
    
    public void tick() {
        y += speed;
        
        if (y > (Game.HEIGHT * Game.SCALE)) {
            y = -10;
            speed = (r.nextInt(3) + 1);
            x = r.nextInt(Game.WIDTH * Game.SCALE);
        }
        
        for (int i = 0; i < game.ea.size(); i++) {
            EntityA tempEnt = game.ea.get(i);
            if (Physics.Collision(this, tempEnt)) {
                c.removeEntity(tempEnt);
                c.removeEntity(this);
                game.setEnemy_killed(game.getEnemy_killed() + 1);
                game.setHighscore(game.getHighscore() + 1);
            }
        }
        
        anim.runAnimation();
    }
    
    public void render(Graphics g) {
        anim.drawAnimation(g, x - 10, y, 10);
    }
    
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 32, 32);
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
