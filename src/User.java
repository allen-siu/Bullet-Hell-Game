import java.awt.Color;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.applet.*;


public class User extends MovingObjects
{
    private int spdX, spdY;
    private char moveVertical, moveHorizontal;
    public ArrayList<Bullet> bullets;
    public boolean shoot;
    private long time;
    private long futureTime;
    private long nextHit;
    private int health;
    private boolean showHUD;
    private boolean slowMove;
    private long deathTime;
    
    public User()
    {
        // Spawns user in the bottom middle of screen as a circle
        super(Driver.WIDTH / 2 - 10, Driver.HEIGHT - 150 - 10, 20);
        spdX = 0;
        spdY = 0;
        bullets = new ArrayList<Bullet>();
        shoot = false;
        showHUD = false;
        slowMove = false;
        health = 100000;
        deathTime = 0;
        
        time = System.currentTimeMillis();
        nextHit = System.currentTimeMillis();
    }
    public boolean isDead()
    {
        if(health < 1)
            return true;
        return false;
    }
    public boolean showHUD(){
        return showHUD;
    }
    public int getHealth()
    {
        return health;
    }
    public long getDeathTime()
    {
        return this.deathTime;
    }
    public void setDeathTime(long time)
    {
        deathTime = time;
    }
    
    public void takeDamage()
    {
        health--;
    }
    public boolean canGetHit()
    {
        if(time >= nextHit)
            return true;
        return false;
    }
    public void updateTime()
    {
        time = System.currentTimeMillis();
    }
    public void updateNextHit(long time)
    {
        nextHit = this.time + time;
    }
    
    public void handleMovement()
    {
        // Movement in x-direction
        if(!isDead()){
            if(getX() + spdX < 0)
                setX(0);
            else if(getX() + getR() + spdX > Driver.WIDTH)
                setX(Driver.WIDTH - getR());
            else
                setX(getX() + spdX);

            // Movement in the y-direction
            if(getY() + spdY < 0)
                setY(0);
            else if(getY() + getR() + spdY > Driver.HEIGHT)
                setY(Driver.HEIGHT - getR());
            else
                setY(getY() + spdY);
        }
    }
    
    public void keyPressed(int key)
    {
        // Key = W
        if(key == 87){
            moveVertical = 'W';
            spdY = -7;
            if(slowMove)
                spdY = -3;
        }
        // Key = S
        if(key == 83){
            moveVertical = 'S';
            spdY = 7;
            if(slowMove)
                spdY = 3;
        }
        // Key = A
        if(key == 65){
            moveHorizontal = 'A';
            spdX = -7;
            if(slowMove)
                spdX = -3;
        }
        // Key = D
        if(key == 68){
            moveHorizontal = 'D';
            spdX = 7;
            if(slowMove)
                spdX = 3;
        }
        
        // Key = J
        if(key == 74)
        {
            shoot = true;
        }
        // key = K
        if(key == 75)
        {
            showHUD = true;
        }
        if(key == 16)
            slowMove = true;
    }
     
    public void keyReleased(int key)
    {
        // Key = W
        if(key == 87 && moveVertical == 'W')
            spdY = 0;
        // Key = S
        if(key == 83 && moveVertical == 'S')
            spdY = 0;
        // Key = A
        if(key == 65 && moveHorizontal == 'A')
            spdX = 0;
        // Key = D
        if(key == 68 && moveHorizontal == 'D')
            spdX = 0;
        
        if(key == 74){
            shoot = false;
        }
        if(key == 75)
            showHUD = false;
        if(key == 16)
            slowMove = false;
    }
    
    public void paintUser(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        ImageIcon image = new ImageIcon(User.class.getResource("Images\\Sprite\\pic2.png"));
        ImageIcon hitbox = new ImageIcon(User.class.getResource("Images\\Bullets\\part3\\tile008.png"));
        //g2d.drawImage(image.getImage(), getX() - 20, getY() - 20, getR() + 40, getR() + 40, null);
        if(time < nextHit)
        {
            if((nextHit - time) % 500 < 250)
                g2d.drawImage(image.getImage(), getX() - 20, getY() - 20, getR() + 40, getR() + 40, null);
        }
        else{
            g2d.drawImage(image.getImage(), getX() - 20, getY() - 20, getR() + 40, getR() + 40, null);
        }
        g2d.drawImage(hitbox.getImage(), getX(), getY(), getR(), getR(), null);
        
        for(int i = 0; i < bullets.size(); i++)
        {
            Bullet b = bullets.get(i);
            ImageIcon bullet = new ImageIcon(User.class.getResource("Images\\Bullets\\part4\\tile006.png"));
            g2d.drawImage(bullet.getImage(), b.getX(), b.getY(), b.getR(), b.getR(), null);
        }
        
        if(showHUD)
        {
            ImageIcon heart1 = new ImageIcon(User.class.getResource("Images\\Sprite\\heart1.png"));
            ImageIcon heart2 = new ImageIcon(User.class.getResource("Images\\Sprite\\heart2.png"));
            
            if(health == 3){
                g2d.drawImage(image.getImage(), 20, 20, 50, 50, null);
                g2d.drawImage(heart1.getImage(), 90, 25, 40, 40, null);
                g2d.drawImage(heart1.getImage(), 140, 25, 40, 40, null);
                g2d.drawImage(heart1.getImage(), 190, 25, 40, 40, null);
            }
            else if(health == 2){
                g2d.drawImage(image.getImage(), 20, 20, 50, 50, null);
                g2d.drawImage(heart1.getImage(), 90, 25, 40, 40, null);
                g2d.drawImage(heart1.getImage(), 140, 25, 40, 40, null);
                g2d.drawImage(heart2.getImage(), 190, 25, 40, 40, null);
            }
            else if(health == 1){
                g2d.drawImage(image.getImage(), 20, 20, 50, 50, null);
                g2d.drawImage(heart1.getImage(), 90, 25, 40, 40, null);
                g2d.drawImage(heart2.getImage(), 140, 25, 40, 40, null);
                g2d.drawImage(heart2.getImage(), 190, 25, 40, 40, null);
            }
            else if(health < 1){
                g2d.drawImage(image.getImage(), 20, 20, 50, 50, null);
                g2d.drawImage(heart2.getImage(), 90, 25, 40, 40, null);
                g2d.drawImage(heart2.getImage(), 140, 25, 40, 40, null);
                g2d.drawImage(heart2.getImage(), 190, 25, 40, 40, null);
            }
        }
    }
    
    public void shoot()
    {
        if(!isDead()){
            if(time >= futureTime)
                if(shoot){
                    bullets.add(new Bullet(getCenterX(30) - 20 - 17, getCenterY(30), 30, Math.PI / 2 * 3, 15, "normal", 6, 4));
                    bullets.add(new Bullet(getCenterX(30) + 20 + 17, getCenterY(30), 30, Math.PI / 2 * 3, 15, "normal", 6, 4));
                    futureTime = time + 50;
                }
        }
    }
    
    public void updateUserBullets()
    {
        for(int i = 0; i < bullets.size(); i++)
        {
            Bullet b = bullets.get(i);
            if(b.outOfBounds())
            {
                bullets.remove(b);
                i--;
            }
            else
            {
                bullets.addAll(b.update());
                b.updateBCRadius();
                b.setXY();
            }
        }
    }
    
    public ArrayList<Bullet> getBullets()
    {
        return bullets;
    }
}