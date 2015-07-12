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

public class Player extends GameObject implements EntityA {

    private double velX = 0;
    private double velY = 0;

    private Textures tex;

    Game game;
    Animation anim;
    Controller c;

    public Player(double x, double y, Textures tex, Game game, Controller c) {
        super(x, y);
        this.tex = tex;
        this.game = game;
        this.c = c;
        anim = new Animation(5, tex.player[0], tex.player[1], tex.player[2]);

    }

    public void tick() {
        x += velX;
        y += velY;

        if (x <= 0) {
            x = 0;
        }

        if (x >= 640 - 32) {
            x = 640 - 32;
        }
        if (y <= 0) {
            y = 0;
        }
        if (y >= 480 - 45) { // for the bottom collision
            y = 480 - 45;
        }

        for (int i = 0; i < game.eb.size(); i++) {
            EntityB tempEnt = game.eb.get(i);

            if (Physics.Collision(this, tempEnt)) {
                c.removeEntity(tempEnt);
                Game.HEALTH -= 10;
                game.setEnemy_killed(game.getEnemy_killed() + 1);
            }
        }

        anim.runAnimation();
    }

    public void render(Graphics g) {

        anim.drawAnimation(g, x, y, 0);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x + 20, (int) y, 32, 32);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

}
