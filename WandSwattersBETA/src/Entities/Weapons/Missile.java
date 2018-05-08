/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities.Weapons;
import Entities.*;
import DataTypes.*;
import Util.*;
import java.awt.Color;
/**
 *
 * @author G
 */
public class Missile extends Sprite{
    public Missile(Coord cor, int _life, int _size, Color col) {
        super(cor, _life, _size, col);
    }
    public Missile(Coord cor, Angle ang, double speed,int _life,int _size, Color col){
        super(cor,ang,speed,_life,_size,col);
    }
    public Missile(Line line, double speed, int _life, int _size, Color col){
        super(line.getP1(),line.getAngle(),speed,_life,_size,col);
    }
    public Missile(Line line,Angle ang, double speed, int _life, int _size, Color col){
        super(line.getP1(),line.getAngle().offset(ang),speed,_life,_size,col);
    }
    public void liveFunc(){}
    public void die(){}
}
