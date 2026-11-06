package com.mastegoane.adigabzaforbeginners.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import com.mastegoane.adigabzaforbeginners.R;
import com.mastegoane.adigabzaforbeginners.databinding.ActivitySelectLanguageBinding;
import com.mastegoane.adigabzaforbeginners.utils.GlobalSettings;
import com.mastegoane.adigabzaforbeginners.utils.SoundPlayer;

public class SelectLanguageActivity extends BaseActivity {
    public void buttonLanguageClicked(View view) {
        mRestart = false;
        mBinding.buttonLanguageEnglish.setSelected(false);
        mBinding.buttonLanguageArabic.setSelected(false);
        mBinding.buttonLanguageTurkish.setSelected(false);
        mLanguage = GlobalSettings.Language.ENGLISH;
        if (view == mBinding.buttonLanguageArabic) {
            mLanguage = GlobalSettings.Language.ARABIC;
        } else if (view == mBinding.buttonLanguageTurkish) {
            mLanguage = GlobalSettings.Language.TURKISH;
        }
        view.setSelected(true);
        mBinding.buttonSelect.setEnabled(true);
    }

    public void buttonSelectClicked(View view) {
        SoundPlayer.getInstance().playSound("AllLessons", "click");
        mRestart = true;
        mBinding.buttonSelect.setEnabled(false);
        final SharedPreferences.Editor sharedPrefEditor = mSharedPreferences.edit();
        sharedPrefEditor.putInt("sp_language", mLanguage.ordinal());
        GlobalSettings.inst().setLanguage(mLanguage);
        sharedPrefEditor.putBoolean("sp_first_time_ever_language", false);
        // Note: need to use commit() and not apply() below. Otherwise, the app may restart before data is written down.
        sharedPrefEditor.commit();
        finish();

//        // Now restart the app
//        Intent intent1 = new Intent(this, MainActivity.class);
////        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////        intent1.putExtra(KEY_RESTART_INTENT, nextIntent);
//        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        this.startActivity(intent1);
//        if (this instanceof Activity) {
//            ((Activity) this).finish();
//        }
//        Runtime.getRuntime().exit(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRestart) {
            // Now restart the app
            Intent intent1 = new Intent(this, MainActivity.class);
//            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent1.putExtra(KEY_RESTART_INTENT, nextIntent);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            this.startActivity(intent1);
            if (this instanceof Activity) {
                ((Activity) this).finish();
            }
            Runtime.getRuntime().exit(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AdigabzaForBeginners);
        mBinding = ActivitySelectLanguageBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mRestart = false;
        mBinding.buttonLanguageEnglish.setSelected(false);
        mBinding.buttonLanguageArabic.setSelected(false);
        mBinding.buttonLanguageTurkish.setSelected(false);
        mBinding.constraintLayoutSelectLanguage.setVisibility(View.VISIBLE);
    }

    private ActivitySelectLanguageBinding mBinding;
    private GlobalSettings.Language mLanguage = GlobalSettings.Language.ENGLISH;
    private boolean mRestart = false;
    private static final String TAG = SelectLanguageActivity.class.getSimpleName();
}
