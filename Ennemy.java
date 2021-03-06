public class Ennemy
{
    double x;
    double y;
    int lives;
    String picture;
    GameTimer can_fire;
    
    //Initialize the object ennemy
    public Ennemy(double x, double y, long rate)
    {
        this.x = x;
        this.y = y;
        this.lives = 1;
        picture = "Alien.png";
        can_fire = new GameTimer(rate);
    }
    
    //Move the Ennemy on the left
    public void Move_left()
    {
        if(x > 70)
            x -= 3;
    }
    
    //Move the Ennemy on the right
    public void Move_right()
    {
        if(x < 1850)
            x += 3;
    }
    
    //Move the Ennemy down
    public void Move_down()
    {
        y -= 50;
    }
    
    //Draw the Ennemy
    public void Draw()
    {
        StdDraw.picture(x, y, picture);
    }
    
    //Initialize an array of 4 lines of 10 ennemies
    public static Ennemy[][] Initialize_arr()
    {
        Ennemy[][] ennemy_arr = new Ennemy[4][10];
        double y = 980;
        for(int i = 0; i < 4; i++)
        {
            double x = 100;
            for(int j = 0; j < 10; j++)
            {
                ennemy_arr[i][j] = new Ennemy(x, y, 500);
                x += 80;
            }
            y -= 100;
        }
        
        return ennemy_arr;
    }
    
    //Move all the ennemies down
    public static void Ennemies_down(Ennemy[][] ennemy_arr)
    {
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                if(ennemy_arr[i][j] != null)
                    ennemy_arr[i][j].Move_down();
            }
        }
    }
    
    //Make all the ennemies move on the left or the right 
    public static int[] UpdateEnnemies(Ennemy[][] ennemy_arr, int[] move)
    {
        if(move[0] == 1 && /*ennemy_arr[0][9].x >= 1850*/ move[1] >= 1000)
        {
            move[0] = 0;
            //if(ennemy_arr[3][0].y >= 250)
            Ennemies_down(ennemy_arr);
        }
        if(move[0] == 0 && /*ennemy_arr[0][0].x <= 70*/ move[1] <= 0)
        {
            move[0] = 1;
            //if(ennemy_arr[3][0].y >= 250)
            Ennemies_down(ennemy_arr);
        }
        if(move[0] == 1)
        {
            for(int i = 0; i < 4; i++)
            {
                for(int j = 0; j < 10; j++)
                {
                    if(ennemy_arr[i][j] != null)
                    {
                        ennemy_arr[i][j].Move_right();
                    }
                }
            }
            move[1] += 3;
        }
        if(move[0] == 0)
        {
            for(int i = 0; i < 4; i++)
            {
                for(int j = 0; j < 10; j++)
                {
                    if(ennemy_arr[i][j] != null)
                    {
                        ennemy_arr[i][j].Move_left();
                    }
                }
            }
            move[1] -= 3;
        }
        
        return move;
    }
}