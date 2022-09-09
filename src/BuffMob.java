import java.util.ArrayList;

public class BuffMob extends Mob
{
    public BuffMob(int x, int y, long startTime, String path)
    {
        super(x, y, 30, startTime, path);
    }
    
    public ArrayList<Bullet> selectPath(User user)
    {
        if(getPath().equals("leftToRight"))
            this.leftToRight();
        else if(getPath().equals("rightToLeft"))
            this.rightToLeft();
        else if(getPath().equals("leftCircleRight"))
            this.leftCircleRight();
        else if(getPath().equals("rightCircleLeft"))
            this.rightCircleLeft();
        
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
}