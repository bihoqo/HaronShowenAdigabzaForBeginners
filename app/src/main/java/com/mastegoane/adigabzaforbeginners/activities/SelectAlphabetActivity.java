package com.mastegoane.adigabzaforbeginners.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import com.mastegoane.adigabzaforbeginners.R;
import com.mastegoane.adigabzaforbeginners.utils.GlobalSettings;
import com.mastegoane.adigabzaforbeginners.utils.SoundPlayer;

public class SelectAlphabetActivity extends BaseActivity {

    public void buttonAlphabetClicked(View view) {
        mRestart = false;
        mIsCyrillic = (view != mBinding.buttonAlphabetLatin);
        mBinding.buttonAlphabetCyrillic.setSelected(false);
        mBinding.buttonAlphabetLatin.setSelected(false);
        view.setSelected(true);
        mBinding.buttonSelect.setEnabled(true);
    }

    public void buttonSelectClicked(View view) {
        SoundPlayer.getInstance().playSound("AllLessons", "click");
        mRestart = true;
        GlobalSettings.inst().setCyrillic(mIsCyrillic);
        mBinding.buttonSelect.setEnabled(false);
        final SharedPreferences.Editor sharedPrefEditor = mSharedPreferences.edit();
        sharedPrefEditor.putBoolean("sp_is_cyrillic", mIsCyrillic);
        sharedPrefEditor.putBoolean("sp_first_time_ever_alphabet", false);
        // Note: need to use commit() and not apply() below. Otherwise, the app may restart before data is written down.
        sharedPrefEditor.commit();
        finish();
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
        mBinding = com.mastegoane.adigabzaforbeginners.databinding.ActivitySelectAlphabetBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mRestart = false;
        mBinding.buttonAlphabetCyrillic.setSelected(false);
        mBinding.buttonAlphabetLatin.setSelected(false);
        mBinding.constraintLayoutSelectAlphabet.setVisibility(View.VISIBLE);
    }

    private com.mastegoane.adigabzaforbeginners.databinding.ActivitySelectAlphabetBinding mBinding;
    boolean mIsCyrillic = true;
    private boolean mRestart = false;
    private static final String TAG = SelectAlphabetActivity.class.getSimpleName();
}