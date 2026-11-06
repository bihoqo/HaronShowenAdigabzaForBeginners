package com.mastegoane.adigabzaforbeginners.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.mastegoane.adigabzaforbeginners.utils.GlobalSettings;
import java.util.Locale;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        mSharedPreferences = newBase.getSharedPreferences("AdigabzaForBeginnersPreferences", MODE_PRIVATE);
        int languageInt = mSharedPreferences.getInt("sp_language", GlobalSettings.Language.ENGLISH.ordinal());
        GlobalSettings.inst().setLanguage(GlobalSettings.Language.values()[languageInt]);
        String localeTag;
        switch (GlobalSettings.Language.values()[languageInt]) {
            case ARABIC:
                localeTag = "ar";
                break;
            case TURKISH:
                localeTag = "tr";
                break;
            default:
                localeTag = "en";
        }

//        // TODO temporary for validation
//        localeTag = "ar";

        Locale locale = Locale.forLanguageTag(localeTag);
        Locale.setDefault(locale);
        Configuration configuration = newBase.getResources().getConfiguration();
        configuration.setLocale(locale);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            configuration.setLocale(locale);
//        } else {
//            configuration.locale = locale;
//        }
        Context newContext;
        newContext = newBase.createConfigurationContext(configuration);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            newContext = newBase.createConfigurationContext(configuration);
//        } else {
//            Resources resources = newBase.getResources();
//            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
//            newContext = newBase;
//        }
        super.attachBaseContext(newContext);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        mPx2dpFactor = 1.0f / displayMetrics.density;
        mDp2pxFactor = displayMetrics.density;
        applySystemBarInsetsAsPadding();
    }

    // Stopgap for edge-to-edge enforcement at targetSdk 35+: windowOptOutEdgeToEdgeEnforcement is
    // disabled on Android 16 devices when targeting API 36, so pad the content root below the
    // status/navigation bars to preserve the pre-edge-to-edge layout. Proper per-screen insets
    // handling remains a follow-up.
    private void applySystemBarInsetsAsPadding() {
        getWindow().getDecorView().post(() -> {
            final View content = findViewById(android.R.id.content);
            if (content == null) {
                return;
            }
            final int initialLeft = content.getPaddingLeft();
            final int initialTop = content.getPaddingTop();
            final int initialRight = content.getPaddingRight();
            final int initialBottom = content.getPaddingBottom();
            ViewCompat.setOnApplyWindowInsetsListener(content, (@NonNull View v, @NonNull WindowInsetsCompat insets) -> {
                final androidx.core.graphics.Insets bars = insets.getInsets(
                        WindowInsetsCompat.Type.statusBars()
                                | WindowInsetsCompat.Type.navigationBars()
                                | WindowInsetsCompat.Type.displayCutout());
                v.setPadding(initialLeft + bars.left, initialTop + bars.top,
                        initialRight + bars.right, initialBottom + bars.bottom);
                return insets;
            });
            ViewCompat.requestApplyInsets(content);
        });
    }

    protected SharedPreferences mSharedPreferences = null;
    protected float mPx2dpFactor = 0;
    protected float mDp2pxFactor = 0;
    private static final String TAG = BaseActivity.class.getSimpleName();
}
