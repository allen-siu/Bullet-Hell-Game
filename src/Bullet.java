import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.ArrayList;

public class Bullet extends MovingObjects
{
    private double angle, BCRadius;
    private double spd;
    private long futureTime;
        
    private long startTime, timeSinceSpawn;
    
    private String bulletType;
    
    private int color;
    private int imageType;
    
    private int xloc;
    private int yloc;
    
    private ImageIcon bullet;
    
    public Bullet(int x, int y, int r, double angle, double spd, String bulletType, int color, int imageType)
    {
        super(x, y, r);
     
        BCRadius = 0;
        this.angle = angle;
        this.spd = spd;
        
        startTime = System.currentTimeMillis();
        timeSinceSpawn = 0;
        
        this.bulletType = bulletType;
        
        this.color = color;
        this.imageType = imageType;
        
        if(color < 10)
            bullet = new ImageIcon(Bullet.class.getResource("Images\\Bullets\\part3" + "\\tile00" + color + ".png"));
        else
            bullet = new ImageIcon(Bullet.class.getResource("Images\\Bullets\\part3" + "\\tile0" + color + ".png"));
        
        xloc = x;
        yloc = y;
    }
    
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    
    // Painting bullets
    public void paintBullet(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        
        /*ImageIcon bullet;
        if(color < 10)
            bullet = new ImageIcon(Bullet.class.getResource("Images\\Bullets\\part3" + "\\tile00" + color + ".png"));
        else
            bullet = new ImageIcon(Bullet.class.getResource("Images\\Bullets\\part3" + "\\tile0" + color + ".png"));
        */g2d.drawImage(bullet.getImage(), getX(), getY(), getR(), getR(), null);
    }
    
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    
    /*
        Accessors and Mutators
    */
    public double getAngle(){
        return angle;
    }
    public void setAngle(double angle){
        this.angle = angle;
    }
    public double getBCRadius(){
        return BCRadius;
    }
    public void setBCRadius(double r){
        this.BCRadius = r;
    }
    public double getSpd(){
        return spd;
    }
    public void setSpd(double spd){
        this.spd = spd;
    }
    public long getStartTime(){
        return startTime;
    }
    public void setStartTime(long time){
        this.startTime = time;
    }
    public long getTimeSinceSpawn(){
        return timeSinceSpawn;
    }
    public void setTimeSinceSpawn(long time){
        this.timeSinceSpawn = time;
    }
    public int getColor(){
        return color;
    }
    public void setColor(int color){
        this.color = color;
    }
    public int getImageType(){
        return imageType;
    }
    public void setImageType(int type){
        this.imageType = type;
    }
    public String getBulletType(){
        return bulletType;
    }
    public void setBulletType(String type){
        this.bulletType = type;
    }
    
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    
    /*
        Helper Methods
    */
    public boolean inTimeFrame(long start, long fin)
    {
        if(timeSinceSpawn >= start && timeSinceSpawn <= fin)
            return true;
        return false;
    }
    public boolean inTimeFrame2(long start, long fin)
    {
        if(timeSinceSpawn >= start)
            return true;
        return false;
    }
    public long getFutureTime(){
        return futureTime;
    }
    public void setFutureTime(long time){
        this.futureTime = timeSinceSpawn + time;
    }
    public boolean readyFiring()
    {
        if(timeSinceSpawn >= futureTime)
            return true;
        return false;
    }
    
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    
    /*
        Interesting Methods
    */
    // These methods help move the bullets around
    public void setXY()
    {
        setX((int)(Math.round(BCRadius * Math.cos(angle))) + getXi());  
        setY((int)(Math.round(BCRadius * Math.sin(angle))) + getYi());
    }
    public void updateBCRadius()
    {
        BCRadius += spd;
    }
    
    // This method checks if the bullet is off the screen
    public boolean outOfBounds()
    {
        if(getX() > Driver.WIDTH + 150 || getX() < -150)
            return true;
        else if(getY() > Driver.HEIGHT + 150 || getY() < -150)
            return true;
        
        return false;
    }
    
    // This method will parse the type of bullet
    public boolean isType(String type)
    {
        if(bulletType.substring(0, bulletType.indexOf("(")).equals(type))
            return true;
        return false;
    }
    
