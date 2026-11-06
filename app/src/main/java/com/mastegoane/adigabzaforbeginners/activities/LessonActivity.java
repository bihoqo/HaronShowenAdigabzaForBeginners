package com.mastegoane.adigabzaforbeginners.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.activity.OnBackPressedCallback;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.SeekBar;
import android.widget.TextView;
import com.mastegoane.adigabzaforbeginners.R;
import com.mastegoane.adigabzaforbeginners.databases.QuestionData;
import com.mastegoane.adigabzaforbeginners.databases.LessonInfo;
import com.mastegoane.adigabzaforbeginners.databases.QuestionInfo;
import com.mastegoane.adigabzaforbeginners.databinding.ActivityLessonBinding;
import com.mastegoane.adigabzaforbeginners.utils.DialogPlusWrapper;
import com.mastegoane.adigabzaforbeginners.utils.GlobalSettings;
import com.mastegoane.adigabzaforbeginners.utils.SoundPlayer;
import com.mastegoane.adigabzaforbeginners.viewmodels.LessonViewModel;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;
import java.util.List;
import java.util.Objects;

public class LessonActivity extends BaseActivity implements GestureDetector.OnGestureListener {

    public void buttonLessonViewExit(View view) {
        showDialogPlusExitLesson(false);
//        finish();
    }

    // for debug/validation - does not support question type 9
    public void buttonLessonPickAnswerClicked(@NonNull View view) {
        final String tag = view.getTag().toString();
        String answerText;
        int answerId;
//        int correctAnswerId = mLessonViewModel.getCorrectAnswerId();
        if (tag.equals("pick_correct_answer")) {
            answerText = mLessonViewModel.getCorrectAnswer();
            answerId = mLessonViewModel.getCorrectAnswerId();
        } else {
            answerText = "!@# 👎🏻 $~`"; // Obviously not a correct answer (or is it 🤨)
            answerId = 0;
        }
        mLessonViewModel.setAnswerIsSelected(false);
        mLessonViewModel.setSelectedAnswerText(answerText);
        mLessonViewModel.setSelectedAnswerId(answerId);
        mLessonViewModel.setLastSelectedOptionId(answerId);
        mLessonViewModel.setSelectedOptionId(answerId);
        mLessonViewModel.setSelectButtonEnabled(true);
        mLessonViewModel.setSelectButtonActivated(true);
        mLessonViewModel.setSelectButtonBackgroundResource(R.drawable.background_select);
        mLessonViewModel.setSelectButtonTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        handleSelectButton(false, false, false);
    }

    public void buttonLessonNavigateClicked(View view) {
        mLessonViewModel.setAnswerIsSelected(true);
        final String tag = view.getTag().toString();
        Boolean largeStep = false;
        Boolean rewind = false;
        if (tag.contains("fast")) {
            largeStep = true;
        }
        if (tag.contains("rewind")) {
            rewind = true;
        }
        handleSelectButton(true, largeStep, rewind);
    }

    public void buttonLessonSelectClicked(View view) {
        handleSelectButton(false, false, false);
    }

