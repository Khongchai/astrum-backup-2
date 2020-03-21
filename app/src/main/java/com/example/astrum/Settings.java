package com.example.astrum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class Settings extends AppCompatActivity
{

    private ImageButton ChooseSounds;
    private TextView ChooseSoundsText;

    //make them open about page
    private ImageButton About;
    private TextView AboutText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ChooseSounds = findViewById(R.id.ChooseSounds);
        ChooseSoundsText = findViewById(R.id.ChooseSoundsText);
        ChooseSounds.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openChooseSounds();
            }
        });
        ChooseSoundsText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ChooseSounds.performClick();
            }
        });

        //open about page pls---------------------------------------------------------------------------
        About = findViewById(R.id.chooseSounds2);
        AboutText = findViewById(R.id.About);

        About.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openAboutPage();
            }
        });

        AboutText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                About.performClick();
            }
        });
    }

 //----------------------------------------------------------------------------------------------

    public void openChooseSounds()
    {
        Intent intent = new Intent(this, ChooseSoundsMenu.class);
        startActivity(intent);
    }
    public void openAboutPage()
    {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

}
