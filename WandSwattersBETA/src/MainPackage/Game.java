package MainPackage;
import Entities.*;
import DataTypes.*;
import Entities.Player.weapon;
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
    //Sprite test = new Sprite(new Coord(100,100),Integer.MAX_VALUE,4,0,10,100,200,50);
    
    public ArrayList<Coord> newShape = new ArrayList();
    public ArrayList<Line> path = new ArrayList();
    boolean
        bool_SPACE = false,
        bool_frame = false
    ;
    
    boolean[][] bool_rot = new boolean[4][2]; //focusedKey[player][CW/CCW]
    boolean[][] bool_rot2 = new boolean[4][2]; //pressedKey[player][CW/CCW]
    boolean[] bool_fire = new boolean[4];
    Coord center = new Coord(250,250);
    Line gravity = new Line(new Coord(0,0),new Coord(0,.5));
    Coord[] edgepoints = {
        new Coord(20,110),
        new Coord(20,590),
        new Coord(30,600),
        new Coord(220,600),
        new Coord(220,690),
        new Coord(230,700),
        new Coord(690,700),
        new Coord(700,690),
        new Coord(700,600),
        new Coord(890,600),
        new Coord(900,590),
        new Coord(900,110),
        new Coord(890,100),
        new Coord(700,100),
        new Coord(700,30),
        new Coord(690,20),
        new Coord(230,20),
        new Coord(220,30),
        new Coord(220,100),
        new Coord(30,100)
    };
    Coord[] hudpoints = {
        new Coord(0,720),
        new Coord(920,720),
        new Coord(920,0),
        new Coord(0,0),
        new Coord(20,110),
        new Coord(20,590),
        new Coord(30,600),
        new Coord(220,600),
        new Coord(220,690),
        new Coord(230,700),
        new Coord(690,700),
        new Coord(700,690),
        new Coord(700,600),
        new Coord(890,600),
        new Coord(900,590),
        new Coord(900,110),
        new Coord(890,100),
        new Coord(700,100),
        new Coord(700,30),
        new Coord(690,20),
        new Coord(230,20),
        new Coord(220,30),
        new Coord(220,100),
        new Coord(30,100),
        new Coord(20,110),
        new Coord(0,0),
    };
    Net edge = new Net(edgepoints);
    Net hud = new Net(hudpoints);
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
            for(int i = 0; i < players[x].getCloud().length;i++){
                gc.setColor(new Color(players[x].getCloud()[i].red,players[x].getCloud()[i].green,players[x].getCloud()[i].blue));         
                gc.fillOval((int)players[x].getCloud()[i].getLoc().getX()-(players[x].getCloud()[i].getSize()/2),(int)players[x].getCloud()[i].getLoc().getY()-(players[x].getCloud()[i].getSize()/2),players[x].getCloud()[i].getSize(),players[x].getCloud()[i].getSize());
                gc.setColor(Color.BLACK);
                gc.drawOval((int)players[x].getCloud()[i].getLoc().getX()-(players[x].getCloud()[i].getSize()/2),(int)players[x].getCloud()[i].getLoc().getY()-(players[x].getCloud()[i].getSize()/2),players[x].getCloud()[i].getSize(),players[x].getCloud()[i].getSize());
            }
            gc.drawOval((int)players[x].getX()-4,(int)players[x].getY()-4,(int)players[x].getSize(),(int)players[x].getSize());
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
            gc.fillOval((int)players[x].getX()-4,(int)players[x].getY()-4,(int)players[x].getSize(),(int)players[x].getSize());
            
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
        
        gc.setColor(Color.RED);
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
                players[0].setWeap(weapon.PISTOL);
                players[1].setWeap(weapon.PISTOL);
            break;
            case KeyEvent.VK_2:
                players[0].setWeap(weapon.SHOTGUN);
                players[1].setWeap(weapon.SHOTGUN);
            break;
            case KeyEvent.VK_3:
                players[0].setWeap(weapon.RIFLE);
                players[1].setWeap(weapon.RIFLE);
            break;
            case KeyEvent.VK_E:
                players[0].setTo(new Coord(200,200));
                players[0].getVec().recalc(new Coord(200,200), new Angle(0),0.0);
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
                /*for(int x = 0;x<newShape.size();x++){
                    func.sysout("new Coord(" + newShape.get(x).getX() + "," + newShape.get(x).getY() + "),");
                }*/
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
        }
    }
    
    public void Move(){
        //gravity.recalc(players[0], center);
        for(int x = 0;x<4;x++){
            for(int y = 0;y<m1.getArrWall().length;y++){
                players[x].hitNet(m1.getWall(y));
            }
            players[x].live(bool_rot[x]);
            if(bool_fire[x] == true){
                players[x].createSprite();
            }
            //players[x].getVec().Accel(gravity,0.5);
        }
        bool_frame = !bool_frame;
    }
    public boolean collideSprites(Sprite s,Sprite _s){
        return(new Line(s,_s).getMag()<s.getSize()+_s.getSize());
    }
    public void run(){
        players[0] = new Player(new Coord(300,300));
        players[1] = new Player(new Coord(200,300));
        players[2] = new Player(new Coord(300,200));
        players[3] = new Player(new Coord(200,200));
        func.sysout(str_appath);
        m1.addWalls(edge);
        m1.addUi(hud);
        while(true){
            Move();
            repaint();
            try{
                    /**/Thread.sleep(30);/* Add an extra '/' in front to toggle fps
                    Thread.sleep(16,500000);//*/
            }catch(InterruptedException ex){ 
            } 
        }
    } 
} 
        
