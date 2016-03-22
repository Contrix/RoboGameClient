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
    private final MyPoint position;
    private final int orientation;
    private final int batteryLevel;
    private final String name;
    private final String lastAction = "none";
    
    /**
     * Objekt jednoto bota
     * @param x souřadnice X
     * @param y souřadnice Y
     * @param orientation číselné označení orientace
     * @param batteryLevel úroveň nabití baterie
     * @param name jméno bota
     */
    public Bot(int x, int y, int orientation, int batteryLevel, String name){
        this.position = new MyPoint(y, x);
        this.orientation = orientation;
        this.batteryLevel = batteryLevel;
        this.name = name;
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
    
    /**
     * Vrátí jméno bota
     * @return jméno
     */
    public String getName(){
        return name;
    }

    public boolean isSameBot(Bot bot){
        if (bot.getBatteryLevel() != batteryLevel)
            return false;
        else if (bot.getOrientation() != orientation)
            return false;
        else if (bot.getPosition().getX() != position.getX())
            return false;
        else if (bot.getPosition().getY() != position.getY())
            return false;
        return true;                    
    }
}
