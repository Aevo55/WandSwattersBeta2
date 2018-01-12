package MainPackage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//author Dawson
//updated

public class Main extends JFrame{
    private JPanel border = null;
    private JPanel jContentPane = null;
    private Game playarea = null;
    private Game getPanel() {
        if (playarea == null) {
            playarea = new Game();
        }
        return playarea;
    }
    private void formKeyPressed(KeyEvent evt){
        playarea.keyPressed(evt);
    }
    private void formKeyReleased(KeyEvent evt){
        playarea.keyReleased(evt);
    }
    public Main() {
        super(); //This calls the constructor of the parent class JFrame
       initialize();
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(KeyEvent evt) {
                formKeyReleased(evt);
            }

            });
        } 
    public static void main(String[] args) {
        {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    Main thisClass = new Main();
                    thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
                    thisClass.setVisible(true); 
                }
            });
        }
    }
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getPanel(), BorderLayout.CENTER);
        }
        return jContentPane;
    }
    private void initialize() {
        this.setResizable(false);
        //this.setUndecorated(true);
        this.setBounds(new Rectangle(312, 184, 500, 500));
        this.setMinimumSize(new Dimension(500, 500));
        this.setMaximumSize(new Dimension(500, 500));
        this.setContentPane(getJContentPane());
        this.setTitle("Wand Swatters BETA");
    }
}
