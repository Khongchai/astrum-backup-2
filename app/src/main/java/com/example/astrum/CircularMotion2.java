package com.example.astrum;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;


public class CircularMotion2

{

    //passed
    private Button orb;

    long currentPlayTime = 0;

    //not passed
    private ObjectAnimator animator = new ObjectAnimator();


    CircularMotion2(Button orb, float MidX, float MidY, int duration, int i, float MaxX, float offsetleft, float offsettop)
    {


        //ratios for radius
        float radius;
        MidX = MidX - offsetleft;
        MidY = MidY - offsettop;

        float[] sidelength = new float[] {8, 5.5f, 4.1f, 3, 2.6f};
        Path path = new Path();

        this.orb = orb;

        radius = MaxX/sidelength[i];
        path.arcTo(MidX - radius, MidY - radius, MidX + radius, MidY + radius, 270f, 359f, true);
        animator = ObjectAnimator.ofFloat(orb, View.X, View.Y, path);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(duration);
        animator.setRepeatCount(Animation.INFINITE);

    }
//a
    void LoadAnim ()
    {
        orb.setVisibility(View.VISIBLE);
        animator.start();
        YoYo.with(Techniques.FadeIn).duration(2000).playOn(orb);

    }


    void StopAnim()
    {
        orb.setVisibility(View.INVISIBLE);
        animator.cancel();

    }

    void pauseAnim()
    {

        currentPlayTime = animator.getCurrentPlayTime();
        animator.pause();
    }
    void continueAnim()
    {

        animator.start();
        animator.setCurrentPlayTime(currentPlayTime);
    }
    void scrollThroughTime(float dy)
    {
        int differenceY = (int)dy;
        Log.d("dy", String.valueOf(dy));
       currentPlayTime -= differenceY;
       animator.setCurrentPlayTime(currentPlayTime);
    }

    void setDuration (int duration)
    {
        if (duration >= 0)
        {
            animator.setDuration(duration);
        }
    }
}
