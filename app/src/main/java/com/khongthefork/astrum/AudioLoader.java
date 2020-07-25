package com.khongthefork.astrum;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class AudioLoader {
    private MediaPlayer curPlayer;
    private MediaPlayer nextPlayer = null;

    private Context context;
    private int ResID;
    private int PlanetID;
    private int audioDuration;
    private int refIDForSetSound;
    MainActivity mainActivity = new MainActivity();

    AudioLoader(Context context, int ID, int PlanetID) {
        this.PlanetID = PlanetID;
        this.context = context;
        ResID = ID;

        curPlayer = MediaPlayer.create(context, ResID);
        audioDuration = curPlayer.getDuration();
    }


     void PlayAudio()
     {
         if (curPlayer != null)
         {
             curPlayer.start();
             nextPlayer = MediaPlayer.create(context, ResID);

             curPlayer.setNextMediaPlayer(nextPlayer);
             curPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                 @Override
                 public void onCompletion(MediaPlayer mp) {
                     curPlayer.release();
                     curPlayer = nextPlayer;
                     PlayAudio();
                     mainActivity.setVolumeForNewSounds(PlanetID);
                 }
             });
         }

    }

     void StopSound()
     {

         for (int i = 0; i < 2; i++)
         {
             try
             {
                 curPlayer.stop();
                 curPlayer.release();
                 curPlayer = null;

                 nextPlayer.stop();
                 nextPlayer.release();
                 nextPlayer = null;
             }
             catch (NullPointerException e)
             {
                 e.printStackTrace();
             }

         }

    }



    void resetAudioandPause()
    {
        for (int i = 0; i < 2; i++)
        {
            try
            {
                //resets head only
                curPlayer.stop();
                nextPlayer.stop();
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }

        }
    }


     int GetDur()
     {
         if (curPlayer != null)
         {
             return curPlayer.getDuration();
         }
         return 0;
    }

    public MediaPlayer CheckNull()
    {
        return curPlayer;
    }

    public void setRefIDForSetSound(int refIDForSetSound)
    {
        this.refIDForSetSound = refIDForSetSound;
    }

    public void pauseAudio()
    {
        try
        {
            curPlayer.pause();
            nextPlayer.pause();
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    public void continueAudio()
    {
        try
        {
            curPlayer.start();
            nextPlayer.start();
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    public void scrollThroughTime(float dy)
    {
        int differenceY = (int)dy;

        try {
            int currentTimeCurPlayer = curPlayer.getCurrentPosition();
            int shiftValue = currentTimeCurPlayer - differenceY;
            //if shift value exceed or less than duration
            if (shiftValue > audioDuration)
            {
                shiftValue = 0;
            }
            else if (shiftValue < 0)
            {
                shiftValue = audioDuration;
            }
            if (curPlayer != null)
            {
                curPlayer.seekTo(shiftValue);
            }
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

     void SetVolume(float float1, float float2)
     {
        if (curPlayer != null)
        {
            curPlayer.setVolume(float1, float2);
        }
    }

    public int getResID() {
        return ResID;
    }
}

