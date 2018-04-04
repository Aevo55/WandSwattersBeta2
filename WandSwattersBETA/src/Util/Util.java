package Util;
import DataTypes.*;

/**
 *
 * @author G
 */
public final class Util {
    public Util(){}
    static public boolean squareCollide(Coord point, Line line){
        return range(line.getX1(),point.getX(),line.getX2()) &&
               range(line.getY1(),point.getX(),line.getY2());
    }
    static public boolean range(double a, double b, double c){
        return (a <= b && b <= c) || 
               (c <= b && b <= a);
    }
    static public void sysout(Object... in){
        for(int x = 0;x<in.length;x++){
            Class c = in[x].getClass();
            System.out.println(c.isPrimitive() || c==String.class?in[x]:c);
        }
    }
}
