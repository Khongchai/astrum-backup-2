package com.example.astrum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class Settings extends AppCompatActivity
{

    private ImageButton ChooseSounds;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ChooseSounds = findViewById(R.id.ChooseSounds);
        ChooseSounds.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openChooseSounds();
            }
        });

    }

    public void openChooseSounds()
    {
        Intent intent = new Intent(this, ChooseSoundsMenu.class);
        startActivity(intent);
    }
}
