package kr.co.thinkup.tucircleprogressdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.widget.SeekBar;

import kr.co.thinkup.tucircleprogress.ArcProgress;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArcProgress arcProgress = findViewById(R.id.arcprogress);
        SeekBar seekBar = findViewById(R.id.seekbar);

        seekBar.setProgress(10);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                arcProgress.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
