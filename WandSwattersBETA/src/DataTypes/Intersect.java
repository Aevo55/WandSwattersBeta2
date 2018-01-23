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
    
    public Intersect(){
        exists = false;
    }//*/
    public void recalc(Line _l1, Line _l2){
        exists = false;
        l1 = _l1;
        l2 = _l2;
        if (l1.getAngle().getDeg() == l2.getAngle().getDeg()){
            //func.sysout("they the same");
            exists = false;
        }else if(l1.vert == true){
            //func.sysout("hey line 1 is vertical");
            setX(l1.getX1());
            setY((l2.getSlope()*l1.getX1())+l2.getB());
        }else if(l2.vert == true){
            //func.sysout("hey line 2 is vertical");
            setX(l2.getX1());
            setY((l1.getSlope()*l2.getX1())+l1.getB());
        }else{
            setX((l2.getB()-l1.getB())/(l1.getSlope()-l2.getSlope()));
            setY((getX() * l1.getSlope()) + l1.getB());
        }
    }
}
