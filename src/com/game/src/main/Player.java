/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.game.src.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Player {
    
    private double x; // x-coords
    private double y; // y-coords
    
    private double velX = 0;
    private double velY = 0;
    
    private BufferedImage player;
    
    public Player (double x, double y, Game game) {
        
        this.x = x;
        this.y = y;
        
        SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
        
        player = ss.grabImage(1, 1, 32, 32);
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
    }
    
    
    public void render(Graphics g) {
        
        g.drawImage(player, (int) x, (int) y, null);
        
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
