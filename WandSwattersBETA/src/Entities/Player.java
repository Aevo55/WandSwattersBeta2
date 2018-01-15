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
public class Player extends Coord{
    Line velocity;
    
    
    public Player(Coord _loc){
         this.setX(_loc.getX());
         this.setY(_loc.getY());
         
         
        
    }
}
