
package DataTypes;
import Entities.Sprite;
import Functions.Functions;
import java.util.ArrayList;
/**
 *
 * @author dawsg
 */
public class Cloud {
    public Sprite[] cloud;
    Sprite[] temp;
    Functions func = new Functions();
    Sprite base = new Sprite(new Coord(0,0), Integer.MAX_VALUE, 0, 0, 0, 0, 0, 0);
    public Cloud(Sprite... s){
        if (s.length == 0){
            cloud = new Sprite[1];
            cloud[0] = base;
        }else{
            cloud = new Sprite[s.length];
            for(int x=0;x<s.length;x++){
            cloud[x] = s[x];
        }
        }
    }
    public void addSprite(Sprite... s){
        
        if(cloud[0].getLife() == Integer.MAX_VALUE){
            cloud = new Sprite[s.length];
            for(int x = 0;x<s.length;x++){
                cloud[x] = s[x];
            }
        }else{
            temp = cloud.clone();
            cloud = new Sprite[temp.length + s.length];
            for(int x=0;x<temp.length;x++){
                cloud[x] = temp[x];
            }
            for(int y=0;y<s.length;y++){
                cloud[temp.length + y] = s[y];
            }
        }
        
    }
    public void addSprite(Coord location, int life, double velocity, int heading, int size, int red, int blue, int green){
        addSprite(new Sprite(location, life, velocity, heading, size, red, blue, green));
        System.out.println("new sprite");
    }
    public void killSprite(int i){
        if(cloud[0].getLife() == Integer.MAX_VALUE){
            func.sysout("Tried to remove a sprite that doesnt exist!");
            //this is fuckin' shady
        }else{
            if(cloud.length == 1){
                cloud[0] = base;
            }else{
                temp = cloud.clone();
                cloud = new Sprite[temp.length - 1];
                int skip = 0;
                for(int x=0;x<temp.length-1;x++){
                    if(x == i){
                        skip = 1;
                    }else{
                        cloud[x] = temp[x + skip];
                    }
                }
            }
        }
    }
    public int getSize(){
        return cloud.length;
    }
}
