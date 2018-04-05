
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
    private int life;
    private int damage;
    private Color colour;
    public Sprite(Coord cor, int _life,int _size, Color col){
        this(cor,new Angle(0),0,_life,_size,col);
    }
    public Sprite(Coord cor, Angle ang, double speed,int _life,int _size, Color col){
        setTo(cor);
        life = _life;
        setSize(_size);
        getVec().recalc(cor,ang,speed);
        colour = col;
    }
    public Sprite(Line line, double speed, int _life, int _size, Color col){
        this(line.getP1(),line.getAngle(),speed,_life,_size,col);
    }
    public Sprite(Line line,Angle ang, double speed, int _life, int _size, Color col){
        this(line.getP1(),line.getAngle().offset(ang),speed,_life,_size,col);
    }
    public void liveFunc(){/*Its blank!*/}
    public void live(){
        liveFunc();
        getVec().recalc(getLoc(), getVec().getAngle(), getVec().getMag());
        setX(getVec().getX2());
        setY(getVec().getY2());
        getVec().moveTo(getVec().getP2());
        if(life != Integer.MAX_VALUE){
            life --;
        }
    }
    public void die(){/*Its blank!*/}
//<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public int getLife() {
        return life;
    }
    public void setLife(int life) {
        this.life = life;
    }
    public Color getCol(){
        return colour;
    }
    //</editor-fold>
    
}
