/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataTypes;
import java.util.ArrayList;
/**
 *
 * @author dawsg
 */
public class Cloud {
    ArrayList cloud = new ArrayList();
    public Cloud(){
        
    }
    public void addSprite(Sprite s){
        cloud.add(s);
    }
     public void addSprite(Coord location, int life, double velocity, int heading, int size, int red, int blue, int green){
        cloud.add(new Sprite(location, life, velocity, heading, size, red, blue, green));
    }
     public void killSprite(int i){
         cloud.remove(i);
     }
}
