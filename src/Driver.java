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

public class Driver extends JComponent implements KeyListener, MouseListener
{
    //instance variables
    public static int WIDTH;
    public static int HEIGHT;
    
    private User user;
    private Stage stage;
    private long timePassed, startTime;
    
    //Default Constructor   
    public Driver()
    {
        //initializing instance variables
        WIDTH = 700;
        HEIGHT = 900;
        
        user = new User();
        stage = new Stage(1);
        
        startTime = System.currentTimeMillis() - 0000; //(offsetting time)
        timePassed = 0;
        
        //Setting up the GUI
        JFrame gui = new JFrame(); //This makes the gui box
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Makes sure program can close
        gui.setTitle("Touhou - Creation"); //This is the title of the game, you can change it
        gui.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT)); //Setting the size for gui
        gui.setResizable(true); //Makes it so the gui cant be resized
        gui.getContentPane().add(this); //Adding this class to the gui

         /*If after you finish everything, you can declare your buttons or other things
          *at this spot. AFTER gui.getContentPane().add(this) and BEFORE gui.pack();
          */

        gui.pack(); //Packs everything together
        gui.setLocationRelativeTo(null); //Makes so the gui opens in the center of screen
        gui.setVisible(true); //Makes the gui visible
        gui.addKeyListener(this);//stating that this object will listen to the keyboard
        gui.addMouseListener(this);//stating that this object will listen to the Mouse
        //gui.addMouseMotionListener(this); );//stating that this object will acknowledge when the Mouse moves

    }
    
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    
    //This method will acknowledge user input
    public void keyPressed(KeyEvent e) 
    {
        int key = e.getKeyCode();
        
        // This method handles user's key pressed
        user.keyPressed(key);
    }   
    public void keyReleased(KeyEvent e) 
    {
        int key = e.getKeyCode();
        
        // This method handles user's key released
        user.keyReleased(key);
    }
    
    
    //All your UI drawing goes in here
    public void paintComponent(Graphics g)
    {
        stage.paintBackground(g);
        // This paints the user
        user.paintUser(g);
        // This paints all the active mobs in the stage
        stage.paintMobs(g);
        // Painting boss
        stage.paintBoss(g, user);
        // Painting all bullets
        stage.paintBullets(g);
        stage.drawEnd(g, user);
        stage.drawVictory(g);
    }
    
        
    // All of the game logic goes here
    public void loop()
    {
        // Updates the time passed since program started
        timePassed = System.currentTimeMillis() - startTime;
        
        // This handles all the math of the player's movement
        user.handleMovement();
        user.updateUserBullets();
        user.shoot();
        user.updateTime();
        
        // Updating the Stage mobs
        stage.updateActiveMobs(timePassed);
        stage.updateMobTimes(timePassed);
        stage.moveMobs(user);
        
        // Moving Bullets
        stage.updateBulletTimes();
        stage.moveBullets();
        
        // Methods to handle stage's boss
        stage.updatebossActive(timePassed);
        stage.updateBossInfo(user, timePassed);
        stage.checkMobKilled(user.getBullets());    
        stage.damageBoss(user.getBullets());
        stage.userDead(user);
        
        if(!((BossName)stage.getBoss()).isDead() || user.getHealth() < 1){
            if(stage.collision(user)){
                user.takeDamage();
            }   
        }
        
        //Do not write below this
        repaint();
    }
    
    
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    
    //These methods are required by the compiler.  
    //You might write code in these methods depending on your goal.
    public void keyTyped(KeyEvent e) 
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
    public void mouseDragged(MouseEvent e   )
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
        Driver g = new Driver();
        g.start(60);
    }
}
