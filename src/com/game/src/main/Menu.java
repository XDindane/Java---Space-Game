/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Menu {

    public Rectangle playButton = new Rectangle(Game.WIDTH / 2 + 120, 150, 100, 50);
//    public Rectangle helpButton = new Rectangle(Game.WIDTH / 2 + 120, 250, 100, 50);
    public Rectangle quitButton = new Rectangle(Game.WIDTH / 2 + 120, 250, 100, 50);

    Game game;

    public Menu(Game game) {
        this.game = game;
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Font fnt0 = new Font("arial", Font.BOLD, 50);
        g.setFont(fnt0);
        g.setColor(Color.white);
        g.drawString("SPACE GAME", Game.WIDTH / 2, 100);

        Font fnt1 = new Font("arial", Font.BOLD, 25);
        g.setFont(fnt1);

        g.drawString("Highscore: " + game.getHighscore(), 10, 30);

        Font fnt2 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt2);

        g.drawString("Play", playButton.x + 19, playButton.y + 30);
        g2d.draw(playButton);
//        g.drawString("Help", helpButton.x + 19, helpButton.y + 30);
//        g2d.draw(helpButton);
        g.drawString("Quit", quitButton.x + 19, quitButton.y + 30);
        g2d.draw(quitButton);
        
        
        Font fnt3 = new Font("arial", Font.CENTER_BASELINE, 10);
        g.setFont(fnt3);
        g.drawString("created by Denis", 550, 450);

    }
}
