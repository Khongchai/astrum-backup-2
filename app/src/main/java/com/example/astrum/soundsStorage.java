package com.example.astrum;


import android.util.Log;

import java.lang.reflect.Array;
import java.util.Arrays;

public class soundsStorage
{


    private int[] mercuryAudio1 = new int[]
            {
                    R.raw.mercury1v1,
                    R.raw.mercury1v2,
                    R.raw.mercury1v3,
                    R.raw.mercury1v4
            };
    private int[] mercuryAudio2 = new int[]
            {
                    R.raw.mercury2v1,
                    R.raw.mercury2v2,
                    R.raw.mercury2v3,
                    R.raw.mercury2v4
            };
    private int[] mercuryAudio3 = new int[]
            {
                    R.raw.mercury3v1,
                    R.raw.mercury3v2,
                    R.raw.mercury3v3,
                    R.raw.mercury3v4
            };
    private int[] mercuryAudio4 = new int[]
            {
                    R.raw.mercury4v1,
                    R.raw.mercury4v2,
                    R.raw.mercury4v3,
                    R.raw.mercury4v4
            };
    private int[] mercuryAudio5 = new int[]
            {
                    R.raw.mercury5v1,
                    R.raw.mercury5v2,
                    R.raw.mercury5v3,
                    R.raw.mercury5v4
            };
    //------------------------------------------------------------------------------------------------
    private int[] venusAudio1 = new int[]
            {
                    R.raw.venus1v1,
                    R.raw.venus1v2,
                    R.raw.venus1v3,
                    R.raw.venus1v4
            };
    private int[] venusAudio2 = new int[]
            {
                    R.raw.venus2v1,
                    R.raw.venus2v2,
                    R.raw.venus2v3,
                    R.raw.venus2v4
            };
    private int[] venusAudio3 = new int[]
            {
                    R.raw.venus3v1,
                    R.raw.venus3v2,
                    R.raw.venus3v3,
                    R.raw.venus3v4
            };
    private int[] venusAudio4 = new int[]
            {
                    R.raw.venus4v1,
                    R.raw.venus4v2,
                    R.raw.venus4v3,
                    R.raw.venus4v4
            };
    private int[] venusAudio5 = new int[]
            {
                    R.raw.venus5v1,
                    R.raw.venus5v2,
                    R.raw.venus5v3,
                    R.raw.venus5v4
            };
    //------------------------------------------------------------------------------------------------

    private int[] earthAudio1 = new int[]
            {
                    R.raw.earth1v1,
                    R.raw.earth1v2,
                    R.raw.earth1v3,
                    R.raw.earth1v4
            };
    private int[] earthAudio2 = new int[]
            {
                    R.raw.earth2v1,
                    R.raw.earth2v2,
                    R.raw.earth2v3,
                    R.raw.earth2v4
            };
    private int[] earthAudio3 = new int[]
            {
                    R.raw.earth3v1,
                    R.raw.earth3v2,
                    R.raw.earth3v3,
                    R.raw.earth3v4
            };
    private int[] earthAudio4 = new int[]
            {
                    R.raw.earth4v1,
                    R.raw.earth4v2,
                    R.raw.earth4v3,
                    R.raw.earth4v4
            };
    private int[] earthAudio5 = new int[]
            {
                    R.raw.earth5v1,
                    R.raw.earth5v2,
                    R.raw.earth5v3,
                    R.raw.earth5v4
            };
    //------------------------------------------------------------------------------------------------
    private int[] marsAudio1 = new int[]
            {
                    R.raw.mars1v1,
                    R.raw.mars1v2,
                    R.raw.mars1v3,
                    R.raw.mars1v4
            };
    private int[] marsAudio2 = new int[]
            {
                    R.raw.mars1v1,
                    R.raw.mars2v2,
                    R.raw.mars2v3,
                    R.raw.mars2v4
            };
    private int[] marsAudio3 = new int[]
            {
                    R.raw.mars3v1,
                    R.raw.mars3v2,
                    R.raw.mars3v3,
                    R.raw.mars3v4
            };
    private int[] marsAudio4 = new int[]
            {
                    R.raw.mars4v1,
                    R.raw.mars4v2,
                    R.raw.mars4v3,
                    R.raw.mars4v4
            };
    private int[] marsAudio5 = new int[]
            {
                    R.raw.mars5v1,
                    R.raw.mars5v2,
                    R.raw.mars5v3,
                    R.raw.mars5v4
            };
    //------------------------------------------------------------------------------------------------
    private int[] jupiterAudio1 = new int[]
            {
                    R.raw.jupiter1v1,
                    R.raw.jupiter1v2,
                    R.raw.jupiter1v3,
                    R.raw.jupiter1v4
            };
    private int[] jupiterAudio2 = new int[]
            {
                    R.raw.jupiter2v1,
                    R.raw.jupiter2v2,
                    R.raw.jupiter2v3,
                    R.raw.jupiter2v4
            };
    private int[] jupiterAudio3 = new int[]
            {
                    R.raw.jupiter3v1,
                    R.raw.jupiter3v2,
                    R.raw.jupiter3v3,
                    R.raw.jupiter3v4
            };
    private int[] jupiterAudio4 = new int[]
            {
                    R.raw.jupiter4v1,
                    R.raw.jupiter4v2,
                    R.raw.jupiter4v3,
                    R.raw.jupiter4v4
            };
    private int[] jupiterAudio5 = new int[]
            {
                    R.raw.jupiter5v1,
                    R.raw.jupiter5v2,
                    R.raw.jupiter5v3,
                    R.raw.jupiter5v4
            };
    //------------------------------------------------------------------------------------------------
    private int[] saturnAudio1 = new int[]
            {
                    R.raw.saturn1v1,
                    R.raw.saturn1v2,
                    R.raw.saturn1v3,
                    R.raw.saturn1v4
            };
    private int[] saturnAudio2 = new int[]
            {
                    R.raw.saturn2v1,
                    R.raw.saturn2v2,
                    R.raw.saturn2v3,
                    R.raw.saturn2v4
            };
    private int[] saturnAudio3 = new int[]
            {
                    R.raw.saturn3v1,
                    R.raw.saturn3v2,
                    R.raw.saturn3v3,
                    R.raw.saturn3v4
            };
    private int[] saturnAudio4 = new int[]
            {
                    R.raw.saturn4v1,
                    R.raw.saturn4v2,
                    R.raw.saturn4v3,
                    R.raw.saturn4v4
            };
    private int[] saturnAudio5 = new int[]
            {
                    R.raw.saturn5v1,
                    R.raw.saturn5v2,
                    R.raw.saturn5v3,
                    R.raw.saturn5v4
            };