    public void handleSelectButton(boolean freeNavigation, boolean largeStep, boolean gotoPrevious) {
        if (mLessonViewModel.isAnswerSelected()) {
            mLessonViewModel.setAnswerIsSelected(false);
            mLessonViewModel.setSelectButtonText(getResources().getString(R.string.button_select));
            mLessonViewModel.setSelectButtonEnabled(false);
            mLessonViewModel.setSelectButtonBackgroundResource(R.drawable.background_select_disabled);
            mLessonViewModel.setSelectButtonTextColor(ContextCompat.getColor(this, R.color.black));
            final boolean flowerCollectionMode = GlobalSettings.inst().isFlowerCollectionMode();
            if (mLessonViewModel.getExerciseIsDone()) {
                if (flowerCollectionMode) {
                    updateFlowers(true);
                    GlobalSettings.inst().setFlowerCollectionMode(false);
                }
                if (mLessonViewModel.getLessonIsDone()) {
                    showDialogPlusLessonDone();
                } else {
                    showDialogPlusExerciseDone();
                }
                return;
            }
            if (GlobalSettings.inst().getNumOfFlowers() == 0 && !flowerCollectionMode) {
                if (!mLessonViewModel.getExerciseIsDone()) {
                    mLessonViewModel.resetExercise();
                }
                showDialogPlusFlowersLesson();
                return;
            }

            if (!mLessonViewModel.setNextQuestion(freeNavigation, largeStep, gotoPrevious, -1)) {
                Log.e(TAG, "-B- BUG: Should not reach here: nextQuestionExists == false");
                return;
            }

//            // TODO debug stuff - remove in production
//            final QuestionData questionData = mLessonViewModel.getCurrentQuestion();
//            String debugText = "type: " + questionData.getQuestionType() + "\n" +
//                    "subText: " + questionData.getQuestionSubText() + "\n" +
//                    "subTextEnglish: " + questionData.getQuestionSubTextEnglish() + "\n" +
//                    "subTextArabic: " + questionData.getQuestionSubTextArabic() + "\n" +
//                    "subTextTurkish: " + questionData.getQuestionSubTextTurkish();
//            mBinding.textViewLessonDebugPanel1.setText(debugText);

            final String sound = mLessonViewModel.getCurrentQuestionSound().getValue();
            if (sound != null) {
                SoundPlayer.getInstance().playSound("AllLessons", sound);
            }
            mLessonViewModel.resetQuestion();
//            mBinding.linearLayoutLessonResult.setVisibility(View.INVISIBLE);
//            mBinding.frameLayoutLessonResultBackground.setVisibility(View.INVISIBLE);
            mLessonViewModel.setResultStatus(0); // invisible
        } else {
            if (mLessonViewModel.getCurrentQuestionType().getValue() == 9) {
//                if (mLessonViewModel.checkAnswer()) {
//                    mBinding.imageViewLessonResult.setBackgroundResource(R.drawable.correct230x230);
//                    mBinding.textViewLessonResult.setText(getResources().getString(R.string.lesson_correct_answer));
//                    mBinding.textViewLessonResult.setTextColor(ContextCompat.getColor(this, R.color.lesson_correct_answer_text_color));
//                    mLessonViewModel.setSelectButtonActivated(true);
//                    SoundPlayer.getInstance().playSound("AllLessons", "9001b");
//                } else {
//                    mBinding.imageViewLessonResult.setBackgroundResource(R.drawable.wrong230x230);
//                    mBinding.textViewLessonResult.setText(getResources().getString(R.string.lesson_wrong_answer) + mLessonViewModel.getCorrectAnswer());
//                    mBinding.textViewLessonResult.setTextColor(ContextCompat.getColor(this, R.color.lesson_wrong_answer_text_color));
//                    mLessonViewModel.setSelectButtonActivated(false);
//                    SoundPlayer.getInstance().playSound("AllLessons", "9002");
//                    updateFlowers(false);
//                }
//                mBinding.linearLayoutLessonResult.setVisibility(View.VISIBLE);
//                mBinding.frameLayoutLessonResultBackground.setVisibility(View.VISIBLE);
//                mLessonViewModel.setSelectButtonText(getResources().getString(R.string.next));

                mLessonViewModel.setSelectButtonEnabled(false);
                mLessonViewModel.setSelectButtonActivated(false);
                mLessonViewModel.setSelectButtonBackgroundResource(R.drawable.background_select_disabled);
                mLessonViewModel.setSelectButtonTextColor(ContextCompat.getColor(this, R.color.black));
                mLessonViewModel.setSelectButtonText(getResources().getString(R.string.button_select));
                mLessonViewModel.setResultStatus(0); // invisible
            } else {
                if (freeNavigation) {
//                    mBinding.imageViewLessonResult.setBackgroundResource(R.drawable.correct230x230);
//                    mBinding.textViewLessonResult.setText(getResources().getString(R.string.lesson_correct_answer));
//                    mBinding.textViewLessonResult.setTextColor(ContextCompat.getColor(this, R.color.lesson_correct_answer_text_color));
//                    mBinding.linearLayoutLessonResult.setVisibility(View.VISIBLE);
//                    mBinding.frameLayoutLessonResultBackground.setVisibility(View.VISIBLE);
                    mLessonViewModel.setResultStatus(1); // visible, correct
                    mLessonViewModel.setSelectButtonActivated(true);
                    SoundPlayer.getInstance().playSound("AllLessons", "9001b");
                    mLessonViewModel.setSelectButtonText(getResources().getString(R.string.next));
                    return;
                }
                mLessonViewModel.setAnswerIsSelected(true);
                if (mLessonViewModel.checkAnswer()) {
//                    mBinding.imageViewLessonResult.setBackgroundResource(R.drawable.correct230x230);
//                    mBinding.textViewLessonResult.setText(getResources().getString(R.string.lesson_correct_answer));
//                    mBinding.textViewLessonResult.setTextColor(ContextCompat.getColor(this, R.color.lesson_correct_answer_text_color));
                    mLessonViewModel.setResultStatus(1); // visible, correct
                    mLessonViewModel.setSelectButtonActivated(true);
                    SoundPlayer.getInstance().playSound("AllLessons", "9001b");
                } else {
//                    mBinding.imageViewLessonResult.setBackgroundResource(R.drawable.wrong230x230);
//                    mBinding.textViewLessonResult.setText(getResources().getString(R.string.lesson_wrong_answer) + mLessonViewModel.getCorrectAnswer());
//                    mBinding.textViewLessonResult.setTextColor(ContextCompat.getColor(this, R.color.lesson_wrong_answer_text_color));
                    mLessonViewModel.setResultStatus(2); // visible, wrong
                    mLessonViewModel.setSelectButtonActivated(false);
                    SoundPlayer.getInstance().playSound("AllLessons", "9002");
                    updateFlowers(false);
                }
//                mBinding.linearLayoutLessonResult.setVisibility(View.VISIBLE);
//                mBinding.frameLayoutLessonResultBackground.setVisibility(View.VISIBLE);
                mLessonViewModel.setSelectButtonText(getResources().getString(R.string.next));
            }
        }
    }

