
package DataTypes;
import java.awt.*;
/**
 *
 * @author Dawson
 * @param vert Boolean for if line has infinite slope
 * @param p1.getX() x Coordinate of first point
 * @param p1.getY() y Coordinate of first point
 * @param p2.getX() x Coordinate of first point
 * @param p2.getY() y Coordinate of first point
 * @param p1 Coordinate for second point
 * @param p2 Coordinate for second point
 * @param rise Vertical distance between p1 and p2
 * @param run Horizontal distance between p1 and p2
 * @param slope M value of y=mx+b representation of line
 * @param b y-intercept of y=mx+b representation of line
 * @param mag length of the line
 * @param angle angle (deg between 0 and 360 or rad between 0 2pi) of the line
 * @param temp Temporary coordinate used for colission detection
 */
public class Line{
    boolean vert;
    Coord p2 = new Coord();
    Coord p1 = new Coord();
    double rise, run, slope, b;
    double mag;
    Angle angle = new Angle();
    Coord temp;
    public Line(){
        
    }
    public Line(Coord _p1, Coord _p2){
        p1 = _p1;
        p2 = _p2;
        rise = p1.getY() - p2.getY();
        run = p2.getX() - p1.getX();
        if (run == 0){
            vert = true;
            if(rise > 0){
                slope = -Double.POSITIVE_INFINITY;
                angle.setDeg(90);
            }else{
                slope = -Double.NEGATIVE_INFINITY;
                angle.setDeg(270);
            }
            mag = rise;
        }else{
            vert = false;
            slope = -rise / run;
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
        slope = _slope;
        mag = _mag;
        if(slope == -Double.POSITIVE_INFINITY){
            run = 0;
            p2.setX(p1.getX());
            p2.setY(p1.getY() - mag);
            rise = mag;
            angle.setDeg(90);
            vert = true;
        }else if(slope == -Double.NEGATIVE_INFINITY){
            run = 0;
            p2.setX(p1.getX());
            p2.setY(p1.getY() + mag);
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
            p2.setX(p1.getX() + run);
            p2.setY(p1.getY() - rise);
            
            b = p1.getY() - (slope*p1.getX());
        }
    }
    public Line(Coord _p1, Angle _angle, double _mag){
        p1 = _p1;

        angle = _angle;
        mag = _mag;
        if((int)angle.getDeg() == 90){
            slope = -Double.POSITIVE_INFINITY;
            p2.setX(p1.getX());
            rise = -mag;
            vert = true;
            p2.setY(p1.getY() - mag);
            run = 0;
        }else if((int)angle.getDeg() == 270){
            slope = -Double.NEGATIVE_INFINITY;
            p2.setX(p1.getX());
            rise = mag;
            vert = true;
            p2.setY(p1.getY() + mag);
            run = 0;
        }else{
            slope = -Math.tan(angle.getRad());
            run = (Math.cos(angle.getRad())*mag);
            rise = -(Math.sin(angle.getRad())*mag);
            p2.setX(p1.getX() + run);
            p2.setY(p1.getY() - rise);
            b = p1.getY() - (slope*p1.getX());
            vert = false;
        }
        p2.setX(p2.getX());
        p2.setY(p2.getY());
    }
    public void recalc(Coord _p1, Coord _p2){
        p1 = _p1;
        p2 = _p2;
        rise = p1.getY() - p2.getY();
        run = p2.getX() - p1.getX();
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
            slope = -rise / run;
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
        slope = _slope;
        mag = _mag;
        if(slope == -Double.POSITIVE_INFINITY){
            run = 0;
            p2.setX(p1.getX());
            p2.setY(p1.getY() - mag);
            rise = mag;
            angle.setDeg(90);
            vert = true;
        }else if(slope == -Double.NEGATIVE_INFINITY){
            run = 0;
            p2.setX(p1.getX());
            p2.setY(p1.getY() + mag);
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
            p2.setX(p1.getX() + run);
            p2.setY(p1.getY() - rise);
            
            b = p1.getY() - (slope*p1.getX());
        }
    }
    public void recalc(Coord _p1, Angle _angle, double _mag){
        p1 = _p1;
        angle = _angle;
        mag = _mag;
        if((int)angle.getDeg() == 90){
            slope = Double.POSITIVE_INFINITY;
            p2.setX(p1.getX());
            rise = -mag;
            vert = true;
            p2.setY(p1.getY() + mag);
            run = 0;
        }else if((int)angle.getDeg() == 270){
            slope = Double.NEGATIVE_INFINITY;
            p2.setX(p1.getX());
            rise = mag;
            vert = true;
            p2.setY(p1.getY() - mag);
            run = 0;
        }else{
            slope = Math.tan(angle.getRad());
            run = (Math.cos(angle.getRad())*mag);
            rise = -(Math.sin(angle.getRad())*mag);
            p2.setX(p1.getX() + run);
            p2.setY(p1.getY() - rise);
            b = p1.getY() - (slope*p1.getX());
            vert = false;
        }
        p2.setX(p2.getX());
        p2.setY(p2.getY());
    }
    public void moveBy(double x, double y){
        p1.moveBy(x,-y);
        p2.moveBy(x,-y);
    }
    
    public void moveTo(double x, double y){
        p1.setTo(x,y);
        p2.setTo(x+run,y-rise);
    }
    public void moveTo (Coord c){
        p1.setTo(c);
        p2.setTo(c.offset(run,-rise));
    }
    public int[] draw(){
        int[] pts = {(int)p1.getX(),(int)p1.getY(),(int)p2.getX(),(int)p2.getY()};
        return pts;
    }
    public void compRotate(Line anchor,Coord p, Angle a){
        if(!p.equals(p1)){
            anchor.recalc(p,p1);
            anchor.rotate(a);
            rotate(a);
            moveTo(anchor.p2);
        }else{
            rotate(a);
        }
        
    }
    public void rotate(Angle a){
        angle.setDeg(angle.getDeg() - a.getDeg());
        recalc(p1,angle,mag);
    }
    public void Accel(Line l1){
        //Coord newp2 = new Coord(this.getX2() + amount,this.getY2() + amount);
        //recalc(this.getP1(), new Coord(this.getX1() + 1,this.getY1()+1));
        l1.moveTo(this.p2);
        this.recalc(p1, l1.getP2());
    }
    
    public void merge(Line l1){
        Coord newp2 = new Coord(this.p2.x, this.p2.y);
        newp2.x = this.p2.getX() - l1.getRun();
        newp2.y = this.p2.getY() - l1.getRise();
        
        recalc(this.p1,newp2);
        
    }
    
   
    //<editor-fold defaultstate="collapsed" desc="Setters">
    public boolean isVert() {
        return vert;
    }

    public double getX1() {
        return p1.getX();
    }

    public double getX2() {
        return p2.getX();
    }

    public double getY1() {
        return p1.getY();
    }

    public double getY2() {
        return p2.getY();
    }

    public Coord getP2() {
        return p2;
    }

    public Coord getP1() {
        return p1;
    }

    public double getRise() {
        return rise;
    }

    public double getRun() {
        return run;
    }

    public double getSlope() {
        return slope;
    }

    public double getB() {
        return b;
    }

    public double getMag() {
        return mag;
    }

    public Angle getAngle() {
        return angle;
    }
    //</editor-fold>
}

