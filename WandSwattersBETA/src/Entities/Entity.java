package Entities;
import DataTypes.*;
/**
 * @param vector Movement line of entity
 * @param intersect Intersect for the movement vector
*/
public class Entity extends Line{
    private int life;
    public int getLife(){
        return life;
    }
    public void setLife(int _life){
        life = _life;
    }
    public void addLife(int _life){
        setLife(life + _life);
    }
    public void minLife(int _life){
        if(getLife() > 0){
            setLife(life - _life);
        }
        if(getLife() < 0){
            setLife(0);
        }
    }
}