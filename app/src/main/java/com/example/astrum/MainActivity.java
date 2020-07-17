package com.example.astrum;

import android.graphics.Point;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;
import android.content.Context;
import android.media.AudioManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import android.widget.VideoView;


public class MainActivity extends AppCompatActivity
{
    final static int planetsamount = 5;

    //Create an undeclared button object.
    private ImageButton SettingsButton;
    private Button OrbitButton;

    private Context currentAudioContext;

    private int seekBarsavedProg = 0;

    soundsStorage audioList = new soundsStorage();

    //create volume control
    private SeekBar VolumeSeekbar;
    private AudioManager audioManager;

    private boolean firststart = true;

    RunCheckDur Runnablecheckdur[] = new RunCheckDur[planetsamount];
    Thread threadcheckdur[] = new Thread[planetsamount];

    static AudioLoader audioUnit[] = new AudioLoader[planetsamount];

    private boolean check = true;
    static int[] CheckReady = new int[planetsamount];
    private final static int MAX_VOLUME = 100;

    private Handler planetHandler = new Handler();

    private static CircularMotion2[] circMo = new CircularMotion2[5];


    Button planetButtons[] = new Button[planetsamount];

    static float[] VolForMixerOnCreate = new float[planetsamount];
    VideoView[] planetsvid = new VideoView[planetsamount];

    static int SysNum;
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
            planetButtons[i].setVisibility(View.INVISIBLE);
        }

        OrbitButton = findViewById(R.id.OrbitButton);

        //initiate master volume slider
        initControls();

        ChooseSoundsMenu.PlanetsVal[0] = -1;
        SysNum = -1;

        //start animation for orbit
        OrbitButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                setSysNum();

                //which audio sounds get played should be decided here.
                if (check)
                {
                    //In functions below, use SystemSound value to decide which sound to play.
                    //If a system is chosen, else
                    if (SysNum > 0 && ChooseSoundsMenu.PlanetsVal[0] > -1)
                    {
                        for (int i = 0; i < planetsamount; i++)
                        {
                            loadAudioFiles(i, i, SysNum - 1);
                        }
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
                            final Toast toast = Toast.makeText(getApplicationContext(), "Please choose a planet", Toast.LENGTH_SHORT);
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

                            circMo[i] = new CircularMotion2(planetButtons[i], MidX, MidY, audioUnit[i].GetDur(), i, MaxX, offsetleft, offsettop);
                            if (CheckReady[i] == 1)
                            {
                                circMo[i].LoadAnim();
                            }
                            if (firststart)
                            {
                                firststart = false;
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
                    if (SysNum > 0)
                    {
                        check = false;
                    }

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

                    for (int i = 0; i < planetsamount; i++)
                    {
                        loadAudioFiles(i, i, SysNum - 1);
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
        setSysNum();
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


    public void openSettings()
    {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);

    }
    boolean getcheck()
    {
        return check;
    }

    public void  loadAudioFilesfromFrag(final int spinnerNo, final int orbNo, final int planet, final int spinnerVal)
    {
        final int[][] audioArray = audioList.getAudioArray(orbNo);

        if (audioUnit[spinnerNo] != null)
        {
             if (spinnerVal == 4)
            {
                circMo[spinnerNo].StopAnim();
                audioUnit[spinnerNo].StopSound();
            }
            else if (audioUnit[spinnerNo].getResID() != audioArray[planet][spinnerVal])
            {
                circMo[spinnerNo].StopAnim();
                audioUnit[spinnerNo].StopSound();

                audioUnit[spinnerNo] = new AudioLoader(MyApplication.getAppContext(), audioArray[planet][spinnerVal], spinnerNo);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run()
                    {
                        audioUnit[spinnerNo].PlayAudio();
                        circMo[spinnerNo].LoadAnim();
                    }

                }, 500);
            }


        }

    }
    private void loadAudioFiles(int spinnerNo, int orbNo, int planet)
    {


        if (orbNo > -1) //arbitrary uninitialized value is -1
        {
            int[][] audioArray = audioList.getAudioArray(orbNo);
            for (int j = 0; j < audioArray[planet].length; j++)
            {
                if (check)
                {
                    if (j == ChooseSoundsMenu.PlanetsVal[spinnerNo])
                    {
                        audioUnit[spinnerNo] = new AudioLoader(MyApplication.getAppContext(), audioArray[planet][j], spinnerNo);
                    }
                }
                else
                {
                    stopSound(audioUnit[spinnerNo]);
                }

            }
        }


    }

    private void stopSound(AudioLoader audioforstop)
    {
        if (audioforstop != null)
        {
            audioforstop.StopSound();
        }

    }



    public boolean isNotPlaying(){return check;}

    public int getSysNum(){return SysNum;}

    private void setSysNum()
    {
       SysNum = chooseSystems.getSystemVal();
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