/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataTypes;
import java.awt.*;
import Functions.*;
/**
 *
 * @author G
 */
public class Intersect extends Coord{
    public Functions func = new Functions();
    public boolean exists;
    Line l1,l2;
    /*
    public Intersect(){
        
    }//*/
    public Intersect(Line _l1, Line _l2){
        exists = false;
        l1 = _l1;
        l2 = _l2;
        if (l1.getSlope() == l2.getSlope()){
            func.sysout("They the same");
            exists = false;
        }else if(l1.vert == true){
            func.sysout("hey line 1 is vertical");
            setX(l1.getX1());
            setY((l2.getSlope()*l1.getX1())+l2.getB());
            if(func.range(l2.getX1(), getX(), l2.getX2())){
                exists = true;
            }else{
                exists = false;
            }
        }else if(l2.vert == true){
            func.sysout("hey line 2 is vertical");
            setX(l2.getX1());
            setY((l1.getSlope()*l2.getX1())+l1.getB());
            if(func.range(l1.getX1(), getX(), l1.getX2())){
                exists = true;
            }else{
                exists = false;
            }
        }else{
            setX((l2.getB()-l1.getB())/(l1.getSlope()-l2.getSlope()));
            setY((getX() * l1.getSlope()) + l1.getB());
            if(func.range(l1.getX1(), getX(), l1.getX2()) && func.range(l2.getX1(), getX(), l2.getX2())){
                exists = true;
            }else{
                exists = false;
            }
        }
    }
    public void recalc(Line _l1, Line _l2){
        exists = false;
        l1 = _l1;
        l2 = _l2;          
        if (l1.getSlope() == l2.getSlope()){
            exists = false;
            setX(0);
            setY(0);
        }else{
            setX((l2.getB()-l1.getB())/(l1.getSlope()-l2.getSlope()));
            setY((getX() * l1.getSlope()) + l1.getB());
            if(func.range(l1.getX1(), getX(), l1.getX2()) && func.range(l2.getX1(), getX(), l2.getX2())){
                exists = true;
            }else{
                exists = false;
            }
        }
    }
}
