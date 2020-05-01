package com.example.astrum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class ChooseSystems extends AppCompatActivity
{
    private EditText ValueField;
    private static int System;
    private ImageCycler imgCyc = new ImageCycler();
    private ImageView imgView;
    private static boolean firstStart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_systems);
        Log.d("System: ", String.valueOf(System));



        imgView = findViewById(R.id.ImageView);

        ValueField = findViewById(R.id.valueField);
        Button commitButton = findViewById(R.id.commitButton);

        if (System != 0)
        {

            imgCyc.setImageView(System - 1, imgView);
            YoYo.with(Techniques.DropOut).duration(1600).playOn(imgView);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    YoYo.with(Techniques.Pulse).duration(2000).repeat(Animation.INFINITE).playOn(imgView);
                }
            }, 1600);
        }





        commitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    System = Integer.parseInt(ValueField.getText().toString());
                    Log.d("System value:", String.valueOf(System));
                    if (System  > 8 || System < 0)
                    {
                        final Toast toast = Toast.makeText(getApplicationContext(), "Please enter a value between 1 - 8", Toast.LENGTH_SHORT);
                        toast.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new ToastCancel(toast), 2000);
                    }
                    else
                    {

                        if (!firstStart)
                        {
                            imgCyc.setImageView(System - 1, imgView);
                            YoYo.with(Techniques.DropOut).duration(1600).playOn(imgView);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    YoYo.with(Techniques.Pulse).duration(2000).repeat(Animation.INFINITE).playOn(imgView);
                                }
                            }, 2000);
                            firstStart = true;
                        }
                        else
                        {
                            YoYo.with(Techniques.ZoomOut).duration(1000).playOn(imgView);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    imgCyc.setImageView(System - 1, imgView);
                                    YoYo.with(Techniques.DropOut).duration(1300).playOn(imgView);
                                }
                            }, 1000);
                            //transition out animation
                            //change image after end of out duration
                            //transition in animation
                            //no need for pulse because pulse set to infinite above
                        }



                    }

                }
                catch (NullPointerException | NumberFormatException e )
                {
                    final Toast toast = Toast.makeText(getApplicationContext(), "Please enter a value between 1 - 8", Toast.LENGTH_SHORT);
                    toast.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new ToastCancel(toast), 2000);
                }


            }
        });


    }

    class ToastCancel implements Runnable
    {
        Toast toast;

        ToastCancel(Toast toast)
        {
            this.toast = toast;
        }

        @Override
        public void run()
        {
            toast.cancel();
        }

    }

    public int getSystemVal(){return System;}

}