    private void updateFlowers(boolean increase) {
        final boolean flowerCollectionMode = GlobalSettings.inst().isFlowerCollectionMode();
        final int currentNumOfFlowers = GlobalSettings.inst().getNumOfFlowers();
        int newNumOfFlowers = currentNumOfFlowers;
        if (increase) {
            if (!flowerCollectionMode) {
                return;
            }
            if (currentNumOfFlowers == 0) {
                ++newNumOfFlowers;
            } else {
                newNumOfFlowers = newNumOfFlowers + 2;
            }
            final int defaultNumOfFlowers = GlobalSettings.inst().getDefaultNumOfFlowers();
            if (newNumOfFlowers > defaultNumOfFlowers) {
                newNumOfFlowers = defaultNumOfFlowers;
            }
        } else {
            if (flowerCollectionMode) {
                return;
            }
            --newNumOfFlowers;
            if (newNumOfFlowers < 0) {
                newNumOfFlowers = 0;
            }
        }
        if (newNumOfFlowers != currentNumOfFlowers) {
            GlobalSettings.inst().setNumOfFlowers(newNumOfFlowers);
            final SharedPreferences.Editor sharedPrefEditor = mSharedPreferences.edit();
//            sharedPrefEditor.putInt("sp_num_of_flowers", newNumOfFlowers);
            sharedPrefEditor.putInt("sp_num_of_flowers_course_" + GlobalSettings.inst().getCourseId(), newNumOfFlowers);
            sharedPrefEditor.apply();
        }
    }

