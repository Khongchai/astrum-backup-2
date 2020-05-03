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


public class MainActivity extends AppCompatActivity
{
    final static int planetsamount = 5;

    //Create an undeclared button object.
    private ImageButton SettingsButton;
    private Button OrbitButton;

    private int seekBarsavedProg = 0;

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

    int SysNum = 1;
    ChooseSystems chooseSystems;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chooseSystems = new ChooseSystems();


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
                if (!check)
                {
                   OrbitButton.performClick();
                   //check = true;
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
            CheckReady[i] = 0;
        }

        OrbitButton = findViewById(R.id.OrbitButton);

        //initiate master volume slider
        initControls();
        //start animation for orbit
        OrbitButton.setOnClickListener(new View.OnClickListener()
        {
            private int radiuschange = 0;
            private int extraheight = 130;

            @Override
            public void onClick(View v)
            {
                SysNum = chooseSystems.getSystemVal();

                //which audio sounds get played should be decided here.
                if (check)
                {
                    //In functions below, use SystemSound value to decide which sound to play.
                    PlayOrb1();
                    PlayOrb2();
                    PlayOrb3();
                    PlayOrb4();
                    PlayOrb5();

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
                        radiuschange += 44;
                        extraheight += 45;

                        //play only if file is loaded
                        if (audioUnit[i] != null)
                        {
                            audioUnit[i].PlayAudio();
                        }
                    }
                  check = false;


                } else {
                    //for sounds
                    PlayOrb1();
                    PlayOrb2();
                    PlayOrb3();
                    PlayOrb4();
                    PlayOrb5();


                    //reset values
                    radiuschange = 0;
                    extraheight = 130;

                    //stop animation and reset ready value
                    for (int i = 0; i < planetsamount; i++)
                    {
                        PlanetAnimation(planetButtons[i], check, i, radiuschange, extraheight);
                    }

                    check = true;
                }

            }
        });


    }//OnCreate

    @Override
    protected void onResume()
    {
        super.onResume();
        //resume master slider volume
        if (VolumeSeekbar != null)
        {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, VolumeSeekbar.getProgress(), 0);
        }

    }

