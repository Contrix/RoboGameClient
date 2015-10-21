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
    private int[][] stepMap;
    private int step;
    ArrayList<MyPoint> list = new ArrayList<>();
    ArrayList<MyPoint> old = new ArrayList<>();
    ArrayList<MyPoint> add = new ArrayList<>();
    private MyPoint point = new MyPoint(0, 0);
    
    public String getAction(int[][] array, MyPoint s, MyPoint e, int orientation){
        getMap(array, s, e);
        setCourse(s, orientation);
        //printMap(e);
        
        switch(orientation){
            case 0://sever
                if (s.getX() > point.getX()){
                    return("rotateLeft");//return 0;
                }
                else if (s.getX() < point.getX()){
                    return("rotateRight");//return 3;
                }
                else{
                    if (s.getY() < point.getY()){
                        return("rotateRight");
                    }
                    else{
                        return("step");
                    }
                }
            
            case 2://jih
                if (s.getX() > point.getX()){
                    return("rotateRight");
                }
                else if (s.getX() < point.getX()){
                    return("rotateLeft");
                }
                else{
                    if (s.getY() < point.getY()){
                        return("step");
                    }
                    else{
                        return("rotateRight");
                    }
                }
                
            case 1://východ
                if (s.getX() > point.getX()){
                    return("rotateLeft");
                }
                else if (s.getX() < point.getX()){
                    return("step");
                }
                else{
                    if (s.getY() < point.getY()){
                        return("rotateRight");
                    }
                    else{
                        return("rotateLeft");
                    }
                }
                
            case 3://západ
                if (s.getX() > point.getX()){
                    return("step");
                }
                else if (s.getX() < point.getX()){
                    return("rotateRight");
                }
                else{
                    if (s.getY() < point.getY()){
                        return("rotateLeft");
                    }
                    else{
                        return("rotateRight");
                    }
                }
            
            default:
                break;
        }
        return("chyba");  
    }
    
    private void initialize(int[][] array){
        stepMap = new int[array.length][array[0].length];   
        list.clear();
        old.clear();
        add.clear();
        step = 0;
    }
    
    private int getMap(int[][] array, MyPoint s, MyPoint e){
        initialize(array);
        list.add(e);
        stepMap[e.getY()][e.getX()] = - 1;
        array[s.getY()][s.getX()] = 0;
        
        while (!list.isEmpty()){
            step++;
            for (MyPoint p : list){
                if (!(s.getX() == p.getX() && s.getY() == p.getY())){
                    if (p.getX() + 1 < array[0].length){
                        if(array[p.getY()][p.getX() + 1] == 0 && stepMap[p.getY()][p.getX() + 1] == 0){
                            add.add(new MyPoint(p.getY(), p.getX() + 1));
                            stepMap[p.getY()][p.getX() + 1] = step;
                        }
                    }
                    if(p.getX() - 1 >= 0){
                        if(array[p.getY()][p.getX() - 1] == 0 && stepMap[p.getY()][p.getX() - 1] == 0){
                            add.add(new MyPoint(p.getY(), p.getX() - 1));
                            stepMap[p.getY()][p.getX() - 1] = step;
                        }
                    }
                    if (p.getY() + 1 < array.length){
                        if(array[p.getY() + 1][p.getX()] == 0 && stepMap[p.getY() + 1][p.getX()] == 0){
                            add.add(new MyPoint(p.getY() + 1, p.getX()));
                            stepMap[p.getY() + 1][p.getX()] = step;
                        }
                    }
                    if (p.getY() - 1 >= 0){
                        if(array[p.getY() - 1][p.getX()] == 0 && stepMap[p.getY() - 1][p.getX()] == 0){
                            add.add(new MyPoint(p.getY() - 1, p.getX()));
                            stepMap[p.getY() - 1][p.getX()] = step;
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
        System.out.println("cíl nenalezen");
        return 1;//cíl nenalezen
    }
    
    private void setCourse(MyPoint s, int o){
        if(s.getY() - 1 >= 0){
            if(stepMap[s.getY() - 1][s.getX()] < stepMap[s.getY()][s.getX()] && stepMap[s.getY() - 1][s.getX()] != 0){
                point = new MyPoint(s.getY() - 1, s.getX());
            }
        }
        else if (s.getX() + 1 < stepMap[0].length){
            if(stepMap[s.getY()][s.getX() + 1] < stepMap[s.getY()][s.getX()] && stepMap[s.getY()][s.getX() + 1] != 0){
                point = new MyPoint(s.getY(), s.getX() + 1);
            }
        }
        else if(s.getY() + 1 < stepMap.length){
            if(stepMap[s.getY() + 1][s.getX()] < stepMap[s.getY()][s.getX()] && stepMap[s.getY() + 1][s.getX()] != 0){
                point = new MyPoint(s.getY() + 1, s.getX());
            }
        }
        else if (s.getX() - 1 >= 0){
            if(stepMap[s.getY()][s.getX() - 1] < stepMap[s.getY()][s.getX()] && stepMap[s.getY()][s.getX() - 1] != 0){
                point = new MyPoint(s.getY(), s.getX() - 1);
            }
        }
        
        switch(o){
            case 0:
                if(s.getY() - 1 >= 0){
                    if(stepMap[s.getY() - 1][s.getX()] < stepMap[s.getY()][s.getX()] && stepMap[s.getY() - 1][s.getX()] != 0){
                        point = new MyPoint(s.getY() - 1, s.getX());
                    }
                }
                break;
            case 1:
                if (s.getX() + 1 < stepMap[0].length){
                    if(stepMap[s.getY()][s.getX() + 1] < stepMap[s.getY()][s.getX()] && stepMap[s.getY()][s.getX() + 1] != 0){
                        point = new MyPoint(s.getY(), s.getX() + 1);
                    }
                }
                break;
            case 2:
                if(s.getY() + 1 < stepMap.length){
                    if(stepMap[s.getY() + 1][s.getX()] < stepMap[s.getY()][s.getX()] && stepMap[s.getY() + 1][s.getX()] != 0){
                        point = new MyPoint(s.getY() + 1, s.getX());
                    }
                }
                break;
            case 3:
                if (s.getX() - 1 >= 0){
                    if(stepMap[s.getY()][s.getX() - 1] < stepMap[s.getY()][s.getX()] && stepMap[s.getY()][s.getX() - 1] != 0){
                        point = new MyPoint(s.getY(), s.getX() - 1);
                    }
                }
                break;
            default:
                break;
        }
        
        
        
        //1.
        /*point.setPoint(e);
        for (int i = 0; i < stepMap[e.getY()][e.getX()] - 1; i++){
            if (point.getX() + 1 < stepMap[0].length){
                if(stepMap[point.getY()][point.getX() + 1] != 0 && stepMap[point.getY()][point.getX() + 1] < stepMap[point.getY()][point.getX()]){
                    point.incX();
                    //System.out.println("x+");
                    continue;
                }
            }
            if(point.getX() - 1 >= 0){
                if(stepMap[point.getY()][point.getX() - 1] != 0 && stepMap[point.getY()][point.getX() - 1] < stepMap[point.getY()][point.getX()]){
                    point.decX();
                    //System.out.println("x-");
                    continue;
                }
            }
            if (point.getY() + 1 < stepMap[0].length){
                if(stepMap[point.getY() + 1][point.getX()] !=0 && stepMap[point.getY() + 1][point.getX()] < stepMap[point.getY()][point.getX()]){
                    point.incY();
                    //System.out.println("y+");
                    continue;
                }
            }
            if (point.getY() - 1 >= 0){
                if(stepMap[point.getY() - 1][point.getX()] != 0 && stepMap[point.getY() - 1][point.getX()] < stepMap[point.getY()][point.getX()]){
                    point.decY();
                    //System.out.println("y-");
                }
            }
        }*/
        //System.out.println(point);
        
        //2.
        /*point.setPoint(e);
        for (int i = 0; i < stepMap[e.getY()][e.getX()] - 1; i++){
            
        }*/
        
    }
    
    private void printMap(MyPoint e){
        for (int i = 0; i < stepMap.length; i++){
            for (int j = 0; j < stepMap[0].length; j++){
                System.out.printf(" %2d", stepMap[i][j]);
            }
            System.out.println();
        }
    }
}
