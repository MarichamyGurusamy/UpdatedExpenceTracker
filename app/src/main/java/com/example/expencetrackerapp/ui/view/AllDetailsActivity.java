package com.example.expencetrackerapp.ui.view;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.expencetrackerapp.R;
import com.example.expencetrackerapp.databinding.ActivitySecondBinding;
import com.example.expencetrackerapp.fragment.BilingFragment;
import com.example.expencetrackerapp.fragment.BudgetFragment;
import com.example.expencetrackerapp.fragment.HomeFragment;
import com.example.expencetrackerapp.fragment.SpcifyFragment;
import com.example.expencetrackerapp.interfaces.FragmentBottomNavigation;
import com.example.expencetrackerapp.interfaces.FragmentNavigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AllDetailsActivity extends AppCompatActivity  implements FragmentNavigation, FragmentBottomNavigation {

    ActivitySecondBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new HomeFragment()).addToBackStack(null).commit();


    }

    @Override
    public void navigateFrag(Fragment fragment, boolean addToStack) {
        androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment);
        if (addToStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }


    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;

        int itemId = item.getItemId();
        if (itemId == R.id.navigation_home) {
            selectedFragment = new HomeFragment();
        } else if (itemId == R.id.navigation_you) {
            selectedFragment = new SpcifyFragment();
        } else if (itemId == R.id.navigation_more) {
            selectedFragment = new BilingFragment();
        } else if (itemId == R.id.navigation_cart) {
            selectedFragment = new BudgetFragment();
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();
        }
        return true;
    };

    @Override
    public void navigateBottomFrag( int fragment , boolean addToStack) {
        if (fragment == 1 && addToStack) {
            binding.bottomNavigationView.setVisibility(View.GONE);
        }else  if (fragment == 2 && addToStack){
            binding.bottomNavigationView.setVisibility(View.GONE);
        }else  if (fragment == 3 && addToStack){
            binding.bottomNavigationView.setVisibility(View.VISIBLE);
        }

    }

}
