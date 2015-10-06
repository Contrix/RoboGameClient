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
    private int[][] map;
    private int step;
    ArrayList<MyPoint> list = new ArrayList<>();
    ArrayList<MyPoint> old = new ArrayList<>();
    ArrayList<MyPoint> add = new ArrayList<>();
    private MyPoint point = new MyPoint(0, 0);
    
    public int getCourse(int[][] array, MyPoint s, MyPoint e, int orientation){
        getMap(array, s, e);
        setCourse(s, e);
        setCourse(s, e);
        printMap(e);
        
        switch(orientation){//1 - sever, 2 - jich,  3 - východ, 4 - západ
            case 1:
                if (s.getX() > point.getX()){
                    return 0;
                }
                else if (s.getX() < point.getX()){
                    return 3;
                }
                else{
                    if (s.getY() < point.getY()){
                        return 3;
                    }
                    else{
                        return 4;
                    }
                }
            
            case 2:
                if (s.getX() > point.getX()){
                    return 3;
                }
                else if (s.getX() < point.getX()){
                    return 0;
                }
                else{
                    if (s.getY() < point.getY()){
                        return 4;
                    }
                    else{
                        return 3;
                    }
                }
                
            case 3:
                if (s.getX() > point.getX()){
                    return 4;
                }
                else if (s.getX() < point.getX()){
                    return 3;
                }
                else{
                    if (s.getY() < point.getY()){
                        return 0;
                    }
                    else{
                        return 3;
                    }
                }
                
            case 4:
                if (s.getX() > point.getX()){
                    return 4;
                }
                else if (s.getX() < point.getX()){
                    return 3;
                }
                else{
                    if (s.getY() < point.getY()){
                        return 3;
                    }
                    else{
                        return 0;
                    }
                }
            
            default:
                break;
        }
        return -1;
        
    }
    
    private void initialize(int[][] array){
        map = new int[array.length][array[0].length];
        list.clear();
        old.clear();
        step = 0;
    }
    
    private int getMap(int[][] array, MyPoint s, MyPoint e){
        initialize(array);
        list.add(s);
        map[s.getX()][s.getY()] = - 1;
        while (!list.isEmpty()){
            step++;
            for (MyPoint p : list){
                if (!(e.getX() == p.getX() && e.getY() == p.getY())){
                    if (p.getX() + 1 < array.length){
                        if(array[p.getX() + 1][p.getY()] == 0 && map[p.getX() + 1][p.getY()] == 0){
                            add.add(new MyPoint(p.getX() + 1, p.getY()));
                            map[p.getX() + 1][p.getY()] = step;
                        }
                    }
                    if(p.getX() - 1 >= 0){
                        if(array[p.getX() - 1][p.getY()] == 0 && map[p.getX() - 1][p.getY()] == 0){
                            add.add(new MyPoint(p.getX() - 1, p.getY()));
                            map[p.getX() - 1][p.getY()] = step;
                        }
                    }
                    if (p.getY() + 1 < array[0].length){
                        if(array[p.getX()][p.getY() + 1] == 0 && map[p.getX()][p.getY() + 1] == 0){
                            add.add(new MyPoint(p.getX(), p.getY() + 1));
                            map[p.getX()][p.getY() + 1] = step;
                        }
                    }
                    if (p.getY() - 1 >= 0){
                        if(array[p.getX()][p.getY() - 1] == 0 && map[p.getX()][p.getY() - 1] == 0){
                            add.add(new MyPoint(p.getX(), p.getY() - 1));
                            map[p.getX()][p.getY() - 1] = step;
                        }
                    }
                }
                else{
                    //map[e.getX()][e.getY()] = 20;
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
        for (int i = 0; i < map[e.getX()][e.getY()] - 1; i++){
            if (point.getX() + 1 < map.length){
                if(map[point.getX() + 1][point.getY()] != 0 && map[point.getX() + 1][point.getY()] < map[point.getX()][point.getY()]){
                    point.incX();
                    System.out.println("x+");
                    continue;
                }
            }
            if(point.getX() - 1 >= 0){
                if(map[point.getX() - 1][point.getY()] != 0 && map[point.getX() - 1][point.getY()] < map[point.getX()][point.getY()]){
                    point.decX();
                    System.out.println("x-");
                    continue;
                }
            }
            if (point.getY() + 1 < map[0].length){
                if(map[point.getX()][point.getY() + 1] !=0 && map[point.getX()][point.getY() + 1] < map[point.getX()][point.getY()]){
                    point.incY();
                    System.out.println("y+");
                    continue;
                }
            }
            if (point.getY() - 1 >= 0){
                if(map[point.getX()][point.getY() - 1] != 0 && map[point.getX()][point.getY() - 1] < map[point.getX()][point.getY()]){
                    point.decY();
                    System.out.println("y-");
                }
            }
        }
        System.out.println(point);
    }
    
    private void printMap(MyPoint e){
        //map[e.getX()][e.getY()] = -2;
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[0].length; j++){
                System.out.printf(" %2d", map[i][j]);
            }
            System.out.println();
        }
    }
}
