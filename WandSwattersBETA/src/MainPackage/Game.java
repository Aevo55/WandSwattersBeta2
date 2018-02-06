package MainPackage;
import Entities.*;
import DataTypes.*;
import Functions.*;
import java.awt.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Game extends JPanel implements Runnable { 
    Functions func = new Functions();
    Sprite test = new Sprite(new Coord(100,100),Integer.MAX_VALUE,4,0,10,100,200,50);
    int r = 5;
    int g = 5;
    int b = 255;
    int redx = 50;
    int redy = 400;
    int bluex = 270;
    int bluey = 400;
    int greenx = 160;
    int greeny = 400;
            
    boolean
        bool_D = false, //If right arrow is pressed (can be overwritten by other keys)
        bool_A = false, //If left arrow is pressed (can be overwritten by other keys)
        bool_D2 = false, //If right arrow is pressed (cannot be overwritten by other keys)
        bool_A2 = false, //If left arrow is pressed (cannot be overwritten by other keys)
        bool_W = false,
        bool_W2 = false,
        bool_S = false,
        bool_S2 = false,
        bool_SPACE = false,
        bool_collide = false,
        bool_UP = false,
        bool_DOWN = false,
        bool_UP2 = false,
        bool_DOWN2 = false
    ;
    double 
        xcol,
        ycol,
        slope,
        ratio
    ;
    int gamewidth = 400;
    Angle aimangle = new Angle();
    Angle aimupangle = new Angle();
    Angle aimdownangle = new Angle();
    Coord[] pillar2points = {
        new Coord (50,50),
        new Coord (50,60),
        new Coord (60,60),
        new Coord (60,50)
    };
    Coord[] edgepoints = {
        new Coord(0,0), 
        new Coord(0,470),
        new Coord(495,470),
        new Coord(495,0),
        new Coord(0,0),
        new Coord(10,100),
        new Coord(10,360),
        new Coord(110,460),
        new Coord(385,460),
        new Coord(485,360),
        new Coord(485,110),
        new Coord(385,10),
        new Coord(110,10),
        new Coord(10,110)
    };
    Coord[] pillarpoints = {
        new Coord(20,20), 
        new Coord(20,40),
        new Coord(30,50),
        new Coord(40,40),
        new Coord(50,30),
        new Coord(40,20)
    };
    Net pillar = new Net(pillarpoints);
    Net pillar2 = new Net(pillar2points);
    Net edge = new Net(edgepoints);
    Map m1 = new Map(edge);
    Line[] rays = new Line[m1.totalpoints()];
    Player[] players = new Player[4];
    
    
    Line veloline = new Line();
    
    Line phor = new Line(new Coord(20,20),0.0,20);
    String 
        str_appath = (System.getProperty("user.dir"))
    ;
    Thread timer;
    
    public Game() {
        timer=new Thread(this); //initializes the thread and puts Class1 into it
        timer.start(); //begins the thread
    }
    public void paintComponent(Graphics gc){
        setOpaque(false); //wipes everything on the frame
        super.paintComponent(gc); //creates the class for painting indavidual objects in the frame
        gc.setColor(Color.WHITE);
        gc.fillRect(0, 0, 505, 505);
        gc.setColor(Color.BLACK);
        for (int x = 0; x<m1.nets.length;x++){
            gc.drawPolygon(m1.nets[x].getXs(), m1.nets[x].getYs(), m1.nets[x].length());
            gc.fillPolygon(m1.nets[x].getXs(), m1.nets[x].getYs(), m1.nets[x].length());
        }
        gc.fillOval((int)players[0].getInt().getX()-1,(int)players[0].getInt().getY()-1,2,2);
        for(int x = 0;x<2;x++){
            gc.setColor(Color.black);
            gc.fillOval((int)players[x].getX()-4,(int)players[x].getY()-4,8,8);
            gc.setColor(Color.red);
            gc.drawLine(players[x].getAim().draw()[0],players[x].getAim().draw()[1],players[x].getAim().draw()[2],players[x].getAim().draw()[3]);
        }
        
        
        
        
        Color spritecol = new Color(test.red,test.blue,test.green);
        gc.setColor(spritecol);
        gc.fillOval((int)test.getLoc().getX() - (test.size / 2), (int)test.getLoc().getY() - (test.size / 2), test.size, test.size);
        for(int i = 0; i < players[0].cloud.size();i++){
            gc.setColor(new Color(players[0].cloud.get(i).red,players[0].cloud.get(i).green,players[0].cloud.get(i).blue));         
            gc.fillOval((int)players[0].cloud.get(i).getLoc().getX(),(int)players[0].cloud.get(i).getLoc().getY(),players[0].cloud.get(i).size,players[0].cloud.get(i).size);
            gc.setColor(Color.BLACK);
            gc.drawOval((int)players[0].cloud.get(i).getLoc().getX(),(int)players[0].cloud.get(i).getLoc().getY(),players[0].cloud.get(i).size,players[0].cloud.get(i).size);
        }
        gc.setColor(Color.RED);
        //veloline.recalc(players[0].getVec().getP1(), players[0].getVec().getAngle(), players[0].getVec().getMag() * 28);
        //gc.drawLine(veloline.draw()[0],veloline.draw()[1],veloline.draw()[2],veloline.draw()[3]);
        
        
    }
    public void keyPressed(KeyEvent evt){
        switch(evt.getKeyCode()){
            case KeyEvent.VK_W:
                bool_W2 = true;
                bool_W = true;
                bool_S = false;
            break;
            
            case KeyEvent.VK_N:
                players[0].getAim().getAngle().setDeg(90);
                
            break;
            case KeyEvent.VK_M:
                players[0].getAim().getAngle().setDeg(180);
                
            break;
            
            
            case KeyEvent.VK_S:
                bool_S = true;
                bool_S2 = true;
                bool_W = false;
            break;
            case KeyEvent.VK_A:
                bool_A2 = true;
                bool_A = true;
                bool_D = false;
            break;
            case KeyEvent.VK_D:
                bool_D = true;
                bool_D2 = true;
                bool_A = false;
            break;
            case KeyEvent.VK_UP:   
                bool_UP2 = true;
                bool_UP = true;
                bool_DOWN = false;
                
            break;
            case KeyEvent.VK_DOWN:
                bool_DOWN2 = true;
                bool_UP = false;
                bool_DOWN = true;
            break;
            case KeyEvent.VK_1:
                players[0].setWeap("pistol");
            break;
            case KeyEvent.VK_2:
                players[0].setWeap("shotgun");
            break;
            case KeyEvent.VK_3:
                players[0].setWeap("rifle");
            break;
            case KeyEvent.VK_E:
                players[0].setTo(new Coord(200,200));
                players[0].getVec().recalc(new Coord(200,200), new Angle(0),0.0);
            break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
            break;
            case KeyEvent.VK_SPACE:
                bool_SPACE = true;
            break;
        }
    }

    public void keyReleased(KeyEvent evt){
        switch(evt.getKeyCode()){
            case KeyEvent.VK_W:
                bool_W = false;
                bool_W2 = false;
                if (bool_S2 == true){
                    bool_S = true;
                }
            break;
            case KeyEvent.VK_S:
                bool_S = false;
                bool_S2 = false;
                if (bool_W2 == true){
                    bool_W = true;
                }
            break;
            case KeyEvent.VK_A:
                bool_A = false;
                bool_A2 = false;
                if (bool_D2 == true){
                    bool_D = true;
                }
            break;
            case KeyEvent.VK_D:
                bool_D = false;
                bool_D2 = false;
                if (bool_A2 == true){
                    bool_A = true;
                }
            break;
            case KeyEvent.VK_Q:
                   for(int x = 0; x<m1.nets[1].lines.length;x++){

                }
            break;
            case KeyEvent.VK_UP:   
                bool_UP2 = false;
                bool_UP = false;
                if(bool_DOWN2 == true){bool_DOWN = true;}
            break;
            case KeyEvent.VK_DOWN:
                bool_DOWN2 = false;
                bool_DOWN = false; 
                if(bool_UP2 == true){bool_UP = true;}
            break;
            case KeyEvent.VK_SPACE:
                bool_SPACE = false;
                players[0].setSpray(0);
            break;
        }
    }
    
    
    public void Move(){
        players[0].live();
        players[1].live();
        ///*
        Line temp = new Line(players[1].getLoc(), players[0].getLoc());
        players[1].addVelo((temp.getRun()*0.005), (temp.getRise()*0.005)); //remove this to disable recoil
        players[1].getVec().recalc(players[1].getLoc(), players[1].getVec().getAngle(),0.05/temp.getMag());
        players[1].getVec().recalc(players[1].getLoc(),new Coord(players[1].getLoc().getX() + players[1].getxVelo(),players[1].getLoc().getY() + players[1].getyVelo()));
        //*/
        for(int i = 0; i < m1.nets[0].lines.length;i++){
            players[0].getInt().recalc(players[0].getVec(), m1.nets[0].lines[i]);
            for(int j = 0;j<players[0].cloud.size();j++){
                players[0].cloud.get(j).getInt().recalc(players[0].cloud.get(j).getVec(),m1.nets[0].lines[i]);
                if(players[0].cloud.get(j).getInt().exists){
                players[0].cloud.get(j).getVec().recalc(players[0].cloud.get(j).getVec().getP1(),new Angle(m1.nets[0].lines[i].getAngle().getDeg() + (m1.nets[0].lines[i].getAngle().getDeg()- players[0].cloud.get(j).getVec().getAngle().getDeg())),players[0].cloud.get(j).getVec().getMag()*.5);
                break;
            }
            }
            if(players[0].getInt().exists){
                players[0].getVec().recalc(players[0].getVec().getP1(),new Angle(m1.nets[0].lines[i].getAngle().getDeg() + (m1.nets[0].lines[i].getAngle().getDeg()- players[0].getVec().getAngle().getDeg())),players[0].getVec().getMag()*.5);
                players[0].setxVelo(players[0].getVec().getRun());
                players[0].setyVelo(-players[0].getVec().getRise());
                break;
            }
        }
        
        if(bool_SPACE == true){
            players[0].createSprite();
        }
        if (bool_collide == false){
            if (bool_A == true){
                players[0].setX(players[0].getX() - 2);
            }else if(bool_D == true){
                players[0].setX(players[0].getX() + 2);
            }
            if (bool_W == true){
                players[0].setY(players[0].getY() - 2);
            }else if(bool_S == true){
                players[0].setY(players[0].getY() + 2);
            }
            players[0].getAim().recalc(players[0],players[0].getAim().getAngle(),players[0].getAim().getMag());
            if(bool_DOWN == true){
                players[0].getAim().rotate(new Angle(-15));
                test.getVec().rotate(new Angle(-15));
            }
            if(bool_UP == true){
                players[0].getAim().rotate(new Angle(15));
                test.getVec().rotate(new Angle(15));
            }
        }
    }
    public void collideSprites(Sprite s,ArrayList<Sprite> _s){
        Line norm = new Line();
        Intersect minpoint = new Intersect();
        for(int x = 0;x<_s.size();x++){
            norm.recalc(s, -1/_s.get(x).getVec().getSlope(),999);
            minpoint.recalc(norm,_s.get(x).getVec());
            norm.recalc(norm.getP1(), minpoint);
            if(norm.getMag()<(s.size + _s.get(x).size)){
                func.sysout("U shot my shit");
            }
        }
    }
    public void run(){
        players[0] = new Player(new Coord(200,200));
        players[1] = new Player(new Coord(100,200));
        
        while(true){
            Move();
            test.live();
            //collideSprites(test,players[0].cloud);
            repaint();
            try{
                Thread.sleep(33);
            }catch(InterruptedException ex){ 
            } 
        }
    } 
} 
        
