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
    }
    public Intersect(Line _l1, Line _l2){
        exists = false;
        l1 = _l1;
        l2 = _l2;
        if (l1.getAngle().getDeg() == l2.getAngle().getDeg()){
            func.sysout("they the same");
            exists = false;
        }else if(l1.vert == true){
            //actual collision part?
            func.sysout("hey line 1 is vertical");
            setX(l1.getX1());
            setY((l2.getSlope()*l1.getX1())+l2.getB());
            if(func.squarecollide(getLoc(), l1) && func.squarecollide(getLoc(), l2)){
            //if(func.range(l2.getX1(), getX(), l2.getX2())){
                exists = true;
            }else
            {
                //exists = false;
            }
        }else if(l2.vert == true){
            //actual collision
            func.sysout("hey line 2 is vertical");
            setX(l2.getX1());
            setY((l1.getSlope()*l2.getX1())+l1.getB());
            if(func.squarecollide(getLoc(), l1) && func.squarecollide(getLoc(), l2)){
                exists = true;
            }else
            {
                exists = false;
            }
        }else{
            //no idea what this does
            setX((l2.getB()-l1.getB())/(l1.getSlope()-l2.getSlope()));
            setY((getX() * l1.getSlope()) + l1.getB());
            if(func.squarecollide(getLoc(), l1) && func.squarecollide(getLoc(), l2)){
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
        if (l1.getAngle().getDeg() == l2.getAngle().getDeg()){
            //func.sysout("they the same");
            exists = false;
        }else if(l1.vert == true){
            //func.sysout("hey line 1 is vertical");
            setX(l1.getX1());
            setY((l2.getSlope()*l1.getX1())+l2.getB());
            if(func.range(l2.getX1(), getX(), l2.getX2())){
                if(l1.rise>0 && func.range(l1.getY1(), getY(), l1.getY2())){
                    exists = true;
                }else if(l1.rise<0 && func.range(l1.getY2(), getY(), l1.getY1())){
                    exists = true;
                }else{
                    exists = false;
                }
            }else{
                exists = false;
            }
        }else if(l2.vert == true){
            //func.sysout("hey line 2 is vertical");
            setX(l2.getX1());
            setY((l1.getSlope()*l2.getX1())+l1.getB());
            if(func.range(l1.getX1(), getX(), l1.getX2())){
                if(l1.rise>0 && func.range(l2.getY1(), getY(), l2.getY2())){
                    exists = true;
                }else if(l1.rise<0 && func.range(l2.getY2(), getY(), l2.getY1())){
                    exists = true;
                }else{
                    exists = false;
                }
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
}
