package com.khongthefork.astrum;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class AdjustVolumeFrag extends Fragment
{

    private final static int MAX_VOLUME = 100;
    static SeekBar[] seek = new SeekBar[MainActivity.planetsamount];
    static float[] volume = new float[MainActivity.planetsamount];
    int ii;
    SharedPreferences sharedPrefLink[] = new SharedPreferences[MainActivity.planetsamount];
    static Spinner[] spinner = new Spinner[5];
    //ChooseSoundsMenu getSpinnerVal = new ChooseSoundsMenu();
    MainActivity mainActivity = new MainActivity();

    int noSoundValue = 4;

    int spinnerNo = 0;
    int orbNo = spinnerNo;




    //Inherit from fragments
    //Override oncreate view
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        //basically just saying that volumemixerfrag layout is a fragment
        View view = inflater.inflate(R.layout.volumemixerfrag, container, false);
        seek[0] = view.findViewById(R.id.seekBar1);
        seek[1] = view.findViewById(R.id.seekBar2);
        seek[2] = view.findViewById(R.id.seekBar3);
        seek[3] = view.findViewById(R.id.seekBar4);
        seek[4] = view.findViewById(R.id.seekBar5);

        spinner[0] = view.findViewById(R.id.spinnerone);
        spinner[1] = view.findViewById(R.id.spinnertwo);
        spinner[2] = view.findViewById(R.id.spinnerthree);
        spinner[3] = view.findViewById(R.id.spinnerfour);
        spinner[4] = view.findViewById(R.id.spinnerfive);

        String list[] = new String[]{
                "Sound 1",
                "Sound 2",
                "Sound 3",
                "Sound 4",
                "No Sound"
        };

        //Declare drop down menus
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Objects.requireNonNull(this.getActivity()),
                R.layout.color_spinner_dropdowntwo, list);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(Objects.requireNonNull(this.getActivity()),
                R.layout.color_spinner_dropdownthree, list);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(Objects.requireNonNull(this.getActivity()),
                R.layout.color_spinner_dropdownfour, list);
        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(Objects.requireNonNull(this.getActivity()),
                R.layout.color_spinner_dropdownfive, list);
        ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(Objects.requireNonNull(this.getActivity()),
                R.layout.color_spinner_dropdownsix, list);

        //Set to drop down menus
        spinner[0].setAdapter(adapter2);
        spinner[1].setAdapter(adapter3);
        spinner[2].setAdapter(adapter4);
        spinner[3].setAdapter(adapter5);
        spinner[4].setAdapter(adapter6);

        for (int i = 0; i < spinner.length; i++)
        {
            volume[i] = -1;
            if (ChooseSoundsMenu.PlanetsVal[0] == -1 || mainActivity.getSysNum() == -1)
            {
                spinner[i].setVisibility(View.INVISIBLE);
            }
        }


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

        spinner[0].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            int spinnerNo = 0;
            int orbNo = spinnerNo;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                ChooseSoundsMenu.PlanetsVal[spinnerNo] = spinner[spinnerNo].getSelectedItemPosition();

                //for checking if it should appear
                if (ChooseSoundsMenu.PlanetsVal[spinnerNo] == noSoundValue)
                {
                    spinner[0].setEnabled(false);
                    MainActivity.CheckReady[spinnerNo] = 0;
                }
                else
                {
                    spinner[0].setEnabled(true);
                    MainActivity.CheckReady[spinnerNo] = 1;
                }


                mainActivity.loadAudioFilesfromFrag(spinnerNo, orbNo, mainActivity.getSysNum() - 1, ChooseSoundsMenu.PlanetsVal[spinnerNo]);

                try
                {
                    mainActivity.setVolumeForNewSounds(spinnerNo);
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }

        });

        spinner[1].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            int spinnerNo = 1;
            int orbNo = spinnerNo;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                ChooseSoundsMenu.PlanetsVal[spinnerNo] = spinner[spinnerNo].getSelectedItemPosition();

                //for checking if it should appear
                if (ChooseSoundsMenu.PlanetsVal[spinnerNo] == noSoundValue)
                {
                    spinner[1].setEnabled(false);
                    MainActivity.CheckReady[spinnerNo] = 0;
                }
                else
                {
                    spinner[1].setEnabled(true);
                    MainActivity.CheckReady[spinnerNo] = 1;
                }


                mainActivity.loadAudioFilesfromFrag(spinnerNo, orbNo, mainActivity.getSysNum() - 1, ChooseSoundsMenu.PlanetsVal[spinnerNo]);

                try
                {
                    mainActivity.setVolumeForNewSounds(spinnerNo);
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }

        });

        spinner[2].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            int spinnerNo = 2;
            int orbNo = spinnerNo;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                ChooseSoundsMenu.PlanetsVal[spinnerNo] = spinner[spinnerNo].getSelectedItemPosition();

                //for checking if it should appear
                if (ChooseSoundsMenu.PlanetsVal[spinnerNo] == noSoundValue)
                {
                    spinner[2].setEnabled(false);
                    MainActivity.CheckReady[spinnerNo] = 0;
                }
                else
                {
                    spinner[2].setEnabled(true);
                    MainActivity.CheckReady[spinnerNo] = 1;
                }

                mainActivity.loadAudioFilesfromFrag(spinnerNo, orbNo, mainActivity.getSysNum() - 1, ChooseSoundsMenu.PlanetsVal[spinnerNo]);

                try
                {
                    mainActivity.setVolumeForNewSounds(spinnerNo);
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }

        });

        spinner[3].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            int spinnerNo = 3;
            int orbNo = spinnerNo;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {


                ChooseSoundsMenu.PlanetsVal[spinnerNo] = spinner[spinnerNo].getSelectedItemPosition();

                //for checking if it should appear
                if (ChooseSoundsMenu.PlanetsVal[spinnerNo] == noSoundValue)
                {
                    MainActivity.CheckReady[spinnerNo] = 0;
                    spinner[3].setEnabled(false);
                }
                else
                {
                    spinner[3].setEnabled(true);
                    MainActivity.CheckReady[spinnerNo] = 1;
                }
                mainActivity.loadAudioFilesfromFrag(spinnerNo, orbNo, mainActivity.getSysNum() - 1, ChooseSoundsMenu.PlanetsVal[spinnerNo]);

                try
                {
                    mainActivity.setVolumeForNewSounds(spinnerNo);
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }

        });

        spinner[4].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            int spinnerNo = 4;
            int orbNo = spinnerNo;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {


                ChooseSoundsMenu.PlanetsVal[spinnerNo] = spinner[spinnerNo].getSelectedItemPosition();

                //for checking if it should appear
                if (ChooseSoundsMenu.PlanetsVal[spinnerNo] == noSoundValue)
                {
                    spinner[4].setEnabled(false);
                    MainActivity.CheckReady[spinnerNo] = 0;
                }
                else
                {
                    spinner[4].setEnabled(true);
                    MainActivity.CheckReady[spinnerNo] = 1;
                }

                mainActivity.loadAudioFilesfromFrag(spinnerNo, orbNo, mainActivity.getSysNum() - 1, ChooseSoundsMenu.PlanetsVal[spinnerNo]);

                try
                {
                    mainActivity.setVolumeForNewSounds(spinnerNo);
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }

        });


        return view;

    }//oncreate



    public void LoadProgress()
    {
        SharedPreferences[]  sharedPref= new SharedPreferences[MainActivity.planetsamount];
        int[]  seekGetShared= new int[MainActivity.planetsamount];
        for (int i = 0; i < MainActivity.planetsamount; i++)
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
                if (MainActivity.audioUnit[0] != null)
                {
                    mainActivity.setVolumeForNewSounds(0);

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
                if (MainActivity.audioUnit[1] != null)
                {
                    mainActivity.setVolumeForNewSounds(1);
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
                if (MainActivity.audioUnit[2] != null)
                {
                    mainActivity.setVolumeForNewSounds(2);
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
                if (MainActivity.audioUnit[3] != null)
                {
                    mainActivity.setVolumeForNewSounds(3);
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
                if (MainActivity.audioUnit[4] != null)
                {
                    mainActivity.setVolumeForNewSounds(4);
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

    @Override
    public void onResume() {
        super.onResume();
        if (mainActivity.getSysNum() > 0)
        {
            ChooseSoundsMenu chooseSoundsMenu = new ChooseSoundsMenu();
            int[] planetsVal = chooseSoundsMenu.getPlanetsVal();
            for (int i = 0; i < spinner.length; i++)
            {
                {
                    spinner[i].setSelection(planetsVal[i]);
                    spinner[i].setVisibility(View.VISIBLE);
                }


            }
        }

    }

    public float getProgress(int i) {return seek[i].getProgress();}


}