    private void selectFragment(int questionType) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        switch (questionType) {
            case 1:
            case 2:
            case 3:
                fragmentTransaction.replace(R.id.frameLayoutLessonFragmentQuestion, mFragmentTypeA, null);
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                fragmentTransaction.replace(R.id.frameLayoutLessonFragmentQuestion, mFragmentTypeB, null);
                break;
            case 9:
                fragmentTransaction.replace(R.id.frameLayoutLessonFragmentQuestion, mFragmentTypeC, null);
                break;
            case 10:
                fragmentTransaction.replace(R.id.frameLayoutLessonFragmentQuestion, mFragmentTypeD, null);
                break;
            default:
                //TODO should not reach here - add error/dialog??
                Log.e(TAG, "-B- BUG: selectFragment(): unexpected question type: " + questionType);
                fragmentTransaction.replace(R.id.frameLayoutLessonFragmentQuestion, mFragmentTypeA, null);
                break;
        }
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AdigabzaForBeginners);
        mLessonViewModel = new ViewModelProvider(this,
                (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(LessonViewModel.class);
        mBinding = ActivityLessonBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        setSupportActionBar(mBinding.toolBarLesson);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTypeA = new QuestionTypeAFragment();
        mFragmentTypeB = new QuestionTypeBFragment();
        mFragmentTypeC = new QuestionTypeCFragment();
        mFragmentTypeD = new QuestionTypeDFragment();
//        selectFragment(1);

        mBinding.seekBarLesson.setEnabled(false);
        mOnSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // updated continuously as the user slides the thumb
                if (progress > 0) {
//                    mDontUpdateDebugNavSeekbar = true;
                    mLessonViewModel.setNextQuestion(true, false, false, progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // called when the user first touches the SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // called after the user finishes moving the SeekBar
            }
        };
        mBinding.seekBarLessonDebugNav.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        updateDebugViews();

        final Pair<Integer, Integer> lessonId = new Pair<Integer, Integer>(GlobalSettings.inst().getCurrentStageNumber(), GlobalSettings.inst().getCurrentLessonNumber());
        mLessonViewModel.setLessonId(lessonId);

        mLessonViewModel.setExerciseNumber(GlobalSettings.inst().getCurrentExerciseNumber());

        if (GlobalSettings.inst().isFlowerCollectionMode()) {
            if (!mLessonViewModel.initForFlowerCollection()) {
                // TODO show a dialog with summary
                finish();
            }
        } else {
            // it calls resetExercise() but it has no impact - exercise resumes from last time's question
            final int previousProcessID = mSharedPreferences.getInt("sp_process_id", -1);
            final int currentProcessID = android.os.Process.myPid();
            Log.d(TAG, "YYY431 previousProcessID, currentProcessID: " + previousProcessID + ", " + currentProcessID);
            if ((previousProcessID != currentProcessID)) {
                final SharedPreferences.Editor sharedPrefEditor = mSharedPreferences.edit();
                sharedPrefEditor.putInt("sp_process_id", android.os.Process.myPid());
                sharedPrefEditor.commit();
                // next check if getExerciseIsDone() is redundant
                if (!mLessonViewModel.getExerciseIsDone()) {
                    mLessonViewModel.resetExercise();
                }
            }

            final LiveData<List<QuestionData>> lessonQuestionsLD = mLessonViewModel.getLessonQuestionDatas(lessonId);
            lessonQuestionsLD.observe(this, new Observer<List<QuestionData>>() {
                @Override
                public void onChanged(List<QuestionData> questionDataList) {
                    if (questionDataList != null && !questionDataList.isEmpty()) {
                        if (!mLessonViewModel.init(questionDataList)) {
                            // TODO show a dialog with summary
                            finish();
                        }
                        Log.d(TAG, "number of questions: " + lessonQuestionsLD.getValue().size());
                    }
                }
            });
        }

        final LiveData<Integer> questionTypeLD = mLessonViewModel.getCurrentQuestionType();
        questionTypeLD.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer questionType) {
                selectFragment(questionType);
            }
        });

        LiveData<Integer> numOfFlowersLD = GlobalSettings.inst().getNumOfFlowersLD();
        numOfFlowersLD.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mBinding.textViewLessonFlowers.setText(integer.toString());
            }
        });

        LiveData<Integer> resultStatusLD = mLessonViewModel.getResultStatus();
        resultStatusLD.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                switch (val) {
                    case 0: // invisible
                        mBinding.linearLayoutLessonResult.setVisibility(View.INVISIBLE);
                        mBinding.frameLayoutLessonResultBackground.setVisibility(View.INVISIBLE);
                        break;
                    case 1: // visible, correct
                        mBinding.imageViewLessonResult.setBackgroundResource(R.drawable.correct230x230);
                        mBinding.textViewLessonResult.setText(getResources().getString(R.string.lesson_correct_answer));
                        mBinding.textViewLessonResult.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.lesson_correct_answer_text_color));

                        mBinding.linearLayoutLessonResult.setVisibility(View.VISIBLE);
                        mBinding.frameLayoutLessonResultBackground.setVisibility(View.VISIBLE);
                        break;
                    case 2: // visible, wrong
                        mBinding.imageViewLessonResult.setBackgroundResource(R.drawable.wrong230x230);
                        mBinding.textViewLessonResult.setText(getResources().getString(R.string.lesson_wrong_answer) + mLessonViewModel.getCorrectAnswer());
                        mBinding.textViewLessonResult.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.lesson_wrong_answer_text_color));

                        mBinding.linearLayoutLessonResult.setVisibility(View.VISIBLE);
                        mBinding.frameLayoutLessonResultBackground.setVisibility(View.VISIBLE);
                        break;
                    case 3: // visible, first time wrong pair (in question type 9)
                        mBinding.imageViewLessonResult.setBackgroundResource(R.drawable.wrong230x230);
                        mBinding.textViewLessonResult.setText(getResources().getString(R.string.lesson_wrong_answer_type9));
                        mBinding.textViewLessonResult.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.lesson_wrong_answer_text_color));

                        mBinding.linearLayoutLessonResult.setVisibility(View.VISIBLE);
                        mBinding.frameLayoutLessonResultBackground.setVisibility(View.VISIBLE);
                        updateFlowers(false);
                        break;
                    default:
                        Log.e(TAG, "-B- BUG: Unexpected value for resultStatusLD: " + val);
                        mBinding.linearLayoutLessonResult.setVisibility(View.INVISIBLE);
                        mBinding.frameLayoutLessonResultBackground.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        });

        LiveData<Boolean> selectButtonEnabledLD = mLessonViewModel.getSelectButtonEnabled();
        selectButtonEnabledLD.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean val) {
                mBinding.buttonLessonSelect.setEnabled(val);
            }
        });

        LiveData<Boolean> selectButtonActivatedLD = mLessonViewModel.getSelectButtonActivated();
        selectButtonActivatedLD.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean val) {
                mBinding.buttonLessonSelect.setActivated(val);
            }
        });

        LiveData<Integer> selectButtonBackgroundResourceLD = mLessonViewModel.getSelectButtonBackgroundResource();
        selectButtonBackgroundResourceLD.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                mBinding.buttonLessonSelect.setBackgroundResource(val);
            }
        });

        LiveData<Integer> selectButtonTextColorLD = mLessonViewModel.getSelectButtonTextColor();
        selectButtonTextColorLD.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                mBinding.buttonLessonSelect.setTextColor(val);
            }
        });

        LiveData<String> selectButtonTextLD = mLessonViewModel.getSelectButtonText();
        selectButtonTextLD.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String val) {
                mBinding.buttonLessonSelect.setText(val);
            }
        });

        final LiveData<String> questionSoundLD = mLessonViewModel.getCurrentQuestionSound();
        questionSoundLD.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                SoundPlayer.getInstance().playSound("AllLessons", s);
            }
        });

        LiveData<List<QuestionInfo>> questionInfosLD = mLessonViewModel.getQuestionInfoEntries();
        questionInfosLD.observe(this, new Observer<List<QuestionInfo>>() {
            @Override
            public void onChanged(List<QuestionInfo> questionInfos) {
                mLessonViewModel.refreshQuestionInfos(questionInfos);
//                updateViews();
            }
        });

        LiveData<List<LessonInfo>> lessonInfosLD = mLessonViewModel.getLessonInfoEntries();
        lessonInfosLD.observe(this, new Observer<List<LessonInfo>>() {
            @Override
            public void onChanged(List<LessonInfo> lessonInfos) {
//                mLessonViewModel.refreshLessonInfos(lessonInfos);
////                updateViews();
            }
        });

        final LiveData<Integer> questionNumberLD = mLessonViewModel.getCurrentQuestionNumber();
        questionNumberLD.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                final QuestionData questionData = mLessonViewModel.getCurrentQuestion();
                if (questionData != null) {
                    final int exerciseNumber = questionData.getExerciseNumber();
                    mBinding.textViewLessonQNumber.setText("S" + questionData.getStageNumber() + " L" + questionData.getLessonNumber() + " E" + exerciseNumber + " Q" + integer);
                }
                mBinding.seekBarLessonDebugNav.setOnSeekBarChangeListener(null);
                // set the bottom (debug/navigation) seekbar based on current question index
                final int currentQuestionIndex = mLessonViewModel.getCurrentQuestionIndex() + 1;
                mBinding.seekBarLessonDebugNav.setProgress(currentQuestionIndex);
                mBinding.seekBarLessonDebugNav.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
                // set the top seekbar based on number of correctly answered questions in the entire lesson
                mBinding.seekBarLesson.setProgress(mLessonViewModel.getTotalCorrectAnswersInExercise());
            }
        });
        final LiveData<Integer> totalQuestionsLD = mLessonViewModel.getNumberOfQuestions();
        totalQuestionsLD.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mBinding.textViewLessonQTotal.setText(" total: " + integer);
                mBinding.seekBarLessonDebugNav.setOnSeekBarChangeListener(null);
                mBinding.seekBarLessonDebugNav.setMax(integer);
                mBinding.seekBarLessonDebugNav.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
                mBinding.seekBarLesson.setMax(mLessonViewModel.getNumberOfQuestionsInExercise());
            }
        });

        // TODO set sliding debug panel
        mGestureDetector = new GestureDetectorCompat(this, this);

        mExitOnBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                setEnabled(false);
                showDialogPlusExitLesson(true);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, mExitOnBackPressedCallback);

        // TODO TEMPORARY
