/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataTypes;
import java.awt.*;
/**
 *
 * @author G
 */
public class Intersect {
    public boolean collides;
    public Point intersect;
    Line l1,l2;
    public Intersect(Line _l1, Line _l2){
        l1 = _l1;
        l2 = _l2;
        
    }
}
