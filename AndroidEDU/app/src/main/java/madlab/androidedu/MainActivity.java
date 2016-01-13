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

    private TextView timer;
    private Button startButton;
    private Button shareButton;
    private Button restartButton;

    private long startTime = 0;
    long timeElapsed = 0;
    long pauseTime = 0;
    long currentTime = 0;
    boolean RUNNING_FLAG = false;

    private Handler updateHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = (TextView) findViewById(R.id.timer);
        startButton = (Button) findViewById(R.id.start_button);
        shareButton = (Button) findViewById(R.id.share_button);
        restartButton = (Button) findViewById(R.id.restart_button);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RUNNING_FLAG){
                    startButton.setText("Pause");
                    RUNNING_FLAG = true;
                    startTime = SystemClock.uptimeMillis();
                    updateHandler.postDelayed(updateTimer, 0);
                } else {
                    startButton.setText("Start");
                    RUNNING_FLAG = false;
                    pauseTime += timeElapsed;
                    updateHandler.removeCallbacks(updateTimer);
                }

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
                pauseTime += timeElapsed;
                updateHandler.removeCallbacks(updateTimer);
                sendIntent.putExtra("time elapsed", currentTime);
                startActivity(sendIntent);
            }
        });
    }
    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            timeElapsed = SystemClock.uptimeMillis() - startTime;
            currentTime = pauseTime + timeElapsed;
            int secs = (int) (currentTime / 1000);
            int mins = secs / 60;
            String timeString =
                    String.format("%02d", mins) + ":" +
                    String.format("%02d", secs % 60) + ":" +
                    String.format("%03d", currentTime % 1000);
            timer.setText(timeString);
            updateHandler.postDelayed(this, 0);

        }
    };
}
