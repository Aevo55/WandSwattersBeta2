package MainPackage;
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

public class Game extends JPanel implements Runnable { 
    Functions out = new Functions();
    double x = 1;
    int y = 1;
    Sprite test = new Sprite(new Coord(100,100),1000,4,45,10,100,200,50);
    
    boolean
        bool_right = false, //If right arrow is pressed (can be overwritten by other keys)
        bool_left = false, //If left arrow is pressed (can be overwritten by other keys)
        bool_right2 = false, //If right arrow is pressed (cannot be overwritten by other keys)
        bool_left2 = false, //If left arrow is pressed (cannot be overwritten by other keys)
        bool_up = false,
        bool_up2 = false,
        bool_down = false,
        bool_down2 = false,
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
        gc.fillRect(0, 0, 405, 405);
        gc.setColor(Color.BLACK);
        gc.fillRect(0, 0, 405, 5);
        gc.fillRect(0, 0, 5, 405);
        gc.fillRect(0, 395, 405, 5);
        gc.fillRect(395, 0, 5, 405);
        for (int x = 0; x<m1.nets.length;x++){
            for (int y = 0; y<m1.nets[x].coords.length;y++){
                gc.fillOval((int)m1.nets[x].coords[y].getX() - 2, (int)m1.nets[x].coords[y].getY() - 2, 4, 4);
                gc.drawLine((int)m1.nets[x].lines[y].getX1(), (int)m1.nets[x].lines[y].getY1(), (int)m1.nets[x].lines[y].getX2(), (int)m1.nets[x].lines[y].getY2());
            }
        }
        
        gc.setColor(Color.red);
        gc.drawLine(phor.draw()[0],phor.draw()[1],phor.draw()[2],phor.draw()[3]);
        gc.setColor(Color.BLUE);
        gc.fillOval((int)player.getX()-1,(int)player.getY()-1,2,2);
        gc.drawLine((int)anchor.getX1(),(int)anchor.getY1(),(int)anchor.getX2(),(int)anchor.getY2());
        gc.drawLine(aimline.draw()[0],aimline.draw()[1],aimline.draw()[2],aimline.draw()[3]);
        Color spritecol = new Color(test.red,test.blue,test.green);
        gc.setColor(spritecol);
        
        if(test.getLife()>0)gc.fillOval((int)test.getLoc().getX(), (int)test.getLoc().getY(), test.size, test.size);
        
    }
    public void keyPressed(KeyEvent evt){
        switch(evt.getKeyCode()){
            case KeyEvent.VK_W:
                bool_up2 = true;
                bool_up = true;
                bool_down = false;
            break;
            case KeyEvent.VK_S:
                bool_down = true;
                bool_down2 = true;
                bool_up = false;
            break;
            case KeyEvent.VK_A:
                bool_left2 = true;
                bool_left = true;
                bool_right = false;
            break;
            case KeyEvent.VK_D:
                bool_right = true;
                bool_right2 = true;
                bool_left = false;
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
        }
    }

    public void keyReleased(KeyEvent evt){
        switch(evt.getKeyCode()){
            case KeyEvent.VK_W:
                bool_up = false;
                bool_up2 = false;
                if (bool_down2 == true){
                    bool_down = true;
                }
            break;
            case KeyEvent.VK_S:
                bool_down = false;
                bool_down2 = false;
                if (bool_up2 == true){
                    bool_up = true;
                }
            break;
            case KeyEvent.VK_A:
                bool_left = false;
                bool_left2 = false;
                if (bool_right2 == true){
                    bool_right = true;
                }
            break;
            case KeyEvent.VK_D:
                bool_right = false;
                bool_right2 = false;
                if (bool_left2 == true){
                    bool_left = true;
                }
            break;
            case KeyEvent.VK_Q:
                for(int x = 0; x<m1.nets[1].lines.length;x++){
                    out.sysout(m1.nets[1].lines[x].getAngle().getDeg());
                }
            break;
            case KeyEvent.VK_I:
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
        }
    }
    public void Move(){
        /*
        if (x > 50 || x < -50){
            y = -y;
        }
        x = x + (1 * y);
        out.out(x);
        ang.setDeg(x);
        phor.rotateBy(anchor, phor.p1, ang);
        //*/
        if (bool_collide == false){
            if (bool_left == true){
                prevplayer.setX(player.getX());
                player.setX(player.getX() - 2);
                phor.moveBy(-2, 0);
            }else if(bool_right == true){
                prevplayer.setX(player.getX());
                player.setX(player.getX() + 2);
                phor.moveBy(2, 0);
            }
            if (bool_up == true){
                prevplayer.setY(player.getY());
                player.setY(player.getY() - 2);
                phor.moveBy(0, 2);
            }else if(bool_down == true){
                prevplayer.setY(player.getY());
                player.setY(player.getY() + 2);
                phor.moveBy(0, -2);
            }
            aimline.moveTo(player);
            
            
            if(bool_aimdown == true){
                System.out.println("UP");
                aimline.rotate(aimupangle);
                test.vector.rotate(new Angle(-20));
            }
            if(bool_aimup == true){
                System.out.println("DOWN");
                aimline.rotate(aimdownangle);
                test.vector.rotate(new Angle(20));
            }
        }
        
    }
    public void run() {
        aimangle.setDeg(0);
        aimupangle.setDeg(5);
        aimdownangle.setDeg(-5);
        aimline = new Line(player,aimangle,25.0);
        
        while(true){
            Move();
            test.live();
            repaint();
            try{
                Thread.sleep(33);
            }catch(InterruptedException ex){
            } 
        }
    }
}
