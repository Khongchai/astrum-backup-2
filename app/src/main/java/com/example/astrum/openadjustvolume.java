package com.example.astrum;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class openadjustvolume extends Fragment
{
    //Inherit from fragments
    //Override oncreate view
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        //basically just saying that volumemixerfrag layout is a fragment
        View view = inflater.inflate(R.layout.openvolumemixer, container, false);
        Button Openmixer =  view.findViewById(R.id.OpenMixer);
                //return view has to be at the bottom
        Openmixer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //no need to get support because this one extends the Fragment class already.
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                fr.replace(R.id.FragmentsContainer, new AdjustVolumeFrag());
                fr.commit();
            }
        });
        return view;

    }





}