//---------------------------------------------------------------------------------------------------

    public void initControls()
    {
        try {
            VolumeSeekbar = findViewById(R.id.seekBar1);
            //here, if you remove it, all the other lines won't work as this line specify what exactly we are exerting control over
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

            if (audioManager!=null)
            {
                VolumeSeekbar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
                VolumeSeekbar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) / 2), 0);
            }


            VolumeSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
                {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                    seekBarsavedProg = progress;
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
    public void PlayOrb1() //audiounit[0], planetsval[0]. planetid0
    {
        final int unit = 0;
        if (SysNum == 1)
        {
            if (ChooseSoundsMenu.PlanetsVal[unit] == 0)
            {
                if (check)
                {
                    audioUnit[unit] = new AudioLoader(this, R.raw.mercorb11, 0);
                }
                else
                {
                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

            if (ChooseSoundsMenu.PlanetsVal[unit] == 1)
            {
                if (check)
                {

                    audioUnit[unit] = new AudioLoader(this, R.raw.mercorb12, 0);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

            if (ChooseSoundsMenu.PlanetsVal[unit] == 2)
            {
                if (check)
                {

                    audioUnit[unit] = new AudioLoader(this, R.raw.mercorb13, 0);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

            if (ChooseSoundsMenu.PlanetsVal[unit] == 3)
            {
                if (check)
                {

                    audioUnit[unit] = new AudioLoader(this, R.raw.mercorb14, 0);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

            if (ChooseSoundsMenu.PlanetsVal[unit] == 4)
            {
                if (check)
                {

                    audioUnit[unit] = new AudioLoader(this, R.raw.mercorb15, 0);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }


        }

        if (SysNum == 2)
        {

        }

        if (SysNum == 3)
        {

        }

        if (SysNum == 4)
        {

        }

        if (SysNum == 5)
        {
            if (ChooseSoundsMenu.PlanetsVal[unit] == 0)
            {
                if (check)
                {

                    audioUnit[unit] = new AudioLoader(this, R.raw.juporb11, 0);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

            if (ChooseSoundsMenu.PlanetsVal[unit] == 1)
            {
                if (check)
                {

                    audioUnit[unit] = new AudioLoader(this, R.raw.juporb12, 0);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

            if (ChooseSoundsMenu.PlanetsVal[unit] == 2)
            {
                if (check)
                {

                    audioUnit[unit] = new AudioLoader(this, R.raw.juporb13, 0);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

            if (ChooseSoundsMenu.PlanetsVal[unit] == 3)
            {
                if (check)
                {

                    audioUnit[unit] = new AudioLoader(this, R.raw.juporb14, 0);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

            if (ChooseSoundsMenu.PlanetsVal[unit] == 4)
            {
                if (check)
                {
                    check = false;
                    audioUnit[unit] = new AudioLoader(this, R.raw.juporb15, 0);
                }
                else
                {
                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

        }

        if (SysNum == 6)
        {

        }

        if (SysNum == 7)
        {
            if (ChooseSoundsMenu.PlanetsVal[unit] == 0)
            {
                if (check)
                {
                    audioUnit[unit] = new AudioLoader(this, R.raw.urorb11, 0);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

            if (ChooseSoundsMenu.PlanetsVal[unit] == 1)
            {
                if (check)
                {
                    audioUnit[unit] = new AudioLoader(this, R.raw.urorb12, 0);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

            if (ChooseSoundsMenu.PlanetsVal[unit] == 2)
            {
                if (check)
                {
                    audioUnit[unit] = new AudioLoader(this, R.raw.urorb13, 0);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

            if (ChooseSoundsMenu.PlanetsVal[unit] == 3)
            {
                if (check)
                {
                    audioUnit[unit] = new AudioLoader(this, R.raw.urorb14, 0);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

            if (ChooseSoundsMenu.PlanetsVal[unit] == 4)
            {
                if (check)
                {
                    audioUnit[unit] = new AudioLoader(this, R.raw.urorb15, 0);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

        }

        if (SysNum == 8)
        {

        }

    }

    //-------------------------------------------------------------------------------------------------------------------

    public void PlayOrb2() //audiounit[1], planetsval[1]. planetid1
    {
        final int unit = 1;
        if (SysNum == 1)
        {
            if (ChooseSoundsMenu.PlanetsVal[unit] == 0)
            {
                if (check)
                {
                    audioUnit[unit] = new AudioLoader(this, R.raw.mercorb21, unit);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

            if (ChooseSoundsMenu.PlanetsVal[unit] == 1)
            {
                if (check)
                {
                    audioUnit[unit] = new AudioLoader(this, R.raw.mercorb22, unit);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

            if (ChooseSoundsMenu.PlanetsVal[unit] == 2)
            {
                if (check)
                {
                    audioUnit[unit] = new AudioLoader(this, R.raw.mercorb23, unit);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

            if (ChooseSoundsMenu.PlanetsVal[unit] == 3)
            {
                if (check)
                {
                    audioUnit[unit] = new AudioLoader(this, R.raw.mercorb24, unit);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

            if (ChooseSoundsMenu.PlanetsVal[unit] == 4)
            {
                if (check)
                {
                    audioUnit[unit] = new AudioLoader(this, R.raw.mercorb25, unit);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }


        }

        if (SysNum == 2)
        {

        }

        if (SysNum == 3)
        {

        }

        if (SysNum == 4)
        {

        }

        if (SysNum == 5)
        {
            if (ChooseSoundsMenu.PlanetsVal[unit] == 0)
            {
                if (check)
                {
                    audioUnit[unit] = new AudioLoader(this, R.raw.juporb21, unit);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

            if (ChooseSoundsMenu.PlanetsVal[unit] == 1)
            {
                if (check)
                {
                    audioUnit[unit] = new AudioLoader(this, R.raw.juporb22, unit);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

            if (ChooseSoundsMenu.PlanetsVal[unit] == 2)
            {
                if (check)
                {
                    audioUnit[unit] = new AudioLoader(this, R.raw.juporb23, unit);
                }
                else
                {
                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

            if (ChooseSoundsMenu.PlanetsVal[unit] == 3)
            {
                if (check)
                {
                    audioUnit[unit] = new AudioLoader(this, R.raw.juporb24, unit);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

            if (ChooseSoundsMenu.PlanetsVal[unit] == 4)
            {
                if (check)
                {
                    audioUnit[unit] = new AudioLoader(this, R.raw.juporb25, unit);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

        }

        if (SysNum == 6)
        {

        }

        if (SysNum == 7)
        {
            if (ChooseSoundsMenu.PlanetsVal[unit] == 0)
            {
                if (check)
                {
                    audioUnit[unit] = new AudioLoader(this, R.raw.urorb21, unit);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

            if (ChooseSoundsMenu.PlanetsVal[unit] == 1)
            {
                if (check)
                {
                    audioUnit[unit] = new AudioLoader(this, R.raw.urorb22, unit);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

            if (ChooseSoundsMenu.PlanetsVal[unit] == 2)
            {
                if (check)
                {
                    audioUnit[unit] = new AudioLoader(this, R.raw.urorb23, unit);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

            if (ChooseSoundsMenu.PlanetsVal[unit] == 3)
            {
                if (check)
                {
                    audioUnit[unit] = new AudioLoader(this, R.raw.urorb24, unit);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

            if (ChooseSoundsMenu.PlanetsVal[unit] == 4)
            {
                if (check)
                {
                    audioUnit[unit] = new AudioLoader(this, R.raw.urorb25, unit);
                }
                else
                {

                    audioUnit[unit].StopSound();
                    audioUnit[unit] = null;
                }
            }

        }

        if (SysNum == 8)
        {

        }
    }
//---------------------------------------------------------------------------------------------------------------------------
    public void PlayOrb3()  //audiounit[2], planetsval[2]. planetid2
    {
        {
            final int unit = 2;
            if (SysNum == 1)
            {
                if (ChooseSoundsMenu.PlanetsVal[unit] == 0)
                {
                    if (check)
                    {
                        audioUnit[unit] = new AudioLoader(this, R.raw.mercorb31, unit);
                    }
                    else
                    {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 1)
                {
                    if (check)
                    {
                        audioUnit[unit] = new AudioLoader(this, R.raw.mercorb32, unit);
                    }
                    else
                    {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 2)
                {
                    if (check)
                    {
                        audioUnit[unit] = new AudioLoader(this, R.raw.mercorb33, unit);
                    }
                    else
                    {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 3)
                {
                    if (check)
                    {
                        audioUnit[unit] = new AudioLoader(this, R.raw.mercorb34, unit);
                    }
                    else
                    {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 4)
                {
                    if (check)
                    {
                        audioUnit[unit] = new AudioLoader(this, R.raw.mercorb35, unit);
                    }
                    else
                    {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }
            }
            if (SysNum == 2) {

            }

            if (SysNum == 3) {

            }

            if (SysNum == 4) {

            }

            if (SysNum == 5)
            {
                if (ChooseSoundsMenu.PlanetsVal[unit] == 0) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.juporb31, unit);
                    } else {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 1) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.juporb32, unit);
                    } else {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 2) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.juporb33, unit);
                    } else {
                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 3) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.juporb34, unit);
                    } else {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 4) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.juporb35, unit);
                    } else {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

            }

            if (SysNum == 6) {

            }

            if (SysNum == 7) {
                if (ChooseSoundsMenu.PlanetsVal[unit] == 0) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.urorb31, unit);
                    } else {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 1) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.urorb32, unit);
                    } else {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 2) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.urorb33, unit);
                    } else {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 3) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.urorb34, unit);
                    } else {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 4) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.urorb35, unit);
                    } else {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

            }

            if (SysNum == 8) {

            }
        }
    }
    //--------------------------------------------------------------------------------------------------------------------------

    public void PlayOrb4() //audiounit[3], planetsval[3]. planetid3
    {
        {
            final int unit = 3;
            if (SysNum == 1)
            {
                if (ChooseSoundsMenu.PlanetsVal[unit] == 0)
                {
                    if (check)
                    {
                        audioUnit[unit] = new AudioLoader(this, R.raw.mercorb41, unit);
                    }
                    else
                    {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 1)
                {
                    if (check)
                    {
                        audioUnit[unit] = new AudioLoader(this, R.raw.mercorb42, unit);
                    }
                    else
                    {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 2)
                {
                    if (check)
                    {
                        audioUnit[unit] = new AudioLoader(this, R.raw.mercorb43, unit);
                    }
                    else
                    {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 3)
                {
                    if (check)
                    {
                        audioUnit[unit] = new AudioLoader(this, R.raw.mercorb44, unit);
                    }
                    else
                    {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 4)
                {
                    if (check)
                    {
                        audioUnit[unit] = new AudioLoader(this, R.raw.mercorb45, unit);
                    }
                    else
                    {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }
            }

            if (SysNum == 2) {

            }

            if (SysNum == 3) {

            }

            if (SysNum == 4) {

            }

            if (SysNum == 5)
            {
                if (ChooseSoundsMenu.PlanetsVal[unit] == 0)
                {
                    if (check)
                    {
                        audioUnit[unit] = new AudioLoader(this, R.raw.juporb41, unit);
                    } else {
                        try
                        {
                            audioUnit[unit].StopSound();
                            audioUnit[unit] = null;
                        }
                        catch (NullPointerException e)
                        {
                            e.printStackTrace();
                        }

                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 1) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.juporb42, unit);
                    } else {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 2) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.juporb43, unit);
                    } else {
                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 3) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.juporb44, unit);
                    } else {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 4) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.juporb45, unit);
                    } else {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

            }

            if (SysNum == 6) {

            }

            if (SysNum == 7) {
                if (ChooseSoundsMenu.PlanetsVal[unit] == 0) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.urorb41, unit);
                    } else {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 1) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.urorb42, unit);
                    } else {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 2) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.urorb43, unit);
                    } else {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 3) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.urorb44, unit);
                    } else {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 4) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.urorb45, unit);
                    } else {
                        try
                        {
                            audioUnit[unit].StopSound();
                            audioUnit[unit] = null;
                        }
                        catch (NullPointerException e)
                        {
                            e.printStackTrace();
                        }

                    }
                }

            }

            if (SysNum == 8) {

            }
        }
    }

    public void PlayOrb5() //audiounit[4], planetsval[4]. planetid4
    {
        {
            final int unit = 4;
            if (SysNum == 1)
            {
                if (ChooseSoundsMenu.PlanetsVal[unit] == 0)
                {
                    if (check)
                    {
                        audioUnit[unit] = new AudioLoader(this, R.raw.mercorb51, unit);
                    }
                    else
                    {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 1)
                {
                    if (check)
                    {
                        audioUnit[unit] = new AudioLoader(this, R.raw.mercorb52, unit);
                    }
                    else
                    {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 2)
                {
                    if (check)
                    {
                        audioUnit[unit] = new AudioLoader(this, R.raw.mercorb53, unit);
                    }
                    else
                    {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 3)
                {
                    if (check)
                    {
                        audioUnit[unit] = new AudioLoader(this, R.raw.mercorb54, unit);
                    }
                    else
                    {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 4)
                {
                    if (check)
                    {
                        audioUnit[unit] = new AudioLoader(this, R.raw.mercorb55, unit);
                    }
                    else
                    {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }
            }

            if (SysNum == 2) {

            }

            if (SysNum == 3) {

            }

            if (SysNum == 4) {

            }

            if (SysNum == 5)
            {
                if (ChooseSoundsMenu.PlanetsVal[unit] == 0) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.juporb51, unit);
                    } else {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 1) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.juporb52, unit);
                    } else {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 2) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.juporb53, unit);
                    } else {
                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 3) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.juporb54, unit);
                    } else {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 4) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.juporb55, unit);
                    } else
                        {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

            }

            if (SysNum == 6) {

            }

            if (SysNum == 7) {
                if (ChooseSoundsMenu.PlanetsVal[unit] == 0) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.urorb51, unit);
                    } else {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 1) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.urorb52, unit);
                    } else {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 2) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.urorb53, unit);
                    } else {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 3) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.urorb54, unit);
                    } else {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

                if (ChooseSoundsMenu.PlanetsVal[unit] == 4) {
                    if (check) {
                        audioUnit[unit] = new AudioLoader(this, R.raw.urorb55, unit);
                    } else {

                        audioUnit[unit].StopSound();
                        audioUnit[unit] = null;
                    }
                }

            }

            if (SysNum == 8)
            {

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
                Log.d("Started animation for ", String.valueOf(i));
            }

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