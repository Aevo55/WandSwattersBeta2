package Entities;
import DataTypes.*;
/**
 * @param vector Movement line of entity
 * @param intersect Intersect for the movement vector
*/
public class Entity extends Line{
    private int life;
    
    private int size;
    public void liveMod(boolean[] input){}
    public void liveMod(){}
    public void live(boolean[] input){
        
        setX(getX2());
        setY(getY2());
        moveTo(getP2());
        liveMod(input);
    }
    public void live(){
        
        setX(getX2());
        setY(getY2());
        moveTo(getP2());
        liveMod();
    }
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
    public int getSize(){
        return size;
    }
    public void setSize(int _size){
        size = _size;
    }
}