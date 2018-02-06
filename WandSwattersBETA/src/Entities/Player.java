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
    int level = 0;
    double spray = 1;
    int reload = 0;
    double knockback = 5;
    String weapon = "shotgun";
    double xvelo=0;
    double yvelo=0;
    Functions func = new Functions();
    public ArrayList<Sprite> cloud = new ArrayList();
    Line aimline = new Line();
    public Player(Coord _loc){
        this.setX(_loc.getX());
        this.setY(_loc.getY());
        this.vector = new Line(this.getLoc(), 0, 0);
        aimline.recalc(this, this.offset(20, 0));
    }
    public void live(){
        reload--;
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
    public void createSprite(){
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
        
        if(spray <= 40){
            spray += 2;
        }
        if(reload <= 0){
            
            switch(weapon){
                case "pistol":
                    cloud.add(new Sprite(this, 55, 5, aimline.getAngle().getDeg()+((Math.random()*10)-5), 5, r, b, g));        
                    reload = 15;
                    knockback = 75;
                break;
                case "shotgun":
                    cloud.add(new Sprite(this, 50+(int)(Math.random()*5), 5, aimline.getAngle().getDeg()+((Math.random()*60)-30), 5, r, b, g));  
                    cloud.add(new Sprite(this, 50+(int)(Math.random()*5), 5, aimline.getAngle().getDeg()+((Math.random()*60)-30), 5, r, b, g));  
                    cloud.add(new Sprite(this, 50+(int)(Math.random()*5), 5, aimline.getAngle().getDeg()+((Math.random()*60)-30), 5, r, b, g));
                    cloud.add(new Sprite(this, 50+(int)(Math.random()*5), 5, aimline.getAngle().getDeg()+((Math.random()*60)-30), 5, r, b, g));  
                    cloud.add(new Sprite(this, 50+(int)(Math.random()*5), 5, aimline.getAngle().getDeg()+((Math.random()*60)-30), 5, r, b, g));  
                    cloud.add(new Sprite(this, 50+(int)(Math.random()*5), 5, aimline.getAngle().getDeg()+((Math.random()*60)-30), 5, r, b, g));
                    knockback = 200;
                    reload = 25;
                break;
                case "rifle":
                    //cloud.add(new Sprite(this, 30+(int)(Math.random()*2), 6, aimline.getAngle().getDeg()+((Math.random()*spray)-(spray/2)), 5, r, b, g)); 
                    cloud.add(new Sprite(this, 30+(int)(Math.random()*2), 6, aimline.getAngle().getDeg(), 5, r, b, g)); 
                    
                    reload = 2;
                    knockback = 15;
                break;
                default:  
                    weapon = "pistol";
                break;
            }
            Line temp = new Line(getLoc(), new Angle(aimline.getAngle().getDeg() - 180), knockback);
            addVelo(temp.getRun()*0.05, temp.getRise()*0.05); //remove this to disable recoil
            getVec().recalc(getLoc(), new Angle(aimline.getAngle().getDeg() - 180),knockback);
            vector.recalc(getLoc(),new Coord(getLoc().getX() + xvelo,getLoc().getY() + yvelo));
        }

    }
    public void addVelo(double xadd, double yadd){
        int MAXVELO = Integer.MAX_VALUE;
        if(new Line(new Coord(getLoc().getX(),getLoc().getY()), new Coord(getLoc().getX() + xadd + xvelo,getLoc().getY() - yadd - yvelo)).getMag() <MAXVELO){    
            xvelo += xadd;
            yvelo -= yadd;
        }
        else{
            /*
            double xover = xvelo + xadd;
            double yover = yvelo + yadd;
            func.sysout("XOVER: " + xover + "YOVER: " + yover);
            Line temp = new Line(new Coord(500,500), new Coord(500+xover,500+yover));
            temp.recalc(temp.getP1(), temp.getAngle(), MAXVELO);
            xvelo = temp.getRun();
            yvelo = temp.getRise();
            //*/
            //xvelo = Math.sqrt((Math.pow(xover,2))/(magoversqar)*Math.pow(MAXVELO,2));
            //yvelo = Math.sqrt((Math.pow(yover,2))/(magoversqar)*Math.pow(MAXVELO,2));*/
        }
        
    }
    
    public double getxVelo(){
        
        
    return xvelo;}
    public double getyVelo(){
    return yvelo;}
    public void setxVelo(double x){
        xvelo = x;
    }
    public void setyVelo(double y){
        yvelo = y;
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
    public void setSpray(double s){
        spray = s;
    }
}
