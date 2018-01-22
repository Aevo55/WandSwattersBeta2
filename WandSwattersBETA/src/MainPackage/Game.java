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
    ArrayList<Sprite> cloud = new ArrayList();
    Sprite test = new Sprite(new Coord(100,100),Integer.MAX_VALUE,4,0,10,100,200,50);
    int red = 100;
    int green = 100;
    int blue = 100;
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
    Angle ang = new Angle();
    Coord aim = new Coord(10,10);
    Coord prevplayer = new Coord(50,50);
    Angle aimangle = new Angle();
    Angle aimupangle = new Angle();
    Angle aimdownangle = new Angle();
    Line aimline;
    Intersect testint;
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
    Line add1 = new Line(new Coord(100,100),new Coord(150,150));
    Line add2 = new Line(new Coord(150,150),new Coord(300,200));
    Line add3 = new Line(new Coord(100,100),new Coord(150,150));
    Player player1 = new Player(new Coord(50,50));
    Line veloline = new Line(player1.getVec().getP1(), player1.getVec().getP2());
    
    Line phor = new Line(player1,0.0,20);
    Line aiml = new Line(player1, aim);
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
        gc.fillOval((int)testint.getX()-1,(int)testint.getY()-1,2,2);
        gc.setColor(Color.red);
        gc.fillOval(redx, redy, (int)((red/5)*2), (int)((red/5)*2));
        gc.setColor(Color.green);
        gc.fillOval(greenx,greeny,(int)((green/5)*2),(int)((green/5)*2));
        gc.setColor(Color.BLUE);
        gc.fillOval(bluex,bluey,(int)((blue/5)*2),(int)((blue/5)*2));
        gc.fillOval((int)player1.getX()-4,(int)player1.getY()-4,8,8);
        gc.drawLine(aimline.draw()[0],aimline.draw()[1],aimline.draw()[2],aimline.draw()[3]);
        Color spritecol = new Color(test.red,test.blue,test.green);
        gc.setColor(spritecol);
        gc.fillOval((int)test.getLoc().getX() - (test.size / 2), (int)test.getLoc().getY() - (test.size / 2), test.size, test.size);
        for(int i = 0; i < cloud.size();i++){
            gc.setColor(new Color(cloud.get(i).red,cloud.get(i).green,cloud.get(i).blue));         
            gc.fillOval((int)cloud.get(i).getLoc().getX(),(int)cloud.get(i).getLoc().getY(),cloud.get(i).size,cloud.get(i).size);
            gc.setColor(Color.BLACK);
            gc.drawOval((int)cloud.get(i).getLoc().getX(),(int)cloud.get(i).getLoc().getY(),cloud.get(i).size,cloud.get(i).size);
        }
        
        gc.drawLine(add1.draw()[0],add1.draw()[1],add1.draw()[2],add1.draw()[3]);
        gc.drawLine(add2.draw()[0],add2.draw()[1],add2.draw()[2],add2.draw()[3]);
        gc.setColor(Color.RED);
        gc.drawLine(add3.draw()[0],add3.draw()[1],add3.draw()[2],add3.draw()[3]);
        veloline.recalc(player1.getVec().getP1(), player1.getVec().getAngle(), player1.getVec().getMag() * 28);
        gc.drawLine(veloline.draw()[0],veloline.draw()[1],veloline.draw()[2],veloline.draw()[3]);
        
        
        
        gc.setColor(Color.black);
        gc.drawOval(redx, redy, (int)((red/5)*2), (int)((red/5)*2));
        gc.drawOval(greenx,greeny,(int)((green/5)*2),(int)((green/5)*2));
        gc.drawOval(bluex,bluey,(int)((blue/5)*2),(int)((blue/5)*2));
        
    }
    public void keyPressed(KeyEvent evt){
        switch(evt.getKeyCode()){
            case KeyEvent.VK_W:
                bool_W2 = true;
                bool_W = true;
                bool_S = false;
            break;
            case KeyEvent.VK_U:
                if(red<255){
                red +=15;
                
                
                redx -= 3;
                redy -= 3;}
                
                if(red>255){red = 255;}
                
            break;
            case KeyEvent.VK_N:
                aimline.getAngle().setDeg(90);
                
            break;
            case KeyEvent.VK_M:
                aimline.getAngle().setDeg(180);
                
            break;
            case KeyEvent.VK_I:
                if(green<255){
                green +=15;
                
                
                greenx -= 3;
                greeny -= 3;}
                
                if(green>255){green = 255;}
                
            break;
            case KeyEvent.VK_O:
                if(blue<255){
                blue +=15;
                
                
                bluex -= 3;
                bluey -= 3;}
                
                if(blue>255){blue = 255;}
            break;
            
            case KeyEvent.VK_J:
                if(red>0){
                red -=15;
                
                redx += 3;
                redy +=3;
                }
                if(red<0){red = 0;}
            break;
            case KeyEvent.VK_K:
                if(green>0){
                green -=15;
                
                greenx += 3;
                greeny +=3;
                }
                if(green<0){green = 0;}
            break;
            case KeyEvent.VK_L:
                if(blue>0){
                blue -=15;
                
                bluex += 3;
                bluey +=3;
                }
                if(blue<0){blue = 0;}
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
            case KeyEvent.VK_E:
                player1.setTo(new Coord(200,200));
                player1.getVec().recalc(new Coord(200,200), new Angle(0),0.0);
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
            break;
        }
    }
    Angle a = new Angle();
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
        ///*
        cloud.add(new Sprite(location, life+(int)(Math.random()*10), velocity+(Math.random()*3), heading+((Math.random()*10)-5), size, r, b, 0));
        cloud.add(new Sprite(location,life+(int)(Math.random()*10),velocity+(Math.random()*3),heading+5+(Math.random()*10),size,0,b,g));
        cloud.add(new Sprite(location,life+(int)(Math.random()*10),velocity+(Math.random()*3),heading-5-(Math.random()*10),size,r,0,g));
        //*/
        /*
        cloud.add(new Sprite(location, life, velocity, heading, size, r, b, g));
        cloud.add(new Sprite(location,life,velocity/3,heading+90,size,r,b,g));
        cloud.add(new Sprite(location,life,velocity/3,heading-90,size,r,b,g));
        //*/
        
        Line temp = new Line(player1.getLoc(), new Angle(heading - 180), velocity);
        player1.addVelo(temp.getRun()*0.05, temp.getRise()*0.05);
        player1.getVec().recalc(player1.getLoc(), new Angle(heading - 180),velocity);
       
    }
    
    public void Move(){
        boolean collided = false;
        for(int x = 0;x<edge.length();x++){
            player1.getInt().recalc(player1.getVec() , edge.lines[x]);
            if (player1.getInt().exists){
                break;
            }
            if(x == edge.length()-1){
                player1.live();
            }
        }
        if(bool_SPACE == true){
            createSprite(player1,55,5,(int)aimline.getAngle().getDeg(),5,red,blue,green);
        }
        if (bool_collide == false){
            if (bool_A == true){
                player1.setX(player1.getX() - 2);
            }else if(bool_D == true){
                player1.setX(player1.getX() + 2);
            }
            if (bool_W == true){
                player1.setY(player1.getY() - 2);
            }else if(bool_S == true){
                player1.setY(player1.getY() + 2);
            }
            aimline.moveTo(player1);

            
            if(bool_DOWN == true){
                aimline.rotate(new Angle(-15));
                test.getVec().rotate(new Angle(-15));
            }
            if(bool_UP == true){
                aimline.rotate(new Angle(15));
                test.getVec().rotate(new Angle(15));
            }
        }
    }
    public void run() {
        aimangle.setDeg(0);
        aimupangle.setDeg(5);
        aimdownangle.setDeg(-5);
        aimline = new Line(player1,aimangle,50.0);
        testint = new Intersect(aimline, edge.lines[0]);
        add3.Accel(add2);
        test.live();
        while(true){
            Move();
            for(int x = 0;x<cloud.size();x++){
                cloud.get(x).live();
                if(cloud.get(x).getLife()<=0){
                    cloud.remove(x);
                }
            }
            repaint();
            try{
                Thread.sleep(33);
            }catch(InterruptedException ex){
            } 
        }
    } 
}
           
