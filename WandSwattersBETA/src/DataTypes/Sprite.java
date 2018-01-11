
package DataTypes;
import java.awt.*;
/**
 *
 * @author Sean
 * @param heading direction of sprite in degrees
 * @param life the number of frames before sprite expires
 */
public class Sprite {
    
    public int life;
    public double velocity;
    public int size;
    public int red;
    public int green;
    public int blue;
    public double heading;
    
    //heading should be in degrees
    public Coord loc = new Coord();
    public Line vector = new Line(loc, new Angle(heading), (double)velocity);
    
    public Sprite(Coord _loc,int _life, double _velocity, int _heading, int _size, int _red, int _blue, int _green){
        loc.setX(_loc.getX());
        loc.setY(_loc.getY());
        life = _life;
        velocity = _velocity;
        heading = _heading;
        size = _size;
        red = _red;
        blue = _blue;
        green = _green;
        
        
    }
    
    public void live(){
        vector.recalc(loc, new Angle(heading), velocity);
        loc.setX(vector.getX2());
        loc.setY(vector.getY2());
        vector.moveTo(vector.p2);
        life --;
    }
    
//<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
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

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }

    public Coord getLoc() {
        return loc;
    }

    public void setLoc(Coord loc) {
        this.loc = loc;
    }
    //</editor-fold>
    
}
