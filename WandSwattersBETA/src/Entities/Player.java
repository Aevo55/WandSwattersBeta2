/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;
import Functions.*;
import DataTypes.*;
/**
 *
 * @author dawsg
 */

public class Player extends Entity{
    int weapon = 0;
    double xvelo=0;
    double yvelo=0;
    public Player(Coord _loc){
        
         this.setX(_loc.getX());
         this.setY(_loc.getY());
         this.vector = new Line(this.getLoc(), 0, 0);
         
    }
    
    
    public void live(){
        
        vector.recalc(getLoc(),new Coord(getLoc().getX() + xvelo,getLoc().getY() + yvelo));
        // vector.recalc(getLoc(), vector.getAngle(), vector.getMag()*0.90);
        
        setX(vector.getX2());
        setY(vector.getY2());
        vector.moveTo(vector.getP2());
        
    }
    public void addVelo(double xadd, double yadd){
        
    if(new Line(new Coord(getLoc().getX(),getLoc().getY()), new Coord(getLoc().getX() + xadd + xvelo,getLoc().getY() - yadd - yvelo)).getMag() <10){    
     xvelo += xadd;
     yvelo += yadd;
     }
    }
    public double getxVelo(){
    return xvelo;}
    public double getyVelo(){
    return yvelo;}

}
