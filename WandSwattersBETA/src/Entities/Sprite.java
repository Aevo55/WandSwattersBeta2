
package Entities;
import DataTypes.*;
import java.awt.*;
/**
 *
 * @author Sean
 * @param life Number of frames before sprite expires
 * @param damage How much damage the sprite deals (if any)
 * @param colour Colour of the sprite, usually the same as the player
 */
public class Sprite extends Entity{
    private int damage;
    private double knockback;
    private int reload;
    private int cost;
    private Color colour;
    public Sprite(Coord cor, int _life,int _size, Color col){
        this(cor,new Angle(0),0,_life,_size,col);
    }
    public Sprite(Coord cor, Angle ang, double speed,int _life,int _size, Color col){
        moveTo(cor);
        setLife(_life);
        setSize(_size);
        recalc(cor,ang,speed);
        colour = col;
    }
    public Sprite(Line line, double speed, int _life, int _size, Color col){
        this(line.getP1(),line.getAngle(),speed,_life,_size,col);
    }
    public Sprite(Line line,Angle ang, double speed, int _life, int _size, Color col){
        this(line.getP1(),line.getAngle().offset(ang),speed,_life,_size,col);
    }
    public void liveFunc(){/*Its blank!*/}
    public void liveMod(){
        liveFunc();
        recalc(getLoc(), getAngle(), getMag());
        if(getLife() != Integer.MAX_VALUE){
            minLife(1);
        }
        if(getLife() <= 0){
            die();
        }
    }
    public void die(){
        setLife(0);
        //Other stuff!
    }
//<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public Color getCol(){
        return colour;
    }
    public int getDamage(){
        return damage;
    }
    public void setDamage(int d){
        damage = d;
    }
    public void setKnockback(double d){
        knockback = d;
    }
    public double getKnockback(){
        return knockback;
    }
    public void setReload(int i){
        reload = i;
    }
    public int getReload(){
        return reload;
    }
    public void setCost(int c){
        cost = c;
    }
    public int getCost(){
        return cost;
    }
    //</editor-fold>
    
}
