package com.mastegoane.adigabzaforbeginners.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.mastegoane.adigabzaforbeginners.databases.LessonInfo;
import com.mastegoane.adigabzaforbeginners.databases.QuestionInfo;
import com.mastegoane.adigabzaforbeginners.datastructures.CyrillicWordMap;
import com.mastegoane.adigabzaforbeginners.databases.DictionaryEntry;
import com.mastegoane.adigabzaforbeginners.R;
import com.mastegoane.adigabzaforbeginners.databinding.ActivityMainBinding;
import com.mastegoane.adigabzaforbeginners.utils.DialogPlusWrapper;
import com.mastegoane.adigabzaforbeginners.utils.GlobalSettings;
import com.mastegoane.adigabzaforbeginners.utils.StagesViewPagerAdapter;
import com.mastegoane.adigabzaforbeginners.utils.SoundPlayer;
import com.mastegoane.adigabzaforbeginners.viewmodels.MainViewModel;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends BaseActivity {
    public void onClickLesson(View view) {
        GlobalSettings.inst().setFlowerCollectionMode(false);
        final int lessonNumber = Integer.parseInt(view.getTag().toString());
        if (GlobalSettings.inst().getNumOfFlowers() == 0) {
            showDialogPlusFlowersMain(null);
        } else {
            gotoLesson(new Pair<>(GlobalSettings.inst().getCurrentTabStageNumber(), lessonNumber));
        }
    }

    public void gotoLesson(Pair<Integer, Integer> lessonId) {
        if (!mMainViewModel.getLessonHasExercises(lessonId)) {
            return;
        }
        if (!mStageLesson2IsActivated.get("" + lessonId.first + "," + lessonId.second)) {
            if (!GlobalSettings.inst().getUnlockAllLessons()) {
                return;
            }
        }
        if (mMainViewModel.getLessonIsDone(lessonId)) {
            if (!GlobalSettings.inst().isFlowerCollectionMode()) {
                mMainViewModel.resetLesson(lessonId);
            }
        }
        final List<Integer> exercises = mMainViewModel.getLessonExercises(lessonId);
        if (exercises.size() == 0) {
            Log.e(TAG, "gotoLesson(): exercises.size() == 0");
            return;
        }

        GlobalSettings.inst().setCurrentStageNumber(lessonId.first);
        GlobalSettings.inst().setCurrentLessonNumber(lessonId.second);
        int exerciseNumber = -1;
        for (int exercise : exercises) {
            if (!mMainViewModel.getExerciseIsDone(lessonId, exercise)) {
                exerciseNumber = exercise;
                break;
            }
        }
        if (exerciseNumber == -1) {
            Log.e(TAG, "-B- BUG: gotoLesson(): exerciseNumber == -1");
        } else {
            Log.d("YYY8", "exerciseNumber: " + exerciseNumber);
            GlobalSettings.inst().setCurrentExerciseNumber(exerciseNumber);
            final Intent intent = new Intent(this, LessonActivity.class);
//            intent.putExtra("lesson_id_str", lessonId.first + "," + lessonId.second);
//            intent.putExtra("exercise_number", exerciseNumber);
            startActivity(intent);
        }
    }

    private void gotoLessonForFlowerCollectionMain() {
        GlobalSettings.inst().setCurrentStageNumber(-1);
        GlobalSettings.inst().setCurrentLessonNumber(-1);
        GlobalSettings.inst().setCurrentExerciseNumber(-1);
        final Intent intent = new Intent(this, LessonActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final boolean firstTimeEverLanguage = mSharedPreferences.getBoolean("sp_first_time_ever_language", true);
        final boolean firstTimeEverAlphabet = mSharedPreferences.getBoolean("sp_first_time_ever_alphabet", true);
        if (!firstTimeEverLanguage && !firstTimeEverAlphabet) {
            mBinding.constraintViewMain.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO stop storing delay - it should be a const
//        mSplashScreenDelay = mSharedPreferences.getInt("sp_splash_screen_delay", mDEFAULT_SPLASH_SCREEN_DELAY);
        mSplashScreenDelay = mDEFAULT_SPLASH_SCREEN_DELAY;

        final boolean firstTimeEverLanguage = mSharedPreferences.getBoolean("sp_first_time_ever_language", true);
        final boolean firstTimeEverAlphabet = mSharedPreferences.getBoolean("sp_first_time_ever_alphabet", true);
        if (firstTimeEverLanguage) {
            mSplashScreenDelay = mDEFAULT_SPLASH_SCREEN_DELAY;
        }

//        ApplicationInfo appInfo = getApplicationContext().getApplicationInfo();
//        final boolean debugBuildMode = (appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
//        GlobalSettings.inst().setDebugFeaturesEnabled(debugBuildMode);
        // TODO set this to false for production release
//        GlobalSettings.inst().setDebugFeaturesEnabled(true);
        GlobalSettings.inst().setDebugFeaturesEnabled(false);

        // TODO change to mSplashScreenDelay before releases
//        SystemClock.sleep(400);
//        SystemClock.sleep(2000);
        SystemClock.sleep(mSplashScreenDelay);
//        showPauseOnStartDialog();

        if (firstTimeEverLanguage) {
            startActivity(new Intent(this, SelectLanguageActivity.class));
        } else {
            if (firstTimeEverAlphabet) {
                startActivity(new Intent(this, SelectAlphabetActivity.class));
            }
        }

        setTheme(R.style.Theme_AdigabzaForBeginners);
        mMainViewModel = new ViewModelProvider(this,
                (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(MainViewModel.class);

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        if (!firstTimeEverLanguage && !firstTimeEverAlphabet) {
            mBinding.constraintViewMain.setVisibility(View.VISIBLE);
        }

//        GlobalSettings.inst().initConstants();

        // TODO replace these with data from "lesson_info" database
//        mStageLesson2IsActivated = new ArrayMap<>();
        mStageLesson2IsActivated = mMainViewModel.getStageLesson2IsActivatedMap();
//        mStageLessonExercise2IsDone = new ArrayMap<>();
        mStageLessonExercise2IsDone = mMainViewModel.getStageLessonExercise2IsDoneMap();
//        mStageLesson2NumStars = new ArrayMap<>();
        mStageLesson2NumStars = mMainViewModel.getStageLesson2NumStarsMap();

//        mStagesViewPagerAdapter = new StagesViewPagerAdapter(this, GlobalSettings.inst().getStageNumbers(), GlobalSettings.inst().getStageTitles(), GlobalSettings.inst().getStageLesson2SubTitle(), GlobalSettings.inst().getStageNumOfLessons(), mStageLesson2IsActivated, mStageLesson2NumStars, mStageLessonExercise2IsDone);
        mStagesViewPagerAdapter = new StagesViewPagerAdapter(this, mMainViewModel.getStageNumbers(), mMainViewModel.getStageTitles(), mMainViewModel.getStageLesson2SubTitle(), mMainViewModel.getStageNumOfLessons(), mStageLesson2IsActivated, mStageLesson2NumStars, mStageLessonExercise2IsDone);
        mBinding.viewPagerMain.setAdapter(mStagesViewPagerAdapter);
//        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(mBinding.tabLayoutMain, mBinding.viewPagerMain, (tab, position) -> tab.setText(GlobalSettings.inst().getStageTitles().get(position)));
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(mBinding.tabLayoutMain, mBinding.viewPagerMain, (tab, position) -> tab.setText(mMainViewModel.getStageTitles().get(position)));
        tabLayoutMediator.attach();
        mBinding.tabLayoutMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                GlobalSettings.inst().setCurrentTabStageNumber(GlobalSettings.inst().getStageNumbers().get(tab.getPosition()));
                GlobalSettings.inst().setCurrentTabStageNumber(mMainViewModel.getStageNumbers().get(tab.getPosition()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        final boolean isCyrillic = mSharedPreferences.getBoolean("sp_is_cyrillic", true);
        GlobalSettings.inst().setCyrillic(isCyrillic);

        final boolean smallDb = mSharedPreferences.getBoolean("sp_dbg_small_db", false);
//        Log.d(TAG, "YYY05 smallDb: " + smallDb);
        GlobalSettings.inst().setSmallDb(smallDb);

        mMainViewModel.populateDatabases();

        boolean isDebugMode = false;
        if (GlobalSettings.inst().isDebugFeaturesEnabled()) {
            isDebugMode = mSharedPreferences.getBoolean("sp_is_debug_mode", false);
        }
        GlobalSettings.inst().setDebugMode(isDebugMode);

        final boolean unlockAllLessons = mSharedPreferences.getBoolean("sp_dbg_unlock_all_lessons", false);
        GlobalSettings.inst().setUnlockAllLessons(unlockAllLessons);

        final int numOfFlowers = mSharedPreferences.getInt("sp_num_of_flowers_course_" + GlobalSettings.inst().getCourseId(), GlobalSettings.inst().getDefaultNumOfFlowers());
        GlobalSettings.inst().setNumOfFlowers(numOfFlowers);

//        Log.d(TAG, "YYY904c numOfFlowers: " + numOfFlowers + ", " + GlobalSettings.inst().getDefaultNumOfFlowers());
        if (numOfFlowers < GlobalSettings.inst().getDefaultNumOfFlowers()) {
            // check if 5 hours passed since app was used last time - if so then reset the number of flowers
//            final Calendar calendar = Calendar.getInstance();

//            final String lastTimeStr = "12-10-2021 16:11:05";
            final String lastTimeStr = mSharedPreferences.getString("sp_time_app_closed", mDateFormat.format(Calendar.getInstance().getTime()));
            try {
//                final Date lastTime = dateFormat.parse(lastTimeStr).getTime();
                final long lastTime = mDateFormat.parse(lastTimeStr).getTime();
//                final Date currentTime = Calendar.getInstance().getTime();
                final long currentTime = System.currentTimeMillis();
//                final long diff = (currentTime - lastTime) / 3600000;
                final long diff = (currentTime - lastTime) / 60000;
                if (diff >= 2) {
                    GlobalSettings.inst().setNumOfFlowers(GlobalSettings.inst().getDefaultNumOfFlowers());
                    final SharedPreferences.Editor sharedPrefEditor = mSharedPreferences.edit();
                    sharedPrefEditor.putInt("sp_num_of_flowers_course_1", GlobalSettings.inst().getDefaultNumOfFlowers());
                    sharedPrefEditor.putInt("sp_num_of_flowers_course_2", GlobalSettings.inst().getDefaultNumOfFlowers());
                    sharedPrefEditor.apply();
                }

//                final SharedPreferences.Editor sharedPrefEditor = mSharedPreferences.edit();
//                sharedPrefEditor.putString("sp_time_app_closed", mDateFormat.format(Calendar.getInstance().getTime()));
//                sharedPrefEditor.apply();


//                Log.d(TAG, "YYY904b mDateFormat.format(currentTime1): " + mDateFormat.format(Calendar.getInstance().getTime()));
//                DateUtils.getRelativeTimeSpanString(currentTime, lastTime, DateUtils.HOUR_IN_MILLIS);
//                Log.d(TAG, "YYY904 diff: " + diff);
//                final String niceDateStr = DateUtils.getRelativeTimeSpanString(lastDate.getTime() , Calendar.getInstance().getTime(), DateUtils.HOUR_IN_MILLIS);
//                final long diffInMilliSeconds = Calendar.getInstance().getTime().toInstant().toEpochMilli() - lastDate.getTime().toInstant().toEpochMilli();
//                final
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        GlobalSettings.inst().setFlowerCollectionMode(false);
        mBinding.imageViewMainDebugMode.setVisibility(isDebugMode?View.VISIBLE:View.INVISIBLE);
        setSupportActionBar(mBinding.toolBarMain);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        LiveData<List<DictionaryEntry>> dictionaryEntries = mMainViewModel.getDictionaryEntries();
        dictionaryEntries.observe(this, new Observer<List<DictionaryEntry>>() {
            @Override
            public void onChanged(List<DictionaryEntry> dictionaryEntries) {
                CyrillicWordMap.inst().updateData(dictionaryEntries);
            }
        });

        LiveData<List<QuestionInfo>> questionInfos = mMainViewModel.getQuestionInfoEntries();
        questionInfos.observe(this, new Observer<List<QuestionInfo>>() {
            @Override
            public void onChanged(List<QuestionInfo> questionInfos) {
                mMainViewModel.refreshQuestionInfos(questionInfos);
            }
        });

        LiveData<List<LessonInfo>> lessonInfos = mMainViewModel.getLessonInfoEntries();
        lessonInfos.observe(this, new Observer<List<LessonInfo>>() {
            @Override
            public void onChanged(List<LessonInfo> lessonInfos) {
                mMainViewModel.refreshLessonInfos(lessonInfos);
                mBinding.textViewMainToolbarStars.setText("" + mMainViewModel.getTotalNumOfStars());
                mStageLesson2NumStars = mMainViewModel.getStageLesson2NumStarsMap();
                mStageLesson2IsActivated = mMainViewModel.getStageLesson2IsActivatedMap();
                mStageLessonExercise2IsDone = mMainViewModel.getStageLessonExercise2IsDoneMap();
                mStagesViewPagerAdapter.updateStarsStatus(mStageLesson2NumStars);
                mStagesViewPagerAdapter.updateLessonStatus(mStageLesson2IsActivated);
                mStagesViewPagerAdapter.updateExerciseStatus(mStageLessonExercise2IsDone);
                mStagesViewPagerAdapter.notifyDataSetChanged();
            }
        });

        LiveData<Boolean> isCyrillicLD = GlobalSettings.inst().getCyrillic();
        isCyrillicLD.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isCyrillic) {
                if (isCyrillic) {
                    mBinding.textViewMainSubTitle.setText(R.string.alphabet_label_cyrillic);
                } else {
                    mBinding.textViewMainSubTitle.setText(R.string.alphabet_label_latin);
                }
                // TODO should restart the app when alphabet is changed
                mMainViewModel.initConstants();
            }
        });

        LiveData<Integer> numOfFlowersLD = GlobalSettings.inst().getNumOfFlowersLD();
        numOfFlowersLD.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mBinding.textViewMainToolbarFlowers.setText("" + integer);
            }
        });

//        LiveData<List<LessonInfo>> lessonInfos = mMainViewModel.getLessonInfoEntries();
//        lessonInfos.observe(this, new Observer<List<LessonInfo>>() {
//            @Override
//            public void onChanged(List<LessonInfo> lessonInfos) {
////                mMainViewModel.refreshQuestionInfos(questionInfos);
////                updateViews();
//                mMainViewModel.refreshLessonInfos(lessonInfos);
//                // TODO handle change in lessonInfos
//            }
//        });
        mMainViewModel.initConstants();
        mStagesViewPagerAdapter.notifyDataSetChanged();

        //YYY1
//        testTextSpan();
//        mBinding.constraintLayoutMainTest.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                // Make sure this listener is called only once
//                mBinding.constraintLayoutMainTest.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                testTextSpan();
//            }
//        });

//        // TODO TEMPORARY
//        showDialogPlusFlowersMain(null);
//        GlobalSettings.inst().setCurrentStageNumber(2);
//        GlobalSettings.inst().setCurrentLessonNumber(2);
//        GlobalSettings.inst().setCurrentExerciseNumber(3);
//        GlobalSettings.inst().setCurrentStageNumber(1);
//        GlobalSettings.inst().setCurrentLessonNumber(1);
//        GlobalSettings.inst().setCurrentExerciseNumber(1);
//        GlobalSettings.inst().setCurrentStageNumber(2);
//        GlobalSettings.inst().setCurrentLessonNumber(3);
//        GlobalSettings.inst().setCurrentExerciseNumber(4);
//        final Intent intent = new Intent(this, LessonActivity.class);
//        startActivity(intent);
        // S2L2E3Q83

//        final Intent intent = new Intent(this, AboutActivity.class);
//        startActivity(intent);
//        startActivity(new Intent(this, SelectAlphabetActivity.class));
//        startActivity(new Intent(this, SelectLanguageActivity.class));
//        showDialogPlusLanguage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater menuInflater = getMenuInflater();
        if (GlobalSettings.inst().isDebugFeaturesEnabled()) {
            menuInflater.inflate(R.menu.main_menu_with_debug, menu);
            final MenuItem menuItem = menu.findItem(R.id.dbg_small_db);
            if (GlobalSettings.inst().getSmallDb()) {
                menuItem.setTitle("DBG full DB");
            } else {
                menuItem.setTitle("DBG small DB");
            }
        } else {
            menuInflater.inflate(R.menu.main_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int itemId = item.getItemId();
        if (itemId == R.id.about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        } else if (itemId == R.id.app_language) {
            showDialogPlusLanguage();
            return true;
        } else if (itemId == R.id.alphabet) {
            showDialogPlusAlphabet();
            return true;
        }
        if (GlobalSettings.inst().isDebugFeaturesEnabled()) {
            // TODO disable all DBG cases before production release
            if (itemId == R.id.dbg_dictionary) {
                startActivity(new Intent(this, Debug1Activity.class));
                return true;
            } else if (itemId == R.id.dbg_show_display_params) {
                showDisplayParamsDialog();
                return true;
            } else if (itemId == R.id.dbg_reset_db) {
                mMainViewModel.resetDatabases();
                // Now restart the app
                Intent intent2 = new Intent(this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra(KEY_RESTART_INTENT, nextIntent);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                this.startActivity(intent2);
                if (this instanceof Activity) {
                    ((Activity) this).finish();
                }
                Runtime.getRuntime().exit(0);
                return true;
            } else if (itemId == R.id.dbg_splash_screen_delay) {
                final SharedPreferences.Editor sharedPrefEditor5 = mSharedPreferences.edit();
                if (mSplashScreenDelay == 400) {
                    mSplashScreenDelay = mDEFAULT_SPLASH_SCREEN_DELAY;
                } else {
                    mSplashScreenDelay = 400;
                }
                sharedPrefEditor5.putInt("sp_splash_screen_delay", mSplashScreenDelay);
                sharedPrefEditor5.apply();
                return true;
            } else if (itemId == R.id.dbg_first_time) {
                final SharedPreferences.Editor sharedPrefEditor2 = mSharedPreferences.edit();
                sharedPrefEditor2.putBoolean("sp_first_time_ever_language", true);
                sharedPrefEditor2.putBoolean("sp_first_time_ever_alphabet", true);
                sharedPrefEditor2.apply();
                return true;
            } else if (itemId == R.id.dbg_small_db) {
                // Toggle DB size
                final boolean smallDb = !GlobalSettings.inst().getSmallDb();
                final SharedPreferences.Editor sharedPrefEditor3 = mSharedPreferences.edit();
                sharedPrefEditor3.putBoolean("sp_dbg_small_db", smallDb);
                sharedPrefEditor3.commit();
                GlobalSettings.inst().setSmallDb(smallDb);
                mMainViewModel.resetDatabases();
                // Now restart the app
                Intent intent3 = new Intent(this, MainActivity.class);
                intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                this.startActivity(intent3);
                if (this instanceof Activity) {
                    ((Activity) this).finish();
                }
                Runtime.getRuntime().exit(0);
                return true;
            } else if (itemId == R.id.dbg_unlock_all_lessons) {
                // unlock all lessons
                final boolean unlockAllLessons = !GlobalSettings.inst().getUnlockAllLessons();
                final SharedPreferences.Editor sharedPrefEditor3b = mSharedPreferences.edit();
                sharedPrefEditor3b.putBoolean("sp_dbg_unlock_all_lessons", unlockAllLessons);
                sharedPrefEditor3b.apply();
                GlobalSettings.inst().setUnlockAllLessons(unlockAllLessons);
                mStagesViewPagerAdapter.notifyDataSetChanged();
                return true;
            } else if (itemId == R.id.dbg_debug_mode) {
                // Toggle debug mode
                final boolean debugMode = !GlobalSettings.inst().isDebugMode();
                final SharedPreferences.Editor sharedPrefEditor4 = mSharedPreferences.edit();
                sharedPrefEditor4.putBoolean("sp_is_debug_mode", debugMode);
                sharedPrefEditor4.apply();
                GlobalSettings.inst().setDebugMode(debugMode);
                mBinding.imageViewMainDebugMode.setVisibility(debugMode ? View.VISIBLE : View.INVISIBLE);
                return true;
            } else if (itemId == R.id.dbg_reset_flowers) {
                GlobalSettings.inst().setNumOfFlowers(GlobalSettings.inst().getDefaultNumOfFlowers());
                GlobalSettings.inst().setFlowerCollectionMode(false);
                final SharedPreferences.Editor sharedPrefEditor = mSharedPreferences.edit();
                sharedPrefEditor.putInt("sp_num_of_flowers_course_" + GlobalSettings.inst().getCourseId(), GlobalSettings.inst().getDefaultNumOfFlowers());
                sharedPrefEditor.apply();
                return true;
            } else if (itemId == R.id.bg_music) {
                SoundPlayer.getInstance().toggleBackgroundMusic();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        final SharedPreferences.Editor sharedPrefEditor = mSharedPreferences.edit();
        sharedPrefEditor.putString("sp_time_app_closed", mDateFormat.format(Calendar.getInstance().getTime()));
        sharedPrefEditor.commit();
        super.onDestroy();
        mMainViewModel.onFinish();
    }

    private void showDialogPlusAlphabet() {
        final int totalHeight = Math.round(getResources().getDimension(R.dimen.dialogplus_total_height3));
        DialogPlusBuilder builder = DialogPlusWrapper.getDialogPlusBuilder(this, Gravity.TOP, true,
                R.layout.dialogplus_content_alphabet, android.R.color.transparent, android.R.color.transparent,
                R.layout.dialogplus_header_alphabet, R.layout.dialogplus_footer_alphabet,
                ViewGroup.LayoutParams.WRAP_CONTENT, totalHeight);
        builder.setOnClickListener((dialog, view) -> {
            final int viewId = view.getId();
            final AppCompatButton buttonCyrillic = findViewById(R.id.dialogplus_alphabet_content_buttonCyrillic);
            final AppCompatButton buttonLatin = findViewById(R.id.dialogplus_alphabet_content_buttonLatin);
            final AppCompatButton buttonSelect = findViewById(R.id.dialogplus_alphabet_footer_buttonSelect);
            if (viewId == R.id.dialogplus_alphabet_content_buttonLatin) {
                buttonCyrillic.setSelected(false);
                buttonLatin.setSelected(true);
                buttonSelect.setEnabled(true);
            } else if (viewId == R.id.dialogplus_alphabet_content_buttonCyrillic) {
                buttonCyrillic.setSelected(true);
                buttonLatin.setSelected(false);
                buttonSelect.setEnabled(true);
            } else if (viewId == R.id.dialogplus_alphabet_footer_buttonSelect) {
                if (buttonSelect.isEnabled()) {
                    SoundPlayer.getInstance().playSound("AllLessons", "click");
                    boolean isCyrillic = true;
                    if (buttonLatin.isSelected()) {
                        isCyrillic = false;
                    }
                    GlobalSettings.inst().setCyrillic(isCyrillic);
                    final SharedPreferences.Editor sharedPrefEditor = mSharedPreferences.edit();
                    sharedPrefEditor.putBoolean("sp_is_cyrillic", isCyrillic);
                    sharedPrefEditor.putBoolean("sp_first_time_ever_alphabet", false);
                    // Note: need to use commit() and not apply() below. Otherwise, the app may restart before data is written down.
                    sharedPrefEditor.commit();
                    final Intent intent1 = new Intent(this, MainActivity.class);
//                            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            intent1.putExtra(KEY_RESTART_INTENT, nextIntent);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    this.startActivity(intent1);
                    if (this instanceof Activity) {
                        ((Activity) this).finish();
                    }
                    Runtime.getRuntime().exit(0);
                }
            } else if (viewId == R.id.dialogplus_alphabet_footer_buttonCancel) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    //YYY1
//    private void testTextSpan() {
//        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
////        String between = "";
//        String between = "     ";
//        List<String> words = new ArrayList<>(Arrays.asList("abc", "deff", "ghi", "klmnof"));
//        for (String word : words) {
//            stringBuilder.append(between);
//            if (between.length() == 0) {
//                between = "     ";
//            }
////            String thisTag = "  " + word + "  ";
////            word = "  " + word + "  ";
////            word = "&#160" + word + "&#160";
////            stringBuilder.append(thisTag);
//            stringBuilder.append(word);
////            stringBuilder.setSpan(new RoundedBackgroundSpan(this),
////                    stringBuilder.length() - thisTag.length(), stringBuilder.length() - thisTag.length() + thisTag.length(),
////                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
////            stringBuilder.setSpan(new RoundedBackgroundSpan(this),
////                    stringBuilder.length() - word.length(), stringBuilder.length() - word.length() + word.length(),
////                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//            stringBuilder.setSpan(new RoundedBackgroundSpan(this),
//                    stringBuilder.length() - word.length(), stringBuilder.length(),
//                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
////           stringBuilder.setSpan(new BackgroundColorSpan(getResources().getColor(R.color.gray)), stringBuilder.length() - thisTag.length(), stringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        }
//
////        TextView tv = new TextView(requireActivity().getApplicationContext());
////        tv.setText(stringBuilder);
//        mBinding.textViewMainTextSpanExample.setText(stringBuilder);
//    }

    private int dp2pixel(float dp) {
        return Math.round(dp * mDp2pxFactor);
    }

//    private void testTextSpan() {
//        List<String> words = new ArrayList<>(Arrays.asList("abc", "deff", "ghi", "jklmno", "pq", "rstt", "uvwxyz", "12", "345", "6"));
//        final int availableWidth = mBinding.constraintLayoutMainTest.getWidth();
//        mBinding.constraintLayoutMainTest.removeAllViews();
//        final int marginStartX = dp2pixel(8);
//        final int marginStartY = dp2pixel(8);
//        final int marginEndX = dp2pixel(8);
//        final int marginEndY = dp2pixel(8);
//        final int marginBetweenX = dp2pixel(9);
//        final int marginBetweenY = dp2pixel(12);
//        final int textSize = dp2pixel(18);
//        final int fixedHeight = textSize;
//        int left = marginStartX;
//        int top = marginStartY;
////        boolean firstWord = true;
//        Log.d(TAG, "YYY6a1 availableWidth: " + availableWidth);
//        Log.d(TAG, "YYY6a2 left, top: " + left + ", " + top);
//        int tmpCounter = 0;
//        for (String word : words) {
//            final TextView textView = new TextView(this);
//            textView.setText(word);
//            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
//            textView.setGravity(Gravity.CENTER);
//            if ((tmpCounter++ % 2) == 0) {
////                textView.setBackgroundResource(R.drawable.background_option_new_selected);
//                textView.setBackgroundColor(getColor(R.color.question_type9_bg_fill));
//            } else {
////                textView.setBackgroundResource(R.drawable.background_option_new_unselected);
//                textView.setBackgroundColor(getColor(R.color.lesson_option_text_color_selected_blue));
//            }
//
////            textView.setPadding(dp2pixel(6), dp2pixel(12), dp2pixel(6), dp2pixel(12));
//            textView.setPadding(dp2pixel(0), dp2pixel(0), dp2pixel(0), dp2pixel(0));
//
////            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, fixedHeight);
////            layoutParams.gravity = Gravity.CENTER;
////            layoutParams.weight = 1f;
//            layoutParams.setMargins(0, 0, 0, 0);
//            textView.setLayoutParams(layoutParams);
//
//            final int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(availableWidth, View.MeasureSpec.AT_MOST);
//            final int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//            textView.measure(widthMeasureSpec, heightMeasureSpec);
//            final int textViewWidth = textView.getMeasuredWidth();
//            final int textViewHeight = textView.getMeasuredHeight();
//
//            final int right = left + textViewWidth + marginEndX;
//            if (right >  availableWidth) {
//                left = marginStartX;
////                top = top + textViewHeight + marginBetweenY;
//                top = top + fixedHeight + marginBetweenY;
//            }
//            Log.d(TAG, "YYY6b1 left, top: " + left + ", " + top);
//            Log.d(TAG, "YYY6b2 textViewWidth, textViewHeight: " + textViewWidth + ", " + textViewHeight);
//            Log.d(TAG, "YYY6b3 marginBetweenY: " + marginBetweenY);
//            textView.setX(left);
//            textView.setY(dp2pixel(top));
//            mBinding.constraintLayoutMainTest.addView(textView);
//            left = left + textViewWidth + marginBetweenX;
//        }

//        TextView tv = new TextView(requireActivity().getApplicationContext());
//        tv.setText(stringBuilder);


//        final int widthPx = mBinding.constraintLayoutMainTest.getWidth();
//        Log.d(TAG, "YYY6 widthPx, widthDp: " + widthPx + ", " + widthPx*mPx2dpFactor);
//    }

//    @Override
//    protected void onPostCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        testTextSpan();
//    }

    //YYY1

    private void showDisplayParamsDialog() {
        final DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        final String displayParams = "Width (px): " + displayMetrics.widthPixels + " / Height (px): " + displayMetrics.heightPixels + "\n"
                + "xdpi: " + displayMetrics.xdpi + " / ydpi: " + displayMetrics.ydpi + "\n"
                + "density: " + displayMetrics.density + "\n"
                + "densityDpi: " + displayMetrics.densityDpi + "\n"
                + "scaledDensity: " + displayMetrics.scaledDensity;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(displayParams)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Display Parameters");
        alert.show();
//        setTheme(R.style.SplashScreenTheme);
//        mBinding.constraintViewMain.setVisibility(View.INVISIBLE);
    }

    public void showDialogPlusFlowersMain(View dummyView) {
        final Animation aniFadeIn = new AlphaAnimation(0.3f, 1.0f);
        final Animation aniFadeOut = new AlphaAnimation(1.0f, 0.3f);
        aniFadeOut.setDuration(300);
        aniFadeOut.setFillAfter(true);
        aniFadeIn.setDuration(300);
        mBinding.linearLayoutMainPager.startAnimation(aniFadeOut);
        final int totalHeight = Math.round(getResources().getDimension(R.dimen.dialogplus_total_height2));
        DialogPlusBuilder builder = DialogPlusWrapper.getDialogPlusBuilder(this, Gravity.CENTER, true,
                R.layout.dialogplus_content_flowers, android.R.color.transparent, android.R.color.transparent,
                R.layout.dialogplus_header_flowers, R.layout.dialogplus_footer_yes_no,
                ViewGroup.LayoutParams.WRAP_CONTENT, totalHeight);
        builder.setOnClickListener((dialog, view) -> {
            final int viewId = view.getId();
            if (viewId == R.id.dialogplus_footer_buttonYes) {
                Log.d(TAG, "Button \"Yes\" has been touched!");
                GlobalSettings.inst().setFlowerCollectionMode(true);
                dialog.dismiss();
//                        gotoLesson(new Pair<>(GlobalSettings.inst().getCurrentStageNumber(), GlobalSettings.inst().getCurrentLessonNumber()));
                gotoLessonForFlowerCollectionMain();
            } else if (viewId == R.id.dialogplus_footer_buttonNo) {
                Log.d(TAG, "Button \"No\" has been touched!");
                GlobalSettings.inst().setFlowerCollectionMode(false);
                dialog.dismiss();
            }
        });
        builder.setOnDismissListener(dialog -> mBinding.linearLayoutMainPager.startAnimation(aniFadeIn));
        final DialogPlus dialogPlus = builder.create();
        final int numOfFlowers = GlobalSettings.inst().getNumOfFlowers();
        final TextView tvHeader = dialogPlus.getHeaderView().findViewById(R.id.textViewDialogFlowersHeader);
        if (tvHeader != null) {
            final String headerText;
            if (numOfFlowers == 1) {
                headerText = getResources().getString(R.string.dialog_flowers_title_one);
            } else {
                headerText = getResources().getString(R.string.dialog_flowers_title, numOfFlowers);
            }
            tvHeader.setText(Html.fromHtml(headerText));
        }
        final TextView tvContent = dialogPlus.getHolderView().findViewById(R.id.textViewDialogFlowersContent);
        if (tvContent != null) {
            String contentText;
            if (numOfFlowers == GlobalSettings.inst().getDefaultNumOfFlowers()) {
                contentText = getResources().getString(R.string.dialog_flowers_content2);
            } else {
                contentText = getResources().getString(R.string.dialog_flowers_content1);
            }
            tvContent.setText(contentText);
        }
        dialogPlus.show();
    }

    private void showDialogPlusLanguage() {
        final int totalHeight = Math.round(getResources().getDimension(R.dimen.dialogplus_total_height4));
        DialogPlusBuilder builder = DialogPlusWrapper.getDialogPlusBuilder(this, Gravity.TOP, true,
                R.layout.dialogplus_content_language, android.R.color.transparent, android.R.color.transparent,
                R.layout.dialogplus_header_language, R.layout.dialogplus_footer_language,
                ViewGroup.LayoutParams.WRAP_CONTENT, totalHeight);
        builder.setOnClickListener((dialog, view) -> {
            final int viewId = view.getId();
            final AppCompatButton buttonEnglish = findViewById(R.id.dialogplus_language_content_buttonEnglish);
            final AppCompatButton buttonArabic = findViewById(R.id.dialogplus_language_content_buttonArabic);
            final AppCompatButton buttonTurkish = findViewById(R.id.dialogplus_language_content_buttonTurkish);
            final AppCompatButton buttonSelect = findViewById(R.id.dialogplus_language_footer_buttonSelect);
            if (viewId == R.id.dialogplus_language_content_buttonArabic) {
                buttonEnglish.setSelected(false);
                buttonArabic.setSelected(true);
                buttonTurkish.setSelected(false);
                buttonSelect.setEnabled(true);
            } else if (viewId == R.id.dialogplus_language_content_buttonTurkish) {
                buttonEnglish.setSelected(false);
                buttonArabic.setSelected(false);
                buttonTurkish.setSelected(true);
                buttonSelect.setEnabled(true);
            } else if (viewId == R.id.dialogplus_language_content_buttonEnglish) {
                buttonEnglish.setSelected(true);
                buttonArabic.setSelected(false);
                buttonTurkish.setSelected(false);
                buttonSelect.setEnabled(true);
            } else if (viewId == R.id.dialogplus_language_footer_buttonSelect) {
                if (buttonSelect.isEnabled()) {
                    SoundPlayer.getInstance().playSound("AllLessons", "click");
                    GlobalSettings.Language language = GlobalSettings.Language.ENGLISH;
                    if (buttonArabic.isSelected()) {
                        language = GlobalSettings.Language.ARABIC;
                    } else if (buttonTurkish.isSelected()) {
                        language = GlobalSettings.Language.TURKISH;
                    }
                    final SharedPreferences.Editor sharedPrefEditor = mSharedPreferences.edit();
                    sharedPrefEditor.putInt("sp_language", language.ordinal());
                    GlobalSettings.inst().setLanguage(language);
                    sharedPrefEditor.putBoolean("sp_first_time_ever_language", false);
                    // Note: need to use commit() and not apply() below. Otherwise, the app may restart before data is written down.
                    sharedPrefEditor.commit();
                    final Intent intent1 = new Intent(this, MainActivity.class);
//                            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            intent1.putExtra(KEY_RESTART_INTENT, nextIntent);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    this.startActivity(intent1);
                    if (this instanceof Activity) {
                        ((Activity) this).finish();
                    }
                    Runtime.getRuntime().exit(0);
                }
            } else if (viewId == R.id.dialogplus_language_footer_buttonCancel) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private ActivityMainBinding mBinding;
    private MainViewModel mMainViewModel;
    private StagesViewPagerAdapter mStagesViewPagerAdapter;
    private Map<String, Boolean> mStageLesson2IsActivated;
    private Map<String, Integer> mStageLessonExercise2IsDone;
    private Map<String, Integer> mStageLesson2NumStars;
    private final int mDEFAULT_SPLASH_SCREEN_DELAY = 3000; // ms
    private int mSplashScreenDelay = 0;
    final SimpleDateFormat mDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);

    private static final String TAG = MainActivity.class.getSimpleName();
}

// a á b c ç ć d e é f g ǵ ğ h ḣ ı i j k ḱ l ĺ m n o ö p ṕ q r s ş ś š t ṫ u ü w x y z ź
// A Á B C Ç Ć D E É F G Ǵ Ğ H Ḣ I İ J K Ḱ L Ĺ M N O Ö P Ṕ Q R S Ş Ś Š T Ṫ U Ü W X Y Z Ź
// áçćéǵğḣıḱĺöṕşśšṫüź
// ÁÇĆÉǴĞḢİḰĹÖṔŞŚŠṪÜŹ
//Adıǵe Адыгэ

//===========
// TODO list:
//===========
// test new question type, #8 - same as #7 but missing letter instead of a missing word - need example csv from Suad

// display size break points:
// sw315dp
// sw355dp
// sw375dp
// sw405dp
// sw535dp

// TODO add attribution for sound for correct answer: Sound from Zapsplat.com
// TODO fix question type 9 which causes the question after it to have disabled buttons - e.g. stage 2 lesson 1 ex. 5 last 3 questions. Also stage 2 lesson 3 ex. 3.
// TODO CANNOT REPRODUCE: bug in stage 2 lesson 4 ex. 1 - after answering last question, select is disabled.
// TODO fix turkish translation issue
// TODO proof-reading for the Latin content

// TODO disable debug features before production version (see method called in onCreate)
// Fix content issues found and listed in AdigaCharacterUtils at the end
// fix Latin in stage 2 lessons 3 & 4
// add YouTube video links
// double-check auto-reset numbers of DBs
