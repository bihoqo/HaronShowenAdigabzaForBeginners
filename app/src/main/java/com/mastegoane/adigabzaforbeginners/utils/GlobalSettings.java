package com.mastegoane.adigabzaforbeginners.utils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
import java.util.List;

public class GlobalSettings {

    public enum Language {ENGLISH, ARABIC, TURKISH}

    private GlobalSettings() {
        mLanguage = Language.ENGLISH;
        mCyrillic = new MutableLiveData<>();
        mCyrillic.setValue(true);
        mNumOfFlowers = mDEFAULT_NUM_OF_FLOWERS;
        mNumOfFlowersLD = new MutableLiveData<>();
        mNumOfFlowersLD.setValue(mNumOfFlowers);
        mStageNumOfLessons = new ArrayList<>();
    }

    public static synchronized GlobalSettings inst() {
        return mInstance;
    }

    public void setDebugFeaturesEnabled(boolean mode) {
        mDebugFeaturesEnabled = mode;
        if (!mDebugFeaturesEnabled && mDebugMode) {
            mDebugMode = false;
        }
    }

    public boolean isDebugFeaturesEnabled() {
        return mDebugFeaturesEnabled;
    }

    public int getCourseId() {
        if (isCyrillic()) {
            return 1;
        }
        return 2;
    }

    public void setCyrillic(boolean cyrillic) {
        mCyrillic.setValue(cyrillic);
    }
    public boolean isCyrillic() {
        return mCyrillic.getValue();
    }
    public LiveData<Boolean> getCyrillic() {
        return mCyrillic;
    }

    public void setDebugMode(boolean debugMode) {
        mDebugMode = debugMode;
    }

    public boolean isDebugMode() {
        return mDebugMode;
    }

    public void setLanguage(Language language) {
        mLanguage = language;
    }

    public Language getLanguage() {
        return mLanguage;
    }

    public int getNumOfStages() {
        return mStageNumOfLessons.size();
    }

    public List<Integer> getStageNumOfLessons() {
        return mStageNumOfLessons;
    }

    public void setStageNumOfLessons(List<Integer> stageNumOfLessons) {
        mStageNumOfLessons = stageNumOfLessons;
    }

    public void setCurrentTabStageNumber(int num) {
        mCurrentTabStageNumber = num;
    }
    public int getCurrentTabStageNumber() {
        return mCurrentTabStageNumber;
    }

    public void setCurrentStageNumber(int num) {
        mCurrentStageNumber = num;
    }
    public int getCurrentStageNumber() {
        return mCurrentStageNumber;
    }

    public void setCurrentLessonNumber(int num) {
        mCurrentLessonNumber = num;
    }
    public int getCurrentLessonNumber() {
        return mCurrentLessonNumber;
    }

    public void setCurrentExerciseNumber(int num) {
        mCurrentExerciseNumber = num;
    }
    public int getCurrentExerciseNumber() {
        return mCurrentExerciseNumber;
    }

    public void setSmallDb(boolean smallDb) {
        mDbgSmallDb = smallDb;
    }
    public boolean getSmallDb() {
        return mDbgSmallDb;
    }

    public void setUnlockAllLessons(boolean val) {
        mDbgUnlockAllLessons = val;
    }
    public boolean getUnlockAllLessons() {
        return mDbgUnlockAllLessons;
    }

    public int getMaxQuestionsPerExercise() {
        return mMAX_QUESTIONS_PER_EXERCISE_IN_SMALL_DB;
    }

    public int getDefaultNumOfFlowers() {
        return mDEFAULT_NUM_OF_FLOWERS;
    }

    public LiveData<Integer> getNumOfFlowersLD() {
        return mNumOfFlowersLD;
    }
    public int getNumOfFlowers() {
        return mNumOfFlowers;
    }
    public void setNumOfFlowers(int num) {
        mNumOfFlowers = num;
        mNumOfFlowersLD.setValue(num);
    }

    public boolean isFlowerCollectionMode() {
        return mFlowerCollectionMode;
    }
    public void setFlowerCollectionMode(boolean flowerCollectionMode) {
        this.mFlowerCollectionMode = flowerCollectionMode;
    }

    private static final GlobalSettings mInstance = new GlobalSettings();

    private boolean mDebugFeaturesEnabled = false;
    private final MutableLiveData<Boolean> mCyrillic;
    private Language mLanguage;
    private int mNumOfFlowers;
    private final MutableLiveData<Integer> mNumOfFlowersLD;

    private boolean mDbgSmallDb = false;
    private boolean mDebugMode = false;
    private boolean mDbgUnlockAllLessons = false;

    private List<Integer> mStageNumOfLessons;
    private int mCurrentTabStageNumber = 1;
    private int mCurrentStageNumber = 1;
    private int mCurrentLessonNumber = 1;
    private int mCurrentExerciseNumber = 1;
    private boolean mFlowerCollectionMode = false;

    // all the hard-coded constants in this class
    private final int mDEFAULT_NUM_OF_FLOWERS = 5;
    private final int mMAX_QUESTIONS_PER_EXERCISE_IN_SMALL_DB = 2;

    private static final String TAG = GlobalSettings.class.getSimpleName();
}

/*
#########
# States:
#########
DONE Language: English, Arabic, Turkish
    Scope: global
    Access: GlobalSettings

Alphabet: Cyrillic, Latin
    Scope: course
    Object: GlobalSettings
    Access: each alphabet has its own content and tracking

Course: CYRILLIC_WEST, LATIN_WEST
    Scope: global
    Access: GlobalSettings

numOfStars: 0-5
    Scope: course ?? Lesson
    Access: GlobalSettings ?? CourseTracker & LessonTracker

numOfFlowers: 0-5
    Scope: course
    Access: GlobalSettings
*/
