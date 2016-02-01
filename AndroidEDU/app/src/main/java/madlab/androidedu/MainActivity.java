package madlab.androidedu;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

//    Creation of variables of the elements we use in the layout
    private TextView timer;
    private Button startButton;
    private Button shareButton;
    private Button restartButton;

    private long startTime = 0; // Time when we clicked start
    long timeElapsed = 0; // Time that passed since clicking start
    long pauseTime = 0; // For saving the time for when we pause so we can resume from that time
    long currentTime = 0; // Current time to display

    boolean RUNNING_FLAG = false;

//    A class that will handle our updating function
    private Handler updateHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Set the element variables to the appropriate layout element by using their ids (Initializing Variables)
        timer = (TextView) findViewById(R.id.timer);
        startButton = (Button) findViewById(R.id.start_button);
        shareButton = (Button) findViewById(R.id.share_button);
        restartButton = (Button) findViewById(R.id.restart_button);


//        To actually make the buttons do something when we click them, we must implement the
//        OnClickListener of the buttons
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RUNNING_FLAG){
                    startButton.setText("Pause");
                    RUNNING_FLAG = true;
//                    Start the timer and the update function
                    startTime = SystemClock.uptimeMillis();
                    updateHandler.post(updateTimer);
                } else {
                    startButton.setText("Start");
                    RUNNING_FLAG = false;
                    pauseTime += timeElapsed;
                    updateHandler.removeCallbacks(updateTimer);
                }
                startTime = SystemClock.uptimeMillis();
                updateHandler.post(updateTimer);
            }
        });
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.setText("00:00:000");
                pauseTime = 0;
                updateHandler.removeCallbacks(updateTimer);
            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(getBaseContext(), ShareActivity.class);
                RUNNING_FLAG = false;
                startButton.setText("Start");
//                Handle the time for pausing and stop the update function
                pauseTime += timeElapsed;
                timeElapsed = 0;
                updateHandler.removeCallbacks(updateTimer);
                sendIntent.putExtra("time elapsed", currentTime);
                startActivity(sendIntent);
            }
        });
    }

//    A separate function to update the timer that runs in the background
    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            timeElapsed = SystemClock.uptimeMillis() - startTime;
            currentTime = pauseTime + timeElapsed;
            int secs = (int) (currentTime / 1000);
            int mins = secs / 60;
//            Formatting the time to display inside the TextView
            String timeString =
                    String.format("%02d", mins) + ":" +
                    String.format("%02d", secs % 60) + ":" +
                    String.format("%03d", currentTime % 1000);
            timer.setText(timeString);
            updateHandler.post(this);

        }
    };
}
