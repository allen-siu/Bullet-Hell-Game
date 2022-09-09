import java.util.ArrayList;

public class Boss extends Mob
{
    private boolean isActive;
    private int phase;
    private double hp;
    private int atkCounter;
    private long deathTime;
    
    public Boss(int x, int y, long startTime, double hp)
    {
        super(x, y, 50, startTime, "");
        
        this.hp = hp;
        isActive = false;
        phase = 1;
        atkCounter = 0;
        deathTime = 0;
    }
    
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    
    public long getDeathTime(){
        return this.deathTime;
    }
    public void setDeathTime(long time){
        this.deathTime = time;
    }
    
    public boolean isActive(){
        return isActive;
    }
    public void setActive(boolean active){
        this.isActive = active;
    }
    
    public int getPhase(){
        return phase;
    }
    public void setPhase(int phase){
        this.phase = phase;
    }
    
    public double getHP(){
        return hp;
    }
    public void setHP(double hp){
        this.hp = hp;
    }
    
    public int getAtkCounter(){
        return atkCounter;
    }
    public void setCounter(int count){
        this.atkCounter = count;
    }
    
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    
    public ArrayList<Bullet> selectPattern(User user)
    {
        return new ArrayList<Bullet>();
    }
    
    public static double distance(int x1, int x2, int y1, int y2)
    {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
    
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    
    // TopCenter: 325, 100
    public void moveTopCenter(double spd)
    {
        double angle = Math.atan2(getY() - 100, getX() - 325);
        if(distance(325, getX(), 100, getY()) > 75)
        {
            setX(getX() - (int)Math.round(Math.cos(angle) * spd));
            setY(getY() - (int)Math.round(Math.sin(angle) * spd));
        }
        else if(getX() > 330 || getX() < 320 || getY() > 100)
        {
            if(getX() > 330 || getX() < 320)
                setX(getX() - (int)Math.round(Math.cos(angle) * 3));
            if(getY() > 100)
                setY(getY() - (int)Math.round(Math.sin(angle) * 3));
        }
    }
    
    // LeftMiddle: 200, 190
    public void moveLeftMiddle(double spd)
    {
        double angle = Math.atan2(getY() - 190, getX() - 200);
        if(distance(200, getX(), 190, getY()) > 75)
        {
            setX(getX() - (int)Math.round(Math.cos(angle) * spd));
            setY(getY() - (int)Math.round(Math.sin(angle) * spd));
        }
        else if(getX() > 200 || getY() > 195 || getY() < 185)
        {
            if(getX() > 200)
                setX(getX() - (int)Math.round(Math.cos(angle) * 3));
            if(getY() > 195 || getY() < 185)
                setY(getY() - (int)Math.round(Math.sin(angle) * 3));
        }
    }
    
    // RightMiddle: 450, 190
    public void moveRightMiddle(double spd)
    {
        double angle = Math.atan2(getY() - 190, getX() - 450);
        if(distance(450, getX(), 190, getY()) > 75)
        {
            setX(getX() - (int)Math.round(Math.cos(angle) * spd));
            setY(getY() - (int)Math.round(Math.sin(angle) * spd));
        }
        else if(getX() < 450 || getY() > 195 || getY() < 185)
        {
            if(getX() < 450)
                setX(getX() - (int)Math.round(Math.cos(angle) * 3));
            if(getY() > 195 || getY() < 185)
                setY(getY() - (int)Math.round(Math.sin(angle) * 3));
        }
    }
    
    // BottomLeft: 250, 310
    public void moveBottomLeft(double spd)
    {
        double angle = Math.atan2(getY() - 310, getX() - 250);
        if(distance(250, getX(), 310, getY()) > 75)
        {
            setX(getX() - (int)Math.round(Math.cos(angle) * spd));
            setY(getY() - (int)Math.round(Math.sin(angle) * spd));
        }
        else if(getX() > 255 || getX() < 245 || getY() < 310)
        {
            if(getX() > 255 || getX() < 245)
                setX(getX() - (int)Math.round(Math.cos(angle) * 3));
            if(getY() < 310)
                setY(getY() - (int)Math.round(Math.sin(angle) * 3));
        }
    }
    
    // BottomRight: 400, 310
    public void moveBottomRight(double spd)
    {
        double angle = Math.atan2(getY() - 310, getX() - 400);
        if(distance(400, getX(), 310, getY()) > 75)
        {
            setX(getX() - (int)Math.round(Math.cos(angle) * spd));
            setY(getY() - (int)Math.round(Math.sin(angle) * spd));
        }
        else if(getX() > 405 || getX() < 395 || getY() < 310)
        {
            if(getX() > 405 || getX() < 395)
                setX(getX() - (int)Math.round(Math.cos(angle) * 3));
            if(getY() < 310)
                setY(getY() - (int)Math.round(Math.sin(angle) * 3));
        }
    }
    
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    //==========================================================================
    
    public ArrayList<Bullet> circlefreeze90degChange(int bullets, int layers, double spd, double changeSpd, double angleOffset, int color, int imageType)
    {
        ArrayList<Bullet> output = new ArrayList<Bullet>();
        
        for(int j = 0; j < layers; j++)
            for(int i = 0; i < bullets; i++)
            {
                double angle = (2 * Math.PI / (bullets - 1)) * i + (angleOffset * j);
                String type = "freezeChangeAngle(1000,750!" + (spd + changeSpd * j - 0.5) + "@1750#" + (angle - Math.PI / 2) + "$";
                output.add(new Bullet(getCenterX(25), getCenterY(25), 25, angle, spd + changeSpd * j, type, color, imageType));
            }
        
        return output; 
    }
    
    public ArrayList<Bullet> whipLeft()
    {
        ArrayList<Bullet> output = new ArrayList<Bullet>();
        
        for(int i = 0; i < 12; i++)
        {
            double angle = Math.PI / 12 * i;
            output.add(new Bullet(getCenterX(25), getCenterY(25), 25, angle, 2 + 0.5 * i, "normal", 0, 3));
        }
        return output;
    }
    
    public ArrayList<Bullet> whipRight()
    {
        ArrayList<Bullet> output = new ArrayList<Bullet>();
        
        for(int i = 0; i < 12; i++)
        {
            double angle = Math.PI / 12 * i;
            output.add(new Bullet(getCenterX(25), getCenterY(25), 25, angle, 2 + 0.5 * (11 - i), "normal", 0, 3));
        }
        return output;
    }
    
    public ArrayList<Bullet> pentagon(double angleOffset, double dist)
    {
        ArrayList<Bullet> output = new ArrayList<Bullet>();
        
        for(int i = 1; i <= 5; i++)
        {
            double angle = 2 * Math.PI / 5 * i + angleOffset;
            output.add(new Bullet(getCenterX(25), getCenterY(25), 25, angle, 2, "pentagon(" + dist + ",", 8, 3));
        }
        
        output.add(new Bullet(getCenterX(25), getCenterY(25), 25, Math.PI, 0, "circleMotion(9000,", 5, 3));
        output.get(output.size() - 1).setBCRadius(250);
        
        return output;
    }
    
    public ArrayList<Bullet> flower()
    {
        ArrayList<Bullet> output = new ArrayList<Bullet>();
        
        for(int i = 0; i < 6; i ++)
        {
            double angle = 2 * Math.PI / 6 * i + getPrevAngle();
            output.add(new Bullet(getCenterX(20), getCenterY(20), 20, angle, 3, "normal", 7, 3));
            double angle2 =  2 * Math.PI / 6 * i - getPrevAngle();
            output.add(new Bullet(getCenterX(20), getCenterY(20), 20, angle2, 3, "normal", 7, 3));
        }
        
        return output;
    }
    
    public ArrayList<Bullet> orbitOrbs()
    {
        ArrayList<Bullet> output = new ArrayList<Bullet>();
        
        output.add(new Bullet(getCenterX(25), getCenterY(25), 25, Math.PI, 0, "circleMotion(15000,", 5, 3));
        output.add(new Bullet(getCenterX(25), getCenterY(25), 25, 0, 0, "circleMotion(15000,", 5, 3));
        
        return output;
    }
    
    public ArrayList<Bullet> spiralSpread()
    {
        ArrayList<Bullet> output = new ArrayList<Bullet>();
        
        String type1 = "freezeChangeAngleRelative(1000,500!2@1500#" + (Math.PI / 2) + "$";
        String type2 = "freezeChangeAngleRelative(1000,500!2@1500#" + (Math.PI / 2 * -1) + "$";
        
        output.add(new Bullet(getCenterX(15), getCenterY(15), 15, super.getPrevAngle(), 3.5, type1, 6, 3));
        output.add(new Bullet(getCenterX(15), getCenterY(15), 15, super.getPrevAngle(), 3.5, type2, 7, 3));
        
        return output;
    }
    
    public ArrayList<Bullet> invertCircle()
    {
        ArrayList<Bullet> output = new ArrayList<Bullet>();
        
        for(int i = 0; i < 32; i++)
        {
            double angle = 2 * Math.PI / 32 * i;
            output.add(new Bullet(getCenterX(15), getCenterY(15), 15, angle, 4, "accelerate(-0.03,", 13, 3));
            output.add(new Bullet(getCenterX(15) - 200, getCenterY(15) + 150, 15, angle, 4, "accelerate(-0.03,", 13, 3));
            output.add(new Bullet(getCenterX(15) + 200 + 15, getCenterY(15) + 150, 15, angle, 4, "accelerate(-0.03,", 13, 3));
            output.add(new Bullet(getCenterX(15) - 200, getCenterY(15) - 150, 15, angle, 4, "accelerate(-0.03,", 13, 3));
            output.add(new Bullet(getCenterX(15) + 200 + 15, getCenterY(15) - 150, 15, angle, 4, "accelerate(-0.03,", 13, 3));
        }
        
        return output;
    }
    
    public ArrayList<Bullet> circleClusters(double spacing1, int perCluster, double offSet)
    {
        ArrayList<Bullet> output = new ArrayList<Bullet>();
        int bullets = 65;
        double spacing = ((2 * Math.PI) - ((bullets - 1) * spacing1)) / (bullets / perCluster); 
        
        for(int i = 0; i < bullets; i++)
        {
            double angle = (spacing1 * i) + (spacing * (i / perCluster)) + offSet;
            
            int color =  i % 2 + 13;
            output.add(new Bullet(getCenterX(20), getCenterY(20), 20, angle, 3.5, "normal", color, 3));
        }
        
        return output;
    }
    
    public ArrayList<Bullet> bomb(User user)
    {
        ArrayList<Bullet> output = new ArrayList<Bullet>();
        
        double angle1 = Math.atan2((user.getY() - getY()), (user.getX() - getX()));
        double angle = Math.PI / 2 * Math.random() + Math.PI / 4;
        output.add(new Bullet(getCenterX(50), getCenterY(50), 50, angle, 2, "explode3(", 8, 3));
        
        return output;
    }
    
    public ArrayList<Bullet> lasers(double angleRange, double angleOffSet, int bullets, long time)
    {
        ArrayList<Bullet> output = new ArrayList<Bullet>();
        
        for(int i = 0; i < bullets; i++)
        {
            double angle = angleRange / bullets * i + angleOffSet;
            output.add(new Bullet(getCenterX(25), getCenterY(25), 25, angle, 6, "explodeLasers(" + time + ",3000!", 7, 3));
        }
        
        return output;
    }
    
    public ArrayList<Bullet> verticalClimb()
    {
        ArrayList<Bullet> output = new ArrayList<Bullet>();
        
        output.add(new Bullet(getCenterX(35), getCenterY(35), 35, Math.PI / 2, 0, "verticalClimb(1,", 5, 3));
        output.add(new Bullet(getCenterX(35), getCenterY(35), 35, Math.PI / 2, 0, "verticalClimb(-1,", 5, 3));
        
        return output;
    }
    
    public ArrayList<Bullet> clusterBomb()
    {
        ArrayList<Bullet> output = new ArrayList<Bullet>();
        
        for(int i = 0; i < 5; i++)
        {
            double angle = Math.PI / 4 * i;
            output.add(new Bullet(getCenterX(50), getCenterY(50), 50, angle, 2, "explode3(", 8, 3));
        }
        
        return output;
    }
}