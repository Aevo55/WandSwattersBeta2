/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataTypes;
import java.awt.*;
/**
 *
 * @author G
 */
public class Net {
    public Point[] points;
    public Line[] lines;
    public Net(Point... _points){
        points = _points;
        lines = new Line[points.length];
        for (int x = 0;x<lines.length;x++){
            if (x == 0){
                lines[x] = new Line(points[lines.length - 1], points[x]);
            }else{
                lines[x] = new Line(points[x-1], points[x]);
            }
        }
    }
    public int length(){
        return points.length;
    }
}
