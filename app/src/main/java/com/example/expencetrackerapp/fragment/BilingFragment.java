package com.example.expencetrackerapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.expencetrackerapp.databinding.ActivityExpenceListBinding;
import com.example.expencetrackerapp.interfaces.FragmentBottomNavigation;

public class BilingFragment extends Fragment {

    FragmentBottomNavigation communicator;

    ActivityExpenceListBinding binding;



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            communicator = (FragmentBottomNavigation) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement FragmentToActivityCommunicator");
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = ActivityExpenceListBinding.inflate(inflater, container, false);

        communicator.navigateBottomFrag(3, true);

        return binding.getRoot();
    }
}
