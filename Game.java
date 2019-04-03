public class Game
{
    public static void main(String[] args)
    {
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(0, 1000);
        Player player = new Player(500, 100, 1);
        while(true)
        {
            player.Draw();
            if(StdDraw.isKeyPressed(37))
            {
                player.Move_left();
            }
            if (StdDraw.isKeyPressed(39))
            {
                player.Move_right();
            }
            StdDraw.show();
            StdDraw.clear();
        }
    }
}