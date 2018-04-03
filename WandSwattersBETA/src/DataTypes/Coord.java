/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataTypes;
import java.awt.*;
import Entities.*;
/**
 *
 * @author G
 */
public class Coord{
    double x,y;
    int size;
    public Coord(){
        x = 0;
        y = 0;
    }
    public Coord(Coord c){
        x = c.getX();
        y = c.getY();
    }
    public void setTo(Coord c){
        x = c.getX();
        y = c.getY();
    }
    public void setTo(double _x,double _y){
        x = _x;
        y = _y;
    }
    public Coord(double _x, double _y){
        x = _x;
        y = _y;
    }
    public Coord(int _x, int _y){
        x = (double)_x;
        y = (double)_y;
    }
    public void moveBy(double _x, double _y){
        x = x + _x;
        y = y + _y;
    }
    public Coord offset(double _x, double _y){
        return new Coord((x + _x), (y + _y));
    }
    public Point toPoint(){
        return new Point((int)x,(int)y);
    }
    public void setX(double _x){
        x = _x;
    }
    public void setY(double _y){
        y = _y;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public Coord getLoc(){
        return this;
    }
    public int getSize(){
        return size;
    }
    public void setSize(int _size){
        size = _size;
    }
}
