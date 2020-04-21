package com.example.astrum;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.provider.MediaStore;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    final static int planetsamount = 5;

    //Create an undeclared button object.
    private ImageButton SettingsButton;
    private Button OrbitButton;

    //create objects for playing sounds of everything
   // static MediaPlayer PlanetsMP[] = new MediaPlayer[planetsamount];

    //create volume control
    private SeekBar VolumeSeekbar;
    private AudioManager audioManager;

    RunCheckDur Runnablecheckdur[] = new RunCheckDur[planetsamount];
    Thread threadcheckdur[] = new Thread[planetsamount];

    static AudioLoader audioUnit[] = new AudioLoader[planetsamount];

    private boolean check = true;
    static int[] CheckReady = new int[planetsamount];
    private final static int MAX_VOLUME = 100;

    private Handler planetHandler = new Handler();

    Button planetButtons[] = new Button[planetsamount];

    static float[] VolForMixerOnCreate = new float[planetsamount];
    VideoView[] planetsvid = new VideoView[planetsamount];


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //request for fragment transaction, need supports because this one is not extending Fragment.
        FragmentTransaction fragmenttransaction = getSupportFragmentManager().beginTransaction();

        //Tell program to add the fragment container and new openadjustvolume object programmatically
        fragmenttransaction.add(R.id.FragmentsContainer, new AdjustVolumeFrag());
        fragmenttransaction.commit();


        //Declare the button object.
        SettingsButton = findViewById(R.id.imageButton3);
        SettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
                if (!check) {
                    OrbitButton.performClick();
                }
            }
        });

        planetButtons[0] = findViewById(R.id.PlanetOne);
        planetButtons[1] = findViewById(R.id.PlanetTwo);
        planetButtons[2] = findViewById(R.id.planetThree);
        planetButtons[3] = findViewById(R.id.planetFour);
        planetButtons[4] = findViewById(R.id.planetFive);

        //set all planets players to null and checknull value to false
        for (int i = 0; i < planetsamount; i++)
        {
            //PlanetsMP[i] = null;
            CheckReady[i] = 0;
        }

        //Declare Orbit button
        OrbitButton = findViewById(R.id.OrbitButton);

        //initiate master volume slider
        initControls();


        //start animation for orbit
        OrbitButton.setOnClickListener(new View.OnClickListener() {
            private int radiuschange = 0;
            private int extraheight = 130;

            @Override
            public void onClick(View v)
            {

                //which audio sounds get played should be decided here.
                if (check)
                {
                    PlayMercury();
                    PlayVenus();
                    PlayEarth();
                    PlayJupiter();
                    PlayMars();

                    //Get volume for planets
                    for (int i = 0; i < planetsamount; i++)
                    {
                        if (audioUnit[i] != null)
                        {
                            audioUnit[i].SetVolume(AdjustVolumeFrag.volume[i], AdjustVolumeFrag.volume[i]);
                        }
                    }

                    //initiate animation
                    for (int i = 0; i < planetsamount; i++)
                    {
                        PlanetAnimation(planetButtons[i], check, i, radiuschange, extraheight);
                        //AnimationThread animthread = new AnimationThread(planetButtons[i], check, i, radiuschange, extraheight);
                        //animthread.start();
                        radiuschange += 44;
                        extraheight += 45;

                        //play only if file is loaded
                        if (audioUnit[i] != null)
                        {
                            audioUnit[i].PlayAudio();
                        }
                        /*
                        if (CheckReady[i] == 1 && PlanetsMP[i] != null)
                        {
                            try
                            {
                                PlanetsMP[i].setOnPreparedListener(new MediaPlayer.OnPreparedListener()
                                {
                                    @Override
                                    public void onPrepared(MediaPlayer mp)
                                    {
                                        mp.start();
                                    }
                                });

                            } catch (NullPointerException ex) {
                                ex.printStackTrace();
                            }
                        }

                         */
                    }
                    check = false;
                } else {
                    //for sounds
                    PlayMercury();
                    PlayVenus();
                    PlayEarth();
                    PlayJupiter();
                    PlayMars();


                    //reset values
                    radiuschange = 0;
                    extraheight = 130;

                    //stop animation and reset ready value
                    for (int i = 0; i < planetsamount; i++)
                    {
                        PlanetAnimation(planetButtons[i], check, i, radiuschange, extraheight);
                        //AnimationThread animthread = new AnimationThread(planetButtons[i], check, i, radiuschange, extraheight);
                        //nimthread.start();
                    }

                    check = true;
                }

            }
        });


    }//OnCreate

