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
    Coord[] pillar2points = {
        new Coord(118.60079151192878,334.59508382808224),
new Coord(117.11774111029688,331.8498507128317),
new Coord(116.06041093188769,328.8423919475406),
new Coord(115.43397136224569,325.58129311586356),
new Coord(115.24347464985097,322.0753395819342),
new Coord(115.49387533672741,318.3335694173023),
new Coord(116.19004806305063,314.36533228190535),
new Coord(117.33680277697437,310.1803559658097),
new Coord(118.93889717680275,305.78882270734647),
new Coord(121.00104594871554,301.20145795313744),
new Coord(123.52792599854256,296.4296349669944),
new Coord(126.5241763473508,291.48549969912347),
new Coord(129.99439056707956,286.3821216949596),
new Coord(133.94309840957544,281.1336786951173),
new Coord(138.3747313559148,275.7556851488548),
new Coord(143.293563717544,270.2652783927758),
new Coord(148.70361585422148,264.6815810551705),
new Coord(154.60849763464756,259.02616465686566),
new Coord(161.011155988812,253.32364748747028),
new Coord(167.91346596979477,247.60246881176434),
new Coord(175.3155628196179,241.8958875925069),
new Coord(183.214741732061,236.24324544092286),
new Coord(191.60363924521988,230.69147792496625),
new Coord(200.46725974195618,225.29667565632053),
new Coord(209.7783214052165,220.12502446318112),
new Coord(219.4907740318498,215.25150963904505),
new Coord(229.53311720972263,210.75372373873955),
new Coord(239.80665960898352,206.69927396377187),
new Coord(250.19477422571393,203.1315204051282),
new Coord(260.58288884244433,200.06376684648455),
new Coord(270.86734176807624,197.48514952019195),
new Coord(280.96715702807194,195.37119221200766),
new Coord(290.82040071651375,193.69220899512243),
new Coord(300.3801735678617,192.41804038738),
new Coord(309.6107090727192,191.5201732142295),
new Coord(318.48432178392056,190.9724576073595),
    };
    Line gravity = new Line(new Coord(0,0),new Coord(0,.5));
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
        gc.fillRect(0, 0, 505, 505);
        for(int x = 0;x<2;x++){
            gc.setColor(Color.black);
            gc.fillOval((int)players[x].getX()-4,(int)players[x].getY()-4,8,8);
            gc.setColor(Color.red);
            drawLine(gc,players[x].getAim());
            for(int i = 0; i < players[x].getCloud().length;i++){
                gc.setColor(new Color(players[x].getCloud()[i].red,players[x].getCloud()[i].green,players[x].getCloud()[i].blue));         
                gc.fillOval((int)players[x].getCloud()[i].getLoc().getX()-(players[x].getCloud()[i].getSize()/2),(int)players[x].getCloud()[i].getLoc().getY()-(players[x].getCloud()[i].getSize()/2),players[x].getCloud()[i].getSize(),players[x].getCloud()[i].getSize());
                gc.setColor(Color.BLACK);
                gc.drawOval((int)players[x].getCloud()[i].getLoc().getX()-(players[x].getCloud()[i].getSize()/2),(int)players[x].getCloud()[i].getLoc().getY()-(players[x].getCloud()[i].getSize()/2),players[x].getCloud()[i].getSize(),players[x].getCloud()[i].getSize());
            }
        }
        for(int x = 0;x<path.size();x++){
            drawLine(gc,path.get(x));
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
        for(int x = 0;x<2;x++){
            for(int y = 0;y<m1.getNets().length;y++){
                players[x].hitNet(m1.getNet(y));
            }
            players[x].live(bool_rot[x]);
            if(bool_fire[x] == true){
                players[x].createSprite();
            }
            players[x].getVec().Accel(gravity,0.5);
        }
        bool_frame = !bool_frame;
        
    }
    public boolean collideSprites(Sprite s,Sprite _s){
        return(new Line(s,_s).getMag()<s.getSize()+_s.getSize());
    }
    public void run(){
        players[0] = new Player(new Coord(200,200));
        players[1] = new Player(new Coord(100,200));
        func.sysout(str_appath);
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
        
