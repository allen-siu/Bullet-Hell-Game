import java.util.ArrayList;

public class Mob extends MovingObjects
{
    // These variables deal with the spawn time and behavior of the mob
    private long startTime;
    private long timeSinceSpawn;
    
    // These variables deal with shooting
    private boolean isFiring;
    private long futureTime;
    private double prevAngle;
    
    private int health;
    private long time;
    private long nextHit;
    
    private String path;
    
    public Mob(int x, int y, int r, long startTime, String path)
    {
        super(x, y, r);
        
        this.startTime = startTime;
        this.timeSinceSpawn = 0;
        
        isFiring = false;
        futureTime = 0;
        prevAngle = 0;
        health = 1000;
        
        this.path = path;
        
        time = System.currentTimeMillis();
        nextHit = System.currentTimeMillis();
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
    
    public ArrayList<Bullet> selectPath(User user)
    {
        return new ArrayList<Bullet>();
    }
    
    public void takeDamage()
    {
        health -= 50;
    }
    public boolean dead()
    {
        if(health <= 0)
            return true;
        return false;
    }
    
    
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    
    // Variable startTime a/m
    public long getStartTime(){
        return startTime;
    }
    public void setStartTime(long time){
        this.startTime = time;
    }
    
    // Variable timeSinceSpawn a/m
    public long getTimeSinceSpawn(){
        return timeSinceSpawn;
    }
    public void setTimeSinceSpawn(long time){
        this.timeSinceSpawn = time;
    }
    
    // Variable isFiring a/m
    public boolean isFiring(){
        return isFiring;
    }
    public void setFiring(boolean firing){
        isFiring = firing;
    }
    
    // Variable futureTime a/m
    public long getFutureTime(){
        return futureTime;
    }
    public void setFutureTime(long time){
        this.futureTime = timeSinceSpawn + time;
    }
    
    // Variable prevAngle a/m
    public double getPrevAngle(){
        return prevAngle;
    }
    public void setPrevAngle(double angle){
        this.prevAngle = angle;
    }
    
    // Variable Path a/m
    public String getPath(){
        return path;
    }
    public void setPath(String path){
        this.path = path;
    }
    
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    
    /*
        Helper Methods
    */
    public boolean readyFiring()
    {
        if(timeSinceSpawn >= futureTime)
            return true;
        return false;
    }
    public boolean inTimeFrame(long start, long fin)
    {
        if(timeSinceSpawn >= start && timeSinceSpawn < fin)
            return true;
        return false;
    }
    
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    
    /*
        Any attacks put here
    */
    public ArrayList<Bullet> targettedStream(User user, int bullets, double spd, double changeSpd, int color, int imageType)
    {
        ArrayList<Bullet> output = new ArrayList<Bullet>();
        double angle = Math.PI + Math.atan2((getY() + getH()) - (user.getY() + user.getH()), (getX() + getW()) - (user.getX() + user.getW()));
        
        for(int i = 0; i < bullets; i++)
        {
            output.add(new Bullet(getCenterX(25), getCenterY(25), 25, angle, spd, "normal", color, imageType));
            spd += changeSpd;
        }
        
        return output;
    }
    
    public ArrayList<Bullet> circle(int bullets, int layers, double spd, double changeSpd, double angleOffset, int color, int imageType)
    {
        ArrayList<Bullet> output = new ArrayList<Bullet>();
        
        for(int j = 0; j < layers; j++)
            for(int i = 0; i < bullets; i++)
            {
                double angle = (2 * Math.PI / (bullets)) * i + (angleOffset * j);
                output.add(new Bullet(getCenterX(25), getCenterY(25), 25, angle, spd + changeSpd * j, "normal", color, imageType));
            }
        
        return output; 
    }
    
    public ArrayList<Bullet> targettedAOE(User user, int bullets, int layers, double spd, double changeSpd, double angleSpread, int color, int imageType)
    {
        ArrayList<Bullet> output = new ArrayList<Bullet>();
        
        for(int i = 0; i < layers; i++)
        {
            for(int j = 0; j < bullets; j++)
            {
                double angle = Math.PI + Math.atan2((getY() + getH()) - (user.getY() + user.getH()), (getX() + getW()) - (user.getX() + user.getW()))
                        - (angleSpread / 2) + (angleSpread / (bullets - 1) * j);
                output.add(new Bullet(getCenterX(25), getCenterY(25), 25, angle, spd, "normal", color, imageType));
            }
            spd += changeSpd;
        }
        
        return output;
    }
    
    public ArrayList<Bullet> singleShotsSpirals(double spd, double startAngle, double angleChange, int color, int imageType)
    {
        ArrayList<Bullet> output = new ArrayList<Bullet>();
        
        output.add(new Bullet(getCenterX(25), getCenterY(25), 25, startAngle + prevAngle, spd, "normal", color, imageType));
        prevAngle += angleChange;
        
        return output;
    }
    
    
}