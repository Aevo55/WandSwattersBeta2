/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Functions;
import DataTypes.*;

/**
 *
 * @author G
 */
public class Functions {
    public Functions(){}
    public boolean squarecollide(Coord point, Line line){
        boolean withinx = false;
        boolean withiny = false;
       
        if(range(line.getX1(),point.getX(),line.getX2()))withinx=true;
        if(range(line.getY1(),point.getX(),line.getY2()))withiny=true;
           
        if (withiny == true && withinx == true){
            return true;
        }else{return false;}
    
    }
    public boolean range(double a, double b, double c){
        
        if(a <= b && b <= c){
            return true;
        }else return (c <= b && b <= a);
    }
    public void sysout(Object... in){
        for(int x = 0;x<in.length;x++){
            Class c = in[x].getClass();
            if(c==Integer.class || c==Double.class||c==String.class||c==Long.class||c==Float.class||c==null||c==char.class||c==Boolean.class||c==Class.class){
                System.out.println(in[x]);
            }else{
                sysout(c);
            }
        }
    }
}
