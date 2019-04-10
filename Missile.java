public class Missile
{
    double x;
    double y;
    double start_y;
    String picture;
    GameTimer fire_rate;
    
    //Initialise the object Missile
    public Missile(double x, double y, String picture, long rate)
    {
        this.x = x;
        this.y = y;
        start_y = y;
        this.picture = picture;
        fire_rate = new GameTimer(rate);
    }
    
    //Move the missile
    public void Move()
    {
        y += 15;
    }
    
    public void MoveDown()
    {
        y -= 5;
    }
    
    //Draw the missile
    public void Draw()
    {
        StdDraw.picture(x, y, picture);
    }
}