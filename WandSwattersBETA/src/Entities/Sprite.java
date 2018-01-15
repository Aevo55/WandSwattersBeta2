
package Entities;
import DataTypes.*;
import java.awt.*;
/**
 *
 * @author Sean
 * @param heading direction of sprite in degrees
 * @param life the number of frames before sprite expires
 */
public class Sprite extends Entity{
    
    public int life;
    //public double velocity;
    public int size;
    public int red;
    public int green;
    public int blue;
    //public double heading;
    
    //heading should be in degrees
    
    public Sprite(Coord _loc,int _life, double _velocity, int _heading, int _size, int _red, int _blue, int _green){
        setX(_loc.getX());
        setY(_loc.getY());
        life = _life;
        
        size = _size;
        red = _red;
        blue = _blue;
        green = _green;
        vector = new Line(getLoc(),new Angle(_heading),_velocity);
    }
    public Sprite(Line _line,int _life,  int _size, int _red, int _blue, int _green){
        setX(_line.getX1());
        setY(_line.getY1());
        life = _life;
        size = _size;
        red = _red;
        blue = _blue;
        green = _green;
        vector = _line;
    }
    public void live(){
        vector.recalc(getLoc(), vector.getAngle(), vector.getMag());
        
        setX(vector.getX2());
        setY(vector.getY2());
        vector.moveTo(vector.getP2());
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
