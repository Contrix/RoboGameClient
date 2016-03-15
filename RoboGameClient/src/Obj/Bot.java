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
    
    /**
     * Objekt jednoto bota
     * @param x souřadnice X
     * @param y souřadnice Y
     * @param orientation číselné označení orientace
     * @param batteryLevel úroveň nabití baterie
     */
    public Bot(int x, int y, int orientation, int batteryLevel){
        this.position = new MyPoint(y, x);
        this.orientation = orientation;
        this.batteryLevel = batteryLevel;
    }
    
    /**
     * Vrátí pozici bota
     * @return pozice
     */
    public MyPoint getPosition(){
        return position;
    }
    
    /**
     * Vrátí číselně označenou orientaci bota
     * @return orientace
     */
    public int getOrientation(){
        return orientation;
    }
    
    /**
     * Vrátí úroveň nabití baterie
     * @return úroveň nabití
     */
    public int getBatteryLevel(){
        return batteryLevel;
    }
}
