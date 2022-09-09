import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.applet.*;
import java.awt.Color;

public class Stage
{
    private ArrayList<Mob> waitingMobs;
    private ArrayList<Mob> activeMobs;
    private ArrayList<Bullet> activeBullets;
    private Boss boss;
    private boolean upsideDown;
    private AudioClip bgm;
    private boolean userDead;
    private boolean showHUD;
    
    // waiting bullets / active bullest
    // separate variable for boss
    
    public Stage(int stage)
    {
        waitingMobs = new ArrayList<Mob>();
        activeMobs = new ArrayList<Mob>();
        activeBullets = new ArrayList<Bullet>();
                upsideDown = true;
        bgm = Applet.newAudioClip(this.getClass().getResource("BGM\\stage4.wav"));
        bgm.play();
        userDead = false;
        showHUD = true;
        
        if(stage == 0)  // Stage 0 is a test stage
            stageTest();
        else if(stage == 1)
            stage1();
    }
    
    public void userDead(User user)
    {
        if(user.getHealth() < 1 && !userDead){
            userDead = true;
            user.setDeathTime(System.currentTimeMillis());
        }
    }
    
    public void drawVictory(Graphics g)
    {
        if(boss.getHP() < 0 && System.currentTimeMillis() - boss.getDeathTime() < 13500 && System.currentTimeMillis() - boss.getDeathTime() > 8000){
            Graphics2D g2d = (Graphics2D)g; 
            ImageIcon image = new ImageIcon(Stage.class.getResource("OpeningScreen\\win1.gif"));
            g2d.drawImage(image.getImage(), -20, -20, Driver.WIDTH + 40, Driver.HEIGHT + 40, null);
        }
        else if(boss.getHP() < 0 && System.currentTimeMillis() - boss.getDeathTime() > 9500){
            Graphics2D g2d = (Graphics2D)g; 
            ImageIcon image = new ImageIcon(Stage.class.getResource("OpeningScreen\\win2.png"));
            g2d.drawImage(image.getImage(), -20, -20, Driver.WIDTH + 40, Driver.HEIGHT + 40, null);
        }
    }
    
    public void paintBackground(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g; 
        ImageIcon image = new ImageIcon(Stage.class.getResource("Images\\Background\\bg1.gif"));
        g2d.drawImage(image.getImage(), -20, -20, Driver.WIDTH + 40, Driver.HEIGHT + 40, null);
    }
    
    public void drawEnd(Graphics g, User user)
    {
        if(userDead && System.currentTimeMillis() - user.getDeathTime() > 5500 && System.currentTimeMillis() - user.getDeathTime() < 11000){
            Graphics2D g2d = (Graphics2D)g;
            ImageIcon image = new ImageIcon(Stage.class.getResource("OpeningScreen\\end4.gif"));
            g2d.drawImage(image.getImage(), -20, -20, Driver.WIDTH + 40, Driver.HEIGHT + 40, null);
        }
        else if(userDead && System.currentTimeMillis() - user.getDeathTime() > 11000){
            Graphics2D g2d = (Graphics2D)g;
            ImageIcon image = new ImageIcon(Stage.class.getResource("OpeningScreen\\end8.png"));
            g2d.drawImage(image.getImage(), -20, -20, Driver.WIDTH + 40, Driver.HEIGHT + 40, null);
        }
    }
    
    public static double distance(int x1, int x2, int y1, int y2)
    {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }
    
    public boolean collision(User user)
    {
        if(user.canGetHit()){
            int uCx = user.getCenterX(0);
            int uCy = user.getCenterY(0);
            int uR = user.getR();

            for(int i = 0; i < activeBullets.size(); i++)
            {
                Bullet b = activeBullets.get(i);
                if(distance(uCx, b.getCenterX(0), uCy, b.getCenterY(0)) < (uR + b.getR()) / 2){
                    user.updateNextHit(3000);
                    return true;
                }
            }
        }
        return false;
    }
    
    // Set Stage upsideDown
    // Dream is dead
    public void setUpsideDown()
    {
        if(upsideDown)
        {
            for(Bullet b : activeBullets)
            {
                b.setX(700 - b.getX());
                b.setY(900 - b.getY());
            }

            boss.setX(700 - boss.getX());
            boss.setY(900 - boss.getY());
        } 
    }
    
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    
    /*
        Methods to help spawn mobs / bullets
    */
    public ArrayList<Mob> getActiveMobs(){
        return activeMobs;
    }
    public Boss getBoss(){
        return boss;
    }