//        showDialogPlusExerciseDone();
//        showDialogPlusLessonDone();
//        showDialogPlusFlowersLesson();
//        showDialogPlusExitLesson(false);
    }

//    @Override
//    public void finish() {
//        showDialogPlusExitLesson();
////        super.finish();
//    }

    // START sliding debug panel methods
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (this.mGestureDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
    }

    // TODO debug stuff - disable in production
    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        if (!GlobalSettings.inst().isDebugMode()) {
            return false;
        }
        final float deltaX = motionEvent.getX() - motionEvent1.getX();
        final float deltaYAbs = Math.abs(motionEvent.getY() - motionEvent1.getY());
        if (deltaX < 50 && deltaYAbs < 50) {
            // fling right
            if (mDebugPanelIsIn) {
                debugPanelSlideOut(mBinding.linearLayoutLessonDebugPanel);
            }
        } else if (deltaX > 50 && deltaYAbs < 50) {
            String debugText1 = "";
            for(QuestionData questionData : mLessonViewModel.getExerciseRemainingQuestionDatas()) {
                debugText1 = debugText1 + "S" + questionData.getStageNumber()
                        + "   L" + questionData.getLessonNumber()
                        + "   E" + questionData.getExerciseNumber()
                        + "   Q" + questionData.getQuestionNumber()
                        + "\n";
            }
            mBinding.textViewLessonDebugPanel1.setText(debugText1);
            String debugText2 = "Total questions in exercise: " + mLessonViewModel.getNumberOfQuestionsInExercise() + "\n"
                    + "Answered correctly: " + mLessonViewModel.getTotalCorrectAnswersInExercise();
            mBinding.textViewLessonDebugPanel2.setText(debugText2);
            // fling left
            if (!mDebugPanelIsIn) {
                debugPanelSlideIn(mBinding.linearLayoutLessonDebugPanel);
            }
        }
        return true;
    }

    // slide the debug panel in from right edge of the screen
    public void debugPanelSlideIn(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(0, -view.getWidth(), 0, 0);
        animate.setDuration(300);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        mDebugPanelIsIn = true;
    }

    // slide the debug panel out from the right edge of the screen
    public void debugPanelSlideOut(View view) {
        TranslateAnimation animate = new TranslateAnimation(-view.getWidth(), 0, 0, 0);
        animate.setDuration(300);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        mDebugPanelIsIn = false;
    }
    // END sliding debug panel methods

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        mMenu = menu;
        MenuInflater menuInflater = getMenuInflater();
        if (GlobalSettings.inst().isDebugFeaturesEnabled()) {
            menuInflater.inflate(R.menu.lesson_menu_with_debug, menu);
        } else {
            menuInflater.inflate(R.menu.lesson_menu, menu);
        }
        return true;
    }

    private void updateDebugViews() {
        if (GlobalSettings.inst().isDebugMode()) {
            mBinding.imageViewLessonDebugMode.setVisibility(View.VISIBLE);
//            mBinding.buttonLessonPrevious.setVisibility(View.VISIBLE);
//            mBinding.buttonLessonNext.setVisibility(View.VISIBLE);
            mBinding.linearLayoutLessonDebugNav.setVisibility(View.VISIBLE);
            mBinding.textViewLessonQNumber.setVisibility(View.VISIBLE);
            mBinding.textViewLessonQTotal.setVisibility(View.VISIBLE);
        } else {
            mBinding.imageViewLessonDebugMode.setVisibility(View.INVISIBLE);
//            mBinding.buttonLessonPrevious.setVisibility(View.INVISIBLE);
//            mBinding.buttonLessonNext.setVisibility(View.INVISIBLE);
            mBinding.linearLayoutLessonDebugNav.setVisibility(View.GONE);
            mBinding.textViewLessonQNumber.setVisibility(View.INVISIBLE);
            mBinding.textViewLessonQTotal.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int itemId = item.getItemId();
        if (itemId == R.id.about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        if (GlobalSettings.inst().isDebugFeaturesEnabled()) {
            // TODO disable all DBG cases before production release
            if (itemId == R.id.dbg_debug_mode) {
                // Toggle debug mode
                final boolean debugMode = !GlobalSettings.inst().isDebugMode();
                final SharedPreferences.Editor sharedPrefEditor4 = mSharedPreferences.edit();
                sharedPrefEditor4.putBoolean("sp_is_debug_mode", debugMode);
                sharedPrefEditor4.apply();
                GlobalSettings.inst().setDebugMode(debugMode);
                updateDebugViews();
                return true;
            } else if (itemId == R.id.dbg_dictionary) {
                startActivity(new Intent(this, Debug1Activity.class));
                return true;
            } else if (itemId == R.id.bg_music) {
                // TODO disable before production release
                SoundPlayer.getInstance().toggleBackgroundMusic();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


    private void showDialogPlusExerciseDone() {
        final int totalHeight = Math.round(getResources().getDimension(R.dimen.dialogplus_total_height1));
        mBinding.frameLayoutLessonSemitransparentBG.setVisibility(View.VISIBLE);
        DialogPlusBuilder builder = DialogPlusWrapper.getDialogPlusBuilder(this, Gravity.CENTER, false,
                R.layout.dialogplus_content_exercise_done, android.R.color.transparent, android.R.color.transparent,
                0, 0,
                ViewGroup.LayoutParams.WRAP_CONTENT, totalHeight);
        builder.setOnClickListener((dialog, view) -> {
            mBinding.frameLayoutLessonSemitransparentBG.setVisibility(View.INVISIBLE);
            final int viewId = view.getId();
            if (viewId == R.id.dialogplus_exercise_done_footer_buttonOk) {
//                dialog.dismiss();
                finish();
            }
        });
        builder.create().show();
    }

    private void showDialogPlusLessonDone() {
        final int totalHeight = Math.round(getResources().getDimension(R.dimen.dialogplus_total_height1));
        mBinding.frameLayoutLessonSemitransparentBG.setVisibility(View.VISIBLE);
        DialogPlusBuilder builder = DialogPlusWrapper.getDialogPlusBuilder(this, Gravity.CENTER, false,
                R.layout.dialogplus_content_lesson_done, android.R.color.transparent, android.R.color.transparent,
                0, 0,
                ViewGroup.LayoutParams.WRAP_CONTENT, totalHeight);
        builder.setOnClickListener((dialog, view) -> {
            mBinding.frameLayoutLessonSemitransparentBG.setVisibility(View.INVISIBLE);
            final int viewId = view.getId();
            if (viewId == R.id.dialogplus_lesson_done_footer_buttonOk) {
//                dialog.dismiss();
                finish();
            }
        });
        builder.create().show();
    }

    private void gotoLessonForFlowerCollectionLesson() {
        mLessonViewModel.setResultStatus(0); // invisible
        GlobalSettings.inst().setCurrentStageNumber(-1);
        GlobalSettings.inst().setCurrentLessonNumber(-1);
        GlobalSettings.inst().setCurrentExerciseNumber(-1);
        mDebugPanelIsIn = false;
        mOnSeekBarChangeListener = null;
        recreate();
    }

    private void showDialogPlusFlowersLesson() {
        mBinding.frameLayoutLessonSemitransparentBG.setVisibility(View.VISIBLE);
//        Animation aniFadeIn = new AlphaAnimation(0.3f, 1.0f);
//        Animation aniFadeOut = new AlphaAnimation(1.0f, 0.3f);
//        aniFadeOut.setDuration(300);
//        aniFadeOut.setFillAfter(true);
//        aniFadeIn.setDuration(300);
//        mBinding.frameLayoutLessonFragmentQuestion.startAnimation(aniFadeOut);
        final int totalHeight = Math.round(getResources().getDimension(R.dimen.dialogplus_total_height2));
        DialogPlusBuilder builder = DialogPlusWrapper.getDialogPlusBuilder(this, Gravity.CENTER, true,
                R.layout.dialogplus_content_flowers, android.R.color.transparent, android.R.color.transparent,
                R.layout.dialogplus_header_flowers, R.layout.dialogplus_footer_yes_no,
                ViewGroup.LayoutParams.WRAP_CONTENT, totalHeight);
        builder.setOnClickListener((dialog, view) -> {
            mBinding.frameLayoutLessonSemitransparentBG.setVisibility(View.INVISIBLE);
            final int viewId = view.getId();
            if (viewId == R.id.dialogplus_footer_buttonYes) {
                Log.d(TAG, "Button \"Yes\" has been touched!");
                GlobalSettings.inst().setFlowerCollectionMode(true);
                dialog.dismiss();
//                        restartQuestion();
                gotoLessonForFlowerCollectionLesson();
            } else if (viewId == R.id.dialogplus_footer_buttonNo) {
                Log.d(TAG, "Button \"No\" has been touched!");
                GlobalSettings.inst().setFlowerCollectionMode(false);
//                dialog.dismiss();
                finish();
            }
        });
        builder.setOnDismissListener(dialog -> mBinding.frameLayoutLessonSemitransparentBG.setVisibility(View.INVISIBLE));;
//        builder.setOnDismissListener(dialog -> mBinding.frameLayoutLessonFragmentQuestion.startAnimation(aniFadeIn));
        final DialogPlus dialogPlus = builder.create();
        final int numOfFlowers = GlobalSettings.inst().getNumOfFlowers();
        final TextView tvHeader = dialogPlus.getHeaderView().findViewById(R.id.textViewDialogFlowersHeader);
        if (tvHeader != null) {
            String headerText;
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

    private void showDialogPlusExitLesson(boolean backPressed) {
        // if no question was answered yet then don't show dialog
        if (!mLessonViewModel.getAnsweredOneQuestionOrMore()) {
            finish();
        }
        mBinding.frameLayoutLessonSemitransparentBG.setVisibility(View.VISIBLE);
        final int totalHeight = Math.round(getResources().getDimension(R.dimen.dialogplus_total_height2));
        DialogPlusBuilder builder = DialogPlusWrapper.getDialogPlusBuilder(this, Gravity.CENTER, true,
                R.layout.dialogplus_content_exit_lesson, android.R.color.transparent, android.R.color.transparent,
                R.layout.dialogplus_header_exit_lesson, R.layout.dialogplus_footer_yes_no,
                ViewGroup.LayoutParams.WRAP_CONTENT, totalHeight);
        builder.setOnClickListener((dialog, view) -> {
//            mBinding.frameLayoutLessonSemitransparentBG.setVisibility(View.INVISIBLE);
            final int viewId = view.getId();
            if (viewId == R.id.dialogplus_footer_buttonYes) {
                if (!mLessonViewModel.getExerciseIsDone()) {
                    mLessonViewModel.resetExercise();
                }
                finish();
            } else if (viewId == R.id.dialogplus_footer_buttonNo) {
//                mBinding.frameLayoutLessonSemitransparentBG.setVisibility(View.INVISIBLE);
                dialog.dismiss();
            }
        });
        builder.setOnDismissListener(dialog -> {
            mBinding.frameLayoutLessonSemitransparentBG.setVisibility(View.INVISIBLE);
            if (backPressed && mExitOnBackPressedCallback != null) {
                mExitOnBackPressedCallback.setEnabled(true);
            }
        });;
        builder.create().show();
    }

    private LessonViewModel mLessonViewModel;
    private ActivityLessonBinding mBinding;
    private OnBackPressedCallback mExitOnBackPressedCallback;
    private boolean mDebugPanelIsIn = false;
    private GestureDetectorCompat mGestureDetector;
    private FragmentManager mFragmentManager;
    private QuestionTypeAFragment mFragmentTypeA;
    private QuestionTypeBFragment mFragmentTypeB;
    private QuestionTypeCFragment mFragmentTypeC;
    private QuestionTypeDFragment mFragmentTypeD;
//    boolean mDontUpdateDebugNavSeekbar = false;
    private SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener = null;

    private static final String TAG = LessonActivity.class.getSimpleName();
}
