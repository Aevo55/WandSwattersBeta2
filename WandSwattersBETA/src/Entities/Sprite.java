
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
    public int damage;
    Player.weapon weap;
    public Sprite(Line _line, int _life, double angchange,double speed, int _size, int _red, int _blue, int _green,Player.weapon _weap){
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
    }
    public Sprite(Line _line, int _life, double angchange,double speed, int _size,Player.weapon _weap){
        setX(_line.getX1());
        setY(_line.getY1());
        life = _life;
        setSize(_size);
        red = 0;
        blue = 0;
        green = 0;
        getVec().recalc(_line.copy());
        getVec().setMag(speed);
        getVec().rotate(new Angle(angchange));
        weap = _weap;
    }
    public Sprite(Coord cor, int _size, int _life, Player.weapon _weap){
        setX(cor.getX());
        setY(cor.getY());
        life = _life;
        setSize(_size);
        getVec().recalc(cor,0,0);
        weap = _weap;
    }
    public Sprite(Coord cor, int _life, double ang, double speed, int _size, Player.weapon _weap){
        setX(cor.getX());
        setY(cor.getY());
        life = _life;
        setSize(_size);
        getVec().recalc(cor,new Angle(ang),speed);
        weap = _weap;
        red = 0;
        blue = 0;
        green = 0;
    }
    public Sprite(Coord cor, int _life, double ang, double speed, int _size,int _r, int _g, int _b, Player.weapon _weap){
        red = _r;
        green = _g;
        blue = _b;
        setX(cor.getX());
        setY(cor.getY());
        life = _life;
        setSize(_size);
        getVec().recalc(cor,new Angle(ang),speed);
        weap = _weap;
    }
    public void live(){
        switch (weap){
            case EXHAUST:
            break;
            case FLAME:
            case MISSILE:
                if(getVec().getMag() < 35){getVec().setMag(getVec().getMag() + 1);}
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
