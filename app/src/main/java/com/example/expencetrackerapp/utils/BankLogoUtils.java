package com.example.expencetrackerapp.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

import com.example.expencetrackerapp.R;

public class BankLogoUtils {

    @DrawableRes
    public static int getBankLogo(String bankName) {
        switch (bankName.toLowerCase()) {
            case "sbi":
                return R.drawable.sbi_logo;
            case "hdfc":
                return R.drawable.hdfc_logo;
            case "icici":
                return R.drawable.icici_logo;
            case "axis":
                return R.drawable.axis_logo;
            case "kotak":
                return R.drawable.kotak_logo;
            case "indusind":
                return R.drawable.indusind_logo;
            case "pnb":
                return R.drawable.pnb_logo;
            case "yes":
                return R.drawable.yes_logo;
            case "bank of baroda":
                return R.drawable.bank_of_baroda_logo;
            case "central bank of india":
                return R.drawable.central_bank_logo;
            default:
                return R.drawable.default_bank_logo; // Default logo
        }
    }

    // Optional: Method to get Drawable object
    public static Drawable getBankLogoDrawable(Context context, String bankName) {
        @DrawableRes int logoResId = getBankLogo(bankName);
        return ContextCompat.getDrawable(context, logoResId);
    }
}
