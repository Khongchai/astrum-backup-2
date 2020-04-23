package com.example.astrum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChooseSystems extends AppCompatActivity
{
    private EditText ValueField;
    private static int System;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_systems);
        System = 0;

        ValueField = findViewById(R.id.valueField);
        Button commitButton = findViewById(R.id.commitButton);

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
                        final Toast toast = Toast.makeText(getApplicationContext(), "Please enter a value between 1 - 7", Toast.LENGTH_SHORT);
                        toast.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new ToastCancel(toast), 2000);
                    }
                    else
                    {
                        //leave system value the same; to be read by anything
                    }

                }
                catch (NullPointerException | NumberFormatException e )
                {
                    final Toast toast = Toast.makeText(getApplicationContext(), "Please enter a value between 1 - 7", Toast.LENGTH_SHORT);
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
