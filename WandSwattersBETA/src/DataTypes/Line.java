
package DataTypes;
import Entities.Sprite;
import java.awt.*;
/**
 *
 * @author Dawson
 * @param vert Boolean for if line has infinite slope
 * @param this.getX() x Coordinate of first point
 * @param this.getY() y Coordinate of first point
 * @param p2.getX() x Coordinate of first point
 * @param p2.getY() y Coordinate of first point
 * @param this Coordinate for second point
 * @param p2 Coordinate for second point
 * @param rise Vertical distance between this and p2
 * @param run Horizontal distance between this and p2
 * @param slope M value of y=mx+b representation of line
 * @param b y-intercept of y=mx+b representation of line
 * @param mag length of the line
 * @param angle angle (deg between 0 and 360 or rad between 0 2pi) of the line
 * @param temp Temporary coordinate used for colission detection
 */
public class Line extends Coord{
    private boolean vert;
    private Coord p2 = new Coord();
    private double rise, run, slope, b;
    private double mag;
    private Angle angle = new Angle();
    public Line(){}
    public Line(Coord _p1, Coord _p2){
        recalc(_p1,_p2);
    }
    public Line(Line l){
        recalc(new Coord(l.getP1()),new Coord(l.getP2()));
    }
    public Line(Coord _p1, double _slope, double _mag){
        recalc(_p1,_slope,_mag);
    }
    public Line(Coord _p1, Angle _angle, double _mag){
        recalc(_p1,_angle,_mag);
    }
    public void recalc(Line l){
        recalc(l.getP1(),l.getP2());
    }
    public void recalc(Coord _p1, Coord _p2){
        this.moveTo(_p1);
        p2 = _p2;
        rise = this.getY() - p2.getY();
        run = p2.getX() - this.getX();
        if (run == 0){
            recalc(this,p2.offset(0.0001,0));
        }else{
            vert = false;
            slope = -rise / run;
            if (run < 0){
                angle.setRad(Math.PI + Math.atan(slope));
            }else{
                angle.setRad(Math.atan(slope));
            }
            b = this.y - (this.x * slope);
            mag = Math.sqrt((rise*rise)+(run*run));
        }
    }
    public void recalc(Coord _p1, double _slope, double _mag){
        this.moveTo(_p1);
        slope = _slope;
        mag = _mag;
        if(slope == -Double.POSITIVE_INFINITY){
            run = 0;
            p2.setX(this.getX());
            p2.setY(this.getY() - mag);
            rise = mag;
            angle.setDeg(90);
            vert = true;
        }else if(slope == -Double.NEGATIVE_INFINITY){
            run = 0;
            p2.setX(this.getX());
            p2.setY(this.getY() + mag);
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
            p2.setX(this.getX() + run);
            p2.setY(this.getY() - rise);
            
            b = this.getY() - (slope*this.getX());
        }
    }
    public void recalc(Coord _p1, Angle _angle, double _mag){
        this.moveTo(_p1);
        angle = _angle;
        mag = _mag;
        if((int)angle.getDeg() == 90){
            slope = Double.POSITIVE_INFINITY;
            p2.setX(this.getX());
            rise = -mag;
            vert = true;
            p2.setY(this.getY() + mag);
            run = 0;
        }else if((int)angle.getDeg() == 270){
            slope = Double.NEGATIVE_INFINITY;
            p2.setX(this.getX());
            rise = mag;
            vert = true;
            p2.setY(this.getY() - mag);
            run = 0;
        }else{
            slope = Math.tan(angle.getRad());
            run = (Math.cos(angle.getRad())*mag);
            rise = -(Math.sin(angle.getRad())*mag);
            p2.setX(this.getX() + run);
            p2.setY(this.getY() - rise);
            b = this.getY() - (slope*this.getX());
            vert = false;
        }
        p2.setX(p2.getX());
        p2.setY(p2.getY());
    }
    public void moveBy(double x, double y){
        this.moveBy(x,-y);
        p2.moveBy(x,-y);
    }
    public void moveTo(double x, double y){
        this.setTo(x,y);
        p2.setTo(x+run,y-rise);
    }
    public void moveTo (Coord c){
        this.setTo(c);
        p2.setTo(c.offset(run,-rise));
    }
    public int[] draw(){
        int[] pts = {(int)this.getX(),(int)this.getY(),(int)p2.getX(),(int)p2.getY()};
        return pts;
    }
    public void rotate(Angle a){
        recalc(this,new Angle(angle.getDeg() - a.getDeg()),mag);
    }
    public void setAng(Angle a){
        recalc(this,a,mag);
    }
    public void setSlope(double s){
        recalc(this,s,mag);
    }
    public void incMag(double m){
        recalc(this,angle,mag + m);
    }
    public void setMag(double m){
        recalc(this,angle,m);
    }
    public void Accel(Sprite s){
        Accel(s,(double)s.getDamage());
    }
    public void Accel(Line l1){
        incRise(l1.getRise());
        incRun(l1.getRun());
    }
    public void Accel(Line l,Double d){
        Accel(new Line(l.getP1(),l.getAngle(),d));
    }
    public void Accel(double r, double _r){
        incRise(r);
        incRun(_r);
    }
    public void setX1(double x){
        recalc(new Coord(x,this.getY()),p2);
    }
    public void setX2(double x){
        recalc(this,new Coord(x,p2.getY()));
    }
    public void setY1(double y){
        recalc(new Coord(this.getX(),y),p2);
    }
    public void setY2(double y){
        recalc(this,new Coord(this.getX(),y));
    }
    public void setP1(Coord p){
        recalc(p,p2);
    }
    public void setP2(Coord p){
        recalc(this,p);
    }
    public void setRise(double r){
        recalc(this,this.offset(run,r));
    }
    public void setRun(double r){
        recalc(this,this.offset(r,rise));
    }
    public void incRise(double r){
        recalc(this,p2.offset(0,-r));
    }
    public void incRun(double r){
        recalc(this,p2.offset(r,0));
    }
    public Line copy(){
        return new Line(this);
    }
    public void minMag(double d){
        setMag(getMag() - d);
    }
    public void mulMag(double d){
        setMag(getMag()*d);
    }
    //<editor-fold defaultstate="collapsed" desc="Setters">
    public boolean isVert() {
        return vert;
    }

    public double getX1() {
        return this.getX();
    }

    public double getX2() {
        return p2.getX();
    }

    public double getY1() {
        return this.getY();
    }

    public double getY2() {
        return p2.getY();
    }

    public Coord getP2() {
        return p2;
    }

    public Coord getP1() {
        return this;
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
