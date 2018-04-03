/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;
import Functions.*;
import DataTypes.*;
import java.util.ArrayList;
/**
 *
 * @author dawsg
 */

public class Player extends Entity{
    private int r = 15;
    private int g = 15;
    private int b = 255;
    private int life = 100;
    private int mana = 0;
    private int stamina = 100;
    private int cd = 0;
    public enum weapon {EXHAUST,FLAME,MISSILE}
    weapon weap = weapon.EXHAUST;
    int level = 0;
    double spray = 1;
    int reload = 0;
    double knockback = 5;
    double xvelo=0;
    double yvelo=0;
    private Intersect intersect = new Intersect();
    Functions func = new Functions();
    ArrayList<Sprite> cloud = new ArrayList();
    Line aimline = new Line();
    public Player(Coord _loc){
        
        this.setX(_loc.getX());
        this.setY(_loc.getY());
        vector = new Line(_loc,0,0);
        aimline.recalc(this, this.offset(20, 0));
        setSize(8);
    }
    public void live(boolean[] input){
        reload--;
        setX(getVec().getX2());
        setY(getVec().getY2());
        getVec().moveTo(getVec().getP2());
        for(int x = 0;x<cloud.size();x++){
                cloud.get(cloud.size() - 1 - x).live();
                if(cloud.get(x).getLife()<=0){
                    cloud.remove(x);
                }
            }
        if(input[1] == true){
            getAim().rotate(new Angle(-15));
        }
        if(input[0] == true){
            getAim().rotate(new Angle(15));
        }
        getAim().recalc(this,getAim().getAngle(),getAim().getMag());
        if(stamina <= 0 && cd <= 0){
            stamina = 0;
            cd = 30;
        }
        cd --;
        if(cd <= 0 && stamina < 100){
            stamina+=1;
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
                
                
                default:
                break;
            }
        }
    }
    public void move(){
        if(stamina > 0){
            cloud.add(new Sprite(aimline,2,180+(Math.random()*12-6),10,6,255,0,0,true,Player.weapon.EXHAUST));
            cloud.add(new Sprite(aimline,2,180+(Math.random()*12-6),10,6,255,0,0,true,Player.weapon.EXHAUST));
            cloud.add(new Sprite(aimline,2,180+(Math.random()*12-6),10,6,255,0,0,true,Player.weapon.EXHAUST));
            getVec().Accel(aimline,.5);
            stamina -=4;
        }
    }
    public void cloudHitNet(Net net){
        for(int i = 0; i < net.lines.length;i++){
            for(int j = 0;j<cloud.size();j++){
                intersect.recalc(cloud.get(j).getVec(),net.lines[i]);
                if(intersect.exists){
                    if(cloud.get(j).getWeap() == Player.weapon.EXHAUST){
                        cloud.remove(j);
                    }else{
                        cloud.get(j).hitWall(net.lines[i]);
                    }
                }
            }
        }
    }
    public void hitNet(Net net){
        for(int i = 0; i < net.lines.length;i++){
            intersect.recalc(getVec(), net.lines[i]);
            if(getInt().exists){
                getVec().recalc(getVec().getP1(),new Angle(net.lines[i].getAngle().getDeg() + (net.lines[i].getAngle().getDeg()- getVec().getAngle().getDeg())),getVec().getMag()*.5);
                life -= 50;
            }
        }
    }
    
    public double getxVelo(){
    return xvelo;}
    public double getyVelo(){
    return yvelo;}
    public void setxVelo(double x){
        xvelo = x;}
    public void setyVelo(double y){
        yvelo = y;}
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
    public void setLife(int l){
        life = l;
    }
    public int getLife(){
        return life;
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
}
