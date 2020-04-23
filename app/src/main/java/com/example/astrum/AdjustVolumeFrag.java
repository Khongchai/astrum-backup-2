package com.example.astrum;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import static android.content.Context.MODE_PRIVATE;
import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.example.astrum.MainActivity.VolForMixerOnCreate;
import static com.example.astrum.MainActivity.audioUnit;
import static com.example.astrum.MainActivity.planetsamount;

public class AdjustVolumeFrag extends Fragment
{

    private final static int MAX_VOLUME = 100;
    public static SeekBar[] seek = new SeekBar[planetsamount];
    public static float[] volume = new float[planetsamount];
    int ii;
    SharedPreferences sharedPrefLink[] = new SharedPreferences[planetsamount];



    //Inherit from fragments
    //Override oncreate view
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        for (int i = 0; i < planetsamount; i++)
        {
            volume[i] = -1;
        }
        //basically just saying that volumemixerfrag layout is a fragment
        View view = inflater.inflate(R.layout.volumemixerfrag, container, false);
        seek[0] = view.findViewById(R.id.seekBar1);
        seek[1] = view.findViewById(R.id.seekBar2);
        seek[2] = view.findViewById(R.id.seekBar3);
        seek[3] = view.findViewById(R.id.seekBar4);
        seek[4] = view.findViewById(R.id.seekBar5);

        LinkSounds();
        LoadProgress();

        Button closeMixer = view.findViewById(R.id.closeMixer);
        closeMixer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                fr.replace(R.id.FragmentsContainer, new openadjustvolume());
                fr.commit();
            }
        });
        return view;


    }

    public void LoadProgress()
    {
        SharedPreferences[]  sharedPref= new SharedPreferences[planetsamount];
        int[]  seekGetShared= new int[planetsamount];
        for (int i = 0; i < planetsamount; i++)
        {
            sharedPref[i] = getContext().getSharedPreferences(String.format("progress%d", i), MODE_PRIVATE);
            seekGetShared[i] = sharedPref[i].getInt(String.format("Progress for %d", i), -1);
            //means if seek0GetShared not empty
            if (seekGetShared[i] != -1)
            {
                seek[i].setProgress(seekGetShared[i]);
            }
        }

    }

    //try to shorten this set of code
    private void LinkSounds()
    {
        seek[0].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                //set to scale logarithmically so it feels natural
                volume[0] = (float) (1 - (Math.log(MAX_VOLUME - progress) / Math.log(MAX_VOLUME)));
                if (audioUnit[0] != null)
                {
                    audioUnit[0].SetVolume(volume[0], volume[0]);
                   // volume = VolForMixerOnCreate[0];
                }

                //Send it to sharedPref
                SharedPreferences sharedPref0 = getContext().getSharedPreferences("progress0", MODE_PRIVATE);
                SharedPreferences.Editor SharedPref1Editor = sharedPref0.edit();
                SharedPref1Editor.putInt("Progress for 0", progress).commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seek[1].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                //set to scale logarithmically so it feels natural
                volume[1] = (float) (1 - (Math.log(MAX_VOLUME - progress) / Math.log(MAX_VOLUME)));
                if (audioUnit[1] != null)
                {
                    audioUnit[1].SetVolume(volume[1], volume[1]);
                }

                //Send it to sharedPref
                SharedPreferences sharedPref1 = getContext().getSharedPreferences("progress1", MODE_PRIVATE);
                SharedPreferences.Editor SharedPref1Editor = sharedPref1.edit();
                SharedPref1Editor.putInt("Progress for 1", progress).commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seek[2].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                //set to scale logarithmically so it feels natural
                volume[2] = (float) (1 - (Math.log(MAX_VOLUME - progress) / Math.log(MAX_VOLUME)));
                if (audioUnit[2] != null)
                {
                    audioUnit[2].SetVolume(volume[2], volume[2]);
                }

                //Send it to sharedPref
                SharedPreferences sharedPref2 = getContext().getSharedPreferences("progress2", MODE_PRIVATE);
                SharedPreferences.Editor SharedPref2Editor = sharedPref2.edit();
                SharedPref2Editor.putInt("Progress for 2", progress).commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seek[3].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                //set to scale logarithmically so it feels natural
                volume[3] = (float) (1 - (Math.log(MAX_VOLUME - progress) / Math.log(MAX_VOLUME)));
                if (audioUnit[3] != null)
                {
                    audioUnit[3].SetVolume(volume[3], volume[3]);
                }


                //Send it to sharedPref
                SharedPreferences sharedPref3 = getContext().getSharedPreferences("progress3", MODE_PRIVATE);
                SharedPreferences.Editor SharedPref3Editor = sharedPref3.edit();
                SharedPref3Editor.putInt("Progress for 3", progress).commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seek[4].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                //set to scale logarithmically so it feels natural
                volume[4]= (float) (1 - (Math.log(MAX_VOLUME - progress) / Math.log(MAX_VOLUME)));
                if (audioUnit[4] != null)
                {
                    audioUnit[4].SetVolume(volume[4], volume[4]);
                }

                //Send it to sharedPref
                SharedPreferences sharedPref4 = getContext().getSharedPreferences("progress4", MODE_PRIVATE);
                SharedPreferences.Editor SharedPref4Editor = sharedPref4.edit();
                SharedPref4Editor.putInt("Progress for 4", progress).commit();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public float getProgress(int i) {return seek[i].getProgress();}


}



