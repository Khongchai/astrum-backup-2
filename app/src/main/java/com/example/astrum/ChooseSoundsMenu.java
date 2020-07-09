package com.example.astrum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.astrum.MainActivity.planetsamount;

import static android.content.Context.MODE_PRIVATE;

public class ChooseSoundsMenu extends AppCompatActivity
{
    public static int PlanetsVal[] = new int[5];
    final Spinner Spinner[] = new Spinner[5];
    final int alltextSize = 7;
    private final int nosoundVal = 4;
   // private SeekBar VolumeSeekbar[] = new SeekBar[planetsamount];
   // static float VolumeVal[] = new float[planetsamount];

    Button CommitButton;
    int UpdateVal;
    EditText EnteredVal;

    private Context context;

    private TextView allText[] = new TextView[alltextSize];



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sounds_menu);
        context = this.context;

        CommitButton = findViewById(R.id.commitButton);
        UpdateVal = 0;
        EnteredVal = findViewById(R.id.editText);

        allText[0] = findViewById(R.id.textView3);
        allText[1] = findViewById(R.id.textView4);
        allText[2] = findViewById(R.id.textView5);
        allText[3] = findViewById(R.id.textView6);
        allText[4] = findViewById(R.id.textView7);
        allText[5] = findViewById(R.id.textView8);
        allText[6] = findViewById(R.id.Explaination);


        for (int i = 0; i < allText.length; i++)
        {
            allText[i].setTextColor(Color.parseColor("#FF90604C"));
        }
        allText[6].setLetterSpacing((float)0.1);
        allText[6].setGravity(Gravity.CENTER);

        if (PlanetsVal[0] == -1)
        {
            PlanetsVal[0] = 0;
        }






        CommitButton.setOnClickListener(new View.OnClickListener()
        {
            //fix the bug where when no value is put in, the fragment is destroyed.
            @Override
            public void onClick(View v)
            {
                try
                {
                    UpdateVal = Integer.parseInt(EnteredVal.getText().toString());
                    for (int i = 0; i < planetsamount; i++)
                    {
                        if (UpdateVal <= 0 || UpdateVal > (planetsamount - 2) +1  ){

                            final Toast toast = Toast.makeText(getApplicationContext(), "Value should be between 1-4", Toast.LENGTH_SHORT);
                            toast.show();
                            Handler StopToast = new Handler();
                            StopToast.postDelayed(new ToastCancel(toast), 2000);
                        }
                        else
                        {
                            Spinner[i].setSelection(UpdateVal - 1);
                        }
                    }
                }
                catch (NullPointerException | NumberFormatException e)
                {
                    final Toast toast = Toast.makeText(getApplicationContext(),"Please enter a value between 1 - 4" ,Toast.LENGTH_SHORT);
                    toast.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new ToastCancel(toast), 2000);
                }

            }
        });


        Spinner[0] = findViewById(R.id.SpinnerPlanet1);
        Spinner[1] = findViewById(R.id.SpinnerPlanet2);
        Spinner[2] = findViewById(R.id.spinnerPlanet3);
        Spinner[3] = findViewById(R.id.spinnerPlanet4);
        Spinner[4] = findViewById(R.id.spinnerPlanet5);

        String list[] = new String[]{
                "Sound 1",
                "Sound 2",
                "Sound 3",
                "Sound 4",
                "No sound"
        };

        //Declare drop down menus
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        R.layout.color_spinner_dropdown, list);


        //Set to drop down menus
        for (int i = 0; planetsamount > i; i++)
        {
            Spinner[i].setAdapter(adapter);
        }


//-------------------------------------------------------------------------------------------------
        Spinner[0].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //Create an int for the position
                PlanetsVal[0] = Spinner[0].getSelectedItemPosition();

                //If position is not "No sound", set ready status to 1
                if (PlanetsVal[0] != nosoundVal)
                {
                   MainActivity.CheckReady[0] = 1;
                }

                Log.d("MercuryLog", String.valueOf(Spinner[0].getSelectedItemPosition()));

                //Send it to sharedPref
                SharedPreferences sharedPref0 = getSharedPreferences("Mercury",MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = sharedPref0.edit();

                //Put it in to a key called "somekey"
                prefEditor.putInt("MercuryKey", Spinner[0].getSelectedItemPosition());
                prefEditor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }

        });

        //Load Venus value
        SharedPreferences sharedPref0 = getSharedPreferences("Mercury",MODE_PRIVATE);
        int MercuryShared = sharedPref0.getInt("MercuryKey", -1);
        if(MercuryShared != -1)
        {
            // set the selected value of the spinner
            Spinner[1].setSelection(MercuryShared);
        }

        //initControl1();


