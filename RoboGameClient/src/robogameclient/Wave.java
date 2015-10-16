package robogameclient;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jirka
 */
public class Wave {
    private int[][] myMap;
    private int step;
    ArrayList<MyPoint> list = new ArrayList<>();
    ArrayList<MyPoint> old = new ArrayList<>();
    ArrayList<MyPoint> add = new ArrayList<>();
    private MyPoint point = new MyPoint(0, 0);
    
    public String getAction(int[][] array, MyPoint s, MyPoint e, int orientation){
        System.out.println(e);
        System.out.println(s);
        getMap(array, s, e);
        //setCourse(s, e);
        printMap(e);
        
        switch(orientation){//1 - sever, 2 - jich,  3 - východ, 4 - západ
            case 1:
                if (s.getX() > point.getX()){
                    return("step");//return 0;
                }
                else if (s.getX() < point.getX()){
                    return("rotateRight");//return 3;
                }
                else{
                    if (s.getY() < point.getY()){
                        return("rotateRight");
                    }
                    else{
                        return("rotateLeft");
                    }
                }
            
            case 2:
                if (s.getX() > point.getX()){
                    return("rotateRight");
                }
                else if (s.getX() < point.getX()){
                    return("step");
                }
                else{
                    if (s.getY() < point.getY()){
                        return("rotateLeft");
                    }
                    else{
                        return("rotateRight");
                    }
                }
                
            case 3:
                if (s.getX() > point.getX()){
                    return("rotateLeft");
                }
                else if (s.getX() < point.getX()){
                    return("rotateRight");
                }
                else{
                    if (s.getY() < point.getY()){
                        return("step");
                    }
                    else{
                        return("rotateRight");
                    }
                }
                
            case 4:
                if (s.getX() > point.getX()){
                    return("rotateLeft");
                }
                else if (s.getX() < point.getX()){
                    return("rotateRight");
                }
                else{
                    if (s.getY() < point.getY()){
                        return("rotateRight");
                    }
                    else{
                        return("step");
                    }
                }
            
            default:
                break;
        }
        return("chyba");  
    }
    
    private void initialize(int[][] array){
        myMap = new int[array.length][array[0].length];
        list.clear();
        old.clear();
        step = 0;
    }
    
    private int getMap(int[][] array, MyPoint s, MyPoint e){
        initialize(array);
        list.add(s);
        myMap[s.getY()][s.getX()] = - 1;
        while (!list.isEmpty()){
            step++;
            for (MyPoint p : list){
                if (!(e.getX() == p.getX() && e.getY() == p.getY())){
                    if (p.getX() + 1 < array[0].length){
                        if(array[p.getY()][p.getX() + 1] == 0 && myMap[p.getY()][p.getX() + 1] == 0){
                            add.add(new MyPoint(p.getY(), p.getX() + 1));
                            myMap[p.getY()][p.getX() + 1] = step;
                        }
                    }
                    if(p.getX() - 1 >= 0){
                        if(array[p.getY()][p.getX() - 1] == 0 && myMap[p.getY()][p.getX() - 1] == 0){
                            add.add(new MyPoint(p.getY(), p.getX() - 1));
                            myMap[p.getY()][p.getX() - 1] = step;
                        }
                    }
                    if (p.getY() + 1 < array.length){
                        if(array[p.getY() + 1][p.getX()] == 0 && myMap[p.getY() + 1][p.getX()] == 0){
                            add.add(new MyPoint(p.getY() + 1, p.getX()));
                            myMap[p.getY() + 1][p.getX()] = step;
                        }
                    }
                    if (p.getY() - 1 >= 0){
                        if(array[p.getY() - 1][p.getX()] == 0 && myMap[p.getY() - 1][p.getX()] == 0){
                            add.add(new MyPoint(p.getY() - 1, p.getX()));
                            myMap[p.getY() - 1][p.getX()] = step;
                        }
                    }
                }
                else{
                    //map[e.getX()][e.getY()] = 20;
                    System.out.println("s");
                    return 0;//vše OK
                }
                old.add(p);
            }
            list.removeAll(old);
            list.addAll(add);
            old.clear();
            add.clear();
        }
        System.out.println("cíl nenalezen");
        return 1;//cíl nenalezen
    }
    
    private void setCourse(MyPoint s, MyPoint e){
        point.setPoint(e);
        for (int i = 0; i < myMap[e.getY()][e.getX()] - 1; i++){
            if (point.getX() + 1 < myMap[0].length){
                if(myMap[point.getY()][point.getX() + 1] != 0 && myMap[point.getX() + 1][point.getY()] < myMap[point.getY()][point.getX()]){
                    point.incX();
                    System.out.println("x+");
                    continue;
                }
            }
            if(point.getX() - 1 >= 0){
                if(myMap[point.getY()][point.getX() - 1] != 0 && myMap[point.getX() - 1][point.getY()] < myMap[point.getY()][point.getX()]){
                    point.decX();
                    System.out.println("x-");
                    continue;
                }
            }
            if (point.getY() + 1 < myMap[0].length){
                if(myMap[point.getY() + 1][point.getX()] !=0 && myMap[point.getX()][point.getY() + 1] < myMap[point.getY()][point.getX()]){
                    point.incY();
                    System.out.println("y+");
                    continue;
                }
            }
            if (point.getY() - 1 >= 0){
                if(myMap[point.getY() - 1][point.getX()] != 0 && myMap[point.getX()][point.getY() - 1] < myMap[point.getY()][point.getX()]){
                    point.decY();
                    System.out.println("y-");
                }
            }
        }
        System.out.println(point);
    }
    
    private void printMap(MyPoint e){
        //map[e.getX()][e.getY()] = -2;
        for (int i = 0; i < myMap.length; i++){
            for (int j = 0; j < myMap[0].length; j++){
                System.out.printf(" %2d", myMap[i][j]);
            }
            System.out.println();
        }
    }
}
