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
    public enum weapon {PISTOL,SHOTGUN,RIFLE,SNIPER}
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
        this.vector = new Line(this.getLoc(), 0, 0);
        aimline.recalc(this, this.offset(20, 0));
        setSize(2);
    }
    public void live(boolean[] input){
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
            
            switch(weap){
                case PISTOL:
                    cloud.add(new Sprite(this, 55, 5, aimline.getAngle().getDeg()+((Math.random()*10)-5), 5, r, b, g));        
                    reload = 15;
                    knockback = 75;
                break;
                case SHOTGUN:
                    cloud.add(new Sprite(this, 50+(int)(Math.random()*5), 5, aimline.getAngle().getDeg()+((Math.random()*60)-30), 5, r, b, g));  
                    cloud.add(new Sprite(this, 50+(int)(Math.random()*5), 5, aimline.getAngle().getDeg()+((Math.random()*60)-30), 5, r, b, g));  
                    cloud.add(new Sprite(this, 50+(int)(Math.random()*5), 5, aimline.getAngle().getDeg()+((Math.random()*60)-30), 5, r, b, g));
                    cloud.add(new Sprite(this, 50+(int)(Math.random()*5), 5, aimline.getAngle().getDeg()+((Math.random()*60)-30), 5, r, b, g));  
                    cloud.add(new Sprite(this, 50+(int)(Math.random()*5), 5, aimline.getAngle().getDeg()+((Math.random()*60)-30), 5, r, b, g));  
                    cloud.add(new Sprite(this, 50+(int)(Math.random()*5), 5, aimline.getAngle().getDeg()+((Math.random()*60)-30), 5, r, b, g));
                    knockback = 200;
                    reload = 25;
                break;
                case RIFLE:
                    //cloud.add(new Sprite(this, 30+(int)(Math.random()*2), 6, aimline.getAngle().getDeg()+((Math.random()*spray)-(spray/2)), 5, r, b, g)); 
                    cloud.add(new Sprite(this, 30+(int)(Math.random()*2), 6, aimline.getAngle().getDeg(), 5, r, b, g)); 
                    
                    reload = 1;
                    knockback = 15;
                break;
                default:  
                    weap = weapon.PISTOL;
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
    public void hitNet(Net net){
        for(int i = 0; i < net.lines.length;i++){
            getInt().recalc(getVec(), net.lines[i]);
            for(int j = 0;j<cloud.size();j++){
                cloud.get(j).getInt().recalc(cloud.get(j).getVec(),net.lines[i]);
                if(cloud.get(j).getInt().exists){
                    cloud.get(j).getVec().recalc(cloud.get(j).getVec().getP1(),new Angle(net.lines[i].getAngle().getDeg() + (net.lines[i].getAngle().getDeg()- cloud.get(j).getVec().getAngle().getDeg())),cloud.get(j).getVec().getMag()*.5);
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