    // This method adds the next ready mobs to the field
    public void updateActiveMobs(long timePassed)
    {
        while(nextMobReady(timePassed))
            activeMobs.add(waitingMobs.remove(0));
    }
    // This method checks if the next mob is ready to be painted and deployed
    public boolean nextMobReady(long timePassed)
    {
        if(waitingMobs.size() > 0)
            if(timePassed >= waitingMobs.get(0).getStartTime())
                return true;
        return false;
    }
    
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    
    /*
        Methods to update stuff
    */
    public void paintMobs(Graphics g)
    {
        for(int i = 0; i < activeMobs.size(); i++){
            Mob mob = activeMobs.get(i);    
            if(mob instanceof Fairy){
                ((Fairy)mob).increment();
                ((Fairy)mob).paintFairy(g);
            }
        }
    }
    public void updateMobTimes(long timePassed)
    {
        for(Mob mob : activeMobs){
            mob.setTimeSinceSpawn(timePassed - mob.getStartTime());
            mob.updateTime();
        }
    }
    public void moveMobs(User user)
    {
        for(Mob mob : activeMobs)
            for(Bullet b : ((Fairy)mob).selectPath(user, boss))
                activeBullets.add(b);
    }
    public void checkMobKilled(ArrayList<Bullet> bullets)
    {
        for(int i = 0; i < bullets.size(); i++)
        {
            Bullet b = bullets.get(i);
            for(int j = 0; j < activeMobs.size(); j++){
                Mob mob = activeMobs.get(j);
                if(mob.canGetHit()){
                    if(distance(mob.getCenterX(0), b.getCenterX(0), mob.getCenterY(0), b.getCenterY(0)) < (mob.getR() + b.getR()) / 2){
                        mob.takeDamage();
                        mob.updateNextHit(50);
                        if(mob.dead()){
                            activeMobs.remove(mob);
                            bullets.remove(b);
                            i--;
                            j--;
                        }
                    }
                }
            }
        }
    }
    
    //===========================================================================
    //===========================================================================
    //===========================================================================
    //===========================================================================
    //===========================================================================
    
    public void moveBullets()
    {
        for(int i = 0; i < activeBullets.size(); i++)
        {
            Bullet b = activeBullets.get(i);
            if(b.outOfBounds())
            {
                activeBullets.remove(b);
                i--;
            }
            else
            {
                activeBullets.addAll(b.update());
                b.updateBCRadius();
                b.setXY();
            }
        }
    }
    public void updateBulletTimes()
    {
        for(Bullet b : activeBullets)
            b.setTimeSinceSpawn(System.currentTimeMillis() - b.getStartTime());
    }
    public void paintBullets(Graphics g)
    {
        for(int i = 0; i < activeBullets.size(); i++){
            Bullet b = activeBullets.get(i);
            b.paintBullet(g);
        }     
    }
    
    //===========================================================================
    //===========================================================================
    //===========================================================================
    //===========================================================================
    //===========================================================================
    
    public void paintBoss(Graphics g, User user)
    {
        if(boss.isActive())
        {
            ((BossName)boss).paint(g);
            
            if(user.showHUD()){
                g.setColor(Color.black);
                g.fillRect(300, 0, 600, 90);
                g.setColor(Color.GRAY);
                g.fillRect(320, 15, 305, 60);

                if(boss.getHP() > 1300 / 4 * 3)
                    g.setColor(Color.GREEN);
                else if(boss.getHP() > 1300 / 2)
                    g.setColor(Color.YELLOW);
                else if(boss.getHP() > 1300 / 4)
                    g.setColor(Color.ORANGE);
                else
                    g.setColor(Color.RED);

                g.fillRect(320 + (305 - (int)((double)boss.getHP() / 1300 * 305)), 15, (int)((double)boss.getHP() / 1300 * 305), 60);

                Graphics2D g2d = (Graphics2D)g;
                ImageIcon image = new ImageIcon(Stage.class.getResource("Images\\Sprite\\pic1.png"));
                g2d.drawImage(image.getImage(), 630, 20, 50, 50, null);
            }
        }
    }
    public void updatebossActive(long timePassed)
    {
        if(timePassed >= boss.getStartTime() && timePassed <= boss.getStartTime() + 100){
            boss.setActive(true);
        }
    }
    public void updateBossInfo(User user, long timePassed)
    {
        if(boss.isActive())
        {
            for(Bullet b : boss.selectPattern(user))
                activeBullets.add(b);
            boss.setTimeSinceSpawn(timePassed - boss.getStartTime());
            ((BossName)boss).updateHit();
            ((BossName)boss).checkDead();
        }
    }
    public void damageBoss(ArrayList<Bullet> bullets)
    {
        if(((BossName)boss).canTakeDamage()){
            for(int i = 0; i < bullets.size(); i++){
                Bullet b = bullets.get(i);
                if(distance(boss.getCenterX(0), b.getCenterX(0), boss.getCenterY(0), b.getCenterY(0)) < (boss.getR() + b.getR()) / 2){
                    ((BossName)boss).takeDamage();
                    ((BossName)boss).setNextHit();
                    bullets.remove(b);
                    i--;
                }
            }
        }
    }
    
