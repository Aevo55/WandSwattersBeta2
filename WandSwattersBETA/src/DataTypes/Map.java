package DataTypes;
import java.awt.*;
import java.util.*;
public class Map {
    ArrayList<Net> walls = new ArrayList();
    ArrayList<Net> ui = new ArrayList();
    public Map(){}
    public void addWalls(Net... _walls){
        for(int x = 0;x<_walls.length;x++){
            walls.add(_walls[x]);
        }
    }
    public void addUi(Net... _ui){
        for(int x = 0;x<_ui.length;x++){
            ui.add(_ui[x]);
        }
    }
    public Net[] getArrWall(){
        Net[] _walls = new Net[walls.size()];
        walls.toArray(_walls);
        return _walls;
    }
    public Net[] getArrUi(){
        Net[] _ui = new Net[ui.size()];
        ui.toArray(_ui);
        return _ui;
    }
    public Net getWall(int net){
        return walls.get(net);
    }
    public Net getUi(int net){
        return ui.get(net);
    }
    public void remWalls(int... i){
        for(int x = 0;x<i.length;x++){
            walls.remove(i[x]);
        }
    }
    public void remUi(int... i){
        for(int x = 0;x<i.length;x++){
            ui.remove(i[x]);
        }
    }
    public int totalWalls(){
        int x = 0;
        for (int y = 0;y<walls.size();y++){
            x = x + walls.get(y).length();
        }
        return x;
    }
    public int totalUi(){
        int x = 0;
        for(int y = 0;y<ui.size();y++){
            x = x + ui.get(y).length();
        }
        return x;
    }
}
