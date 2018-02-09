
package DataTypes;
import java.awt.*;
import java.util.*;
/**
 *
 * @author G
 */
public class Map {
    ArrayList<Net> nets = new ArrayList();
    public Map(Net... _nets){
        for(int x = 0;x<_nets.length;x++){
            nets.add(_nets[x]);
        }
    }
    
    public Net[] getNets(){
        Net[] _nets = new Net[nets.size()];
        nets.toArray(_nets);
        return _nets;
    }
    public Net getNet(int net){
        return nets.get(net);
    }
    public void addNet(Net _net){
        nets.add(_net);
    }
    public void remNet(int i){
        nets.remove(i);
    }
    public int totalpoints(){
        int x = 0;
        for (int y = 0;y<nets.size();y++){
            x = x + nets.get(y).length();
        }
        return x;
    }
}
