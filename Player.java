public class Player
{
    double x;
    double y;
    double start_x;
    int is_alive;
    int rotate;
    int lives;
    int player;
    String picture;
    
    //Initialize the object Player
    public Player(double x, double y, int player, int lives)
    {
        this.x = x;
        this.y = y;
        start_x = x;
        is_alive = 1;
        this.lives = lives;
        rotate = 0;
        this.player = player;
        picture = "Player1.png";
    }
    
    //Draw the Player
    public void Draw()
    {
        StdDraw.picture(x, y, picture);
    }
    
    //Move the Player on the left
    public void Move_left()
    {
        if(x > 148)
            x -= 6;
    }
    
    //Move the player on the right
    public void Move_right()
    {
        if(x < 1750)
            x += 6;
    }
}