package Entities;
import DataTypes.*;
/**
 *
 * @author G
 */
public class Entity extends Coord{
    Line vector;
    Intersect intersect = new Intersect();
    public Line getVec(){
        return vector;
    }
    public Intersect getInt(){ 
        return intersect;
    }
}
