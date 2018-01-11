
package DataTypes;
import java.awt.*;
/**
 *
 * @author Dawson
 * @param vert Boolean for if line has infinite slope
 * 
 */
public class Line {
    public boolean vert;
    public double x1,x2;
    public double y1,y2;
    public Coord p2 = new Coord();
    public Coord p1 = new Coord();
    public double rise, run, slope, b;
    public double mag;
    public Angle angle = new Angle();
    Coord temp;
    public Line(Coord _p1, Coord _p2){
        p1 = _p1;
        p2 = _p2;
        x1 = p1.x;
        x2 = p2.x;
        y1 = p1.y;
        y2 = p2.y;
        rise = y1 - y2;
        run = x2 - x1;
        if (run == 0){
            vert = true;
            if(rise > 0){
                slope = Double.POSITIVE_INFINITY;
                angle.setDeg(90);
            }else{
                slope = Double.NEGATIVE_INFINITY;
                angle.setDeg(270);
            }
            mag = rise;
        }else{
            vert = false;
            slope = rise / run;
            if (run < 0){
                angle.setRad(Math.PI + Math.atan(slope));
            }else{
                angle.setRad(Math.atan(slope));
            }
            b = p1.y - (p1.x * slope);
            mag = Math.sqrt((rise*rise)+(run*run));
        }
    }
    public Line(Coord _p1, double _slope, double _mag){
        p1 = _p1;
        x1 = p1.x;
        y1 = p1.y;
        slope = _slope;
        mag = _mag;
        if(slope == Double.POSITIVE_INFINITY){
            run = 0;
            x2 = x1;
            y2 = y1 - mag;
            rise = mag;
            angle.setDeg(90);
            vert = true;
        }else if(slope == Double.NEGATIVE_INFINITY){
            run = 0;
            x2 = x1;
            y2 = y1 + mag;
            rise = -mag;
            angle.setDeg(270);
            vert = true;
        }else{
            vert = false;
            if (mag < 0){
                angle.setRad(Math.PI + Math.atan(slope));
            }else{
                angle.setRad(Math.atan(slope));
            }
            run = (Math.cos(angle.getRad())*mag);
            rise = (Math.sin(angle.getRad())*mag);
            x2 = x1 + run;
            y2 = y1 - rise;
            
            b = y1 - (slope*x1);
        }
    }
    public Line(Coord _p1, Angle _angle, double _mag){
        p1 = _p1;
        x1 = p1.x;
        y1 = p1.y;
        angle = _angle;
        mag = _mag;
        if((int)angle.getDeg() == 90){
            slope = Double.POSITIVE_INFINITY;
            x2 = x1;
            rise = mag;
            vert = true;
            y2 = y1 - mag;
            run = 0;
        }else if((int)angle.getDeg() == 270){
            slope = Double.NEGATIVE_INFINITY;
            x2 = x1;
            rise = -mag;
            vert = true;
            y2 = y1 + mag;
            run = 0;
        }else{
            slope = Math.tan(angle.getRad());
            run = (Math.cos(angle.getRad())*mag);
            rise = (Math.sin(angle.getRad())*mag);
            x2 = x1 + run;
            y2 = y1 - rise;
            b = y1 - (slope*x1);
            vert = false;
        }
        p2.x = (int)x2;
        p2.y = (int)y2;
    }
    public void recalc(Coord _p1, Angle _angle, double _mag){
        p1 = _p1;
        x1 = p1.x;
        y1 = p1.y;
        angle = _angle;
        mag = _mag;
        if((int)angle.getDeg() == 90){
            slope = Double.POSITIVE_INFINITY;
            x2 = x1;
            rise = mag;
            vert = true;
            y2 = y1 - mag;
            run = 0;
        }else if((int)angle.getDeg() == 270){
            slope = Double.NEGATIVE_INFINITY;
            x2 = x1;
            rise = -mag;
            vert = true;
            y2 = y1 + mag;
            run = 0;
        }else{
            slope = Math.tan(angle.getRad());
            run = (Math.cos(angle.getRad())*mag);
            rise = (Math.sin(angle.getRad())*mag);
            x2 = x1 + run;
            y2 = y1 - rise;
            b = y1 - (slope*x1);
            vert = false;
        }
        Point p2 = new Point((int)x2,(int)y2);
    }
    public void recalc(Coord _p1, Coord _p2){
        p1 = _p1;
        p2 = _p2;
        x1 = p1.x;
        x2 = p2.x;
        y1 = p1.y;
        y2 = p2.y;
        rise = y1 - y2;
        run = x2 - x1;
        if (run == 0){
            vert = true;
            if(rise > 0){
                slope = Double.POSITIVE_INFINITY;
                angle.setDeg(90);
            }else{
                slope = Double.NEGATIVE_INFINITY;
                angle.setDeg(270);
            }
            mag = rise;
        }else{
            vert = false;
            slope = rise / run;
            if (run < 0){
                angle.setRad(Math.PI + Math.atan(slope));
            }else{
                angle.setRad(Math.atan(slope));
            }
            b = p1.y - (p1.x * slope);
            mag = Math.sqrt((rise*rise)+(run*run));
        }
    }
    public void recalc(Coord _p1, double _slope, double _mag){
        p1 = _p1;
        x1 = p1.x;
        y1 = p1.y;
        slope = _slope;
        mag = _mag;
        if(slope == Double.POSITIVE_INFINITY){
            run = 0;
            x2 = x1;
            y2 = y1 - mag;
            rise = mag;
            angle.setDeg(90);
            vert = true;
        }else if(slope == Double.NEGATIVE_INFINITY){
            run = 0;
            x2 = x1;
            y2 = y1 + mag;
            rise = -mag;
            angle.setDeg(270);
            vert = true;
        }else{
            vert = false;
            if (mag < 0){
                angle.setRad(Math.PI + Math.atan(slope));
            }else{
                angle.setRad(Math.atan(slope));
            }
            run = (Math.cos(angle.getRad())*mag);
            rise = (Math.sin(angle.getRad())*mag);
            x2 = x1 + run;
            y2 = y1 - rise;
            
            b = y1 - (slope*x1);
        }
    }
    public void moveBy(double x, double y){
        x1 += x;
        x2 += x;
        y1 -= y;
        y2 -= y;
    }
    
