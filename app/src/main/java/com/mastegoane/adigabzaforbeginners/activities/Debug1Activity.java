package com.mastegoane.adigabzaforbeginners.activities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mastegoane.adigabzaforbeginners.datastructures.CyrillicWordMap;
import com.mastegoane.adigabzaforbeginners.R;
import com.mastegoane.adigabzaforbeginners.utils.AdigaCharacterUtils;
import com.mastegoane.adigabzaforbeginners.utils.SoundPlayer;
import com.mastegoane.adigabzaforbeginners.databinding.ActivityDebug1Binding;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Debug1Activity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AdigabzaForBeginners);
        mBinding = ActivityDebug1Binding.inflate(getLayoutInflater());
        mContext = this;
        setContentView(mBinding.getRoot());
        LiveData<Set<String>> cyrillicWordsLiveData = CyrillicWordMap.inst().getCyrillicWords();
        cyrillicWordsLiveData.observe(this, new Observer<Set<String>>() {
            @Override
            public void onChanged(Set<String> cyrillicWords) {
                int index = 1;
                final int colorEven = Color.parseColor("#eeeeee");
                final int colorOdd = Color.parseColor("#ddddff");
                // first print all the supported cyrillic characters
                final String supportedChars = "АБВГДЕЁЖЗИЙКЛМНОӀПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя";
                for (int charIndex = 0; charIndex < supportedChars.length(); ++charIndex) {
                    final String character = supportedChars.substring(charIndex, charIndex + 1);
                    String text = character + ": ";
                    try {
                        final byte[] utf16Bytes = character.getBytes("UTF-16");
                        final int numOfBytes = utf16Bytes.length;
                        int byteCounter = 0;
                        for (byte currentByte : utf16Bytes) {
                            ++byteCounter;
                            if (numOfBytes == 4 && byteCounter < 3) {
                                continue;
                            }
                            text = text + String.format("%02x", currentByte);
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    TextView textView1 = new TextView(mContext);
                    textView1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                    Log.d(TAG, "YYY291 " + text);
                    textView1.setText(text);
                    if ((charIndex % 2) == 0) {
                        textView1.setBackgroundColor(colorEven);
                    } else {
                        textView1.setBackgroundColor(colorOdd);
                    }
                    textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                    textView1.setTextColor(Color.BLACK);
                    textView1.setGravity(Gravity.START);
                    textView1.setPadding(20, 8, 20, 8);
                    mBinding.linearLayoutDebug1.addView(textView1);
                }
                // now print all the cyrillic tokens read from all the input files
//                Log.d(TAG, "" + cyrillicWords.size());
                final List<String> sortedCyrillicWordsList = new ArrayList<>(cyrillicWords);
                Collections.sort(sortedCyrillicWordsList);
                for (String cyrillicWord : sortedCyrillicWordsList) {
                    final String fullId = CyrillicWordMap.inst().getFullId(cyrillicWord);
                    TextView textView1 = new TextView(mContext);
                    textView1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    final String formattedCyrillicWord = AdigaCharacterUtils.inst().highlightNonCyrillicChars(cyrillicWord, "#000000", "#ff0000");
                    final String latinWord = AdigaCharacterUtils.inst().cyrillic2Latin(cyrillicWord);
                    final String fullText = formattedCyrillicWord + "<font color=#00bb00>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + fullId + "</font>" + "<font color=#0000bb>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + latinWord + "</font>";
//                    textView1.setText(Html.fromHtml(formattedCyrillicWord + "<font color=#00bb00>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + fullId + "</font>"));
                    textView1.setText(Html.fromHtml(fullText));
                    textView1.setTag(cyrillicWord);
                    if ((index % 2) == 0) {
                        textView1.setBackgroundColor(colorEven);
                    } else {
                        textView1.setBackgroundColor(colorOdd);
                    }
                    textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                    textView1.setTextColor(Color.BLACK);
                    textView1.setGravity(Gravity.START);
                    textView1.setPadding(20, 8, 20, 8);
                    textView1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String plainTextWord = v.getTag().toString();
                            Log.d(TAG, "plainTextWord, Latin: " + plainTextWord + " : " + AdigaCharacterUtils.inst().cyrillic2Latin(plainTextWord));
                            // TODO bug: getSound() returns empty when it should not (e.g. with the word Блэ
                            SoundPlayer.getInstance().playSound("AllLessons", CyrillicWordMap.inst().getSound(plainTextWord));
                        }
                    });
                    mBinding.linearLayoutDebug1.addView(textView1);
                    ++index;
                }
            }
        });
    }
    private ActivityDebug1Binding mBinding;
    private Context mContext = null;
    private static final String TAG = Debug1Activity.class.getSimpleName();
}
// áçćéǵğḣıḱĺöṕşśšṫüź
// ÁÇĆÉǴĞḢİḰĹÖṔŞŚŠṪÜŹ
