/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.src.main;

import com.games.src.main.classes.EntityA;
import com.games.src.main.classes.EntityB;
import java.util.LinkedList;

public class Physics {

    public static boolean Collision(EntityA enta, LinkedList<EntityB> entb) {
        
        for (int i = 0; i < entb.size(); i++) {
            if (enta.getBounds().intersects(entb.get(i).getBounds())) {
                return true;
            }
        }
        return false;
    }
}
