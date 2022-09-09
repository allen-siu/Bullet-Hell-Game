import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Font;
//imports for drawing Images
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

import java.applet.*;


public class OpeningScreen extends JComponent implements KeyListener, MouseListener, MouseMotionListener
{
    //instance variables
    private int WIDTH;
    private int HEIGHT;
    private JFrame gui;
    private long timePassed, startTime;
    private boolean keys;
    private boolean credits;
    private AudioClip bgm;
    
    //Default Constructor
    public OpeningScreen()
    {
        //initializing instance variables
        WIDTH = 800;
        HEIGHT = 600;
        
        startTime = System.currentTimeMillis();
        timePassed = 0;
        keys = false;
        credits = false;
        
        bgm = Applet.newAudioClip(this.getClass().getResource("BGM\\opening.wav"));
        bgm.loop();
        
        //Setting up the GUI
        gui = new JFrame(); //This makes the gui box    
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Makes sure program can close
        gui.setTitle("Touhou - Creation"); //This is the title of the game, you can change it
        gui.setPreferredSize(new Dimension(WIDTH + 5, HEIGHT + 30)); //Setting the size for gui
        gui.setResizable(false); //Makes it so the gui cant be resized
        gui.getContentPane().add(this); //Adding this class to the gui

         /*If after you finish everything, you can declare your buttons or other things
          *at this spot. AFTER gui.getContentPane().add(this) and BEFORE gui.pack();
          */

        gui.pack(); //Packs everything together
        gui.setLocationRelativeTo(null); //Makes so the gui opens in the center of screen
        gui.setVisible(true); //Makes the gui visible
        gui.addKeyListener(this);//stating that this object will listen to the keyboard
        gui.addMouseListener(this); //stating that this object will listen to the Mouse
        gui.addMouseMotionListener(this); //stating that this object will acknowledge when the Mouse moves

    }
    //This method will acknowledge user input
    public void keyPressed(KeyEvent e) 
    {
        int key = e.getKeyCode();
        
        if(key != -3858979 && credits){
            Driver g = new Driver();
            g.start(60);
            bgm.stop();
            gui.dispose();
        }
        else if(key != -342341 && !keys)
        {
            keys = true;
        }
        else if(key != 3942048 && !credits)
        {
            credits = true;
        }
    }   
    //All your UI drawing goes in here
    public void paintComponent(Graphics g)
    {
        if(!keys){
            Graphics2D g2d = (Graphics2D)g;
            ImageIcon image = new ImageIcon(Stage.class.getResource("OpeningScreen\\bg.png"));
            g2d.drawImage(image.getImage(), -10, -10, WIDTH + 20, HEIGHT + 20, null);

            Font f = new Font("Mermaid", Font.PLAIN, 60);
            g.setColor(Color.WHITE);
            g.setFont(f);
            g.drawString("Touhou - Creation ", 80, 140);


            if(timePassed % 1500 < 750)
            {
                Font fe = new Font("Mermaid", Font.PLAIN, 50);
                g.setFont(fe);
                g.drawString("Press any key to continue", 200, HEIGHT - 55);
            }
        }
        else if(keys && !credits)
        {
            Graphics2D g2d = (Graphics2D)g;
            ImageIcon image = new ImageIcon(Stage.class.getResource("OpeningScreen\\bg.png"));
            g2d.drawImage(image.getImage(), -10, -10, WIDTH + 20, HEIGHT + 20, null);

            Font f = new Font("Mermaid", Font.PLAIN, 60);
            g.setColor(Color.WHITE);
            g.setFont(f);
            g.drawString("How to Play and Controls", 80, 140);
            
            Font f2 = new Font("Mermaid", Font.PLAIN, 30);
            g.setFont(f2);
            g.setColor(Color.DARK_GRAY);
            g.drawString("WASD  -  Movement", 80, 220);
            g.drawString("J  -  Fire Bullets", 80, 260);
            g.drawString("K  -  Toggle HUD", 80, 300);
            g.drawString("Shift  -  Slow Movement", 80, 340);
            
            g.drawString("This        is your hitbox", 80, 490);
            
            ImageIcon bullet = new ImageIcon(Stage.class.getResource("Images\\Bullets\\part3\\tile008.png"));
            g2d.drawImage(bullet.getImage(), 147, 465, 30, 30, null);
            
            g.drawString("Survive the barrage of bullets", 80, 390);
            g.drawString("and defeat Reimu", 120, 430);

            if(timePassed % 1500 < 750)
            {
                Font fe = new Font("Mermaid", Font.PLAIN, 50);
                g.setFont(fe);
                g.setColor(Color.WHITE);
                g.drawString("Press any key to continue", 200, HEIGHT - 55);
            }
        }
        else if(credits)
        {
            Graphics2D g2d = (Graphics2D)g;
            ImageIcon image = new ImageIcon(Stage.class.getResource("OpeningScreen\\bg.png"));
            g2d.drawImage(image.getImage(), -10, -10, WIDTH + 20, HEIGHT + 20, null);

            Font f = new Font("Mermaid", Font.PLAIN, 60);
            g.setColor(Color.WHITE);
            g.setFont(f);
            g.drawString("Credits", 80, 140);
            
            Font f2 = new Font("Mermaid", Font.PLAIN, 30);
            g.setFont(f2);
            g.setColor(Color.DARK_GRAY);
            g.drawString("Victory / End Screen  -  Kimchi-", 80, 300);
            g.drawString("Sprites  -  Arnie", 80, 260);
            g.drawString("Ideas and Debugging  -  Mr. Suriel", 80, 220);
            g.drawString("Game Concept  -  Touhou Project", 80, 340);
            
            if(timePassed % 1500 < 750)
            {
                Font fe = new Font("Mermaid", Font.PLAIN, 50);
                g.setFont(fe);
                g.setColor(Color.WHITE);
                g.drawString("Press any key to continue", 200, HEIGHT - 55);
            }
        }
    }
    public void loop()
    {
        timePassed = System.currentTimeMillis() - startTime;
        
        //Do not write below this
        repaint();
    }
    //These methods are required by the compiler.  
    //You might write code in these methods depending on your goal.
    public void keyTyped(KeyEvent e) 
    {
    }
    public void keyReleased(KeyEvent e) 
    {
    }
    public void mousePressed(MouseEvent e)
    {
    }
    public void mouseReleased(MouseEvent e)
    {
    }
    public void mouseClicked(MouseEvent e)
    {
    }
    public void mouseEntered(MouseEvent e)
    {
    }
    public void mouseExited(MouseEvent e)
    {
    }
    public void mouseMoved(MouseEvent e)
    {
    }
    public void mouseDragged(MouseEvent e)
    {
    }
    public void start(final int ticks){
        Thread gameThread = new Thread(){
            public void run(){
                while(true){
                    loop();
                    try{
                        Thread.sleep(1000 / ticks);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };	
        gameThread.start();
    }

    public static void main(String[] args)
    {
        OpeningScreen g = new OpeningScreen();
        g.start(60);
    }
}
