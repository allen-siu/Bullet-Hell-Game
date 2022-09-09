import java.awt.Color;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Fairy extends Mob
{
    private String color;
    private int frame;
    
    public Fairy(int x, int y, long startTime, String path, String color)
    {
        super(x, y, 45, startTime, path);
        
        this.color = color;
        frame = 0;
    }
    
    public void paintFairy(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        if(color.equals("blue"))
        {
            for(int i = 0; i <= 3; i++)
                if(frame % 60 <= (i + 1) * 15){
                    ImageIcon image = new ImageIcon(Fairy.class.getResource("Images\\Fairy\\tile00" + i + ".png"));
                    g2d.drawImage(image.getImage(), getX(), getY(), getR(), getR(), null);
                }
        }
        else if(color.equals("red"))
        {
            for(int i = 5; i <= 8; i++)
                if(frame % 60 <= (i - 4) * 15){
                    ImageIcon image = new ImageIcon(Fairy.class.getResource("Images\\Fairy\\tile00" + i + ".png"));
                    g2d.drawImage(image.getImage(), getX(), getY(), getR(), getR(), null);
                }
        }
        else if(color.equals("green"))
        {
            for(int i = 10; i <= 13; i++)
                if(frame % 60 <= (i - 9) * 15){
                    ImageIcon image = new ImageIcon(Fairy.class.getResource("Images\\Fairy\\tile0" + i + ".png"));
                    g2d.drawImage(image.getImage(), getX(), getY(), getR(), getR(), null);
                }
        }
        else if(color.equals("yellow"))
        {
            for(int i = 16; i <= 18; i++)
                if(frame % 60 <= (i - 15) * 20){
                    ImageIcon image = new ImageIcon(Fairy.class.getResource("Images\\Fairy\\tile0" + i + ".png"));
                    g2d.drawImage(image.getImage(), getX(), getY(), getR(), getR(), null);
                }
        }
    }
    
    public void increment()
    {
        frame++;
    }
    
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    
    public ArrayList<Bullet> selectPath(User user, Boss boss)
    {
        if(!((BossName)boss).isDead()){
            if(getPath().equals("leftL"))
                return this.leftL();
            else if(getPath().equals("leftLShoot"))
                return this.leftLShoot(user);
            else if(getPath().equals("rightL"))
                return this.rightL();
            else if(getPath().equals("rightLShoot"))
                return this.rightLShoot(user);
            else if(getPath().equals("downShootSide"))
                return this.downShootSide(user);
            else if(getPath().equals("downNoShootSide"))
                return this.downNoShootSide(user);
            else if(getPath().equals("downCircleDownCircleDown"))
                return this.downCircleDownCircleDown();
            else if(getPath().equals("sideShoot"))
                return this.sideShoot(user);
            else if(getPath().equals("downSpiral"))
                return this.downSpiral();
            else if(getPath().equals("leftToRight"))
                this.leftToRight();
            else if(getPath().equals("rightToLeft"))
                this.rightToLeft();
            else if(getPath().equals("leftCircleRight"))
                this.leftCircleRight();
            else if(getPath().equals("rightCircleLeft"))
                this.rightCircleLeft();
            else if(getPath().equals("diagLeft"))
                return this.diagLeft(user);
            else if(getPath().equals("diagRight"))
                return this.diagRight(user);
        }
        
        return new ArrayList<Bullet>();
    }
    
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    
    public void leftToRight()
    {
        setX(getX() + 5);
    }
    
    public void rightToLeft()
    {
        setX(getX() - 5);
    }
    
    public void leftCircleRight()
    {
        if(super.inTimeFrame(0, 1200))
        {
            setX(getX() + 5);
            setXi(getX());
            setYi(getY());
        }
        else if(super.inTimeFrame(1200, 2800))
        {
            double angle = Math.atan2(getY() - (getYi() + 100), getX() - (getXi())) + (Math.PI / 50);
            setX(getXi() + (int)Math.round(Math.cos(angle) * 100));
            setY(getYi() + 100 + (int)Math.round(Math.sin(angle) * 100));
        }
        else if(super.inTimeFrame(2800, 100000))
            setX(getX() + 5);
    }
    
    public void rightCircleLeft()
    {
        if(super.inTimeFrame(0, 1200))
        {
            setX(getX() - 5);
            setXi(getX());
            setYi(getY());
        }
        else if(super.inTimeFrame(1200, 2800))
        {
            double angle = Math.atan2(getY() - (getYi() + 100), getX() - (getXi())) - (Math.PI / 50);
            setX(getXi() + (int)Math.round(Math.cos(angle) * 100));
            setY(getYi() + 100 + (int)Math.round(Math.sin(angle) * 100));
        }
        else if(super.inTimeFrame(2800, 100000))
            setX(getX() - 5);
    }
    
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    
    public ArrayList<Bullet> leftL()
    {
        if(super.inTimeFrame(0, 1000))
            setY(getY() + 4);
        else if(super.inTimeFrame(1000, 100000))
        {
            setX(getX() + 3);
            setY(getY() + 2);
        }
        
        return new ArrayList<Bullet>();
    }
    
    public ArrayList<Bullet> leftLShoot(User user)
    {
        if(super.inTimeFrame(0, 1000))
            setY(getY() + 5);
        else if(super.inTimeFrame(1200, 1400))
        {
            if(super.readyFiring())
            {
                super.setFutureTime(500);
                return this.targettedStream(user, 3, 2, 0.5, 12, 3);
            }
        }
        else if(super.inTimeFrame(1400, 100000))
        {
            setX(getX() + 3);
            setY(getY() + 2);
        }
        
        return new ArrayList<Bullet>();
    }
    
    public ArrayList<Bullet> rightL()
    {
        if(super.inTimeFrame(0, 1000))
            setY(getY() + 4);
        else if(super.inTimeFrame(1000, 100000))
        {
            setX(getX() - 3);
            setY(getY() + 2);
        }
        
        return new ArrayList<Bullet>();
    }
    
    public ArrayList<Bullet> rightLShoot(User user)
    {
        if(super.inTimeFrame(0, 1000))
            setY(getY() + 5);
        else if(super.inTimeFrame(1200, 1400))
        {
            if(super.readyFiring())
            {
                super.setFutureTime(500);
                return this.targettedStream(user, 3, 2, 0.5, 12, 3);
            }
        }
        else if(super.inTimeFrame(1400, 100000))
        {
            setX(getX() - 3);
            setY(getY() + 2);
        }
        
        return new ArrayList<Bullet>();
    }
    
    public ArrayList<Bullet> downShootSide(User user)
    {
        // Moving mob down
        if(super.inTimeFrame(0, 1000))
            setY(getY() + 4);
        // Making mob shoot
        else if(super.inTimeFrame(1500, 2500))
        {
            if(super.readyFiring())
            {
                super.setFutureTime(1500);
                return super.targettedStream(user, 5, 3, 0.5, 6, 3);
            }
        }
        // Moving mob off the screen
        else if(super.inTimeFrame(2500, 100000))
        {
            if(getX() - getR() / 2 > 350)
                setX(getX() + 2);
            else
                setX(getX() - 2);
        }
            
        return new ArrayList<Bullet>();
    }
    
    public ArrayList<Bullet> downNoShootSide(User user)
    {
        // Moving mob down
        if(super.inTimeFrame(0, 1000))
            setY(getY() + 4);
        // Making mob shoot
        else if(super.inTimeFrame(1500, 2500))
        {
            
        }
        // Moving mob off the screen
        else if(super.inTimeFrame(2500, 100000))
        {
            if(getX() - getR() / 2 > 350)
                setX(getX() + 2);
            else
                setX(getX() - 2);
        }
            
        return new ArrayList<Bullet>();
    }
    
    public ArrayList<Bullet> downCircleDownCircleDown()
    {
        if(super.inTimeFrame(0, 1000))
            setY(getY() + 3);
        else if(super.inTimeFrame(1500, 3000))
        {
            if(super.readyFiring())
            {
                super.setFutureTime(1500);
                return super.circle(24, 1, 2, 0, 0, 6, 3);
            }
        }
        else if(super.inTimeFrame(3000, 6000))
            setY(getY() + 2);
        else if(super.inTimeFrame(6000, 100000))
        {
            if(super.readyFiring())
            {
                super.setFutureTime(100000);
                return super.circle(24, 1, 2, 0, 0, 6, 3);
            }
            setY(getY() + 2);
        }
        return new ArrayList<Bullet>();
    }
    
    public ArrayList<Bullet> sideShoot(User user)
    {
        if(super.inTimeFrame(0, 500))
        {
            if(getX() < 350)
                setX(getX() + 3);
            else
                setX(getX() - 3);
        }
        else if(super.inTimeFrame(1000, 2000))
        {
            if(super.readyFiring())
            {
                super.setFutureTime(20000);
                return super.targettedAOE(user, 5, 2, 2, 1, Math.PI / 2, 7, 3);
            }
        }
        else if(super.inTimeFrame(6000, 1000000))
        {
            if(getX() < 350)
                setX(getX() - 3);
            else
                setX(getX() + 3);
        }
        
        return new ArrayList<Bullet>();
    }
    
    public ArrayList<Bullet> downSpiral()
    {
        if(super.inTimeFrame(0, 1000))
            setY(getY() + 5);
        else if(super.inTimeFrame(1000, 2000))
        {
            if(super.readyFiring())
            {
                super.setFutureTime(20);
                return super.singleShotsSpirals(2.5, 0, Math.PI / 10, 9, 3);
            }
        }
        else if(super.inTimeFrame(3000, 100000))
            setY(getY() - 3);
        
        return new ArrayList<Bullet>();
    }
    
    public ArrayList<Bullet> diagRight(User user)
    {
        if(super.inTimeFrame(0, 1000))
        {
            super.setX(getX() -  4);
            super.setY(getY() + 5);
        }
        else if(super.inTimeFrame(1000, 1600))
        {
            if(super.readyFiring())
            {
                super.setFutureTime(500);
                return super.targettedStream(user, 5, 3, 0.25, 11, 3);
            }
        }
        else if(super.inTimeFrame(1600, 2400))
        {
            super.setY(getY() + 5);
        }
        else if(super.inTimeFrame(2400, 3000))
        {
            if(super.readyFiring())
            {
                super.setFutureTime(1000);
                return this.circle(12, 1, 2.5, 0, 0, 14, 3);
            }
        }
        else if(super.inTimeFrame(3000, 1000000))
        {
            super.setX(getX() -  4);
            super.setY(getY() + 5);
        }
        
        return new ArrayList<Bullet>();
    }
    
    public ArrayList<Bullet> diagLeft(User user)
    {
        if(super.inTimeFrame(0, 1000))
        {
            super.setX(getX() +  4);
            super.setY(getY() + 5);
        }
        else if(super.inTimeFrame(1000, 1600))
        {
            if(super.readyFiring())
            {
                super.setFutureTime(500);
                return super.targettedStream(user, 5, 3, 0.25, 11, 3);
            }
        }
        else if(super.inTimeFrame(1600, 2400))
        {
            super.setY(getY() + 5);
        }
        else if(super.inTimeFrame(2400, 3000))
        {
            if(super.readyFiring())
            {
                super.setFutureTime(1000);
                return this.circle(12, 1, 2.5, 0, 0, 14, 3);
            }
        }
        else if(super.inTimeFrame(3000, 1000000))
        {
            super.setX(getX() +  4);
            super.setY(getY() + 5);
        }
        
        
        return new ArrayList<Bullet>();
    }
    
}