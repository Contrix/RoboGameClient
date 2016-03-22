/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Obj;

import robogameclient.MyPoint;

/**
 *
 * @author Jirka
 */
public class Treasure {
    private final MyPoint position;
    
    public Treasure(int x, int y){
        this.position = new MyPoint(y, x);
    }
}
