package DataTypes;
import java.awt.*;
/**
 * @author G
 * @param coords Array of coordinates present in the net
 * @param lines Array of lines forming the net
 */
public class Net {
    public Coord[] coords;
    public Line[] lines;
    public Net(Coord... _coords){
        coords = _coords;
        lines = new Line[coords.length];
        for (int x = 0;x<lines.length;x++){
            if (x == 0){
                lines[x] = new Line(coords[lines.length - 1], coords[x]);
            }else{
                lines[x] = new Line(coords[x-1], coords[x]);
            }
        }
    }
    public int[] getXs(){
        Point[] _points = getPoints();
        int[] xs = new int[length()];
        for(int z = 0;z<length();z++){
            xs[z] = _points[z].x;
        }
        return xs;
    }
    public int[] getYs(){
        Point[] _points = getPoints();
        int[] ys = new int[length()];
        for(int z = 0;z<length();z++){
            ys[z] = _points[z].y;
        }
        return ys;
    }
    public Point[] getPoints(){
        Point[] points = new Point[length()];
        for (int z = 0;z<length();z++){
            points[z] = coords[z].toPoint();
        }
        return points;
    }
    public int length(){
        return coords.length;
    }
}
