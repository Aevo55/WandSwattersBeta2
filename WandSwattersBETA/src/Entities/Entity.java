package Entities;
import DataTypes.*;
/**
 * @param vector Movement line of entity
 * @param intersect Intersect for the movement vector
*/
public class Entity extends Coord{
    Line vector = new Line(new Coord(0,0),0,0);
    public Line getVec(){
        return vector;
    }
    
}
