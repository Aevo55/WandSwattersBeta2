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
    
    
    
    public Player(Coord _loc){
         this.setX(_loc.getX());
         this.setY(_loc.getY());
         this.vector = new Line(this.getLoc(), 0, 0);
         
        
    }
    
    
    public void live(){
        vector.recalc(getLoc(), vector.getAngle(), vector.getMag()*0.97);
        
        setX(vector.getX2());
        setY(vector.getY2());
        vector.moveTo(vector.getP1());
        
        
    }
}