//-------------------------------------------------------------------------------------------------
        Spinner[1].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //Create an int for the position
                PlanetsVal[1] = Spinner[1].getSelectedItemPosition();
                Log.d("VenusLog", String.valueOf(Spinner[1].getSelectedItemPosition()));

                //If position is not "No sound", set ready status to 1

                if (PlanetsVal[1] != nosoundVal)
                {
                    MainActivity.CheckReady[1] = 1;
                }

                //Send it to sharedPref
                SharedPreferences sharedPref1 = getSharedPreferences("Venus",MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = sharedPref1.edit();

                //Put it in to a key called "somekey"
                prefEditor.putInt("VenusKey", Spinner[1].getSelectedItemPosition());
                prefEditor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }

        });

        //Load Venus value
        SharedPreferences sharedPref1 = getSharedPreferences("Venus",MODE_PRIVATE);
        int VenusShared = sharedPref1.getInt("VenusKey", -1);
        if(VenusShared != -1)
        {
            // set the selected value of the spinner
            Spinner[1].setSelection(VenusShared);
        }
//-------------------------------------------------------------------------------------------------
        Spinner[2].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //Create an int for the position
                PlanetsVal[2] = Spinner[2].getSelectedItemPosition();
                Log.d("EarthLog", String.valueOf(Spinner[2].getSelectedItemPosition()));

                //If position is not "No sound", set ready status to 1
                if (PlanetsVal[2] != nosoundVal)
                {
                   MainActivity.CheckReady[2] = 1;
                }

                //Send it to sharedPref
                SharedPreferences sharedPref2 = getSharedPreferences("Earth",MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = sharedPref2.edit();

                //Put it in to a key called "somekey"
                prefEditor.putInt("EarthKey", Spinner[2].getSelectedItemPosition());
                prefEditor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }

        });

        //Load Earth value
        SharedPreferences sharedPref2 = getSharedPreferences("Earth",MODE_PRIVATE);
        int EarthShared = sharedPref2.getInt("EarthKey", -1);
        if(EarthShared != -1)
        {
            // set the selected value of the spinner
            Spinner[2].setSelection(EarthShared);
        }
//-------------------------------------------------------------------------------------------------
        Spinner[3].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                PlanetsVal[3] = Spinner[3].getSelectedItemPosition();
                Log.d("MarsLog", String.valueOf(Spinner[3].getSelectedItemPosition()));

                //If position is not "No sound", set ready status to 1
                if (PlanetsVal[3] != nosoundVal)
                {
                   MainActivity.CheckReady[3] = 1;
                }

                //Send it to sharedPref
                SharedPreferences sharedPref3 = getSharedPreferences("Mars",MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = sharedPref3.edit();

                //Put it in to a key called "somekey"
                prefEditor.putInt("MarsKey", Spinner[3].getSelectedItemPosition());
                prefEditor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }

        });

        //Load Mars value
        SharedPreferences sharedPref3 = getSharedPreferences("Mars",MODE_PRIVATE);
        int MarsShared = sharedPref3.getInt("MarsKey", -1);
        if(MarsShared != -1)
        {
            // set the selected value of the spinner
            Spinner[3].setSelection(MarsShared);
        }
//-------------------------------------------------------------------------------------------------

        Spinner[4].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                PlanetsVal[4] = Spinner[4].getSelectedItemPosition();
                Log.d("JupiterLog", String.valueOf(Spinner[4].getSelectedItemPosition()));

                //If position is not "No sound", set ready status to 1
                if (PlanetsVal[4] != nosoundVal)
                {
                    MainActivity.CheckReady[4] = 1;
                }

                //Send it to sharedPref
                SharedPreferences sharedPref4 = getSharedPreferences("Jupiter",MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = sharedPref4.edit();

                //Put it in to a key called "somekey"
                prefEditor.putInt("JupiterKey", Spinner[4].getSelectedItemPosition());
                prefEditor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }

        });

        //Load Jupiter Value
        SharedPreferences sharedPref4 = getSharedPreferences("Jupiter",MODE_PRIVATE);
        int JupitergetShared = sharedPref4.getInt("JupiterKey", -1);
        if(JupitergetShared != -1)
        {
            // set the selected value of the spinner
            Spinner[4].setSelection(JupitergetShared);
        }



//-------------------------------------------------------------------------------------------------

    }//onCreate

    class ToastCancel implements Runnable
    {
        Toast toast;

        ToastCancel (Toast toast)
        {
            this.toast = toast;
        }

        @Override
        public void run()
        {
            toast.cancel();
        }
    }

/*
    private void initControl1()
    {
        try
        {
            //for controlling volume
            VolumeSeekbar[0] = findViewById(R.id.seekBar1);
            VolumeSeekbar[0].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
            {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
                {
                    VolumeVal[0] = progress;
                    Log.d("Progress", "Value"+VolumeVal[0]);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar)
                {

                }
            });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

*/

}
