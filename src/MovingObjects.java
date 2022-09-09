public class MovingObjects
{
    private int x, y, w, h;
    private int r;
    private int xi, yi;
    
    public MovingObjects(int x, int y, int w, int h)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        
        // Initial X and Y position
        xi = x;
        yi = y;
    }
    
    public MovingObjects(int x, int y, int r)
    {
        this.x = x;
        this.y = y;
        this.r = r;
        
        xi = x;
        yi = y;
    }
    
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getW(){
        return w;
    }
    public int getH(){
        return h;
    }
    public int getR(){
        return r;
    }
    public int getXi(){
        return xi;
    }
    public int getYi(){
        return yi;
    }
    
    
    public int getCenterX(int size){
        return x + (r / 2) - (size / 2);
    }
    public int getCenterY(int size){
        return y + (r / 2) - (size / 2);
    }
    
    
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public void setW(int w){
        this.w = w;
    }
    public void setH(int h){
        this.h = h;
    }
    public void setR(int r){
        this.r = r;
    }
    public void setXi(int x){
        this.xi = x;
    }
    public void setYi(int y){
        this.yi = y; 
    }
}