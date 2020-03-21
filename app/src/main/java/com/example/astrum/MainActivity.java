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
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    //Create an undeclared button object.
    private ImageButton SettingsButton;
    private Button OrbitButton;

    //create objects for playing sounds of everything
    static MediaPlayer PlanetsMP[] = new MediaPlayer[8];

    //create volume control
    private SeekBar VolumeSeekbar;
    private AudioManager audioManager;

    private boolean check = true;
    static int[] CheckReady = new int[8];
    private final static int MAX_VOLUME = 100;

    private Handler planetHandler = new Handler();

    Button planetButtons[] = new Button[8];


    static float[] VolForMixerOnCreate = new float[8];


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
        planetButtons[5] = findViewById(R.id.planetSix);
        planetButtons[6] = findViewById(R.id.planetSeven);
        planetButtons[7] = findViewById(R.id.planetEight);

        //set all planets players to null and checknull value to false
        for (int i = 0; i < 8; i++) {
            PlanetsMP[i] = null;
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
                if (check) {
                    PlayMercury();
                    PlayVenus();
                    PlayEarth();
                    PlayJupiter();
                    PlayMars();
                    PlayNeptune();
                    PlaySaturn();
                    PlayUranus();

                    //Get volume for planets
                    for (int i = 0; i < 8; i++) {
                        if (AdjustVolumeFrag.volume[i] != -1)
                        {
                            PlanetsMP[i].setVolume(AdjustVolumeFrag.volume[i], AdjustVolumeFrag.volume[i]);
                        }
                    }

                    //initiate animation
                    for (int i = 0; i < 8; i++)
                    {
                        //PlanetAnimation(planetButtons[i], check, i, radiuschange, extraheight);
                        AnimationThread animthread = new AnimationThread(planetButtons[i], check, i, radiuschange, extraheight);
                        animthread.start();
                        radiuschange += 44;
                        extraheight += 45;

                        //play only if file is loaded
                        if (CheckReady[i] == 1 && PlanetsMP[i] != null)
                        {
                            try
                            {
                                PlanetsMP[i].start();
                            } catch (NullPointerException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    check = false;
                } else {
                    //for sounds
                    PlayMercury();
                    PlayVenus();
                    PlayEarth();
                    PlayJupiter();
                    PlayMars();
                    PlayNeptune();
                    PlaySaturn();
                    PlayUranus();

                    //reset values
                    radiuschange = 0;
                    extraheight = 130;

                    //stop animation and reset ready value
                    for (int i = 0; i < 8; i++)
                    {
                        //PlanetAnimation(planetButtons[i], check, i, radiuschange, extraheight);
                        AnimationThread animthread = new AnimationThread(planetButtons[i], check, i, radiuschange, extraheight);
                        animthread.start();
                    }

                    check = true;
                }

            }
        });


    }//OnCreate

