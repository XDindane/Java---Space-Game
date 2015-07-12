/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.src.main;

import com.game.src.libs.Animation;
import com.game.src.main.classes.EntityA;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet extends GameObject implements EntityA {

    private Textures tex;
    private Game game;

    Animation anim;

    public Bullet(double x, double y, Textures tex, Game game) {
        super(x, y);
        this.tex = tex;
        this.game = game;

        anim = new Animation(5, tex.missile[0], tex.missile[1], tex.missile[2]);
    }

    public void tick() {
        y -= 10;

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

    public double getY() {
        return y;
    }

}
