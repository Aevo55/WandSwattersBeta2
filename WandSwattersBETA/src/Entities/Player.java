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
    int r = 5;
    int g = 5;
    int b = 255;
    int life = 100;
    int reload = 0;
    int knockback = 5;
    String weapon = "shotgun";
    double xvelo=0;
    double yvelo=0;
    Functions func = new Functions();
    public ArrayList<Sprite> cloud = new ArrayList();
    Line aimline = new Line();
    Intersect intersect = new Intersect();
    public Player(Coord _loc){
        
        this.setX(_loc.getX());
        this.setY(_loc.getY());
        this.vector = new Line(this.getLoc(), 0, 0);
        aimline.recalc(this, this.offset(20, 0));
    }
    public void live(){
        reload--;
        vector.recalc(getLoc(),new Coord(getLoc().getX() + xvelo,getLoc().getY() + yvelo));
        setX(vector.getX2());
        setY(vector.getY2());
        vector.moveTo(vector.getP2());
        for(int x = 0;x<cloud.size();x++){
                cloud.get(cloud.size() - 1 - x).live();
                if(cloud.get(x).getLife()<=0){
                    cloud.remove(x);
                }
            }
        if(xvelo !=0){
            xvelo *=0.99;
        }
        if(yvelo !=0){
            yvelo*=0.99;
        }
    }
    public void createSprite(Coord location, int life, double velocity, int heading, int size, int red, int blue, int green){
        //<editor-fold defaultstate="collapsed" desc="RGB cycle">
        if(b == 255 && g == 5){
            r+=25;
        }
        if(b == 5 && g == 255){
            r-=25;
        }
        if(r == 255 && b == 5){
            g+=25;
        }
        if(r == 5 && b == 255){
            g-=25;
        }
        if(g == 255 && r == 5){
            b+=25;
        }
        if(g == 5 && r == 255){
            b-=25;
        }
        //</editor-fold>
        if(reload <= 0){
            switch(weapon){
                case "pistol":
                    cloud.add(new Sprite(location, life, velocity, heading+((Math.random()*10)-5), size, r, b, g));        
                    reload = 15;
                    knockback = 75;
                break;
                case "shotgun":
                    cloud.add(new Sprite(location, life, velocity, heading+((Math.random()*20)-10), size, r, b, g));   
                    cloud.add(new Sprite(location, life, velocity, heading-(Math.random()*10)-10, size, r, b, g));   
                    cloud.add(new Sprite(location, life, velocity, heading+(Math.random()*10)+10, size, r, b, g));  
                    knockback = 200;
                    reload = 30;
                break;
                case "":
                    
                break;
                default:  
                    weapon = "pistol";
                break;
            }
            Line temp = new Line(getLoc(), new Angle(heading - 180), knockback);
            addVelo(temp.getRun()*0.05, temp.getRise()*0.05);
            getVec().recalc(getLoc(), new Angle(heading - 180),knockback); 
        }

    }
    public void addVelo(double xadd, double yadd){
        if(new Line(new Coord(getLoc().getX(),getLoc().getY()), new Coord(getLoc().getX() + xadd + xvelo,getLoc().getY() - yadd - yvelo)).getMag() <12){    
            xvelo += xadd;
            yvelo -= yadd;
        }
    }
    public double getxVelo(){
    return xvelo;}
    public double getyVelo(){
    return yvelo;}
    public Intersect getInt(){
        return intersect;
    }
    public Line getAim(){
        return aimline;
    }
    public ArrayList getCloud(){
        return cloud;
    }
    public String getWeap(){
        return weapon;
    }
    public void setWeap(String weap){
        weapon = weap;
    }
}
