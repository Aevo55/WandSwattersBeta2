/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    public int length(){
        return coords.length;
    }
}
