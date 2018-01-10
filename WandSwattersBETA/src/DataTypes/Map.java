
package DataTypes;
import java.awt.*;
/**
 *
 * @author G
 */
public class Map {
    public Net[] nets;
    public Map(Net... _nets){
        nets = _nets;
    }
    public int totalpoints(){
        int x = 0;
        for (int y = 0;y<nets.length;y++){
            x = x + nets[y].length();
        }
        return x;
    }
}
