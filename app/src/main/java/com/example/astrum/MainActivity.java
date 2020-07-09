package com.example.astrum;

import android.graphics.Point;
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
import android.text.Layout;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Display;
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

    private boolean firststart = false;

    RunCheckDur Runnablecheckdur[] = new RunCheckDur[planetsamount];
    Thread threadcheckdur[] = new Thread[planetsamount];

    static AudioLoader audioUnit[] = new AudioLoader[planetsamount];

    private boolean check = true;
    static int[] CheckReady = new int[planetsamount];
    private final static int MAX_VOLUME = 100;

    private Handler planetHandler = new Handler();

    private CircularMotion2[] circMo = new CircularMotion2[5];


    Button planetButtons[] = new Button[planetsamount];

    static float[] VolForMixerOnCreate = new float[planetsamount];
    VideoView[] planetsvid = new VideoView[planetsamount];

    int SysNum = 0;
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

        ChooseSoundsMenu.PlanetsVal[0] = -1;

        //space for new CircAnim
        //get display size
        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        final float MaxX = mdispSize.x;
        final float MaxY = mdispSize.y;
        final float MidX = MaxX/2;
        final float MidY = MaxY/2;

        final float offsetleft;
        final float offsettop;

        //calculate offset for screen smaller than 1440 (width)
        if (1440 - MaxX > 0)
        {
            offsetleft = (1440 - MaxX) * 0.083f;
        }
        //calculate offset for sceren larger than or equals to 1440 (width)
        else
        {
            offsetleft = 0;
        }

        //calculate offset for screen smaller than 1440 (height)
        if (2621 - MaxY > 0)
        {
            offsettop = ((2612 - MaxY) * 0.06f) + 5;
        }
        //calculate offset for sceren larger than or equals to 1440 (width)
        else
        {
            offsettop = 0;
        }

        //set all planets players to null and checknull value to false and set visibility to 0
        for (int i = 0; i < planetsamount; i++)
        {

            CheckReady[i] = 0;
            if (!firststart)
            {
                planetButtons[i].setVisibility(View.INVISIBLE);
            }

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
                    //If a system is chosen, else
                    if (SysNum != 0 && ChooseSoundsMenu.PlanetsVal[0] != -1)
                    {
                        PlayOrb1();
                        PlayOrb2();
                        PlayOrb3();
                        PlayOrb4();
                        PlayOrb5();
                    }
                    else
                    {
                        if (ChooseSoundsMenu.PlanetsVal[0] == -1)
                        {
                            final Toast toast = Toast.makeText(getApplicationContext(), "Please choose sounds", Toast.LENGTH_SHORT);
                            toast.show();
                            Handler StopToast = new Handler();
                            StopToast.postDelayed(new Runnable() {
                                @Override
                                public void run()
                                {
                                    toast.cancel();
                                }
                            }, 2000);
                        }
                        else
                        {
                            final Toast toast = Toast.makeText(getApplicationContext(), "Please choose a planet and sounds", Toast.LENGTH_SHORT);
                            toast.show();
                            Handler StopToast = new Handler();
                            StopToast.postDelayed(new Runnable() {
                                @Override
                                public void run()
                                {
                                    toast.cancel();
                                }
                            }, 2000);
                        }

                    }
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

                        //play only if file is loaded
                        if (audioUnit[i] != null)
                        {
                            audioUnit[i].PlayAudio();

                            //CircMo
                            circMo[i] = new CircularMotion2(planetButtons[i], MidX, MidY, audioUnit[i].GetDur(), i, MaxX, offsetleft, offsettop);
                            circMo[i].LoadAnim();
                            if (!firststart)
                            {
                                firststart = true;
                                for (int q = 0; q < planetsamount; q++)
                                {
                                    if (CheckReady[q] == 1)
                                    {
                                        planetButtons[q].setVisibility(View.VISIBLE);
                                    }

                                }

                            }
                        }
                    }
                  check = false;


                } else {

                    //stop animation and reset ready value
                    for (int i = 0; i < planetsamount; i++)
                    {

                        //circAnim
                        if (audioUnit[i] != null)
                        {
                            circMo[i].StopAnim();
                        }

                    }
                    //for sounds
                    PlayOrb1();
                    PlayOrb2();
                    PlayOrb3();
                    PlayOrb4();
                    PlayOrb5();


                    //reset values
                    radiuschange = 0;
                    extraheight = 130;



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
        {
            final int unit = 0;
            switch (SysNum)
            {
                case 1:
                    //TODO
                    break;
                case 2:
                    //TODO
                    break;
                case 3:
                    //TODO
                    break;
                case 4:
                    break;
                case 5:
                    switch (ChooseSoundsMenu.PlanetsVal[unit])
                    {
                        case 0:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.jupiter1v1, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                        case 1:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.jupiter1v2, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        case 2:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.jupiter1v3, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        case 3:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.jupiter1v4, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        default:
                            //do nothing
                    }
                    break;
                case 6:
                    //TODO
                    break;
                case 7:
                    //TODO
                    break;
                case 8:
                    switch (ChooseSoundsMenu.PlanetsVal[unit])
                    {
                        case 0:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.neptune1v1, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                        case 1:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.neptune1v2, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        case 2:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.neptune1v3, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        case 3:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.neptune1v4, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        default:
                            //do nothing
                    }
                    break;
                default:
                    //do nothing
            }

        }
    }

    //-------------------------------------------------------------------------------------------------------------------

    public void PlayOrb2() //audiounit[1], planetsval[1]. planetid1
    {
        {
            final int unit = 1;
            switch (SysNum)
            {
                case 1:
                    //TODO
                    break;
                case 2:
                    //TODO
                    break;
                case 3:
                    //TODO
                    break;
                case 4:
                    break;
                case 5:
                    switch (ChooseSoundsMenu.PlanetsVal[unit])
                    {
                        case 0:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.jupiter2v1, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                        case 1:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.jupiter2v2, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        case 2:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.jupiter2v3, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        case 3:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.jupiter2v4, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        default:
                            //do nothing
                    }
                    break;
                case 6:
                    //TODO
                    break;
                case 7:
                    //TODO
                    break;
                case 8:
                    switch (ChooseSoundsMenu.PlanetsVal[unit])
                    {
                        case 0:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.neptune2v1, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                        case 1:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.neptune2v2, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        case 2:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.neptune2v3, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        case 3:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.neptune2v4, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        default:
                            //do nothing
                    }
                    break;
                default:
                   // do nothing
            }

        }
    }
