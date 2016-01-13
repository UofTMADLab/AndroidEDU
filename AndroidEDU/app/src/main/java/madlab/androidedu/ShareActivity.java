package madlab.androidedu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShareActivity extends AppCompatActivity {
    long time = 0;
    TextView timerShare;
    TextView firstAnswer;
    TextView secondAnswer;
    TextView thirdAnswer;
    TextView fourthAnswer;
    final long timeToReadTheGreatGatsby = 9432000;
    final long timeToRunAMile = 300000;
    final long timeToBuyGroceries =1800000;
    final long timeToLearnALanguageOnCodeacademy = 18000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        timerShare = (TextView) findViewById(R.id.timer_share);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            time = extras.getLong("time elapsed");
        }
        String timeString = format(time);
        timerShare.setText(timeString);
        firstAnswer = (TextView)findViewById(R.id.first_answer);
        firstAnswer.setText(String.valueOf(time/timeToRunAMile) + " times or");
        secondAnswer = (TextView)findViewById(R.id.second_answer);
        secondAnswer.setText(String.valueOf(time/timeToBuyGroceries) + " times or");
        thirdAnswer = (TextView)findViewById(R.id.third_answer);
        thirdAnswer.setText(String.valueOf(time/timeToReadTheGreatGatsby) + " times or");
        fourthAnswer = (TextView)findViewById(R.id.fourth_answer);
        fourthAnswer.setText(String.valueOf(time/timeToLearnALanguageOnCodeacademy) + " times");

    }
    String format (long time){
        int secs = (int) (time / 1000);
        int mins = secs / 60;
        String timeString =
                String.format("%02d", mins) + ":" +
                        String.format("%02d", secs % 60) + ":" +
                        String.format("%03d", time % 1000);
        return timeString;
    }
}