    //------------------------------------------------------------------------------------------------
    private int[] uranusAudio1 = new int[]
            {
                    R.raw.uranus1v1,
                    R.raw.uranus1v2,
                    R.raw.uranus1v3,
                    R.raw.uranus1v4
            };
    private int[] uranusAudio2 = new int[]
            {
                    R.raw.uranus2v1,
                    R.raw.uranus2v2,
                    R.raw.uranus2v3,
                    R.raw.uranus2v4
            };
    private int[] uranusAudio3 = new int[]
            {
                    R.raw.uranus3v1,
                    R.raw.uranus3v2,
                    R.raw.uranus3v3,
                    R.raw.uranus3v4
            };
    private int[] uranusAudio4 = new int[]
            {
                    R.raw.uranus4v1,
                    R.raw.uranus4v2,
                    R.raw.uranus4v3,
                    R.raw.uranus4v4
            };
    private int[] uranusAudio5 = new int[]
            {
                    R.raw.uranus5v1,
                    R.raw.uranus5v2,
                    R.raw.uranus5v3,
                    R.raw.uranus5v4
            };
    //------------------------------------------------------------------------------------------------
    private int[] neptuneAudio1 = new int[]
            {
                    R.raw.neptune1v1,
                    R.raw.neptune1v2,
                    R.raw.neptune1v3,
                    R.raw.neptune1v4
            };
    private int[] neptuneAudio2 = new int[]
            {
                    R.raw.neptune2v1,
                    R.raw.neptune2v2,
                    R.raw.neptune2v3,
                    R.raw.neptune2v4
            };
    private int[] neptuneAudio3 = new int[]
            {
                    R.raw.neptune3v1,
                    R.raw.neptune3v2,
                    R.raw.neptune3v3,
                    R.raw.neptune3v4
            };
    private int[] neptuneAudio4 = new int[]
            {
                    R.raw.neptune4v1,
                    R.raw.neptune4v2,
                    R.raw.neptune4v3,
                    R.raw.neptune4v4
            };
    private int[] neptuneAudio5 = new int[]
            {
                    R.raw.neptune5v1,
                    R.raw.neptune5v2,
                    R.raw.neptune5v3,
                    R.raw.neptune5v4
            };
    //------------------------------------------------------------------------------------------------
    private int[][] audio1Array = new int[][]
            {
                    mercuryAudio1,
                    venusAudio1,
                    earthAudio1,
                    marsAudio1,
                    jupiterAudio1,
                    saturnAudio1,
                    uranusAudio1,
                    neptuneAudio1
            };
    private int[][] audio2Array = new int[][]
            {
                    mercuryAudio2,
                    venusAudio2,
                    earthAudio2,
                    marsAudio2,
                    jupiterAudio2,
                    saturnAudio2,
                    uranusAudio2,
                    neptuneAudio2
            };
    private int[][] audio3Array = new int[][]
            {
                    mercuryAudio3,
                    venusAudio3,
                    earthAudio3,
                    marsAudio3,
                    jupiterAudio3,
                    saturnAudio3,
                    uranusAudio3,
                    neptuneAudio3
            };
    private int[][] audio4Array = new int[][]
            {
                    mercuryAudio4,
                    venusAudio4,
                    earthAudio4,
                    marsAudio4,
                    jupiterAudio4,
                    saturnAudio4,
                    uranusAudio4,
                    neptuneAudio4
            };
    private int[][] audio5Array = new int[][]
            {
                    mercuryAudio5,
                    venusAudio5,
                    earthAudio5,
                    marsAudio5,
                    jupiterAudio5,
                    saturnAudio5,
                    uranusAudio5,
                    neptuneAudio5
            };
    private int[][][] arrayList = new int[][][]
            {
                audio1Array,
                audio2Array,
                audio3Array,
                audio4Array,
                audio5Array,
            };



    public int[][] getAudioArray(int index)
    {
        return Arrays.copyOf(arrayList[index], arrayList[index].length);
    }
}
