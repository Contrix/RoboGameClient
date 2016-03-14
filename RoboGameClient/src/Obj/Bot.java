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
public class Bot {
    private MyPoint position;
    private int orientation;
    private int batteryLevel;
    
    public Bot(int x, int y, int orientation, int batteryLevel){
        this.position = new MyPoint(y, x);
        this.orientation = orientation;
        this.batteryLevel = batteryLevel;
    }    
}
