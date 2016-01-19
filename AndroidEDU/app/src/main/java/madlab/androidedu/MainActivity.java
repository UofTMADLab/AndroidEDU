package madlab.androidedu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

//    Initialize variables of the elements we use in the layout
    private TextView timer;
    private Button startButton;
    private Button pauseButton;
    private Button restartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Set the element variables to the appropriate layout element by using their ids
        timer = (TextView) findViewById(R.id.timer);
        startButton = (Button) findViewById(R.id.start_button);
        pauseButton = (Button) findViewById(R.id.pause_button);
        restartButton = (Button) findViewById(R.id.restart_button);

//        The text in the TextView can also be changed with code!
        timer.setText("This is the timer");

//        To actually make the buttons do something when we click them, we must implement the
//        OnClickListener of the buttons
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "This is a toast", Toast.LENGTH_SHORT).show();
            }
        });
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.setText("00:00:00");
            }
        });

    }
}
