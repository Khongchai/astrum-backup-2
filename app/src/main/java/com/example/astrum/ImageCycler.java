package com.example.astrum;

import android.util.Log;
import android.widget.ImageView;


public class ImageCycler

{

    private int[] ImageIDs = new int[]
            {
                    R.drawable.mecury,
                    R.drawable.venus,
                    R.drawable.earth,
                    R.drawable.mars,
                    R.drawable.jupiter,
                    R.drawable.saturn,
                    R.drawable.uranus,
                    R.drawable.neptune
            };

    void setImageView (int count, ImageView imgView)
    {

        Log.d("countval", String.valueOf(count));
        imgView.setBackgroundResource(ImageIDs[count]);
    }


}
