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
        int score = 0;
        
        while(is_finished[0] == 0)
        {
            if(StdDraw.isKeyPressed(27))
            {
                while(true)
                {
                    StdDraw.clear();
                    StdDraw.textLeft(960, 790, "Press C to Continue");
                    StdDraw.textLeft(960, 590, "Press E to Exit");
                    StdDraw.show();
                    if(StdDraw.isKeyPressed(69))
                    {
                        System.exit(0);
                    }
                    if(StdDraw.isKeyPressed(67))
                    {
                        break;
                    }
                }
            }
            player.Draw();
            Draw_Ennemies(ennemy_arr);
            move = Ennemy.UpdateEnnemies(ennemy_arr, move);
            if(StdDraw.isKeyPressed(32))
            {
                if(last_missile == null || last_missile.fire_rate.getTime() == 0)
                {
                    Missile missile = new Missile(player.x, player.y, "Banana.png", 500, player.rotate);
                    last_missile = AddMissile(missile, missile_arr);
                }
            }
            if(StdDraw.isKeyPressed(38))
            {
                if(player.rotate == 360)
                    player.rotate = 0;
                if(player.rotate < 46 || player.rotate >= 314)
                    player.rotate += 2;
            }
            if(StdDraw.isKeyPressed(40))
            {
                if(player.rotate == 0)
                    player.rotate = 360;
                if(player.rotate > 314 || player.rotate <= 46)
                    player.rotate -= 2;
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
                Missile missile = new Missile(shooter.x, shooter.y, "Laser.png", 200, 0);
                last_ennemy_missile = AddMissile(missile, ennemy_missile_arr);
            }
            score = UpdateMissiles(missile_arr, ennemy_arr, score);
            Draw_missiles(missile_arr);
            player = UpdateEnnemiesMissiles(player, ennemy_arr, missile_arr, move, ennemy_missile_arr, is_finished);
            Draw_missiles(ennemy_missile_arr);
            if(player.lives == 0)
            {
                StdDraw.clear();
                StdDraw.picture(960, 590, "Game_over.png");
                while(true)
                {
                    StdDraw.textLeft(960, 590, "Press R to try again");
                    StdDraw.show();
                    if(StdDraw.isKeyPressed(82))
                    {
                        player = new Player(960, 100, 1, 3);
                        missile_arr = new Missile[20];
                        ennemy_missile_arr = new Missile[20];
                        ennemy_arr = Ennemy.Initialize_arr();
                        move = new int[2];
                        move[0] = 1;
                        move[1] = 0;
                        is_finished[0] = 0;
                        last_missile = null;
                        last_ennemy_missile = null;
                        score = 0;
                        break;
                    }
                }
            }
            if (CheckWin(ennemy_arr) == 1)
            {
                StdDraw.clear();
                StdDraw.picture(960, 590, "Win.png");
                break;
            }
            if(CheckGround(ennemy_arr) == 1)
            {
                StdDraw.clear();
                StdDraw.picture(960, 590, "Game_over.png");
                while(true)
                {
                    StdDraw.textLeft(960, 490, "Press E to Exit");
                    StdDraw.textLeft(960, 590, "Press R to try again");
                    StdDraw.show();
                    if(StdDraw.isKeyPressed(82))
                    {
                        player = new Player(960, 100, 1, 3);
                        missile_arr = new Missile[20];
                        ennemy_missile_arr = new Missile[20];
                        ennemy_arr = Ennemy.Initialize_arr();
                        move = new int[2];
                        move[0] = 1;
                        move[1] = 0;
                        is_finished[0] = 0;
                        last_missile = null;
                        last_ennemy_missile = null;
                        score = 0;
                        break;
                    }
                    if(StdDraw.isKeyPressed(67))
                        System.exit(0);
                }
            }
            StdDraw.textLeft(50, 50, Integer.toString(score));
            StdDraw.show();
            StdDraw.clear();
        }
        StdDraw.show();
    }
    
    //Check if all the ennemies are dead
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
    
    //Check if the ennemies reached the ground
    public static int CheckGround(Ennemy[][] ennemy_arr)
    {
        for(int j = 0; j < 4; j++)
        {
            for(int k = 0; k < 10; k++)
            {
                if(ennemy_arr[j][k] != null)
                {
                    if (ennemy_arr[j][k].y <= 50)
                        return 1;
                }
            }
        }
        return 0;
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
    //Delete the missile and the ennemy if the ennemy get shot and updatethe score
    public static int UpdateMissiles(Missile[] missile_arr, Ennemy[][] ennemy_arr, int score)
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
                                score += 10;
                            }
                        }
                    }
                }
            }
        }
        return score;
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