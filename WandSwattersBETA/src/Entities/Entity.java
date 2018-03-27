package Entities;
import DataTypes.*;
/**
 * @param vector Movement line of entity
 * @param intersect Intersect for the movement vector
*/
public class Entity extends Coord{
    Line vector;
    private Intersect intersect = new Intersect();
    public Line getVec(){
        return vector;
    }
    public Intersect getInt(){ 
        return intersect;
    }
}
