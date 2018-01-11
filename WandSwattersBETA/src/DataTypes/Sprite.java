
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
    public int red;
    public int green;
    public int blue;
    public double heading;
    
    //heading should be in degrees
    public Coord loc = new Coord(0,0);
    public Line vector = new Line(loc, new Angle(heading), (double)velocity);
    
    public Sprite(int _life, double _velocity, int _heading, int _red, int _blue, int _green){
        life = _life;
        velocity = _velocity;
        heading = _heading;
        red = _red;
        blue = _blue;
        green = _green;
    }
    
    public void live(){
        
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
