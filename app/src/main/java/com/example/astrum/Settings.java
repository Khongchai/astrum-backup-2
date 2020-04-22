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

    private ImageButton ChooseSystems;
    private TextView ChooseSysText;

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
        //open ChooseSyspage----------------------------------------------------------------------
        ChooseSystems = findViewById(R.id.ChooseSystemicon);
        ChooseSysText = findViewById(R.id.ChooseSystemText);

        ChooseSysText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openChooseSysPage();
            }
        });

        ChooseSystems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseSysText.performClick();
            }
        });

        //open about pls---------------------------------------------------------------------------
        About = findViewById(R.id.About);
        AboutText = findViewById(R.id.AboutText);

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
        startActivity(new Intent(this, ChooseSoundsMenu.class));
    }
    public void openAboutPage()
    {
        startActivity(new Intent(this, About.class));
    }

    public void openChooseSysPage()
    {
        startActivity(new Intent(this, ChooseSystems.class));
    }

}
