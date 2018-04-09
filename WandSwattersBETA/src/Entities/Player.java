/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;
import Util.*;
import DataTypes.*;
import java.util.ArrayList;
import java.awt.*;
/**
 *
 * @author dawsg
 */

public class Player extends Entity{
    private int r = 15;
    private int g = 15;
    private int b = 255;
    private int shield = 0;
    private int mana = 0;
    private int stamina = 100;
    private double stamMul = 1;
    private int cd = 0;
    private Color colour;
    public enum weapon {EXHAUST,FLAME,MISSILE}
    weapon weap = weapon.EXHAUST;
    double spray = 1;
    int reload = 0;
    double knockback = 5;
    private Intersect intersect = new Intersect();
    Util func = new Util();
    public ArrayList<Sprite> cloud = new ArrayList();
    Line aimline = new Line();
    public Player(Coord _loc, Color col){
        setLife(100);
        this.setX(_loc.getX());
        this.setY(_loc.getY());
        aimline.recalc(this, this.offset(20, 0));
        setSize(8);
        colour = col;
        recalc(this.getLoc(),0,0);
    }
    public void live(boolean[] input){
        reload--;
        setX(getX2());
        setY(getY2());
        moveTo(getP2());
        if(getLife() > 0){
            mulMag(0.985);
        }
        if(getMag()<0.125){
            setMag(0);
        }
        for(int x = 0;x<cloud.size();x++){
                cloud.get(cloud.size() - 1 - x).live();
                if(cloud.get(x).getLife()<=0){
                    cloud.remove(x);
                }
            }
        if(input[1] == true){
            getAim().rotate(new Angle(-6));
        }
        if(input[0] == true){
            getAim().rotate(new Angle(6));
        }
        getAim().recalc(this,getAim().getAngle(),getAim().getMag());
        if(stamina <= 0 && cd <= 0){
            stamina = 0;
            cd = 60;
        }
        cd --;
        if(cd <= 0 && stamina < 100){
            stamina+=(1*stamMul);
            stamMul *= 1.05;
        }
        if(stamina > 100){
            stamina = 100;
        }
    }
    public void createSprite(){
        //<editor-fold defaultstate="collapsed" desc="RGB cycle">
        if(b == 255 && g == 15){
            r+=(20);
        }
        if(b == 15 && g == 255){
            r-=20;
        }
        if(r == 255 && b == 15){
            g+=20;
        }
        if(r == 15 && b == 255){
            g-=20;
        }
        if(g == 255 && r == 15){
            b+=20;
        }
        if(g == 15 && r == 255){
            b-=20;
        }
        //</editor-fold>
        int _life = 50;
        if(spray <= 20){
            spray += 2;
        }
        if(reload <= 0){
            switch(weap){
                //weapon types
                case MISSILE:
                    cloud.add(new Sprite(aimline, 5, 50, 6,getCol()));
                    reload = 0;
                break;
                default:
                    weap = Player.weapon.MISSILE;
                break;
            }
        }
    }
    public void move(){
        if(stamina > 0){
            cloud.add(new Sprite(aimline,new Angle(170+Math.random()*20),5,1,4,getCol()));
            cloud.add(new Sprite(aimline,new Angle(170+Math.random()*20),5,2,4,getCol()));
            cloud.add(new Sprite(aimline,new Angle(170+Math.random()*20),5,3,4,getCol()));
            Accel(aimline,.25);
            stamina -=4;
            stamMul = 1;
        }
    }
    public void cloudHitNet(Net net){
        for(int i = 0; i < net.lines.length;i++){
            for(int j = 0;j<cloud.size();j++){
                intersect.recalc(cloud.get(j),net.lines[i]);
                if(intersect.exists){
                for(int y=0;y<6;y++){
                    //cloud.add(new Sprite(cloud.get(j).getLoc(),new Angle(60*y),10,10,5));
                }
                cloud.remove(j);
                }
            }
        }
    }
    public void hitNet(Net net){
        int x = 0;
        for(int i = 0; i < net.lines.length;i++){
            intersect.recalc(this, net.lines[i]);
            if(getInt().exists){
                this.setAng(new Angle(net.lines[i].getAngle().getDeg() + (net.lines[i].getAngle().getDeg()- getAngle().getDeg())));
            }
        }
    }
    public Line getAim(){
        return aimline;}
    public Sprite[] getCloud(){
        Sprite[] _cloud = new Sprite[cloud.size()];
        for(int x = 0;x<cloud.size();x++){
            _cloud[x] = cloud.get(x);
        }
        return _cloud;
    }
    public weapon getWeap(){
        return weap;
    }
    public void setWeap(weapon _weap){
        weap = _weap;
    }
    public void setStamina(int s){
        stamina = s;
    }
    public int getStamina(){
        return stamina;
    }
    public void setMana(int m){
        mana = m;
    }
    public int getMana(){
        return mana;
    }
    public void setSpray(double s){
        spray = s;
    }
    public Intersect getInt(){ 
        return intersect;
    }
    /**Return the color of the sprite*/
    public Color getCol(){
        return colour;
    }
    public void setCol(Color col){
        colour = col;
    }
}