    public long parseLong(String a, String b)
    {
        return Long.parseLong((bulletType.substring(bulletType.indexOf(a) + 1, bulletType.indexOf(b))));
    }
    public double parseDouble(String a, String b)
    {
        String parse = bulletType.substring(bulletType.indexOf(a) + 1, bulletType.indexOf(b));
        return Double.parseDouble(parse);
    }
    
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    
    // This will select the type to update according to it's variables
    public ArrayList<Bullet> update()
    {
        if(bulletType.equals("normal"))
        {
            
        }
        else if(isType("freeze"))
        {
            long startTime = parseLong("(", ",");
            long timeFrozen = parseLong(",", "!");
            double spdAfter = parseDouble("!", "@");
            
            this.freeze(startTime, timeFrozen, spdAfter);
        }
        else if(isType("changeAngle"))
        {
            long timeOfChange = parseLong("(", ",");
            double angleAfter = parseDouble(",", "!");
            
            this.changeAngle(timeOfChange, angleAfter);
        }
        else if(isType("freezeChangeAngle"))
        {
            long startTime = parseLong("(", ",");
            long timeFrozen = parseLong(",", "!");
            double spdAfter = parseDouble("!", "@");
            
            long timeOfChange = parseLong("@", "#");
            double angleAfter = parseDouble("#", "$");
            
            this.changeAngle(timeOfChange, angleAfter);
            this.freeze(startTime, timeFrozen, spdAfter);
        }
        else if(isType("freezeChangeAngleRelative"))
        {
            long startTime = parseLong("(", ",");
            long timeFrozen = parseLong(",", "!");
            double spdAfter = parseDouble("!", "@");
            
            long timeOfChange = parseLong("@", "#");
            double offSet = parseDouble("#", "$");
            
            this.changeAngleRelative(timeOfChange, offSet);
            this.freeze(startTime, timeFrozen, spdAfter);
        }
        else if(isType("freezeChangeAngleExplode"))
        {
            long startTime = parseLong("(", ",");
            long timeFrozen = parseLong(",", "!");
            double spdAfter = parseDouble("!", "@");
            
            long timeOfChange = parseLong("@", "#");
            double angleAfter = parseDouble("#", "$");
            
            long start = parseLong("$", "%");
            long end = parseLong("%", "^");
            
            this.changeAngle(timeOfChange, angleAfter);
            this.freeze(startTime, timeFrozen, spdAfter);
            return this.explode(start, end);
        }
        else if(isType("pentagon"))
        {
            return this.pentagon(this.parseDouble("(", ","));
        }
        else if(isType("circleMotion"))
        {
            long time = this.parseLong("(", ",");
            return this.circleMotion(time);
        }
        else if(isType("accelerate"))
        {
            double a = this.parseDouble("(", ",");
            this.accelerate(a);
        }
        else if(isType("explode3"))
        {
            return this.explode3();
        }
        else if(isType("explodeLasers"))
        {
            long time = this.parseLong("(", ",");
            long end = this.parseLong(",", "!");
            
            return this.explodeLasers(time, end);
        }
        else if(isType("verticalClimb"))
        {
            double dir = this.parseDouble("(", ",");
            return this.verticalClimb(dir);
        }

        return new ArrayList<Bullet>();
    }
    
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    
    // At a certain time in lifespan, stop moving bullet for certain amount of time
    // then change to speed given after time passed.
    public void freeze(long startTime, long timeFrozen, double spdAfter)
    {
        if(timeSinceSpawn >= startTime && timeSinceSpawn < startTime + timeFrozen)
            spd = 0;
        else if(timeSinceSpawn > startTime + timeFrozen)
            this.spd = spdAfter;
    }
    
    // After a certain time in lifespan change the angle of the bullet
    public void changeAngle(long timeOfChange, double angleAfter)
    {
        if(timeSinceSpawn >= timeOfChange && timeSinceSpawn < timeOfChange + 17)
        {
            setXi(getX()); 
            setYi(getY());
            setBCRadius(0);
            angle = angleAfter;
        }
    }
    
    public void changeAngleRelative(long timeOfChange, double offSet)
    {
        if(timeSinceSpawn >= timeOfChange && timeSinceSpawn < timeOfChange + 1000/ 60)
        {
            setXi(getX()); 
            setYi(getY());
            setBCRadius(0);
            angle += offSet;
        }
    }
    
    public ArrayList<Bullet> explode(long start, long end)
    {
        if(this.inTimeFrame(start, end))
        {
            if(this.readyFiring()){
                this.setFutureTime(150);
                ArrayList<Bullet> output = new ArrayList<Bullet>();
                output.add(new Bullet(getCenterX(15), getCenterY(15), 15, this.angle + Math.PI, 2, "normal", this.color, this.imageType));
                output.add(new Bullet(getCenterX(15), getCenterY(15), 15, this.angle + Math.PI + Math.PI / 20, 2, "normal", this.color, this.imageType));
                output.add(new Bullet(getCenterX(15), getCenterY(15), 15, this.angle + Math.PI - Math.PI / 20, 2, "normal", this.color, this.imageType));
                return output;
            }
        }
        return new ArrayList<Bullet>();
    }
    
    public ArrayList<Bullet> explode1()
    {
        ArrayList<Bullet> output = new ArrayList<Bullet>();
        output.add(new Bullet(getCenterX(15), getCenterY(15), 15, this.angle + Math.PI, this.spd - 0.5, "normal", 4, 3));
        return output;
    }
    public ArrayList<Bullet> circleMotion(long time)
    {
        if(this.inTimeFrame(0, time))
        {
            this.angle += Math.PI / 90;
            if(this.readyFiring()){
                this.setFutureTime(750);
                
                ArrayList<Bullet> output = new ArrayList<Bullet>();
                for(int i = 0; i < 16; i++){
                    double angle = 2 * Math.PI / 16 * i;
                    output.add(new Bullet(getCenterX(20), getCenterY(20), 20, angle, 3, "normal", this.color, this.imageType));
                }
                return output;
            }
        }
        else if(this.inTimeFrame(time, time + 17))
        {
            setXi(getX());
            setYi(getY());
            this.BCRadius = 0;
            this.spd = 3;
            this.angle += Math.PI / 2;
        }
        
        
        return new ArrayList<Bullet>();
    }
    
