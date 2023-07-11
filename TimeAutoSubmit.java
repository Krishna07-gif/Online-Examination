import java.util.Timer;
import java.util.TimerTask;

// TimerTask class defines a task that can be scheduled to run for just once or for repeated number of time.
class TimerTaskExample extends TimerTask {
    private int seconds = 0;
    private int totalTime;   // total time for the timer 
    private Timer timer;
 
    public TimerTaskExample(int totalTime, Timer timer){
        this.totalTime = totalTime;
        this.timer = timer;
    }

    @Override

    public void run(){
        System.out.println("Timer : " + seconds++ + " seconds");
        if(seconds >= totalTime){
            System.out.println("Time's Up! Submitting answer...");
            timer.cancel();
            submitAnswers();
        }
    }

    private void submitAnswers(){
        System.out.println("Answer Submitted!");
    }
}

public class TimeAutoSubmit {
    public static void main(String[] args) {
        int totalTime = 10;
        Timer timer = new Timer();
        TimerTaskExample task = new TimerTaskExample(totalTime, timer);
        timer.scheduleAtFixedRate(task, 0, 1000);
    }
}
