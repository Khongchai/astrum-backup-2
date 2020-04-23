package com.example.astrum;
import android.content.Context;
import android.media.MediaPlayer;

public class AudioLoader {
    private MediaPlayer curPlayer = null;
    private MediaPlayer nextPlayer = null;

    private Context context = null;
    private int ResID = 0;
    private int PlanetID = 0;

    AudioLoader(Context context, int ID, int PlanetID) {
        this.PlanetID = PlanetID;
        this.context = context;
        ResID = ID;
        curPlayer = MediaPlayer.create(this.context, ResID);
    }


     void PlayAudio() {
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

     void StopSound()
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

     int GetDur() {
        return curPlayer.getDuration();
    }

    public MediaPlayer CheckNull() {
        return curPlayer;
    }

     void SetVolume(float float1, float float2) {

        if (curPlayer != null) {
            curPlayer.setVolume(float1, float2);
        }
    }
}

