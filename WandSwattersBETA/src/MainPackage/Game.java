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
    double x = 1;
    int y = 1;
    Sprite test = new Sprite(new Coord(100,100),Integer.MAX_VALUE,4,45,10,100,200,50);
    int red = 100;
    int green = 100;
    int blue = 100;
    
    
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
        bool_space = false,
        bool_collide = false,
        bool_aimup = false,
        bool_aimdown = false,
        bool_upPressed = false,
        bool_downPressed = false
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
    Coord player = new Coord(50,50);
    Coord prevplayer = new Coord(50,50);
    Line anchor = new Line(player, prevplayer);
    Angle aimangle = new Angle();
    Angle aimupangle = new Angle();
    Angle aimdownangle = new Angle();
    Line aimline;
    Intersect testint = new Intersect();
    Coord[] pillar2points = {
        new Coord (50,50),
        new Coord (50,60),
        new Coord (60,60),
        new Coord (60,50)
    };
    Coord[] edgepoints = {
        new Coord(0,0), 
        new Coord(0,100),
        new Coord(100,100),
        new Coord(100,0)
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
    //Net edge = new Net(edgepoints);
    Map m1 = new Map(pillar, pillar2);
    Line[] rays = new Line[m1.totalpoints()];
    Line phor = new Line(player,0.0,20);
    Line aiml = new Line(player, aim);
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
        gc.fillRect(0, 0, 505, 5);
        gc.fillRect(0, 0, 5, 505);
        gc.fillRect(0, 495, 505, 5);
        gc.fillRect(495, 0, 5, 505);
        for (int x = 0; x<m1.nets.length;x++){
            gc.drawPolygon(m1.nets[x].getXs(), m1.nets[x].getYs(), m1.nets[x].length());
            gc.fillPolygon(m1.nets[x].getXs(), m1.nets[x].getYs(), m1.nets[x].length());
            /*
            for (int y = 0; y<m1.nets[x].coords.length;y++){
                gc.fillOval((int)m1.nets[x].coords[y].getX() - 2, (int)m1.nets[x].coords[y].getY() - 2, 4, 4);
            }//*/
        }
        gc.fillOval((int)testint.getX()-1,(int)testint.getY()-1,2,2);
        gc.setColor(Color.red);
        //gc.drawLine(phor.draw()[0],phor.draw()[1],phor.draw()[2],phor.draw()[3]);
        gc.fillOval(redx, redy, (int)((red/5)*2), (int)((red/5)*2));
        gc.setColor(Color.green);
        gc.fillOval(greenx,greeny,(int)((green/5)*2),(int)((green/5)*2));
        gc.setColor(Color.BLUE);
        gc.fillOval(bluex,bluey,(int)((blue/5)*2),(int)((blue/5)*2));
        gc.fillOval((int)player.getX()-1,(int)player.getY()-1,2,2);
        //gc.drawLine((int)anchor.getX1(),(int)anchor.getY1(),(int)anchor.getX2(),(int)anchor.getY2());
        gc.drawLine(aimline.draw()[0],aimline.draw()[1],aimline.draw()[2],aimline.draw()[3]);
        Color spritecol = new Color(test.red,test.blue,test.green);
        gc.setColor(spritecol);
        //if(test.getLife()>0);
        gc.fillOval((int)test.getLoc().getX(), (int)test.getLoc().getY(), test.size, test.size);
        for(int i = 0; i < cloud.size();i++){
            gc.setColor(new Color(cloud.get(i).red,cloud.get(i).green,cloud.get(i).blue));         
            gc.fillOval((int)cloud.get(i).getLoc().getX(),(int)cloud.get(i).getLoc().getY(),cloud.get(i).size,cloud.get(i).size);
        }
        
        
        
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
                bool_upPressed = true;
                bool_aimup = true;
                bool_aimdown = false;
                
            break;
            case KeyEvent.VK_DOWN:
                bool_downPressed = true;
                bool_aimup = false;
                bool_aimdown = true;
                
                
            break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
            break;
            case KeyEvent.VK_SPACE:
                bool_space = true;
                //func.sysout(aimline.getAngle().getDeg());
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
                    //out.sysout(m1.nets[1].lines[x].getAngle().getDeg());
                }
            break;
            case KeyEvent.VK_V:
                prevplayer = player.offset(10, 10);
                phor.compRotate(anchor, prevplayer, new Angle(10));
            break;
            case KeyEvent.VK_UP:   
                bool_upPressed = false;
                bool_aimup = false;
                if(bool_downPressed == true){bool_aimdown = true;}
            break;
            case KeyEvent.VK_DOWN:
                bool_downPressed = false;
                bool_aimdown = false;
                if(bool_upPressed == true){bool_aimup = true;}
            break;
            case KeyEvent.VK_SPACE:
                bool_space = false;
            break;
        }
    }   
    
    public void createSprite(Coord location, int life, double velocity, int heading, int size, int red, int blue, int green){
        cloud.add(new Sprite(location, life, velocity, heading, size, red, blue, green));
    }
    public void Move(){
        /*
        if (x > 50 || x < -50){
            y = -y;
        }
        x = x + (1 * y);
        out.sysout(x);
        ang.setDeg(x);
        phor.rotate(ang);
        //*/
        if(bool_space == true){
            createSprite(player,25,5,(int)aimline.getAngle().getDeg(),5,red,blue,green);
        }
        if (bool_collide == false){
            if (bool_A == true){
                prevplayer.setX(player.getX());
                player.setX(player.getX() - 2);
                phor.moveBy(-2, 0);
            }else if(bool_D == true){
                prevplayer.setX(player.getX());
                player.setX(player.getX() + 2);
                phor.moveBy(2, 0);
            }
            if (bool_W == true){
                prevplayer.setY(player.getY());
                player.setY(player.getY() - 2);
                phor.moveBy(0, 2);
            }else if(bool_S == true){
                prevplayer.setY(player.getY());
                player.setY(player.getY() + 2);
                phor.moveBy(0, -2);
            }
            aimline.moveTo(player);
            testint.recalc(phor, test.vector);
            
            if(bool_aimdown == true){
                //System.out.println("DOWN");
                aimline.rotate(new Angle(-10));
                test.vector.rotate(new Angle(-10));
            }
            if(bool_aimup == true){
                //System.out.println("UP");
                aimline.rotate(new Angle(10));
                test.vector.rotate(new Angle(10));
            }
        }
        
    }
    public void run() {
        aimangle.setDeg(0);
        aimupangle.setDeg(5);
        aimdownangle.setDeg(-5);
        aimline = new Line(player,aimangle,50.0);
        
        while(true){
            Move();
            test.live();
            
            for(int x = 0;x<cloud.size();x++){
                cloud.get(x).live();
                cloud.get(x).vector.recalc(cloud.get(x).getLoc(),new Angle(cloud.get(x).getHeading()) , cloud.get(x).vector.getMag());
                if(cloud.get(x).getLife()<=0){
                    cloud.remove(x);
                }
                //func.sysout(cloud2.size());
            }
            //cloud.kill();
            repaint();
            try{
                Thread.sleep(33);
            }catch(InterruptedException ex){
            } 
        }
    }
}
