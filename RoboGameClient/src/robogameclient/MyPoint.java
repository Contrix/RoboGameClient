/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robogameclient;

/**
 *
 * @author Jirka
 */
public class MyPoint {
    private int x = 0;
    private int y = 0;
    
    /**
     *
     * @param x X coordinate
     * @param y Y coordinate
     */
    public MyPoint(int y, int x){
        this.x = x;
        this.y = y;
    }
    
    /**
     *
     * @param p
     */
    public void setPoint(MyPoint p){
        this.x = p.getX();
        this.y = p.getY();
    }   
    
    /**
     *
     * @param x
     * @param y
     */
    public void setPoint(int x, int y){
        this.x = x;
        this.y = y;
    } 
    
    /**
     *
     * @return
     */
    public int getX(){
        return(x);
    }
    
    /**
     *
     * @return
     */
    public int getY(){
        return(y);
    }
    
    /**
     *
     * @param x
     */
    public void setX(int x){
        this.x = x;
    }
    
    /**
     *
     * @param y
     */
    public void setY(int y){
        this.y = y;
    }
    
    /**
     *
     */
    public void decX(){
        x--;
    }
    
    /**
     *
     */
    public void decY(){
        y--;
    }
    
    /**
     *
     */
    public void incX(){
        x++;
    }
    
    /**
     *
     */
    public void incY(){
        y++;
    }
    
    @Override
    public String toString(){
        return (x + " - " + y);
    }
}
