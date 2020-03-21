package com.example.astrum;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import static com.example.astrum.MainActivity.PlanetsMP;
import static com.example.astrum.MainActivity.VolForMixerOnCreate;

public class AdjustVolumeFrag extends Fragment
{

    private final static int MAX_VOLUME = 100;
    public static SeekBar[] seek = new SeekBar[8];
    public static float[] volume = new float[8];



    //Inherit from fragments
    //Override oncreate view
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        for (int i = 0; i < 8; i++)
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
        seek[5] = view.findViewById(R.id.seekBar6);
        seek[6] = view.findViewById(R.id.seekBar7);
        seek[7] = view.findViewById(R.id.seekBar8);
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
        SharedPreferences sharedPref1 = getContext().getSharedPreferences("progress0", MODE_PRIVATE);

        //Store loaded value in here
        int seek0GetShared = sharedPref1.getInt("Progress for 0", -1);

        //means if seek0GetShared not empty
        if (seek0GetShared != -1)
        {
            seek[0].setProgress(seek0GetShared);
        }
        //---------------------------------------------------------------------------------------------

    }

    private void LinkSounds()
    {
        seek[0].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                //set to scale logarithmically so it feels natural
                volume[0] = (float) (1 - (Math.log(MAX_VOLUME - progress) / Math.log(MAX_VOLUME)));
                if (PlanetsMP[0] != null)
                {
                    PlanetsMP[0].setVolume(volume[0], volume[0]);
                   // volume = VolForMixerOnCreate[0];
                }

                //Send it to sharedPref
                SharedPreferences sharedPref1 = getContext().getSharedPreferences("progress0", MODE_PRIVATE);
                SharedPreferences.Editor SharedPref1Editor = sharedPref1.edit();
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
                float volume = (float) (1 - (Math.log(MAX_VOLUME - progress) / Math.log(MAX_VOLUME)));
                if (PlanetsMP[1] != null)
                {
                    PlanetsMP[1].setVolume(volume, volume);
                }


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
                float volume = (float) (1 - (Math.log(MAX_VOLUME - progress) / Math.log(MAX_VOLUME)));
                if (PlanetsMP[2] != null)
                {
                    PlanetsMP[2].setVolume(volume, volume);
                }


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
                float volume = (float) (1 - (Math.log(MAX_VOLUME - progress) / Math.log(MAX_VOLUME)));
                if (PlanetsMP[3] != null)
                {
                    PlanetsMP[3].setVolume(volume, volume);
                }


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
                float volume = (float) (1 - (Math.log(MAX_VOLUME - progress) / Math.log(MAX_VOLUME)));
                if (PlanetsMP[4] != null)
                {
                    PlanetsMP[4].setVolume(volume, volume);
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seek[5].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                //set to scale logarithmically so it feels natural
                float volume = (float) (1 - (Math.log(MAX_VOLUME - progress) / Math.log(MAX_VOLUME)));
                if (PlanetsMP[5] != null)
                {
                    PlanetsMP[5].setVolume(volume, volume);
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seek[6].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                //set to scale logarithmically so it feels natural
                float volume = (float) (1 - (Math.log(MAX_VOLUME - progress) / Math.log(MAX_VOLUME)));
                if (PlanetsMP[6] != null)
                {
                    PlanetsMP[6].setVolume(volume, volume);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seek[7].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                //set to scale logarithmically so it feels natural
                float volume = (float) (1 - (Math.log(MAX_VOLUME - progress) / Math.log(MAX_VOLUME)));
                if (PlanetsMP[7] != null)
                {
                    PlanetsMP[7].setVolume(volume, volume);
                }
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



