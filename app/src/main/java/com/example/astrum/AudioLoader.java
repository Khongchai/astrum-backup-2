package com.example.astrum;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class AudioLoader {
    private MediaPlayer curPlayer;
    private MediaPlayer nextPlayer = null;

    private Context context;
    private int ResID;
    private int PlanetID;

    AudioLoader(Context context, int ID, int PlanetID) {
        this.PlanetID = PlanetID;
        this.context = context;
        ResID = ID;
        Log.d("context", String.valueOf(context));

        curPlayer = MediaPlayer.create(context, ResID);
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
                     nextPlayer.setVolume(AdjustVolumeFrag.seek[PlanetID].getProgress(), AdjustVolumeFrag.seek[PlanetID].getProgress()); //this line somehow affects all audio sometimes?
                     curPlayer.release();
                     curPlayer = nextPlayer;

                     PlayAudio();
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

    public void scrollThroughTime()
    {

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

