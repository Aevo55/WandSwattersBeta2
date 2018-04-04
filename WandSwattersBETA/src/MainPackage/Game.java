package MainPackage;
import Entities.*;
import DataTypes.*;
import Entities.Player.weapon;
import Util.*;
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
    Util func = new Util();
    public ArrayList<Coord> newShape = new ArrayList();
    public ArrayList<Line> path = new ArrayList();
    boolean
        bool_SPACE = false,
        bool_frame = false
    ;
    private int r = 15;
    private int g = 15;
    private int b = 255;
    boolean[][] bool_rot = new boolean[4][2]; //focusedKey[player][CW/CCW]
    boolean[][] bool_rot2 = new boolean[4][2]; //pressedKey[player][CW/CCW]
    boolean[] bool_fire = new boolean[4];
    boolean[] bool_move = new boolean[4];
    Coord center = new Coord(250,250);
    Line gravity = new Line(new Coord(0,0),new Coord(0,.5));
    //<editor-fold defaultstate="collapsed" desc="Edge Net">
    Coord[] edgepoints = {
        new Coord(20,130),
        new Coord(20,590),
        new Coord(30,600),
        new Coord(210,600),
        new Coord(220,610),
        new Coord(220,690),
        new Coord(230,700),
        new Coord(690,700),
        new Coord(700,690),
        new Coord(700,610),
        new Coord(710,600),
        new Coord(890,600),
        new Coord(900,590),
        new Coord(900,130),
        new Coord(890,120),
        new Coord(710,120),
        new Coord(700,110),
        new Coord(700,30),
        new Coord(690,20),
        new Coord(230,20),
        new Coord(220,30),
        new Coord(220,110),
        new Coord(210,120),
        new Coord(30,120)
    };
    Net edge = new Net(edgepoints);
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="HUD Net">
    Coord[] hudpoints = {
        new Coord(0,720),
        new Coord(920,720),
        new Coord(920,0),
        new Coord(0,0),
        new Coord(20,130),
        new Coord(20,590),
        new Coord(30,600),
        new Coord(210,600),
        new Coord(220,610),
        new Coord(220,690),
        new Coord(230,700),
        new Coord(690,700),
        new Coord(700,690),
        new Coord(700,610),
        new Coord(710,600),
        new Coord(890,600),
        new Coord(900,590),
        new Coord(900,130),
        new Coord(890,120),
        new Coord(710,120),
        new Coord(700,110),
        new Coord(700,30),
        new Coord(690,20),
        new Coord(230,20),
        new Coord(220,30),
        new Coord(220,110),
        new Coord(210,120),
        new Coord(30,120),
        new Coord(20,130),
        new Coord(0,0),
    };
    Net hud = new Net(hudpoints);
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Wall Nets">
    Coord[] wall1p = {
        new Coord(460,360),
        new Coord(360,310),
        new Coord(360,260),
        new Coord(410,260),
    };
    Net wall1 = new Net(wall1p);
    Coord[] wall2p = {
        new Coord(460,360),
        new Coord(560,310),
        new Coord(560,260),
        new Coord(510,260),
    };
    Net wall2 = new Net(wall2p);
    Coord[] wall3p = {
        new Coord(460,360),
        new Coord(360,410),
        new Coord(360,460),
        new Coord(410,460),
    };
    Net wall3 = new Net(wall3p);
    Coord[] wall4p = {
        new Coord(460,360),
        new Coord(560,410),
        new Coord(560,460),
        new Coord(510,460),
    };
    Net wall4 = new Net(wall4p);
    Coord[] wall5p = {
        new Coord(435,335),
        new Coord(435,385),
        new Coord(485,385),
        new Coord(485,335),
    };
    Net wall5 = new Net(wall5p);
    //</editor-fold>
    
    Map m1 = new Map();
    
    Player[] players = new Player[4];
    String 
        str_appath = (System.getProperty("user.dir"))
    ;
    Thread timer;
    
    public Game() {
        timer=new Thread(this); //initializes the thread and puts Class1 into it
        timer.start(); //begins the thread
    }
    public void drawLine(Graphics gc, Line l){
        gc.drawLine(l.draw()[0], l.draw()[1], l.draw()[2], l.draw()[3]);
    }
    
    public void paintComponent(Graphics gc){
        setOpaque(false); //wipes everything on the frame
        super.paintComponent(gc); //creates the class for painting indavidual objects in the frame
        gc.setColor(Color.WHITE);
        gc.fillRect(0, 0, 920, 920);
        
        for(int x = 0;x<4;x++){
            gc.setColor(Color.black);
            drawLine(gc,players[x].getAim());
            switch(x){
                case 0:
                    gc.setColor(Color.red);
                break;
                case 1:
                    gc.setColor(Color.blue);
                break;
                case 2:
                    gc.setColor(Color.green);
                break;
                case 3:
                    gc.setColor(Color.orange);
                break;
            }
            Color col = gc.getColor();
            for(int i = 0; i < players[x].getCloud().length;i++){
                gc.setColor(col);
                gc.fillOval((int)players[x].getCloud()[i].getLoc().getX()-(players[x].getCloud()[i].getSize()/2),(int)players[x].getCloud()[i].getLoc().getY()-(players[x].getCloud()[i].getSize()/2),players[x].getCloud()[i].getSize(),players[x].getCloud()[i].getSize());
                gc.setColor(Color.BLACK);
                gc.drawOval((int)players[x].getCloud()[i].getLoc().getX()-(players[x].getCloud()[i].getSize()/2),(int)players[x].getCloud()[i].getLoc().getY()-(players[x].getCloud()[i].getSize()/2),players[x].getCloud()[i].getSize(),players[x].getCloud()[i].getSize());
            }
            gc.setColor(col);
            gc.fillOval((int)players[x].getX()-4,(int)players[x].getY()-4,(int)players[x].getSize(),(int)players[x].getSize());
            gc.setColor(Color.BLACK);
            gc.drawOval((int)players[x].getX()-4,(int)players[x].getY()-4,(int)players[x].getSize(),(int)players[x].getSize());
        }
        for(int x = 0;x<path.size();x++){
            drawLine(gc,path.get(x));
        }
        
        gc.setColor(Color.BLACK);
        for (int x = 1; x<m1.getArrWall().length;x++){
            gc.drawPolygon(m1.getWall(x).getXs(), m1.getWall(x).getYs(), m1.getWall(x).length());
            gc.fillPolygon(m1.getWall(x).getXs(), m1.getWall(x).getYs(), m1.getWall(x).length());
        }
        for(int x = 0;x<m1.getArrUi().length;x++){
            gc.drawPolygon(m1.getUi(x).getXs(), m1.getUi(x).getYs(), m1.getUi(x).length());
            gc.fillPolygon(m1.getUi(x).getXs(), m1.getUi(x).getYs(), m1.getUi(x).length());
        }
        //<editor-fold defaultstate="collapsed" desc="Draw UI">
        gc.setColor(Color.red);
        gc.fillRect(10, 10, 200, 100);
        gc.setColor(Color.blue);
        gc.fillRect(710,10,200,100);
        gc.setColor(Color.green);
        gc.fillRect(10,610,200,100);
        gc.setColor(Color.orange);
        gc.fillRect(710, 610, 200, 100);
        
        gc.setColor(Color.BLACK);
        gc.fillRect(15,15,190,90);
        gc.fillRect(715,15,190,90);
        gc.fillRect(15,615,190,90);
        gc.fillRect(715,615,190,90);
        
        gc.setColor(Color.LIGHT_GRAY);
        gc.fillRect(20,20,180,23);
        gc.fillRect(20,48,180,23);
        gc.fillRect(20,76,180,23);
        gc.fillRect(720,20,180,23);
        gc.fillRect(720,48,180,23);
        gc.fillRect(720,76,180,23);
        gc.fillRect(20,620,180,23);
        gc.fillRect(20,648,180,23);
        gc.fillRect(20,676,180,23);
        gc.fillRect(720,620,180,23);
        gc.fillRect(720,648,180,23);
        gc.fillRect(720,676,180,23);
        
        gc.setColor(Color.BLACK);
        gc.fillRect(23,23,174,17);
        gc.fillRect(23,51,174,17);
        gc.fillRect(23,79,174,17);
        gc.fillRect(723,23,174,17);
        gc.fillRect(723,51,174,17);
        gc.fillRect(723,79,174,17);
        gc.fillRect(23,623,174,17);
        gc.fillRect(23,651,174,17);
        gc.fillRect(23,679,174,17);
        gc.fillRect(723,623,174,17);
        gc.fillRect(723,651,174,17);
        gc.fillRect(723,679,174,17);
        
        gc.setColor(Color.RED);
        gc.fillRect(23,23,(int)(players[0].getLife()*1.74),17);
        gc.fillRect(723,23,(int)(players[1].getLife()*1.74),17);
        gc.fillRect(23,623,(int)(players[2].getLife()*1.74),17);
        gc.fillRect(723,623,(int)(players[3].getLife()*1.74),17);
        
        gc.setColor(Color.BLUE);
        gc.fillRect(23,51,(int)(players[0].getMana()*1.74),17);
        gc.fillRect(723,51,(int)(players[1].getMana()*1.74),17);
        gc.fillRect(23,651,(int)(players[2].getMana()*1.74),17);
        gc.fillRect(723,651,(int)(players[3].getMana()*1.74),17);
        
        gc.setColor(Color.GREEN);
        gc.fillRect(23,79,(int)(players[0].getStamina()*1.74),17);
        gc.fillRect(723,79,(int)(players[1].getStamina()*1.74),17);
        gc.fillRect(23,679,(int)(players[2].getStamina()*1.74),17);
        gc.fillRect(723,679,(int)(players[3].getStamina()*1.74),17);
        //</editor-fold>
    }
    public void keyPressed(KeyEvent evt){
        switch(evt.getKeyCode()){
            case KeyEvent.VK_A:
                bool_rot2[1][0] = true;
                bool_rot[1][0] = true;
                bool_rot[1][1] = false;
            break;
            case KeyEvent.VK_D:
                bool_rot[1][1] = true;
                bool_rot2[1][1] = true;
                bool_rot[1][0] = false;
            break;
            case KeyEvent.VK_G:
                bool_rot[2][0] = true;
                bool_rot2[2][0] = true;
                bool_rot[2][1] = false;
            break;
            case KeyEvent.VK_J:
                bool_rot[2][1] = true;
                bool_rot2[2][1] = true;
                bool_rot[2][0] = false;
            break;
            case KeyEvent.VK_LEFT:   
                bool_rot[0][0] = true;
                bool_rot2[0][0] = true;
                bool_rot[0][1] = false;
                
            break;
            case KeyEvent.VK_RIGHT:
                bool_rot2[0][1] = true;
                bool_rot[0][0] = false;
                bool_rot[0][1] = true;
            break;
            case KeyEvent.VK_1:
                players[0].setWeap(weapon.EXHAUST);
                players[1].setWeap(weapon.EXHAUST);
            break;
            case KeyEvent.VK_2:
                players[0].setWeap(weapon.FLAME);
                players[1].setWeap(weapon.FLAME);
            break;
            case KeyEvent.VK_3:
                players[0].setWeap(weapon.MISSILE);
                players[1].setWeap(weapon.MISSILE);
            break;
            case KeyEvent.VK_E:
                //players[0].getVec().moveTo(new Coord(0,0));
            break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
            break;
            case KeyEvent.VK_UP:
                bool_fire[0] = true;
            break;
            case KeyEvent.VK_W:
                bool_fire[1] = true;
            break;
            case KeyEvent.VK_Y:
                bool_fire[2] = true;
            break;
            case KeyEvent.VK_DOWN:
                bool_move[0] = true;
            break;
            case KeyEvent.VK_S:
                bool_move[1] = true;
            break;
            case KeyEvent.VK_H:
                bool_move[2] = true;
            break;
        }
    }

    public void keyReleased(KeyEvent evt){
        switch(evt.getKeyCode()){
            case KeyEvent.VK_A:
                bool_rot[1][0] = false;
                bool_rot2[1][0] = false;
                if (bool_rot2[1][1] == true){
                    bool_rot[1][1] = true;
                }
            break;
            case KeyEvent.VK_D:
                bool_rot[1][1] = false;
                bool_rot2[1][1] = false;
                if (bool_rot2[1][0] == true){
                    bool_rot[1][0] = true;
                }
            break;
            case KeyEvent.VK_G:
                bool_rot[2][0] = false;
                bool_rot2[2][0] = false;
                if (bool_rot2[2][1] == true){
                    bool_rot[2][1] = true;
                }
            break;
            case KeyEvent.VK_J:
                bool_rot[2][1] = false;
                bool_rot2[2][1] = false;
                if (bool_rot2[2][0] == true){
                    bool_rot[2][0] = true;
                }
            break;
            case KeyEvent.VK_LEFT:   
                bool_rot[0][0] = false;
                bool_rot2[0][0] = false;
                if(bool_rot2[0][1] == true){bool_rot[0][1] = true;}
            break;
            case KeyEvent.VK_RIGHT:
                bool_rot2[0][1] = false;
                bool_rot[0][1] = false; 
                if(bool_rot2[0][0] == true){bool_rot[0][0] = true;}
            break;
            case KeyEvent.VK_UP:
                bool_fire[0] = false;
                players[0].setSpray(0);
            break;
            case KeyEvent.VK_W:
                bool_fire[1] = false;
                players[1].setSpray(0);
            break;
            case KeyEvent.VK_Y:
                bool_fire[2] = false;
                players[2].setSpray(0);
            break;
            case KeyEvent.VK_DOWN:
                bool_move[0] = false;
            break;
            case KeyEvent.VK_S:
                bool_move[1] = false;
            break;
            case KeyEvent.VK_H:
                bool_move[2] = false;
            break;
        }
    }
    
    public void Move(){
        //gravity.recalc(players[0], center);
        for(int x = 0;x<4;x++){
            for(int y = 0;y<m1.getArrWall().length;y++){
                players[x].hitNet(m1.getWall(y));
                players[x].cloudHitNet(m1.getWall(y));
                
            }
            players[x].live(bool_rot[x]);
            if(bool_fire[x] == true){
                players[x].createSprite();
            }
            if(bool_move[x] == true){
                players[x].move();
            }
            //players[x].getVec().Accel(gravity,0.5);
        }
        bool_frame = !bool_frame;
    }
    public boolean collideEntity(Entity e,Entity _e){
        return(new Line(e,_e).getMag()<e.getSize()+_e.getSize());
    }
    public void run(){
        players[0] = new Player(new Coord(460,285));
        players[0].getAim().setAng(new Angle(270));
        players[1] = new Player(new Coord(535,360));
        players[2] = new Player(new Coord(385,360));
        players[2].getAim().setAng(new Angle(180));
        players[3] = new Player(new Coord(460,435));
        players[3].getAim().setAng(new Angle(90));
        func.sysout(str_appath);
        m1.addWalls(edge,wall1,wall2,wall3,wall4,wall5);
        m1.addUi(hud);
        while(true){
            Move();
            repaint();
            for(int x = 1;x<3;x++){
                if(collideEntity(players[0],players[x])){
                    System.out.println("Ab you suck " + x);
                }
            }
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
            try{
                    /**/Thread.sleep(30);/* Add an extra '/' in front to toggle fps
                    Thread.sleep(16,500000);//*/
            }catch(InterruptedException ex){ 
            } 
        }
    } 
} 
        