    public ArrayList<Bullet> pentagon(double dist)
    {
        if(this.inTimeFrame(0, 1000 / 60))
        {
            setXi((int)Math.round(dist * Math.cos(angle) + getXi()));
            setYi((int)Math.round(dist * Math.sin(angle) + getYi()));
            this.angle -= Math.PI / 2;
        }
        else if(this.inTimeFrame(17, 1000))
        {
            if(this.readyFiring())
            {
                this.setFutureTime(75);
                return this.explode1();
            }
        }
        
        return new ArrayList<Bullet>();
    }
    
    public void accelerate(double acceleration)
    {
        spd += acceleration;
    }
    
    public ArrayList<Bullet> explode3()
    {
        if(this.inTimeFrame(2000, 2000 + 1000 / 60)){
            setXi(100000);
            setYi(100000);
            if(this.readyFiring()){
                this.setFutureTime(startTime);
                ArrayList<Bullet> output = new ArrayList<Bullet>();

                for(int i = 0; i < 56; i++)
                {
                    double angle = 2 * Math.PI / 56 * i;
                    output.add(new Bullet(getCenterX(15), getCenterY(15), 15, angle, 3, "normal", 8, 3));
                }
                
                
                return output;
            }
        
        }
        return new ArrayList<Bullet>();
    }
    
    public ArrayList<Bullet> explodeLasers(long time, long end)
    {
        ArrayList<Bullet> output = new ArrayList<Bullet>();
        if(this.inTimeFrame2(end + 1000 / 60,Long.MAX_VALUE))
        {
            setXi(200000000);
            setYi(200000000);
        }
        else if(this.inTimeFrame(time, time + 1000 / 60))
        {
            xloc = getX();
            yloc = getY();
            this.spd = 0;
        }
        else if(this.inTimeFrame(time + 1000 / 60, end))
        {
            output.add(new Bullet(xloc, yloc, 25, this.angle + Math.PI / 2, 20, "normal", 7, 3));
            output.add(new Bullet(xloc, yloc, 25, this.angle - Math.PI / 2, 20, "normal", 7, 3)); 
            
            return output;
        }
        else if(this.inTimeFrame(end, end + 1000 / 60))
        {
            setXi(xloc);
            setYi(yloc);
            BCRadius = 0;
            this.angle -= Math.PI / 2;
            spd = 20;
        }
                
        
        /*
        if(this.inTimeFrame2(end + 1000 / 60,Long.MAX_VALUE))
        {
            setXi(200000000);
            setYi(200000000);
        }
        else if(this.inTimeFrame2(end, end + 1000 / 60))
        {
            setXi(xloc);
            setYi(yloc);
            BCRadius = 0;
            this.angle -= Math.PI / 2;
            spd = 20;
        }
        else if(this.inTimeFrame2(time + 1000 / 60, end))
        {
            output.add(new Bullet(xloc, yloc, 25, this.angle + Math.PI / 2, 20, "normal", 7, 3));
            output.add(new Bullet(xloc, yloc, 25, this.angle - Math.PI / 2, 20, "normal", 7, 3)); 
            
            return output;
        }
        else if(this.inTimeFrame2(time, time + 1000 / 60))
        {
            xloc = getX();
            yloc = getY();
            this.spd = 0;
        }*/
        return new ArrayList<Bullet>();
    }
    
    public ArrayList<Bullet> verticalClimb(double dir)
    {
        if(dir < 0)
        {
            this.BCRadius = 300;
            if(this.inTimeFrame(1000 / 60, 15000))
            {
                angle -= Math.PI / 180;
                if(this.readyFiring())
                {
                    this.setFutureTime(250);
                    ArrayList<Bullet> output = new ArrayList<Bullet>();
                    for(int i = 0; i < 5; i++)
                    {
                        double angle = Math.PI / 7 * i + Math.PI / 7 + this.angle + Math.PI  / 2;;
                        output.add(new Bullet(getCenterX(25), getCenterY(25), 25, angle, 3, "normal", 4, this.imageType));
                    }

                    return output;
                }
            }
        }
        else 
        {
            this.BCRadius = 300;
            if(this.inTimeFrame(1000 / 60, 15000))
            {
                angle += Math.PI / 180;
                if(this.readyFiring())
                {
                    this.setFutureTime(250);
                    ArrayList<Bullet> output = new ArrayList<Bullet>();
                    for(int i = 0; i < 5; i++)
                    {
                        double angle = Math.PI / 7 * i + Math.PI / 7 + this.angle - Math.PI  / 2;
                        output.add(new Bullet(getCenterX(25), getCenterY(25), 25, angle, 3, "normal", 4, this.imageType));
                    }

                    return output;
                }
            }
        }
        
        if(this.inTimeFrame(15000, 1000000))
        {
            setX(1000000);
            setY(1000000);
        }
        
        return new ArrayList<Bullet>();
    }
}