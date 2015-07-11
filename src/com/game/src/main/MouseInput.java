/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.src.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author denis
 */
public class MouseInput implements MouseListener {

    Game game;

    public MouseInput(Game game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent me) {

        int mx = me.getX();
        int my = me.getY();

        /**
         * public Rectangle playButton = new Rectangle(Game.WIDTH / 2 + 120,
         * 150, 100, 50); public Rectangle helpButton = new Rectangle(Game.WIDTH
         * / 2 + 120, 250, 100, 50); public Rectangle quitButton = new
         * Rectangle(Game.WIDTH / 2 + 120, 350, 100, 50);
         *
         */
        // playbutton
        if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220) {

            if (my >= 150 && my <= 200) {
                // Pressed Play Button
                game.setHighscore(0);
                game.setEnemy_count(5);
                game.setEnemy_killed(0);
                Game.State = Game.STATE.GAME;
            }
        }

        // quitButton
        if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220) {

            if (my >= 350 && my <= 400) {
                // Pressed quit Button
                System.exit(1);
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
