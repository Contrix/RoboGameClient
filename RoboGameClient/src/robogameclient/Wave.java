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
    
    public int getCourse(int[][] array, MyPoint s, MyPoint e){
        getMap(array, s, e);
        printMap(e);
        
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
                    return 0;//vše OK
                }
                old.add(p);
            }
            list.removeAll(old);
            list.addAll(add);
            old.clear();
            add.clear();
        }
        return 1;//cíl nenalezen
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