    //===========================================================================
    //===========================================================================
    //===========================================================================
    //===========================================================================
    //===========================================================================
    
    /*
        Stage creation
    */
    public void stageTest()
    {
        boss = new BossName();
        waitingMobs.add(new Fairy(-30, -30, 10000, "diagLeft", "red"));
        waitingMobs.add(new Fairy(700, -30, 10500, "diagRight", "red"));
        waitingMobs.add(new Fairy(-30, -30, 11000, "diagLeft", "red"));
        waitingMobs.add(new Fairy(700, -30, 11500, "diagRight", "red"));
        waitingMobs.add(new Fairy(-30, -30, 12000, "diagLeft", "red"));
        waitingMobs.add(new Fairy(700, -30, 12500, "diagRight", "red"));
        waitingMobs.add(new Fairy(-30, -30, 13000, "diagLeft", "red"));
    }
    
    public void stage1()
    {
        boss = new BossName();
        
        // Wave 1
        waitingMobs.add(new Fairy(100, -30, 10000, "leftL", "blue"));
        waitingMobs.add(new Fairy(150, -45, 10150, "leftL", "blue"));
        waitingMobs.add(new Fairy(100, -30, 10300, "leftL", "blue"));
        waitingMobs.add(new Fairy(150, -45, 10450, "leftL", "blue"));
        
        waitingMobs.add(new Fairy(120, -30, 10800, "leftL", "green"));
        waitingMobs.add(new Fairy(170, -45, 11000, "leftL", "green"));
        
        waitingMobs.add(new Fairy(100, -30, 11350, "leftL", "blue"));
        waitingMobs.add(new Fairy(150, -45, 11500, "leftL", "blue"));
        waitingMobs.add(new Fairy(100, -30, 11650, "leftL", "blue"));
        waitingMobs.add(new Fairy(150, -45, 11800, "leftL", "blue"));
        
        // Wave 2
        waitingMobs.add(new Fairy(570, -30, 14000, "rightL", "red"));
        waitingMobs.add(new Fairy(520, -45, 14150, "rightL", "red"));
        waitingMobs.add(new Fairy(570, -30, 14300, "rightL", "red"));
        waitingMobs.add(new Fairy(520, -45, 14450, "rightL", "red"));
        
        waitingMobs.add(new Fairy(550, -30, 14800, "rightL", "blue"));
        waitingMobs.add(new Fairy(500, -45, 15000, "rightL", "blue"));
        
        waitingMobs.add(new Fairy(570, -30, 15350, "rightL", "red"));
        waitingMobs.add(new Fairy(520, -45, 15500, "rightL", "red"));
        waitingMobs.add(new Fairy(570, -30, 15650, "rightL", "red"));
        waitingMobs.add(new Fairy(520, -45, 15800, "rightL", "red"));
        
        // Wave 3
        waitingMobs.add(new Fairy(100, -30, 18000, "leftLShoot", "yellow"));
        waitingMobs.add(new Fairy(150, -45, 18150, "leftL", "yellow"));
        waitingMobs.add(new Fairy(100, -60, 18300, "leftL", "yellow"));
        waitingMobs.add(new Fairy(150, -75, 18450, "leftLShoot", "yellow"));
        
        waitingMobs.add(new Fairy(120, -90, 18800, "leftL", "green"));
        waitingMobs.add(new Fairy(170, -105, 19000, "leftL", "green"));
        
        
        waitingMobs.add(new Fairy(570, -120, 19350, "rightLShoot", "blue"));
        waitingMobs.add(new Fairy(520, -135, 19500, "rightL", "blue"));
        waitingMobs.add(new Fairy(570, -150, 19650, "rightL", "blue"));
        waitingMobs.add(new Fairy(520, -165, 19800, "rightLShoot", "blue"));
        
        // Wave 4
        waitingMobs.add(new Fairy(570, -30, 22000, "rightLShoot", "red"));
        waitingMobs.add(new Fairy(520, -45, 22150, "rightL", "red"));
        waitingMobs.add(new Fairy(570, -60, 22300, "rightL", "red"));
        waitingMobs.add(new Fairy(520, -75, 22450, "rightLShoot", "red"));
        
        waitingMobs.add(new Fairy(550, -90, 22800, "rightL", "yellow"));
        waitingMobs.add(new Fairy(500, -105, 23000, "rightL", "yellow"));
        
        waitingMobs.add(new Fairy(100, -120, 23350, "leftLShoot", "green"));
        waitingMobs.add(new Fairy(150, -135, 23500, "leftL", "green"));
        waitingMobs.add(new Fairy(100, -150, 23650, "leftL", "green"));
        waitingMobs.add(new Fairy(150, -165, 23800, "leftLShoot", "green"));
        
        // Wave 5
        waitingMobs.add(new Fairy(700, 200, 27000, "rightCircleLeft", "red"));
        waitingMobs.add(new Fairy(700, 200, 27200, "rightCircleLeft", "red"));
        waitingMobs.add(new Fairy(700, 200, 27400, "rightCircleLeft", "red"));
        waitingMobs.add(new Fairy(700, 200, 27600, "rightCircleLeft", "red"));
        waitingMobs.add(new Fairy(700, 200, 27800, "rightCircleLeft", "red"));
        waitingMobs.add(new Fairy(700, 200, 28000, "rightCircleLeft", "red"));
        
        // Wave 6
        waitingMobs.add(new Fairy(100, -30, 31000, "downCircleDownCircleDown", "blue"));
        waitingMobs.add(new Fairy(570, -30, 31000, "downCircleDownCircleDown", "green"));
        
        // Wave 7
        waitingMobs.add(new Fairy(200, -30, 37000, "downNoShootSide", "blue"));
        waitingMobs.add(new Fairy(300, -30, 37400, "downNoShootSide", "blue"));
        waitingMobs.add(new Fairy(500, -30, 37800, "downNoShootSide", "blue"));
        waitingMobs.add(new Fairy(600, -30, 38200, "downShootSide", "red"));
        waitingMobs.add(new Fairy(100, -30, 38600, "downShootSide", "red"));
        waitingMobs.add(new Fairy(400, -30, 39000, "downShootSide", "red"));
        
        // Wave 8
        waitingMobs.add(new Fairy(-30, 200, 41000, "leftCircleRight", "green"));
        waitingMobs.add(new Fairy(-30, 200, 41200, "leftCircleRight", "green"));
        waitingMobs.add(new Fairy(-30, 200, 41400, "leftCircleRight", "green"));
        waitingMobs.add(new Fairy(-30, 200, 41600, "leftCircleRight", "green"));
        waitingMobs.add(new Fairy(-30, 200, 41800, "leftCircleRight", "green"));
        waitingMobs.add(new Fairy(-30, 200, 42000, "leftCircleRight", "green"));
        
        // Wave 9
        waitingMobs.add(new Fairy(-30, 100, 45000, "sideShoot", "yellow"));
        waitingMobs.add(new Fairy(730, 250, 45750, "sideShoot", "green"));
        waitingMobs.add(new Fairy(-30, 400, 46500, "sideShoot", "blue"));
        
        // Wave 10
        waitingMobs.add(new Fairy(700, 200, 50000, "rightCircleLeft", "blue"));
        waitingMobs.add(new Fairy(700, 200, 50200, "rightCircleLeft", "blue"));
        waitingMobs.add(new Fairy(700, 200, 50400, "rightCircleLeft", "blue"));
        waitingMobs.add(new Fairy(700, 200, 50600, "rightCircleLeft", "blue"));
        waitingMobs.add(new Fairy(700, 200, 50800, "rightCircleLeft", "blue"));
        waitingMobs.add(new Fairy(700, 200, 51000, "rightCircleLeft", "blue"));
        
        // Wave 11
        waitingMobs.add(new Fairy(100, -30, 53000, "downShootSide", "green"));
        waitingMobs.add(new Fairy(335, -30, 53300, "downSpiral", "yellow"));
        waitingMobs.add(new Fairy(570, -30, 53600, "downShootSide", "green"));
        
        // Wave 12
        waitingMobs.add(new Fairy(-30, -30, 55000, "diagLeft", "red"));
        waitingMobs.add(new Fairy(700, -30, 55500, "diagRight", "red"));
        waitingMobs.add(new Fairy(-30, -30, 56000, "diagLeft", "red"));
        waitingMobs.add(new Fairy(700, -30, 56500, "diagRight", "red"));
        waitingMobs.add(new Fairy(-30, -30, 57000, "diagLeft", "red"));
        waitingMobs.add(new Fairy(700, -30, 57500, "diagRight", "red"));
        waitingMobs.add(new Fairy(-30, -30, 58000, "diagLeft", "red"));
        
        // Wave 13
        waitingMobs.add(new Fairy(-30, 400, 62000, "sideShoot", "blue"));
        waitingMobs.add(new Fairy(570, -30, 62000, "downCircleDownCircleDown", "green"));
        waitingMobs.add(new Fairy(335, -30, 62000, "downSpiral", "yellow"));
    }
}