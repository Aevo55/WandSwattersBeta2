
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
    public Missile(Line line, Angle ang, double speed, Color col){
        super(line.getP1(),line.getAngle().offset(ang),speed,50,6,col);
    }
    public Missile(Line line, Angle ang, Color col){
        super(line.getP1(),line.getAngle().offset(ang),5,50,6,col);
    }
    public Missile(Line line, Color col){
        super(line.getP1(),line.getAngle(),5,50,6,col);
    }
    public Missile(Line line, double speed, Color col){
        super(line.getP1(),line.getAngle(),speed,50,6,col);
    }
    public void liveFunc(){}
    public void die(){
        setLife(0);
    }
    public int getReload(){
        return 15;
    }
    public double getKnockback(){
        return 0.9;
    }
    public int getDamage(){
        return 5;
    }
}
