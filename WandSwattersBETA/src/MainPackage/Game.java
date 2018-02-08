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
    public ArrayList<Coord> newShape = new ArrayList();
    boolean
        bool_SPACE = false
    ;
    boolean[][] bool_rot = new boolean[4][2]; //focusedKey[player][up/down]
    boolean[][] bool_rot2 = new boolean[4][2]; //pressedKey[player][up/down]
    Coord[] pillar2points = {

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
    Map m1 = new Map(edge,pillar2);
    Player[] players = new Player[4];
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
        gc.fillOval((int)players[1].getX()-10,(int)players[1].getY()-10,20,20);
        
        
        
        Color spritecol = new Color(test.red,test.blue,test.green);
        gc.setColor(spritecol);
        gc.fillOval((int)test.getLoc().getX() - (test.getSize() / 2), (int)test.getLoc().getY() - (test.getSize() / 2), test.getSize(), test.getSize());
        for(int i = 0; i < players[0].cloud.size();i++){
            gc.setColor(new Color(players[0].cloud.get(i).red,players[0].cloud.get(i).green,players[0].cloud.get(i).blue));         
            gc.fillOval((int)players[0].cloud.get(i).getLoc().getX(),(int)players[0].cloud.get(i).getLoc().getY(),players[0].cloud.get(i).getSize(),players[0].cloud.get(i).getSize());
            gc.setColor(Color.BLACK);
            gc.drawOval((int)players[0].cloud.get(i).getLoc().getX(),(int)players[0].cloud.get(i).getLoc().getY(),players[0].cloud.get(i).getSize(),players[0].cloud.get(i).getSize());
        }
        gc.setColor(Color.RED);
        //veloline.recalc(players[0].getVec().getP1(), players[0].getVec().getAngle(), players[0].getVec().getMag() * 28);
        //gc.drawLine(veloline.draw()[0],veloline.draw()[1],veloline.draw()[2],veloline.draw()[3]);
        
        
    }
    public void keyPressed(KeyEvent evt){
        switch(evt.getKeyCode()){
            case KeyEvent.VK_W:
                bool_rot2[1][0] = true;
                bool_rot[1][0] = true;
                bool_rot[1][1] = false;
            break;
            case KeyEvent.VK_S:
                bool_rot[1][1] = true;
                bool_rot2[1][1] = true;
                bool_rot[1][0] = false;
            break;
            case KeyEvent.VK_UP:   
                bool_rot[0][0] = true;
                bool_rot2[0][0] = true;
                bool_rot[0][1] = false;
                
            break;
            case KeyEvent.VK_DOWN:
                bool_rot2[0][1] = true;
                bool_rot[0][0] = false;
                bool_rot[0][1] = true;
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
                bool_rot[1][0] = false;
                bool_rot2[1][0] = false;
                if (bool_rot2[1][1] == true){
                    bool_rot[1][1] = true;
                }
            break;
            case KeyEvent.VK_S:
                bool_rot[1][1] = false;
                bool_rot2[1][1] = false;
                if (bool_rot2[1][0] == true){
                    bool_rot[1][0] = true;
                }
                for(int x = 0;x<newShape.size();x++){
                    func.sysout("new Coord(" + newShape.get(x).getX() + "," + newShape.get(x).getY() + "),");
                }
            break;
            case KeyEvent.VK_UP:   
                bool_rot[0][0] = false;
                bool_rot2[0][0] = false;
                if(bool_rot2[0][1] == true){bool_rot[0][1] = true;}
            break;
            case KeyEvent.VK_DOWN:
                bool_rot2[0][1] = false;
                bool_rot[0][1] = false; 
                if(bool_rot2[0][0] == true){bool_rot[0][0] = true;}
            break;
            case KeyEvent.VK_SPACE:
                bool_SPACE = false;
                players[0].setSpray(0);
            break;
        }
    }
    
    public void Move(){
        for(int x = 0;x<2;x++){
            for(int y = 0;y<m1.nets.length;y++){
                players[x].hitNet(m1.nets[y]);
            }
            players[x].live(bool_rot[x]);
        }
        if(bool_SPACE == true){
            players[0].createSprite();
        }
        if(!bool_rot[1][0] == false){
            newShape.add(new Coord(players[0].getLoc()));
        }

    }
    public void collideSprites(Sprite s,ArrayList<Sprite> _s){
        Line norm = new Line();
        Intersect minpoint = new Intersect();
        for(int x = 0;x<_s.size();x++){
            norm.recalc(s, -1/_s.get(x).getVec().getSlope(),999);
            minpoint.recalc(norm,_s.get(x).getVec());
            norm.recalc(norm.getP1(), minpoint);
            if(norm.getMag()<(s.getSize() + _s.get(x).getSize())){
                func.sysout("U shot my shit");
            }
        }
    }
    public void run(){
        players[0] = new Player(new Coord(200,200));
        players[1] = new Player(new Coord(100,200));
        func.sysout(str_appath);
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
        
