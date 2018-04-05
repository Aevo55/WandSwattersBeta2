package DataTypes;
import Util.Util;
import java.awt.*;
public class Angle {
    private double deg,rad;
    public Angle(){
        deg = 0;
        rad = 0;
        Util.sysout();
    }
    public Angle(double _deg){
        deg = _deg;
        deg = fix(deg);
        rad = deg/360*(2*Math.PI);
        Util.sysout();
    }
    public double getDeg(){
        return deg;
    }
    public double getRad(){
        return rad;
    }
    public void rotateByDeg(double a){
        setDeg(getDeg() + a);
    }
     /**There are Math.toDegrees and Math.toRadians methods*/
    public void setDeg(double _val){
        deg = _val;
        deg = fix(deg);
        rad = deg/360*(2*Math.PI);
    }
    /**There are Math.toDegrees and Math.toRadians methods*/
    public void setRad(double _val){
        deg = fix(_val/(2*Math.PI)*360);
        rad = deg/360*(2*Math.PI);
    }
    public double fix(double _deg){
        return _deg<0?_deg%360+360:_deg%360;
    }
    public Angle offset(Angle ang){
        return(new Angle(getDeg() + ang.getDeg()));
    }
    public Angle offset(double ang){
        return(new Angle(getDeg() + ang));
    }
}
