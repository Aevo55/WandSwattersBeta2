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
    private int mana = 100;
    public enum weapon {PISTOL,SHOTGUN,RIFLE}
    weapon weap = weapon.PISTOL;
    int level = 0;
    double spray = 1;
    int reload = 0;
    double knockback = 5;
    double xvelo=0;
    double yvelo=0;
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
        
        if(spray <= 20){
            spray += 2;
        }
        if(reload <= 0){
            switch(weap){
                case PISTOL:
                    cloud.add(new Sprite(this, 55, 5, aimline.getAngle().getDeg()+((Math.random()*10)-5), 5, r, b, g,weap));        
                    reload = 15;
                    knockback = 75;
                break;
                case SHOTGUN:
                    cloud.add(new Sprite(this, 50+(int)(Math.random()*5), 5, aimline.getAngle().getDeg()+((Math.random()*60)-30), 5, r, b, g,weap));  
                    cloud.add(new Sprite(this, 50+(int)(Math.random()*5), 5, aimline.getAngle().getDeg()+((Math.random()*60)-30), 5, r, b, g,weap));  
                    cloud.add(new Sprite(this, 50+(int)(Math.random()*5), 5, aimline.getAngle().getDeg()+((Math.random()*60)-30), 5, r, b, g,weap));
                    cloud.add(new Sprite(this, 50+(int)(Math.random()*5), 5, aimline.getAngle().getDeg()+((Math.random()*60)-30), 5, r, b, g,weap));  
                    cloud.add(new Sprite(this, 50+(int)(Math.random()*5), 5, aimline.getAngle().getDeg()+((Math.random()*60)-30), 5, r, b, g,weap));  
                    cloud.add(new Sprite(this, 50+(int)(Math.random()*5), 5, aimline.getAngle().getDeg()+((Math.random()*60)-30), 5, r, b, g,weap));
                    knockback = 200;
                    reload = 25;
                break;
                case RIFLE:
                    //cloud.add(new Sprite(this, 30+(int)(Math.random()*2), 6, aimline.getAngle().getDeg()+((Math.random()*spray)-(spray/2)), 5, r, b, g)); 
                    cloud.add(new Sprite(this, 30+(int)(Math.random()*2), 6, aimline.getAngle().getDeg()+(spray*(Math.random()*2-1)), 5, r, b, g,weap)); 
                    
                    reload = 1;
                    knockback = 15;
                break;
                default:  
                    weap = weapon.PISTOL;
                break;
            }
            getVec().Accel(new Line(this,new Angle(aimline.getAngle().getDeg() - 180),knockback/20));
            
        }
    }
    public void hitNet(Net net){
        for(int i = 0; i < net.lines.length;i++){
            getInt().recalc(getVec(), net.lines[i]);
            for(int j = 0;j<cloud.size();j++){
                cloud.get(j).getInt().recalc(cloud.get(j).getVec(),net.lines[i]);
                if(cloud.get(j).getInt().exists){
                    cloud.get(j).hitWall(net.lines[i]);
                    break;
                }
            }
            if(getInt().exists){
                getVec().recalc(getVec().getP1(),new Angle(net.lines[i].getAngle().getDeg() + (net.lines[i].getAngle().getDeg()- getVec().getAngle().getDeg())),getVec().getMag()*.5);
                setxVelo(getVec().getRun());
                setyVelo(-getVec().getRise());
                break;
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
        return weap;}
    public void setWeap(weapon _weap){
        weap = _weap;}
    public void setSpray(double s){
        spray = s;}
}
