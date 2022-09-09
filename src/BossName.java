import java.awt.Color;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.applet.*;

public class BossName extends Boss 
{
    private int attackHelper;
    private long nextHit;
    private long time;
    private boolean isDead;
    
    public BossName()
    {
        super(325, 200, 67000, 1300);
        attackHelper = 0;
        time = System.currentTimeMillis();
        nextHit = System.currentTimeMillis();
        isDead = false;
    }
    
    public boolean isDead()
    {
        return isDead;
    }
    
    public boolean canTakeDamage()
    {
        if(time >= nextHit)
            return true;
        return false;
    }
    
    public void takeDamage()
    {
        super.setHP(getHP() - 1);
    }
    
    public void updateHit()
    {
        time = System.currentTimeMillis();
    }
    public void checkDead()
    {
        if(getHP() < 0 && isDead == false){
            super.setDeathTime(System.currentTimeMillis());
            isDead = true;
        }
    }
    public void setNextHit()
    {
        nextHit = time + 50;
    }
    
    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        ImageIcon image = new ImageIcon(BossName.class.getResource("Images\\Sprite\\pic1.png"));
        g2d.drawImage(image.getImage(), getX(), getY(), getR(), getR(), null);
    }
    
    public ArrayList<Bullet> selectPattern(User user)
    {
        if(!isDead){
            if(getPhase() == 1)
                return this.phase1(user);
        }
        
        return new ArrayList<Bullet>();
    }
    
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    
    public ArrayList<Bullet> phase0(User user)
    {
        /*if(super.inTimeFrame(0, 16000))
        {
            if(super.readyFiring())
            {
                attackHelper++;
                super.setFutureTime(2300);
                return this.lasers(2 * Math.PI, Math.random() * Math.PI, attackHelper * 2, 750);
            }

        }*//*
        if(super.inTimeFrame(0, 100000000))
        {
           if(super.readyFiring())
            {
                super.setFutureTime(1000);
                return super.bomb(user);
            }
        }
        /*if(super.inTimeFrame(0, 100000000))
        {
            if(super.readyFiring())
            {
                super.setFutureTime(1000);
                attackHelper++;
                return super.bomb((Math.PI / 10) * attackHelper + Math.PI / 6);
            }
        }*/
        /*if(super.inTimeFrame(0, 1000000000))
        {
            if(super.inTimeFrame(46000, 46017))
                attackHelper = 0;
            if(super.readyFiring())
            {
                super.setFutureTime(15000);
                attackHelper++;
                return super.circleClusters(Math.PI / 60, 5, Math.PI / 6 * attackHelper);
            }
        }*//*
        if(super.inTimeFrame(0, 10000000))
        {
            if(super.readyFiring())
            {
                super.setFutureTime(5000);
                return super.invertCircle();
            }
        }
        /*if(super.inTimeFrame(0, 8000))
        {
            if(super.readyFiring())
            {
                super.setFutureTime(20);
                super.setPrevAngle(super.getPrevAngle() + Math.PI / 20);
                attackHelper++;
                if(attackHelper % 5 != 0)
                    return super.spiralSpread();
            }
        }
        /*if(super.inTimeFrame(0, 5999))
        {
            if(super.readyFiring())
            {
                super.setFutureTime(1000);
                attackHelper++;
                return this.pentagon(Math.PI / 18 * attackHelper, (6 - attackHelper) * 40);
            }
        }*/
        return new ArrayList<Bullet>();
    }
    
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    
    public ArrayList<Bullet> phase1(User user)
    {
        if(super.inTimeFrame(2000, 4000))
        {
            if(super.readyFiring())
            {
                super.setFutureTime(1500);
                return this.circle(12, 5, 2, 0.5, Math.PI / 18, 4, 3);
            }
        }
        else if(super.inTimeFrame(4000, 5000))
        {
            super.moveBottomRight(5);
        }
        else if(super.inTimeFrame(6000, 9000))
        {
            super.moveLeftMiddle(2);
            if(super.readyFiring())
            {
                super.setFutureTime(500);
                return this.targettedAOE(user, 3, 4, 2.5, 0.5, Math.PI / 20, 5, 3);
            }
        }
        else if(super.inTimeFrame(9500, 11000))
        {
            super.moveRightMiddle(3);
        }
        else if(super.inTimeFrame(11000, 13000))
        {
            if(super.readyFiring())
            {
                super.setFutureTime(2000);
                return this.circlefreeze90degChange(24, 3, 2, 0.5, 0, 1, 3);
            }
        }
        else if(super.inTimeFrame(13000, 15000))
        {
            if(super.readyFiring())
            {
                super.setFutureTime(50);
                return this.singleShotsSpirals(3.5, 0, Math.PI / 30, 7, 3); 
            }
        }
        else if(super.inTimeFrame(15000, 16500))
        {
            super.moveTopCenter(3);
            if(super.readyFiring())
            {
                super.setFutureTime(249);
                return this.circle(16, 1, 3.5, 0, 0, 11, 3);
            }
        }
        else if(super.inTimeFrame(16500, 18000))
        {
            super.moveLeftMiddle(3);
            if(super.readyFiring())
            {
                super.setFutureTime(249);
                return this.circle(16, 1, 3.5, 0, 0, 11, 3);
            }
        }
        else if(super.inTimeFrame(18000, 19500))
        {
            super.moveBottomLeft(3);
            if(super.readyFiring())
            {
                super.setFutureTime(249);
                return this.circle(16, 1, 3.5, 0, 0, 11, 3);
            }
        }
        else if(super.inTimeFrame(19500, 21000))
        {
            super.moveBottomRight(3);
            if(super.readyFiring())
            {
                super.setFutureTime(249);
                return this.circle(16, 1, 3.5, 0, 0, 11, 3);
            }
        }
        else if(super.inTimeFrame(22500, 30500))
        {
            if(super.inTimeFrame(30500, 30517))
                attackHelper = 0;
            if(super.readyFiring())
            {
                super.setFutureTime(20);
                super.setPrevAngle(super.getPrevAngle() + Math.PI / 20);
                attackHelper++;
                if(attackHelper % 5 != 0){
                    return super.spiralSpread();
                }
            }
        }
        else if(super.inTimeFrame(33500, 35500))
        {
            if(super.readyFiring())
            {   
                super.setFutureTime(500);
                attackHelper++;
                if(attackHelper % 2 == 0)
                    return super.whipLeft();
                else
                    return super.whipRight();
            }
        }
        else if(super.inTimeFrame(35500, 42500))
        {
            if(super.inTimeFrame(35500, 33517))
                attackHelper = 0;
            if(super.readyFiring())
            {       
                super.setFutureTime(120);
                super.setPrevAngle(getPrevAngle() + Math.PI / 35);
                attackHelper++;
                if(attackHelper % 25 == 8)
                    return this.circle(24, 1, 3.5, 0, 0, 6, 3);
                else
                    return this.flower();
            }
        }
        else if(super.inTimeFrame(44000, 48000))
        {
            if(super.inTimeFrame(46000, 46017))
                attackHelper = 0;
            if(super.readyFiring())
            {
                super.setFutureTime(1500);
                attackHelper++;
                return super.circleClusters(Math.PI / 60, 5, Math.PI / 4 * attackHelper);
            }
        }
        else if(super.inTimeFrame(48000, 53000))
        {
            super.moveTopCenter(1);
            if(super.readyFiring())
            {
                super.setFutureTime(4999);
                return super.invertCircle();
            }
        }
        else if(super.inTimeFrame(53000, 56000))
        {
            if(super.inTimeFrame(53000, 53000 + 1000 / 60))
                attackHelper = 0;
            
            super.moveBottomRight(2);
            if(super.readyFiring())
            {
                super.setFutureTime(1500);
                return super.bomb(user);
            }
        }
        else if(super.inTimeFrame(56000, 62000))
        {
            super.moveTopCenter(1);
            if(super.readyFiring())
            {
                super.setFutureTime(1000);
                return super.bomb(user);
            }
        }
        else if(super.inTimeFrame(66000, 82000))
        {
            if(getY() < 200)
                setY(getY() + 2);
            if(super.inTimeFrame(59000, 59000 + 1000 / 60))
                attackHelper = 0;
            if(super.readyFiring())
            {
                attackHelper++;
                super.setFutureTime(2300);
                return this.lasers(2 * Math.PI, Math.random() * Math.PI, attackHelper * 2, 750);
            }
        }
        else if(super.inTimeFrame(83000, 89000))
        {
            if(super.readyFiring())
            {
                super.setFutureTime(1100);
                attackHelper++;
                return this.pentagon(Math.PI / 18 * attackHelper, (6 - attackHelper) * 40);
            }
        }
        else if(super.inTimeFrame(93000, 96000))
        {
           if(super.inTimeFrame(91000, 91000 + 1000 / 60))
               attackHelper = 0;
           if(super.readyFiring())
           {
               super.setFutureTime(500);
                attackHelper++;
                if(attackHelper % 2 == 0)
                    return super.whipLeft();
                else
                    return super.whipRight();
           }
        }
        else if(super.inTimeFrame(96000, 106000))
        {
            if(super.readyFiring())
            {
                super.setFutureTime(5000);
                return this.verticalClimb();
            }
        }
        else if(super.inTimeFrame(112000, 10000000))
        {
            if(super.readyFiring())
            {
                super.setFutureTime(5000);
                return this.clusterBomb();
            }
        }
        
        return new ArrayList<Bullet>();
    }
    
    
}