//---------------------------------------------------------------------------------------------------

    public void initControls() {
        try {
            VolumeSeekbar = findViewById(R.id.seekBar1);
            //here, if you remove it, all the other lines won't work as this line specify what exactly we are exerting control over
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            VolumeSeekbar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            VolumeSeekbar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC) / 2);
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
    public void PlayMercury() {
        //Mercury
        //if one is selected
        if (ChooseSoundsMenu.PlanetsVal[0] == 0) {
            if (check) {
                PlanetsMP[0] = MediaPlayer.create(this, R.raw.mercury);
                PlanetsMP[0].setLooping(true);
            } else {
                try {
                    PlanetsMP[0].reset();
                    PlanetsMP[0].release();
                    PlanetsMP[0] = null;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    public void PlayVenus() {
        //Venus
        //if one is selected
        //This switch case might not be working so well.
        if (ChooseSoundsMenu.PlanetsVal[1] == 0 || ChooseSoundsMenu.PlanetsVal[1] == 1) {
            if (check) {
                switch (ChooseSoundsMenu.PlanetsVal[1]) {
                    case 0:
                        PlanetsMP[1] = MediaPlayer.create(this, R.raw.venus1);
                    case 1:
                        PlanetsMP[1] = MediaPlayer.create(this, R.raw.venus2);
                }
                PlanetsMP[1].setLooping(true);
            } else if (!check && PlanetsMP[1] != null) {
                PlanetsMP[1].reset();
                PlanetsMP[1].release();
                PlanetsMP[1] = null;
            }
        }
    }

    public void PlayEarth() {
        //Mercury
        //if one is selected
        if (ChooseSoundsMenu.PlanetsVal[2] == 0) {
            if (check) {
                PlanetsMP[2] = MediaPlayer.create(this, R.raw.erde1);
                PlanetsMP[2].setLooping(true);
            } else if (!check && PlanetsMP[2] != null) {
                PlanetsMP[2].reset();
                PlanetsMP[2].release();
                PlanetsMP[2] = null;
            }
        }
    }

    public void PlayMars() {
        //Mercury
        //if one is selected
        if (ChooseSoundsMenu.PlanetsVal[3] == 0) {
            if (check) {
                PlanetsMP[3] = MediaPlayer.create(this, R.raw.mars1);
                PlanetsMP[3].setLooping(true);
            } else if (!check && PlanetsMP[3] != null) {
                PlanetsMP[3].reset();
                PlanetsMP[3].release();
                PlanetsMP[3] = null;
            }
        }
    }

    public void PlayJupiter() {
        //Mercury
        //if one is selected
        if (ChooseSoundsMenu.PlanetsVal[4] == 0) {
            if (check) {
                PlanetsMP[4] = MediaPlayer.create(this, R.raw.jupiter1);
                PlanetsMP[4].setLooping(true);
            } else if (!check && PlanetsMP[4] != null) {
                PlanetsMP[4].reset();
                PlanetsMP[4].release();
                PlanetsMP[4] = null;
            }
        }
    }

    public void PlaySaturn() {
        //Mercury
        //if one is selected
        if (ChooseSoundsMenu.PlanetsVal[5] == 0) {
            if (check) {
                PlanetsMP[5] = MediaPlayer.create(this, R.raw.saturn1);
                PlanetsMP[5].setLooping(true);
            } else if (!check && PlanetsMP[5] != null) {
                PlanetsMP[5].reset();
                PlanetsMP[5].release();
                PlanetsMP[5] = null;
            }
        }
    }

    public void PlayUranus() {
        //Mercury
        //if one is selected
        if (ChooseSoundsMenu.PlanetsVal[6] == 0) {
            if (check) {
                PlanetsMP[6] = MediaPlayer.create(this, R.raw.uranus1);
                PlanetsMP[6].setLooping(true);
            } else if (!check && PlanetsMP[6] != null) {
                PlanetsMP[6].reset();
                PlanetsMP[6].release();
                PlanetsMP[6] = null;
            }
        }
    }

    public void PlayNeptune() {
        //Mercury
        //if one is selected
        if (ChooseSoundsMenu.PlanetsVal[7] == 0) {
            if (check) {
                PlanetsMP[7] = MediaPlayer.create(this, R.raw.neptune1);
                PlanetsMP[7].setLooping(true);
            } else if (check == false && PlanetsMP[7] != null) {
                PlanetsMP[7].reset();
                PlanetsMP[7].release();
                PlanetsMP[7] = null;
            }
        }
    }

/*
    private void PlanetAnimation(Button planet, boolean check, int i, int radiuschange, int extraheight) {
        Animation PlanetAnim = new MyAnimation(planet, 127 + radiuschange, extraheight);

        if (check) {
            //Get duration of audio file
            if (PlanetsMP[i] != null) {
                int PlanetDur = PlanetsMP[i].getDuration();
                PlanetAnim.setDuration(PlanetDur + 80); //For now, + 80 millisecs sync the animation

                //set interpolator to linear and repeat animation
                PlanetAnim.setInterpolator(new LinearInterpolator());
                PlanetAnim.setRepeatCount(Animation.INFINITE);
                planet.startAnimation(PlanetAnim);

                //Debug
                Log.d("Duration" + i, String.valueOf(PlanetsMP[i].getDuration()));
            }

        } else {
            try {
                planet.clearAnimation();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }
    }
*/

    class AnimationThread extends Thread
    {
        Button planet;
        boolean check;
        int i;
        int radiuschange;
        int extraheight;

        AnimationThread(Button planet, boolean check, int i, int radiuschange, int extraheight)
        {
            this.planet = planet;
            this.check = check;
            this.i = i;
            this.radiuschange = radiuschange;
            this.extraheight = extraheight;
        }

        @Override
        public void run()
        {
            final Animation PlanetAnim = new MyAnimation(planet, 127 + radiuschange, extraheight);

            if (check)
            {
                planetHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        //Get duration of audio file
                        if (PlanetsMP[i] != null)
                        {
                            int PlanetDur = PlanetsMP[i].getDuration();
                            PlanetAnim.setDuration(PlanetDur + 80); //For now, + 80 millisecs sync the animation

                            //set interpolator to linear and repeat animation
                            PlanetAnim.setInterpolator(new LinearInterpolator());
                            PlanetAnim.setRepeatCount(Animation.INFINITE);
                            planet.startAnimation(PlanetAnim);


                            Log.d("Duration" + i, String.valueOf(PlanetsMP[i].getDuration()));
                        }

                    }
                });
            }
            else
            {
                try
                {
                    planet.clearAnimation();
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }


}


