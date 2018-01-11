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
    Point aim = new Point(10,10);
    Point player = new Point(50,50);
    Point prevplayer = new Point(50,50);
    Line anchor = new Line(player, prevplayer);
    Angle aimangle = new Angle();
    Angle aimupangle = new Angle();
    Angle aimdownangle = new Angle();
    Line aimline;
    
    Point[] pillar2points = {
        new Point (50,50),
        new Point (50,60),
        new Point (60,60),
        new Point (60,50)
    };
    Point[] edgepoints = {
        new Point(0,0), 
        new Point(0,100),
        new Point(100,100),
        new Point(100,0)
    };
    Point[] pillarpoints = {
        new Point(20,20), 
        new Point(20,40),
        new Point(30,50),
        new Point(40,40),
        new Point(50,30),
        new Point(40,20)
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
            for (int y = 0; y<m1.nets[x].points.length;y++){
                gc.fillOval(m1.nets[x].points[y].x - 2, m1.nets[x].points[y].y - 2, 4, 4);
                gc.drawLine((int)m1.nets[x].lines[y].x1, (int)m1.nets[x].lines[y].y1, (int)m1.nets[x].lines[y].x2, (int)m1.nets[x].lines[y].y2);
            }
        }
        
        gc.setColor(Color.red);
        gc.drawLine(phor.draw()[0],phor.draw()[1],phor.draw()[2],phor.draw()[3]);
        gc.setColor(Color.BLUE);
        gc.fillOval(player.x-1,player.y-1,2,2);
        gc.drawLine((int)anchor.x1,(int)anchor.y1,(int)anchor.x2,(int)anchor.y2);
        gc.drawLine(aimline.draw()[0],aimline.draw()[1],aimline.draw()[2],aimline.draw()[3]);
        
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
                    out.out(m1.nets[1].lines[x].angle.getDeg());
                }
            break;
            case KeyEvent.VK_I:
                prevplayer.y = 10;
                prevplayer.x = 10;
                ang.setDeg(10);
                phor.rotateBy(anchor, prevplayer, ang);
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
                prevplayer.x = player.x;
                player.x = player.x - 2;
                phor.moveBy(-2, 0);
            }else if(bool_right == true){
                prevplayer.x = player.x;
                player.x = player.x + 2;
                phor.moveBy(2, 0);
            }
            if (bool_up == true){
                prevplayer.y = player.y;
                player.y = player.y - 2;
                phor.moveBy(0, 2);
            }else if(bool_down == true){
                prevplayer.y = player.y;
                player.y = player.y + 2;
                phor.moveBy(0, -2);
            }
            aimline.moveTo(player.x, player.y);
            
            
            if(bool_aimup == true){
                System.out.println("UP");
                aimline.simpleRotate(aimupangle);
            }
            if(bool_aimdown == true){
                System.out.println("DOWN");
                aimline.simpleRotate(aimdownangle);
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
            repaint();
            try{
                Thread.sleep(33);
            }catch(InterruptedException ex){
            } 
        }
    }
}
