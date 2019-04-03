public class Player
{
    double x;
    double y;
    double start_x;
    int is_alive;
    int rotate;
    int player;
    String picture;
    
    public Player(double x, double y, int player)
    {
        this.x = x;
        this.y = y;
        start_x = x;
        is_alive = 1;
        rotate = 0;
        this.player = player;
        picture = "Banana.png";
    }
    
    public void Draw()
    {
        StdDraw.picture(x, y, picture);
    }
    
    public void Move_left()
    {
        if(x > 148)
            x -= 2;
    }
    
    public void Move_right()
    {
        if(x < 852)
            x += 2;
    }
    
    public void Shot()
    {
        
    }
}