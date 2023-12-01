package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    // Initialize variable
    TextView playerPosition,playerDuration;
    SeekBar seekBar;
    ImageView btRew,btPlay,btFf, btPause;

    MediaPlayer mediaPlayer;
    Handler handler = new Handler();
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Assign variable
        playerPosition = findViewById(R.id.play_position);
        playerDuration = findViewById(R.id.playe_duration);
        seekBar = findViewById(R.id.seek_bar);
        btRew = findViewById(R.id.bt_rew);
        btPause = findViewById(R.id.bt_pause);
        btFf = findViewById(R.id.bt_ff);

        //initialise MediaPlayer
        mediaPlayer = MediaPlayer.create(this,R.raw.hindi);

        //Initialize runnable
         runnable = new Runnable(){

             @Override
             public void run() {
                 //Set Progress on seek bar
                 seekBar.setProgress(mediaPlayer.getCurrentPosition());
                 //handler post delay for 0,5 second
                 handler.postDelayed(this,500);

             }
         };
         //Get duration of media player
        int duration = mediaPlayer.getDuration();
        //Convert ms to minute and s
        String sDuration = converFormat(duration);
        //Set duration on text view\
        playerDuration.setText(sDuration);

        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hide play buutton
                btPlay.setVisibility(View.GONE);
                //Show pause button
                btPause.setVisibility(View.VISIBLE);
                //Start media player
                mediaPlayer.start();
                //Set max on see Bar
                seekBar.setMax(mediaPlayer.getDuration());
                //Start handel
                handler.postDelayed(runnable,0);
            }
        });

        btPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hide play buutton
                btPause.setVisibility(View.GONE);
                //Show pause button
                btPlay.setVisibility(View.VISIBLE);
                //Pause media player
                mediaPlayer.pause();
                //Stop handel
                handler.removeCallbacks(runnable);
            }
        });

        btFf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get current position of media player
                int currentPosition = mediaPlayer.getCurrentPosition();
                //Get duration of media player
                int duration = mediaPlayer.getDuration();
                //check conditon
                if (mediaPlayer.isPlaying() && duration != currentPosition){
                    // When media is playing and duration is not equal to current position
                    //Fast forward for 5 seconds
                    currentPosition = currentPosition + 5000;
                    //Set current position on text view
                    playerPosition.setText(converFormat(currentPosition));
                    //Set progress on seek bar
                    mediaPlayer.seekTo(currentPosition);
                }
            }
        });

        btRew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get current position of media player
                int currentPosition = mediaPlayer.getCurrentPosition();
                //check condition
                if (mediaPlayer.isPlaying() && currentPosition > 5000){
                    //When media is playing and current position in greater than 5 seconds
                    //Rewied for 5 seconds
                    currentPosition = currentPosition - 5000;
                    //Get current position on text view
                    playerPosition.setText(converFormat(currentPosition));
                    //Set progress on seek bar
                    mediaPlayer.seekTo(currentPosition);
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // Check condition
                if (b){
                    //When drag the seek bar
                    // set progress on seek bar
                }
                playerPosition.setText(converFormat(mediaPlayer.getCurrentPosition()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                // Hide pause button
                btPause.setVisibility(View.GONE);
                // Show playe button
                btPlay.setVisibility(View.VISIBLE);
                //set media player to initial position
                mediaPlayer.seekTo(0);

            }
        });

    }

    private String converFormat(int duration) {
        return String.format("%02d:%02d"
        , TimeUnit.MILLISECONDS.toMinutes(duration)
        ,TimeUnit.MILLISECONDS.toSeconds(duration)
        ,TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }
}
