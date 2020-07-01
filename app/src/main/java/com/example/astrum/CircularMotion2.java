package com.example.astrum;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Handler;
import android.text.Layout;
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

    void LoadAnim ()
    {
        animator.start();
        YoYo.with(Techniques.FadeIn).duration(4000).playOn(orb);

    }

    void StopAnim()
    {

        YoYo.with(Techniques.FadeOut).duration(2000).playOn(orb);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                animator.cancel();
            }
        }, 2000);

    }

}
