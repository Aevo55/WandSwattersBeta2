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
    public ArrayList<Coord> newShape = new ArrayList();
    boolean
        bool_SPACE = false,
        bool_frame = false
    ;
    boolean[][] bool_rot = new boolean[4][2]; //focusedKey[player][CW/CCW]
    boolean[][] bool_rot2 = new boolean[4][2]; //pressedKey[player][CW/CCW]
    Coord[] pillar2points = {
        new Coord(125.223457889522,274.7765421104783),
new Coord(123.63246763185225,276.3675323681481),
new Coord(237.44854168445298,388.8727513099821),
new Coord(244.1762572789925,390.9799415808636),
new Coord(250.903972873532,393.08713185174514),
new Coord(432.41498651291744,334.4639001892295),
new Coord(437.00744498536113,328.60220239322615),
new Coord(441.5999034578048,322.7405045972228),
new Coord(243.8204018682883,320.9220394426559),
new Coord(236.22087177316735,323.2944914035533),
new Coord(228.6213416780464,325.66694336445073),
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
        for(int i = 0; i < players[0].getCloud().length;i++){
            gc.setColor(new Color(players[0].getCloud()[i].red,players[0].getCloud()[i].green,players[0].getCloud()[i].blue));         
            gc.fillOval((int)players[0].getCloud()[i].getLoc().getX()-(players[0].getCloud()[i].getSize()/2),(int)players[0].getCloud()[i].getLoc().getY()-(players[0].getCloud()[i].getSize()/2),players[0].getCloud()[i].getSize(),players[0].getCloud()[i].getSize());
            gc.setColor(Color.BLACK);
            gc.drawOval((int)players[0].getCloud()[i].getLoc().getX()-(players[0].getCloud()[i].getSize()/2),(int)players[0].getCloud()[i].getLoc().getY()-(players[0].getCloud()[i].getSize()/2),players[0].getCloud()[i].getSize(),players[0].getCloud()[i].getSize());
        }
        gc.setColor(Color.BLACK);
        for (int x = 0; x<m1.getNets().length;x++){
            gc.drawPolygon(m1.getNet(x).getXs(), m1.getNet(x).getYs(), m1.getNet(x).length());
            gc.fillPolygon(m1.getNet(x).getXs(), m1.getNet(x).getYs(), m1.getNet(x).length());
        }
        gc.setColor(Color.RED);
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
            for(int y = 0;y<m1.getNets().length;y++){
                players[x].hitNet(m1.getNet(y));
            }
            players[x].live(bool_rot[x]);
        }
        if(bool_SPACE == true){
            players[0].createSprite();
        }
        bool_frame = !bool_frame;
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
                /**/Thread.sleep(30);/* (Add a second "/" in front of this line to toggle to 60 fps)
                Thread.sleep(16,500000);//*/
            }catch(InterruptedException ex){ 
            } 
        }
    } 
} 
        
