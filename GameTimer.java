import java.util.Timer;
import java.util.TimerTask;

public class GameTimer
{
    int time;
    
    public GameTimer(long rate)
    {
        time = 1;
        Timer timer = new Timer();
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                time = 0;
                timer.cancel();
            }
        };
        timer.schedule(task, rate, rate);
    }
    
    public int getTime()
    {
        return time;
    }
}