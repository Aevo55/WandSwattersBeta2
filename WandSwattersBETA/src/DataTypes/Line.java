
package DataTypes;
import java.awt.*;
/**
 *
 * @author Dawson
 */
public class Line {
    public boolean vert;
    public double x1,x2;
    public double y1,y2;
    public Point p2 = new Point();
    public Point p1 = new Point();
    public double rise, run, slope, b;
    public double mag;
    public Angle angle = new Angle();
    Point temp;
    public Line(Point _p1, Point _p2){
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
    public Line(Point _p1, double _slope, double _mag){
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
    public Line(Point _p1, Angle _angle, double _mag){
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
    public void recalc(Point _p1, Angle _angle, double _mag){
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
    public void recalc(Point _p1, Point _p2){
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
    public void recalc(Point _p1, double _slope, double _mag){
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
    public int[] draw(){
        int[] pts = {(int)x1,(int)y1,(int)x2,(int)y2};
        return pts;
    }
    public void rotateBy(Line anchor,Point p, Angle a){
        if(p.equals(p1)){
            anchor.recalc(p1,angle.getDeg() + a.getDeg(),mag);
        }else{
            anchor.recalc(p,p1);
            
            //anchor.recalc(p,anchor.angle.getDeg() + a.getDeg(), anchor.mag);
        }
        angle.setDeg(angle.getDeg() + a.getDeg());
        recalc(anchor.p2,angle,mag);
    }
}