    public void moveTo(double x, double y){
        x1 = x;
        x2 = x + run;
        y1 = y;
        y2 = y - rise;
    }
    public void moveTo(Coord c){
        x1 = c.getX();
        y1 = c.getY();
        x2 = c.getX() + run;
        y2 = c.getY() - rise;
    }
    public int[] draw(){
        int[] pts = {(int)x1,(int)y1,(int)x2,(int)y2};
        return pts;
    }
    public void rotateBy(Line anchor,Coord p, Angle a){
        if(p.equals(p1)){
            anchor.recalc(p1,angle.getDeg() + a.getDeg(),mag);
        }else{
            anchor.recalc(p,p1);
            
            //anchor.recalc(p,anchor.angle.getDeg() + a.getDeg(), anchor.mag);
        }
        angle.setDeg(angle.getDeg() + a.getDeg());
        recalc(anchor.p2,angle,mag);
    }
    public void simpleRotate(Angle a){
        angle.setDeg(angle.getDeg() + a.getDeg());
        recalc(p1,angle,mag);
    }
    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public boolean isVert() {
        return vert;
    }

    public void setVert(boolean vert) {
        this.vert = vert;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    public Coord getP2() {
        return p2;
    }

    public void setP2(Coord p2) {
        this.p2 = p2;
    }

    public Coord getP1() {
        return p1;
    }

    public void setP1(Coord p1) {
        this.p1 = p1;
    }

    public double getRise() {
        return rise;
    }

    public void setRise(double rise) {
        this.rise = rise;
    }

    public double getRun() {
        return run;
    }

    public void setRun(double run) {
        this.run = run;
    }

    public double getSlope() {
        return slope;
    }

    public void setSlope(double slope) {
        this.slope = slope;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getMag() {
        return mag;
    }

    public void setMag(double mag) {
        this.mag = mag;
    }

    public Angle getAngle() {
        return angle;
    }

    public void setAngle(Angle angle) {
        this.angle = angle;
    }
    //</editor-fold>
}