//---------------------------------------------------------------------------------------------------------------------------
    public void PlayOrb3()  //audiounit[2], planetsval[2]. planetid2
    {
        {
            final int unit = 2;
            switch (SysNum)
            {
                case 1:
                    //TODO
                    break;
                case 2:
                    //TODO
                    break;
                case 3:
                    //TODO
                    break;
                case 4:
                    break;
                case 5:
                    switch (ChooseSoundsMenu.PlanetsVal[unit])
                    {
                        case 0:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.jupiter3v1, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                        case 1:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.jupiter3v2, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        case 2:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.jupiter3v3, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        case 3:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.jupiter3v4, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        default:
                            //do nothing
                    }
                    break;
                case 6:
                    //TODO
                    break;
                case 7:
                    //TODO
                    break;
                case 8:
                    switch (ChooseSoundsMenu.PlanetsVal[unit])
                    {
                        case 0:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.neptune3v1, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                        case 1:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.neptune3v2, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        case 2:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.neptune3v3, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        case 3:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.neptune3v4, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        default:
                            //do nothing
                    }
                    break;
                default:
                    // do nothing
            }

        }
    }
    //--------------------------------------------------------------------------------------------------------------------------

    public void PlayOrb4() //audiounit[3], planetsval[3]. planetid3
    {
        {
            final int unit = 3;
            switch (SysNum)
            {
                case 1:
                    //TODO
                    break;
                case 2:
                    //TODO
                    break;
                case 3:
                    //TODO
                    break;
                case 4:
                    break;
                case 5:
                    switch (ChooseSoundsMenu.PlanetsVal[unit])
                    {
                        case 0:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.jupiter4v1, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                        case 1:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.jupiter4v2, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        case 2:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.jupiter4v3, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        case 3:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.jupiter4v4, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        default:
                            //do nothing
                    }
                    break;
                case 6:
                    //TODO
                    break;
                case 7:
                    //TODO
                    break;
                case 8:
                    switch (ChooseSoundsMenu.PlanetsVal[unit])
                    {
                        case 0:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.neptune4v1, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                        case 1:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.neptune4v2, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        case 2:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.neptune4v3, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        case 3:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.neptune4v4, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        default:
                            //do nothing
                    }
                    break;
                default:
                    // do nothing
            }

        }
    }

    public void PlayOrb5() //audiounit[4], planetsval[4]. planetid4
    {
        {
            final int unit = 4;
            switch (SysNum)
            {
                case 1:
                    //TODO
                    break;
                case 2:
                    //TODO
                    break;
                case 3:
                    //TODO
                    break;
                case 4:
                    break;
                case 5:
                    switch (ChooseSoundsMenu.PlanetsVal[unit])
                    {
                        case 0:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.jupiter5v1, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                        case 1:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.jupiter5v2, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        case 2:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.jupiter5v3, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        case 3:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.jupiter5v4, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        default:
                            //do nothing
                    }
                    break;
                case 6:
                    //TODO
                    break;
                case 7:
                    //TODO
                    break;
                case 8:
                    switch (ChooseSoundsMenu.PlanetsVal[unit])
                    {
                        case 0:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.neptune5v1, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                        case 1:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.neptune5v2, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        case 2:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.neptune5v3, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        case 3:
                            if (check)
                            {
                                audioUnit[unit] = new AudioLoader(this, R.raw.neptune5v4, unit);
                            }
                            else
                            {
                                stopSound(audioUnit[unit]);
                            }
                            break;
                        default:
                            //do nothing
                    }
                    break;
                default:
                    // do nothing
            }

        }
    }

    private void stopSound(AudioLoader audioforstop)
    {
        if (audioforstop != null)
        {
            audioforstop.StopSound();
            audioforstop = null;
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