//---------------------------------------------------------------------------------------------------

    public void initControls()
    {
        try {
            VolumeSeekbar = findViewById(R.id.seekBar1);
            //here, if you remove it, all the other lines won't work as this line specify what exactly we are exerting control over
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            VolumeSeekbar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            VolumeSeekbar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) / 2), 0);


            VolumeSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }


    public void openSettings() {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }


    //takes care of playing when button pressed and then stop when button pressed again
    public void PlayMercury()
    {
        if (ChooseSoundsMenu.PlanetsVal[0] == 0)
        {
            if (check)
            {
                audioUnit[0] = new AudioLoader(this, R.raw.mercurynotdone, 0);
            }
            else
            {

                audioUnit[0].StopSound();
                audioUnit[0] = null;
            }
        }
        //Mercury
        //if one is selected

    }

    public void PlayVenus()
    {
        //Venus
        //if one is selected
        //This switch case might not be working so well.
        if (ChooseSoundsMenu.PlanetsVal[1] == 0)
        {
            if (check)
            {
                audioUnit[1] = new AudioLoader(this, R.raw.venus1, 1);
            }
            else
            {

                audioUnit[1].StopSound();
                audioUnit[1] = null;
            }
        }
    }

    public void PlayEarth() {
        if (ChooseSoundsMenu.PlanetsVal[2] == 0)
        {
            if (check)
            {
                audioUnit[2] = new AudioLoader(this, R.raw.earthnotdone, 2);
            }
            else
            {

                audioUnit[2].StopSound();
                audioUnit[2] = null;
            }
        }
    }

    public void PlayMars() {
        if (ChooseSoundsMenu.PlanetsVal[3] == 0)
        {
            if (check)
            {
                audioUnit[3] = new AudioLoader(this, R.raw.marsdone, 3);
            }
            else
            {

                audioUnit[3].StopSound();
                audioUnit[3] = null;
            }
        }
    }

    public void PlayJupiter() {
        if (ChooseSoundsMenu.PlanetsVal[4] == 0)
        {
            if (check)
            {
                audioUnit[4] = new AudioLoader(this, R.raw.jupiternotdone, 4);
            }
            else
            {

                audioUnit[4].StopSound();
                audioUnit[4] = null;
            }
        }
    }


    private void PlanetAnimation(Button planet, boolean check, int i, int radiuschange, int extraheight) {
        Animation PlanetAnim = new MyAnimation(planet, 127 + radiuschange, extraheight);

        if (check)
        {
            //Get duration of audio file
            if (audioUnit[i] != null)
            {
                int PlanetDur = audioUnit[i].GetDur();

                PlanetAnim.setDuration(PlanetDur-20); //for now 20 seems to be working

                PlanetAnim.setInterpolator(new LinearInterpolator());
                PlanetAnim.setRepeatCount(Animation.INFINITE);
                planet.startAnimation(PlanetAnim);

                /*
                Runnablecheckdur[i] = new RunCheckDur(PlanetDur);
                Runnablecheckdur[i].checkrun = true;
                threadcheckdur[i] = new Thread(Runnablecheckdur[i]);
                threadcheckdur[i].start();
                 */
            }

        }
        else
            {
            try
            {
               // Runnablecheckdur[i].checkrun = false;
                planet.clearAnimation();
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }

        }
    }

    class RunCheckDur implements Runnable
    {
        volatile boolean checkrun = false;

        int AudioDuration;
        RunCheckDur(int AudioDuration)
        {
            this.AudioDuration = AudioDuration;
        }

        @Override
        public void run()
        {
            for (int i = 0; checkrun; i++)
            {
                Log.d("AudioDuration", String.valueOf(i) + "Of" + String.valueOf(AudioDuration));
                try
                {
                    Thread.sleep(AudioDuration);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

            }

        }
    }
}