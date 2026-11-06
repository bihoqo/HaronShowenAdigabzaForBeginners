package com.mastegoane.adigabzaforbeginners.activities;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import com.mastegoane.adigabzaforbeginners.R;
import com.mastegoane.adigabzaforbeginners.databinding.ActivityAboutBinding;

public class AboutActivity extends BaseActivity {

//    // TODO temporary just to test different colors
//    public void debug_buttonChangeColorsClicked(View view) {
//        mColorType = !mColorType;
//        if (mColorType) {
//            mBinding.about1.setBackgroundColor(Color.parseColor("#3000ff00"));
//            mBinding.about2.setTextColor(Color.parseColor("#000000"));
//            mBinding.about3.setTextColor(Color.parseColor("#000000"));
//            mBinding.about3b.setTextColor(Color.parseColor("#555555"));
//            mBinding.about4.setTextColor(Color.parseColor("#000000"));
//            mBinding.about4b.setTextColor(Color.parseColor("#555555"));
//            mBinding.about5b.setTextColor(Color.parseColor("#555555"));
//            mBinding.textViewVersionInfo.setTextColor(Color.parseColor("#555555"));
//            // about1, 2, 3, 3b, 4, 4b, 5b
//            //textViewVersionInfo
//            //555555
//        } else {
//            mBinding.about1.setBackgroundColor(Color.parseColor("#66B32F"));
//            mBinding.about2.setTextColor(Color.parseColor("#ffffff"));
//            mBinding.about3.setTextColor(Color.parseColor("#ffffff"));
//            mBinding.about3b.setTextColor(Color.parseColor("#eeeeee"));
//            mBinding.about4.setTextColor(Color.parseColor("#ffffff"));
//            mBinding.about4b.setTextColor(Color.parseColor("#eeeeee"));
//            mBinding.about5b.setTextColor(Color.parseColor("#eeeeee"));
//            mBinding.textViewVersionInfo.setTextColor(Color.parseColor("#eeeeee"));
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.Theme_AdigabzaForBeginners);
        mBinding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.textViewAboutYoutube.setMovementMethod(LinkMovementMethod.getInstance());
        mBinding.textViewAboutFacebook.setMovementMethod(LinkMovementMethod.getInstance());
        try {
            PackageInfo packageInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
            mBinding.textViewVersionInfo.setText("Version name: " + packageInfo.versionName + "    Version code: " + packageInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // TODO get a custom/fixed URL to YouTube channel from Suad
    }

    private ActivityAboutBinding mBinding;

    private boolean mColorType = true;
}
