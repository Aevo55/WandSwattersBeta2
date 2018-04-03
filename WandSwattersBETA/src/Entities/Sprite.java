
package Entities;
import DataTypes.*;
import java.awt.*;
/**
 *
 * @author Sean
 * @param heading direction of sprite in degrees
 * @param life the number of frames before sprite expires
 * 
 */
public class Sprite extends Entity{
    
    public int life;
    public int red;
    public int green;
    public int blue;
    public boolean exhaust;
    public int damage;
    Player.weapon weap;
    public Sprite(Line _line, int _life, double angchange,double speed, int _size, int _red, int _blue, int _green,boolean _exhaust,Player.weapon _weap){
        setX(_line.getX1());
        setY(_line.getY1());
        life = _life;
        setSize(_size);
        red = _red;
        blue = _blue;
        green = _green;
        getVec().recalc(_line.copy());
        getVec().setMag(speed);
        getVec().rotate(new Angle(angchange));
        weap = _weap;
        exhaust = _exhaust;
    }
    public void live(){
        switch (weap){
            case EXHAUST:
            case FLAME:
            case MISSILE:
                
            break;
            
        }
        getVec().recalc(getLoc(), getVec().getAngle(), getVec().getMag());
        setX(getVec().getX2());
        setY(getVec().getY2());
        getVec().moveTo(getVec().getP2());
        if(life != Integer.MAX_VALUE){
            life --;
        }
    }
    public void hitWall(Line l){
        switch(weap){
            case EXHAUST:
                System.out.println("Man you really fucked up how did you manage to call this");
            break;
            case FLAME:
            case MISSILE:
                getVec().recalc(getVec().getP1(),new Angle(l.getAngle().getDeg() + (l.getAngle().getDeg()- getVec().getAngle().getDeg())),getVec().getMag()*.5);
            break;
        }
    }
//<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
    public Player.weapon getWeap(){
        return weap;
    }
    public void setWeap(Player.weapon w){
        weap = w;
    }
    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }
    //</editor-fold>
    
}
