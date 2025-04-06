package com.example.expencetrackerapp.ui.view;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

    private static final int SMS_PERMISSION_CODE = 123;

    private final String PREFERENCE_NAME = "MyPrefs";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new HomeFragment()).addToBackStack(null).commit();

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
        checkForSMSPermissions();
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
    private void checkForSMSPermissions() {
        // Check if the SMS permissions have already been granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {

            // If permission is not granted, request SMS permissions
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, SMS_PERMISSION_CODE);
        } else {
            // Permissions are already granted, you can continue your SMS-related functionality here
            Toast.makeText(this, "SMS Permissions are already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Check if the request code matches the SMS permission request code
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted
                Toast.makeText(this, "SMS Permission Granted", Toast.LENGTH_SHORT).show();
                // Proceed with SMS-related functionality
            } else {
                // Permission was denied
                Toast.makeText(this, "SMS Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
//
//        // Clear all data
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear();
//        editor.apply();
//    }

}
