import java.lang.Math;

public class Game
{
    //Launch the game and apply all the different interactions
    public static void main(String[] args)
    {
        StdDraw.setCanvasSize(1920, 1080);
        StdDraw.setXscale(0, 1920);
        StdDraw.setYscale(0, 1080);
        StdDraw.enableDoubleBuffering();
        Player player = new Player(960, 100, 1, 3);
        Missile[] missile_arr = new Missile[20];
        Missile[] ennemy_missile_arr = new Missile[20];
        Ennemy[][] ennemy_arr = Ennemy.Initialize_arr();
        int[] move = new int[2];
        move[0] = 1;
        move[1] = 0;
        int[] is_finished = {0};
        Missile last_missile = null;
        Missile last_ennemy_missile = null;
        
        while(is_finished[0] == 0)
        {
            player.Draw();
            Draw_Ennemies(ennemy_arr);
            move = Ennemy.UpdateEnnemies(ennemy_arr, move);
            if(StdDraw.isKeyPressed(38))
            {
                if(last_missile == null || last_missile.fire_rate.getTime() == 0)
                {
                    Missile missile = new Missile(player.x, player.y, "Banana.png", 500);
                    last_missile = AddMissile(missile, missile_arr);
                }
            }
            if(StdDraw.isKeyPressed(37))
            {
                player.Move_left();
            }
            if (StdDraw.isKeyPressed(39))
            {
                player.Move_right();
            }
            int proba = (int)(Math.random() * 40);
            while(ennemy_arr[proba / 10][proba % 10] == null)
                proba = (int)(Math.random() * 40);
            if(last_ennemy_missile == null || (last_ennemy_missile.fire_rate.getTime() == 0 
                                                   && ennemy_arr[proba / 10][proba % 10].can_fire.getTime() == 0))
            {
                Ennemy shooter = ennemy_arr[proba / 10][proba % 10];
                Missile missile = new Missile(shooter.x, shooter.y, "Laser.png", 200);
                last_ennemy_missile = AddMissile(missile, ennemy_missile_arr);
            }
            UpdateMissiles(missile_arr, ennemy_arr);
            Draw_missiles(missile_arr);
            player = UpdateEnnemiesMissiles(player, ennemy_arr, missile_arr, move, ennemy_missile_arr, is_finished);
            Draw_missiles(ennemy_missile_arr);
            if(player.lives == 0)
            {
                StdDraw.clear();
                StdDraw.picture(960, 590, "Game_over.png");
                break;
            }
            if (CheckWin(ennemy_arr) == 1)
            {
                StdDraw.clear();
                StdDraw.picture(960, 590, "Win.png");
                break;
            }
            StdDraw.show();
            StdDraw.clear();
        }
        StdDraw.show();
    }
    
    public static int CheckWin(Ennemy[][] ennemy_arr)
    {
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                if(ennemy_arr[i][j] != null)
                {
                    return 0;
                }
            }
        }
        
        return 1;
    }
    
    public static Player ResetGame(Player player, Ennemy[][] ennemy_arr, Missile[] missile_arr, int[] move, 
        Missile[] ennemy_missile_arr)
    {
        int lives = player.lives;
        player = new Player(960, 100, 1, lives);
        missile_arr = new Missile[20];
        ennemy_missile_arr = new Missile[20];
        player.Draw();
        
        return player;
    }
    
    //Draw all the missiles on the screen
    public static void Draw_missiles(Missile[] missile_arr)
    {
        for(int i = 0; i < 20; i++)
        {
            if(missile_arr[i] != null)
                missile_arr[i].Draw();
        }
    }
    
    //Add a missile in the list of missiles which have to be drawn
    public static Missile AddMissile(Missile missile, Missile[] missile_arr)
    {
        for(int i = 0; i < 20; i++)
        {
            if(missile_arr[i] == null)
            {
                missile_arr[i] = missile;
                break;
            }
        }
        
        return missile;
    }
    
    //Update the position of all the missiles of the list
    //Delete a missile if he is out of the screen
    //Delete the missile and the ennemy if the ennemy get shot
    public static void UpdateMissiles(Missile[] missile_arr, Ennemy[][] ennemy_arr)
    {
        for(int i = 0; i < 20; i++)
        {
            if(missile_arr[i] != null)
            {
                if(missile_arr[i].y >= 1080)
                {
                    missile_arr[i] = null;
                }
                else
                {
                    missile_arr[i].Move();
                    for(int j = 0; j < 4; j++)
                    {
                        for(int k = 0; k < 10; k++)
                        {
                            if(ennemy_arr[j][k] != null && missile_arr[i] != null && ennemy_arr[j][k].x - 20 <= missile_arr[i].x 
                                   && ennemy_arr[j][k].x + 20 >= missile_arr[i].x && ennemy_arr[j][k].y - 20 <= missile_arr[i].y)
                            {
                                ennemy_arr[j][k] = null;
                                missile_arr[i] = null;
                            }
                        }
                    }
                }
            }
        }
    }
    
    //
    public static Player UpdateEnnemiesMissiles(Player player, Ennemy[][] ennemy_arr, Missile[] missile_arr, int[] move, 
        Missile[] ennemy_missile_arr, int[] is_finished)
    {
        for(int i = 0; i < 20; i++)
        {
            if(ennemy_missile_arr[i] != null)
            {
                if(ennemy_missile_arr[i].y <= 0)
                {
                    ennemy_missile_arr[i] = null;
                }
                else
                {
                    ennemy_missile_arr[i].MoveDown();
                    if(player.x - 30 <= ennemy_missile_arr[i].x && player.x + 30 >= ennemy_missile_arr[i].x 
                           && player.y + 20 >= ennemy_missile_arr[i].y)
                    {
                        player.lives--;
                        if(player.lives == 0)
                        {
                            is_finished[0] = 1;
                        }
                        else
                        {
                            player = ResetGame(player, ennemy_arr, missile_arr, move, ennemy_missile_arr);
                        }
                    }
                }
            }
        }
        
        return player;
    }
    
    //Draw all the ennemies on the screen
    public static void Draw_Ennemies(Ennemy[][] ennemy_arr)
    {
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                if(ennemy_arr[i][j] != null)
                    ennemy_arr[i][j].Draw();
            }
        }
    }
}