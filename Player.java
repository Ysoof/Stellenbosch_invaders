public class Player
{
    double x;
    double y;
    double start_x;
    int is_alive;
    int rotate;
    int player;
    String png;
    
    public Player(double x, double y, int player)
    {
        this.x = x;
        this.y = y;
        start_x = x;
        is_alive = 1;
        rotate = 0;
        this.player = player;
        png = "Player_im.png"
    }
    
    public Draw()
    {
        StdDraw.picture(x, y, png);
    }
}