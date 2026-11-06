package com.mastegoane.adigabzaforbeginners.viewmodels;

import android.app.Application;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.collection.ArraySet;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.mastegoane.adigabzaforbeginners.R;
import com.mastegoane.adigabzaforbeginners.databases.QuestionData;
import com.mastegoane.adigabzaforbeginners.databases.LessonInfo;
import com.mastegoane.adigabzaforbeginners.databases.LessonInfoRepository;
import com.mastegoane.adigabzaforbeginners.databases.QuestionDataRepository;
import com.mastegoane.adigabzaforbeginners.databases.QuestionInfo;
import com.mastegoane.adigabzaforbeginners.databases.QuestionInfoRepository;
import com.mastegoane.adigabzaforbeginners.datastructures.LessonTracker;
import com.mastegoane.adigabzaforbeginners.utils.GlobalSettings;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class LessonViewModel extends AndroidViewModel {

    public enum ButtonOptionState {UNSELECTED, BLUE, GREEN, RED, DISABLED}

    public LessonViewModel(@NonNull Application application) {
        super(application);
        mApplication = application;
        mCurrentQuestionImage = new MutableLiveData<>();
        mCurrentQuestionText = new MutableLiveData<>();
        mCurrentQuestionSound = new MutableLiveData<>();

        mCurrentOption1 = new MutableLiveData<>();
        mCurrentOption2 = new MutableLiveData<>();
        mCurrentOption3 = new MutableLiveData<>();
        mCurrentOption4 = new MutableLiveData<>();

        mCurrentOptionA1 = new MutableLiveData<>();
        mCurrentOptionA2 = new MutableLiveData<>();
        mCurrentOptionA3 = new MutableLiveData<>();
        mCurrentOptionA4 = new MutableLiveData<>();
        mCurrentOptionA5 = new MutableLiveData<>();
        mCurrentOptionB1 = new MutableLiveData<>();
        mCurrentOptionB2 = new MutableLiveData<>();
        mCurrentOptionB3 = new MutableLiveData<>();
        mCurrentOptionB4 = new MutableLiveData<>();
        mCurrentOptionB5 = new MutableLiveData<>();

//        mCurrentQuestionIndex = new MutableLiveData<>();
        mCurrentQuestionNumber = new MutableLiveData<>();
        mCurrentQuestionType = new MutableLiveData<>();
//        mExerciseNumber = new MutableLiveData<>();
        mNumberOfQuestions = new MutableLiveData<>();
        mQuestionDataRepository = new QuestionDataRepository(mApplication);
        mQuestionInfoRepository = new QuestionInfoRepository(mApplication);
        mLessonInfoRepository = new LessonInfoRepository(mApplication);
        mLessonInfo = mLessonInfoRepository.getLessonInfoByIdOfCourse(GlobalSettings.inst().getCourseId(), mLessonId.first, mLessonId.second);

        mResultStatus = new MutableLiveData<>();

        mSelectButtonEnabled = new MutableLiveData<>();
        mSelectButtonActivated = new MutableLiveData<>();
        mSelectButtonBackgroundResource = new MutableLiveData<>();
        mSelectButtonTextColor = new MutableLiveData<>();
        mSelectButtonText = new MutableLiveData<>();

        mButtonLessonOptionEnabled1 = new MutableLiveData<>();
        mButtonLessonOptionEnabled2 = new MutableLiveData<>();
        mButtonLessonOptionEnabled3 = new MutableLiveData<>();
        mButtonLessonOptionEnabled4 = new MutableLiveData<>();

        mButtonLessonOptionEnabledA1 = new MutableLiveData<>();
        mButtonLessonOptionEnabledA2 = new MutableLiveData<>();
        mButtonLessonOptionEnabledA3 = new MutableLiveData<>();
        mButtonLessonOptionEnabledA4 = new MutableLiveData<>();
        mButtonLessonOptionEnabledA5 = new MutableLiveData<>();

        mButtonLessonOptionEnabledB1 = new MutableLiveData<>();
        mButtonLessonOptionEnabledB2 = new MutableLiveData<>();
        mButtonLessonOptionEnabledB3 = new MutableLiveData<>();
        mButtonLessonOptionEnabledB4 = new MutableLiveData<>();
        mButtonLessonOptionEnabledB5 = new MutableLiveData<>();

        mButtonLessonOptionBackgroundResource1 = new MutableLiveData<>();
        mButtonLessonOptionBackgroundResource2 = new MutableLiveData<>();
        mButtonLessonOptionBackgroundResource3 = new MutableLiveData<>();
        mButtonLessonOptionBackgroundResource4 = new MutableLiveData<>();
        mButtonLessonOptionTextColor1 = new MutableLiveData<>();
        mButtonLessonOptionTextColor2 = new MutableLiveData<>();
        mButtonLessonOptionTextColor3 = new MutableLiveData<>();
        mButtonLessonOptionTextColor4 = new MutableLiveData<>();

        mButtonLessonOptionBackgroundResourceA1 = new MutableLiveData<>();
        mButtonLessonOptionBackgroundResourceA2 = new MutableLiveData<>();
        mButtonLessonOptionBackgroundResourceA3 = new MutableLiveData<>();
        mButtonLessonOptionBackgroundResourceA4 = new MutableLiveData<>();
        mButtonLessonOptionBackgroundResourceA5 = new MutableLiveData<>();

        mButtonLessonOptionBackgroundResourceB1 = new MutableLiveData<>();
        mButtonLessonOptionBackgroundResourceB2 = new MutableLiveData<>();
        mButtonLessonOptionBackgroundResourceB3 = new MutableLiveData<>();
        mButtonLessonOptionBackgroundResourceB4 = new MutableLiveData<>();
        mButtonLessonOptionBackgroundResourceB5 = new MutableLiveData<>();

        mButtonLessonOptionTextColorA1 = new MutableLiveData<>();
        mButtonLessonOptionTextColorA2 = new MutableLiveData<>();
        mButtonLessonOptionTextColorA3 = new MutableLiveData<>();
        mButtonLessonOptionTextColorA4 = new MutableLiveData<>();
        mButtonLessonOptionTextColorA5 = new MutableLiveData<>();

        mButtonLessonOptionTextColorB1 = new MutableLiveData<>();
        mButtonLessonOptionTextColorB2 = new MutableLiveData<>();
        mButtonLessonOptionTextColorB3 = new MutableLiveData<>();
        mButtonLessonOptionTextColorB4 = new MutableLiveData<>();
        mButtonLessonOptionTextColorB5 = new MutableLiveData<>();

        mLessonTracker = new LessonTracker();

//        mCourseFSM = new CourseFSM(mApplication.getApplicationContext());
//        mCourseFSM.selectCourse(CourseFSM.CourseId.CYRILLIC_WEST);
    }

    private void resetLists() {
        mExerciseRemainingQuestionDatas.clear();
        mTotalCorrectAnswers = 0;
        mTotalCorrectAnswersInExercise = 0;
    }

    private void populateListsForFlowerMode() {
        // Need to add 5 questions from each of the following categories:
        // last 5 questions which were not answered correctly
        // 5 random questions which were not answered correctly (must be different from the first 5)
        // 5 questions which were answered correctly
        // note: last question that was answered incorrectly should be the first in the new list.
        // note: the questions can be from any lesson and any exercise.
//        Log.d(TAG, "YYY1000 populateListsForFlowerMode()");
        List<QuestionData> allQuestionDatas = mQuestionDataRepository.getAllEntriesOfCourse(GlobalSettings.inst().getCourseId());

        // wrongly answered questions (ordered from oldest to latest
        List<QuestionData> wrongQuestionDatas = new ArrayList<>();
        // correctly answered questions (ordered from oldest to latest
        List<QuestionData> correctQuestionDatas = new ArrayList<>();

        // Populate the correctly-answered questions, the wrongly-answered questions - from all the lessons and all the exercises.
        for (QuestionData questionData : allQuestionDatas) {
            final int questionNumber = questionData.getQuestionNumber();
            final Pair<Integer, Integer> lessonId = new Pair<>(questionData.getStageNumber(), questionData.getLessonNumber());
            final QuestionInfo questionInfo = mQuestionInfoRepository.getQuestionByNumberOfCourse(GlobalSettings.inst().getCourseId(), lessonId, questionNumber);
            if (questionInfo.getBeenWrong()) {
                wrongQuestionDatas.add(questionData);
            } else if (questionInfo.getTried()) {
                // was tried and answered correctly the first time
                correctQuestionDatas.add(questionData);
            }
        }
        Set<QuestionData> alreadyAddedQuestionDatas = new ArraySet<>();
        final boolean smallDbMode = GlobalSettings.inst().getSmallDb();
        final int maxQuestionsPerExercise = GlobalSettings.inst().getMaxQuestionsPerExercise();
        // First add last 5 wrong ones
        int lastFiveWrongCnt = smallDbMode?maxQuestionsPerExercise:5;
        int wrongQuestionsCnt = wrongQuestionDatas.size();
        for (; wrongQuestionsCnt > 0; --wrongQuestionsCnt) {
            final QuestionData questionData = wrongQuestionDatas.get(wrongQuestionsCnt - 1);
            --lastFiveWrongCnt;
            mExerciseRemainingQuestionDatas.add(questionData);
            ++mNumberOfQuestionsInExercise;
            alreadyAddedQuestionDatas.add(questionData);
//            Log.d(TAG, "YYY301a Q:" + questionData.getQuestionNumber());
            if (lastFiveWrongCnt == 0) {
                break;
            }
        }
        // Next, add 5 random wrong ones
        int randomFiveWrongCnt = smallDbMode?maxQuestionsPerExercise:5;
        if (wrongQuestionsCnt > 0) {
            List<QuestionData> remainingWrongQuestionDatas = wrongQuestionDatas.subList(0, wrongQuestionsCnt);
            Collections.shuffle(remainingWrongQuestionDatas);
            for(QuestionData questionData : remainingWrongQuestionDatas) {
                if (alreadyAddedQuestionDatas.contains(questionData)) {
                    continue;
                }
                --randomFiveWrongCnt;
                mExerciseRemainingQuestionDatas.add(questionData);
                ++mNumberOfQuestionsInExercise;
                alreadyAddedQuestionDatas.add(questionData);
//                Log.d(TAG, "YYY301b Q:" + questionData.getQuestionNumber());
                if (randomFiveWrongCnt == 0) {
                    break;
                }
            }
        }
        // Next, add 5 correct ones
        int randomFiveCorrectCnt = smallDbMode?maxQuestionsPerExercise:5;
        List<QuestionData> shuffledCorrectQuestionDatas = new ArrayList<>(correctQuestionDatas);
        Collections.shuffle(shuffledCorrectQuestionDatas);
        for (QuestionData questionData : shuffledCorrectQuestionDatas) {
            if (alreadyAddedQuestionDatas.contains(questionData)) {
                continue;
            }
            --randomFiveCorrectCnt;
            mExerciseRemainingQuestionDatas.add(questionData);
            ++mNumberOfQuestionsInExercise;
            alreadyAddedQuestionDatas.add(questionData);
//            Log.d(TAG, "YYY301c Q:" + questionData.getQuestionNumber());
            if (randomFiveCorrectCnt == 0) {
                break;
            }
        }
        // Almost last, if there were not enough (15) questions found above then add more wrong questions if there are any left
        // (i.e. not already included in the above 5+5).
        final int requiredNumOfQuestions = 3 * ((smallDbMode)?maxQuestionsPerExercise:5);
        if (mNumberOfQuestionsInExercise < requiredNumOfQuestions) {
            if (wrongQuestionsCnt > 0) {
                List<QuestionData> otherRemainingWrongQuestionDatas = wrongQuestionDatas.subList(0, wrongQuestionsCnt);
                Collections.shuffle(otherRemainingWrongQuestionDatas);
                for (QuestionData questionData : otherRemainingWrongQuestionDatas) {
                    if (alreadyAddedQuestionDatas.contains(questionData)) {
                        continue;
                    }
                    mExerciseRemainingQuestionDatas.add(questionData);
                    ++mNumberOfQuestionsInExercise;
                    alreadyAddedQuestionDatas.add(questionData);
//                    Log.d(TAG, "YYY301d Q:" + questionData.getQuestionNumber());
                    if (mNumberOfQuestionsInExercise == requiredNumOfQuestions) {
                        break;
                    }
                }
            }
        }
        // Last, if even that's not enough then add more from the rest.
        if (mNumberOfQuestionsInExercise < requiredNumOfQuestions) {
            List<QuestionData> shuffledAllQuestionDatas = new ArrayList<>(allQuestionDatas);
            Collections.shuffle(shuffledAllQuestionDatas);
            for (QuestionData questionData : shuffledAllQuestionDatas) {
                if (alreadyAddedQuestionDatas.contains(questionData)) {
                    continue;
                }
                mExerciseRemainingQuestionDatas.add(questionData);
                ++mNumberOfQuestionsInExercise;
                alreadyAddedQuestionDatas.add(questionData);
//                Log.d(TAG, "YYY301e Q:" + questionData.getQuestionNumber());
                if (mNumberOfQuestionsInExercise == requiredNumOfQuestions) {
                    break;
                }
            }
        }
        // shuffle the final list
        Collections.shuffle(mExerciseRemainingQuestionDatas);
    }

    public boolean init(List<QuestionData> questionDataList) {
//        if (mInitialized) {
//            Log.d(TAG, "Already initialized");
//            return true;
//        }
        if (questionDataList == null) {
            Log.e(TAG, "-B- BUG: lessons == null");
            return true;
        }
        resetLists();
        resetQuestion();
        mLessonAllQuestionDatas = questionDataList;
        // first count the number of correctly answered questions because this info is needed for updating the top seekbar.
        for (QuestionData questionData : questionDataList) {
            final int questionNumber = questionData.getQuestionNumber();
            final QuestionInfo questionInfo = mQuestionInfoRepository.getQuestionByNumberOfCourse(GlobalSettings.inst().getCourseId(), mLessonId, questionNumber);
            if (questionInfo.getDone()) {
                ++mTotalCorrectAnswers;
                if (questionInfo.getExerciseNumber() == mExerciseNumber) {
                    ++mTotalCorrectAnswersInExercise;
                }
            }
        }

        // Now add the relevant questions to mExerciseRemainingQuestions based on mode (flower collection or not)
        mNumberOfQuestionsInExercise = 0;
        for (QuestionData questionData : questionDataList) {
            final int questionNumber = questionData.getQuestionNumber();
            final QuestionInfo questionInfo = mQuestionInfoRepository.getQuestionByNumberOfCourse(GlobalSettings.inst().getCourseId(), mLessonId, questionNumber);
            // Need to finish all questions of current exercise before moving to the next one
            if (questionData.getExerciseNumber() != mExerciseNumber) {
                continue;
            }
            ++mNumberOfQuestionsInExercise;
            if (!questionInfo.getDone()) {
                mExerciseRemainingQuestionDatas.add(questionData);
            }
        }
//        Log.d(TAG, "YYY151 mExerciseRemainingQuestions.size(): " + mExerciseRemainingQuestionDatas.size());
        if (mExerciseRemainingQuestionDatas.size() == 0) {
            return false;
        }
        mNumberOfQuestions.setValue(questionDataList.size());
        mCurrentQuestionIndex = -1;
        mCurrentQuestion = mExerciseRemainingQuestionDatas.get(mExerciseRemainingQuestionDatas.size() - 1);
        mCurrentCorrectPairsType9 = 0;
        mCurrentQuestionAllCorrectType9 = true;
        mCurrentQuestionNumber.setValue(mCurrentQuestion.getQuestionNumber());
        mExerciseIsDone = false;
        Log.d(TAG, "YYY391b mNumberOfQuestions, mCurrentQuestionIndex, mCurrentQuestionNumber: " + mNumberOfQuestions.getValue() + ", " + mCurrentQuestionIndex + ", " + mCurrentQuestionNumber.getValue());
        if (!setNextQuestion(false, false, false, -1)) {
            Log.e(TAG, "mQuestions.size() > 0 but setNextQuestion() returned false");
            return false;
        }
        return true;
    }

    public boolean initForFlowerCollection() {
        resetLists();
        resetQuestion();
        // Now add the relevant questions to mExerciseRemainingQuestions based on mode (flower collection or not)
        mNumberOfQuestionsInExercise = 0;
        populateListsForFlowerMode();
        mLessonAllQuestionDatas = mExerciseRemainingQuestionDatas;
//        Log.d(TAG, "YYY151 mExerciseRemainingQuestions.size(): " + mExerciseRemainingQuestionDatas.size());
//        Log.d(TAG, "Finished initializing");
        if (mExerciseRemainingQuestionDatas.size() == 0) {
            return false;
        }
        mNumberOfQuestions.setValue(mNumberOfQuestionsInExercise);
        mCurrentQuestionIndex = -1;
        mCurrentQuestion = mExerciseRemainingQuestionDatas.get(mExerciseRemainingQuestionDatas.size() - 1);
        mCurrentCorrectPairsType9 = 0;
        mCurrentQuestionAllCorrectType9 = true;
        mCurrentQuestionNumber.setValue(mCurrentQuestion.getQuestionNumber());
        mExerciseIsDone = false;
        if (!setNextQuestion(false, false, false, -1)) {
            Log.e(TAG, "mQuestions.size() > 0 but setNextQuestion() returned false");
            return false;
        }
        return true;
    }

    public LiveData<List<QuestionData>> getLessonQuestionDatas(Pair<Integer, Integer> lessonId) {
        return mQuestionDataRepository.getAllEntriesOfLessonOfCourse(GlobalSettings.inst().getCourseId(), lessonId);
    }

    public LiveData<List<QuestionInfo>> getQuestionInfoEntries() {
        return mQuestionInfoRepository.getAllEntriesOfCourse(GlobalSettings.inst().getCourseId());
    }

    public boolean getLessonIsDone() {
        return mLessonIsDone;
    }

    public boolean getAnsweredOneQuestionOrMore() {
        return (mTotalCorrectAnswersInExercise > 0);
    }

    public LiveData<List<LessonInfo>> getLessonInfoEntries() {
        return mLessonInfoRepository.getAllEntriesOfCourse(GlobalSettings.inst().getCourseId());
    }

    public void refreshQuestionInfos(List<QuestionInfo> questionInfos) {
        if (mLessonTracker != null) {
            mLessonTracker.refresh(questionInfos);
        }
    }

//    public void updateCurriculumStatus(List<QuestionInfo> questionInfos) {
//        Log.d(TAG, "YYY600");
//        if (mCurriculumManager != null) {
//            mCurriculumManager.updateQuestions(questionInfos);
//        }
//    }

    public Bitmap getBitmapFromAssets(String fileName) {
        AssetManager assetManager = mApplication.getApplicationContext().getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("images/AllLessons/" + fileName + ".webp");
        }catch(IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(inputStream);
    }

    public boolean setNextQuestion(boolean freeNavigation, boolean largeStep, boolean goBackwards, int specificQuestion) {
        if (freeNavigation) {
            if (mLessonAllQuestionDatas == null || mLessonAllQuestionDatas.size() == 0) {
                return false;
            }
        } else {
            if (mExerciseRemainingQuestionDatas.size() == 0) {
                return false;
            }
        }
        // a small sanity check
        if ((largeStep || goBackwards || (specificQuestion >=0)) && !freeNavigation) {
            Log.e(TAG, "-B- BUG: setNextQuestion(): inconsistent arguments: freeNavigation, largeStep, goBackwards, specificQuestion: " + freeNavigation + ", " + largeStep + ", " + goBackwards + ", " + specificQuestion);
            freeNavigation = false;
            largeStep = false;
            goBackwards = false;
            specificQuestion = -1;
        }
        mLastSelectedOptionId = 0;
        mLastSelectedOptionIdLeft = 0;
        mLastSelectedOptionIdRight = 0;
        int tmpIndex = mCurrentQuestionIndex;
        Log.d(TAG, "YYY391c tmpIndex: " + tmpIndex);
        if (freeNavigation) {
            Log.d(TAG, "YYY391c2 tmpIndex: " + tmpIndex);
//            if (specificQuestion >= 0 && specificQuestion < mExerciseRemainingQuestions.size()) {
            if (specificQuestion >= 0 && specificQuestion < mLessonAllQuestionDatas.size()) {
                tmpIndex = specificQuestion;
            } else {
                if (goBackwards) {
                    if (largeStep) {
                        tmpIndex = tmpIndex - 10;
                    } else {
                        --tmpIndex;
                    }
                    if (tmpIndex < 0) {
//                        tmpIndex = mExerciseRemainingQuestions.size() - 1;
                        tmpIndex = mLessonAllQuestionDatas.size() - 1;
                    }
                } else {
                    if (largeStep) {
                        tmpIndex = tmpIndex + 10;
                    } else {
                        ++tmpIndex;
                    }
//                    if (tmpIndex >= mExerciseRemainingQuestions.size()) {
                    if (tmpIndex >= mLessonAllQuestionDatas.size()) {
                        tmpIndex = 0;
                    }
                }
            }
        } else {
            if (mLastQuestionWasCorrect) {
                mLastQuestionWasCorrect = false;
                Log.d(TAG, "YYY391d");
            } else {
                ++tmpIndex;
                Log.d(TAG, "YYY391e tmpIndex: " + tmpIndex);
            }
            if (tmpIndex >= mExerciseRemainingQuestionDatas.size()) {
                tmpIndex = 0;
                Log.d(TAG, "YYY391f tmpIndex: " + tmpIndex);
            }
        }
        mCurrentQuestionIndex = tmpIndex;
        Log.d(TAG, "YYY391g mCurrentQuestionIndex: " + mCurrentQuestionIndex);

        QuestionData questionData;
        if (freeNavigation) {
            questionData = mLessonAllQuestionDatas.get(mCurrentQuestionIndex);
        } else {
            questionData = mExerciseRemainingQuestionDatas.get(mCurrentQuestionIndex);
            Log.d(TAG, "YYY391 mCurrentQuestionIndex: " + mCurrentQuestionIndex);
        }
        mCurrentQuestion = questionData;
        mCurrentCorrectPairsType9 = 0;
        mCurrentQuestionAllCorrectType9 = true;
        mCurrentQuestionNumber.setValue(questionData.getQuestionNumber());
        final int currentQuestionTypeInt = questionData.getQuestionType();
        mCurrentQuestionType.setValue(currentQuestionTypeInt);
        if (currentQuestionTypeInt == 1 || currentQuestionTypeInt == 4) {
            mCurrentQuestionImage.setValue("speaker230x230");
            mCurrentQuestionText.setValue("");
        } else if (currentQuestionTypeInt == 2 || currentQuestionTypeInt == 3) {
            mCurrentQuestionImage.setValue(questionData.getQuestionImage());
            mCurrentQuestionText.setValue("");
        } else if (currentQuestionTypeInt == 5) {
            mCurrentQuestionImage.setValue("");
            switch (GlobalSettings.inst().getLanguage()) {
                case ARABIC:
                    mCurrentQuestionText.setValue(questionData.getQuestionSubTextArabic());
                    break;
                case TURKISH:
                    mCurrentQuestionText.setValue(questionData.getQuestionSubTextTurkish());
                    break;
                default:
                    mCurrentQuestionText.setValue(questionData.getQuestionSubTextEnglish());
                    break;
            }
        } else if (currentQuestionTypeInt == 6 || currentQuestionTypeInt == 7 || currentQuestionTypeInt == 8) {
            mCurrentQuestionImage.setValue("");
            mCurrentQuestionText.setValue(questionData.getQuestionSubText());
        } else {
            mCurrentQuestionImage.setValue("");
            mCurrentQuestionText.setValue("");
        }
        mCurrentQuestionSound.setValue(questionData.getQuestionAudioFile());

        String currentOption1 = "";
        String currentOption2 = "";
        String currentOption3 = "";
        String currentOption4 = "";

        String currentOptionA1 = "";
        String currentOptionA2 = "";
        String currentOptionA3 = "";
        String currentOptionA4 = "";
        String currentOptionA5 = "";
        String currentOptionB1 = "";
        String currentOptionB2 = "";
        String currentOptionB3 = "";
        String currentOptionB4 = "";
        String currentOptionB5 = "";
        if (currentQuestionTypeInt == 6) {
            switch (GlobalSettings.inst().getLanguage()) {
                case ARABIC:
                    currentOption1 = questionData.getAnswerArabic1();
                    currentOption2 = questionData.getAnswerArabic2();
                    currentOption3 = questionData.getAnswerArabic3();
                    break;
                case TURKISH:
                    currentOption1 = questionData.getAnswerTurkish1();
                    currentOption2 = questionData.getAnswerTurkish2();
                    currentOption3 = questionData.getAnswerTurkish3();
                    break;
                default:
                    currentOption1 = questionData.getAnswerEnglish1();
                    currentOption2 = questionData.getAnswerEnglish2();
                    currentOption3 = questionData.getAnswerEnglish3();
            }
        } else if (currentQuestionTypeInt == 9) {
            currentOptionA1 = questionData.getAnswer1();
            currentOptionA2 = questionData.getAnswer2();
            currentOptionA3 = questionData.getAnswer3();
            currentOptionA4 = questionData.getAnswer4();
            currentOptionA5 = questionData.getAnswer5();
            switch (GlobalSettings.inst().getLanguage()) {
                case ARABIC:
                    currentOptionB1 = questionData.getAnswerArabic1();
                    currentOptionB2 = questionData.getAnswerArabic2();
                    currentOptionB3 = questionData.getAnswerArabic3();
                    currentOptionB4 = questionData.getAnswerArabic4();
                    currentOptionB5 = questionData.getAnswerArabic5();
                    break;
                case TURKISH:
                    currentOptionB1 = questionData.getAnswerTurkish1();
                    currentOptionB2 = questionData.getAnswerTurkish2();
                    currentOptionB3 = questionData.getAnswerTurkish3();
                    currentOptionB4 = questionData.getAnswerTurkish4();
                    currentOptionB5 = questionData.getAnswerTurkish5();
                    break;
                default:
                    currentOptionB1 = questionData.getAnswerEnglish1();
                    currentOptionB2 = questionData.getAnswerEnglish2();
                    currentOptionB3 = questionData.getAnswerEnglish3();
                    currentOptionB4 = questionData.getAnswerEnglish4();
                    currentOptionB5 = questionData.getAnswerEnglish5();
            }
        } else { // all the other currently supported question types
            currentOption1 = questionData.getAnswer1();
            currentOption2 = questionData.getAnswer2();
            currentOption3 = questionData.getAnswer3();
            if (currentQuestionTypeInt < 4) {
                // Note: option #4 is relevant only for question types 1, 2, and 3.
                currentOption4 = questionData.getAnswer4();
            }
        }

        if (currentQuestionTypeInt == 9) {
            mCurrentOptionA1.setValue(currentOptionA1);
            mCurrentOptionA2.setValue(currentOptionA2);
            mCurrentOptionA3.setValue(currentOptionA3);
            mCurrentOptionA4.setValue(currentOptionA4);
            mCurrentOptionA5.setValue(currentOptionA5);
            mCurrentOptionB1.setValue(currentOptionB1);
            mCurrentOptionB2.setValue(currentOptionB2);
            mCurrentOptionB3.setValue(currentOptionB3);
            mCurrentOptionB4.setValue(currentOptionB4);
            mCurrentOptionB5.setValue(currentOptionB5);
        } else {
            mCurrentOption1.setValue(currentOption1);
            mCurrentOption2.setValue(currentOption2);
            mCurrentOption3.setValue(currentOption3);
            mCurrentOption4.setValue(currentOption4);
        }

        mCurrentOptionSound1 = questionData.getAnswerAudio1();
        mCurrentOptionSound2 = questionData.getAnswerAudio2();
        mCurrentOptionSound3 = questionData.getAnswerAudio3();
        mCurrentOptionSound4 = questionData.getAnswerAudio4();
        mCurrentOptionSound5 = questionData.getAnswerAudio5();

        mCurrentCorrectAnswerId = questionData.getRightAnswer1();
        switch (questionData.getRightAnswer1()) {
            case 1:
                mCurrentCorrectAnswer = mCurrentOption1.getValue();
                break;
            case 2:
                mCurrentCorrectAnswer = mCurrentOption2.getValue();
                break;
            case 3:
                mCurrentCorrectAnswer = mCurrentOption3.getValue();
                break;
            case 4:
                mCurrentCorrectAnswer = mCurrentOption4.getValue();
                break;
            default:
                Log.e(TAG, "-B- BUG: Unexpected correctAnswerNumber: " + questionData.getRightAnswer1());
                mCurrentCorrectAnswer = "";
        }
        return true;
    }

    public QuestionData getCurrentQuestion() {
        return mCurrentQuestion;
    }

    public int getCurrentQuestionIndex() {
        return mCurrentQuestionIndex;
    }

    public int getTotalCorrectAnswers() {
        return mTotalCorrectAnswers;
    }

    public int getTotalCorrectAnswersInExercise() {
        return mTotalCorrectAnswersInExercise;
    }

    public LiveData<Integer> getCurrentQuestionType() {
        return mCurrentQuestionType;
    }

    public LiveData<Integer> getCurrentQuestionNumber() {
        return mCurrentQuestionNumber;
    }

//    public LiveData<Integer> getExerciseNumber() {
//        return mExerciseNumber;
//    }

    public int getExerciseNumber() {
        return mExerciseNumber;
    }

    public boolean getExerciseIsDone() {
        return mExerciseIsDone;
    }

    public LiveData<Integer> getNumberOfQuestions() {
        return mNumberOfQuestions;
    }

    public int getNumberOfQuestionsInExercise() {
        return mNumberOfQuestionsInExercise;
    }

    public void setLessonId(Pair<Integer, Integer> lessonId) {
        mLessonId = lessonId;
        if (GlobalSettings.inst().isFlowerCollectionMode()) {
            return;
        }
        mLessonInfo = mLessonInfoRepository.getLessonInfoByIdOfCourse(GlobalSettings.inst().getCourseId(), mLessonId.first, mLessonId.second);
    }

    public void setExerciseNumber(int exerciseNumber) {
        mExerciseNumber = exerciseNumber;
    }

    public String getCorrectAnswer() {
        return mCurrentCorrectAnswer;
    }
    public int getCorrectAnswerId() {
        return mCurrentCorrectAnswerId;
    }

    public LiveData<String> getCurrentQuestionSound() {
        return mCurrentQuestionSound;
    }

    public LiveData<String> getCurrentOption1() {
        return mCurrentOption1;
    }

    public LiveData<String> getCurrentOption2() {
        return mCurrentOption2;
    }

    public LiveData<String> getCurrentOption3() {
        return mCurrentOption3;
    }

    public LiveData<String> getCurrentOption4() {
        return mCurrentOption4;
    }


    public LiveData<String> getCurrentOptionA1() {
        return mCurrentOptionA1;
    }
    public LiveData<String> getCurrentOptionA2() {
        return mCurrentOptionA2;
    }
    public LiveData<String> getCurrentOptionA3() {
        return mCurrentOptionA3;
    }
    public LiveData<String> getCurrentOptionA4() {
        return mCurrentOptionA4;
    }
    public LiveData<String> getCurrentOptionA5() {
        return mCurrentOptionA5;
    }

    public LiveData<String> getCurrentOptionB1() {
        return mCurrentOptionB1;
    }
    public LiveData<String> getCurrentOptionB2() {
        return mCurrentOptionB2;
    }
    public LiveData<String> getCurrentOptionB3() {
        return mCurrentOptionB3;
    }
    public LiveData<String> getCurrentOptionB4() {
        return mCurrentOptionB4;
    }
    public LiveData<String> getCurrentOptionB5() {
        return mCurrentOptionB5;
    }

    public String getSelectedOptionSound() {
        Log.d(TAG, "YYY-2 getSelectedOptionSound: " + mSelectedOptionId);
        switch (mSelectedOptionId) {
            case 1:
                return mCurrentOptionSound1;
            case 2:
                return mCurrentOptionSound2;
            case 3:
                return mCurrentOptionSound3;
            case 4:
                return mCurrentOptionSound4;
            case 5:
                return mCurrentOptionSound5;
            default:
                Log.e(TAG, "-B- BUG: getSelectedOptionSound(): mSelectedOptionId has an illegal value: " + mSelectedOptionId);
                return "";
        }
    }

    public void setSelectedOptionId(int id) {
        Log.d(TAG, "YYY-1 setSelectedOptionId: " + id);
        mSelectedOptionId = id;
    }

    public void setLastSelectedOptionId(int id) {
        mLastSelectedOptionId = id;
    }
    public void setLastSelectedOptionIdLeft(int id) {
        mLastSelectedOptionIdLeft = id;
    }
    public void setLastSelectedOptionIdRight(int id) {
        mLastSelectedOptionIdRight = id;
    }

    public int getLastSelectedOptionId() {
        return mLastSelectedOptionId;
    }
    public int getLastSelectedOptionIdLeft() {
        return mLastSelectedOptionIdLeft;
    }
    public int getLastSelectedOptionIdRight() {
        return mLastSelectedOptionIdRight;
    }

    public LiveData<String> getCurrentQuestionImage() {
        return mCurrentQuestionImage;
    }

    public LiveData<String> getCurrentQuestionText() {
        return mCurrentQuestionText;
    }

    public boolean checkAnswer() {
        Log.d(TAG, "checkAnswer");
//        final String selectedWord = getSelectedAnswerText();
        final int selectedAnswerId = getSelectedAnswerId();
//        Log.d(TAG, "selectedWord, mCurrentCorrectAnswerId, mCurrentCorrectAnswer: " + selectedWord + ", "  + mCurrentCorrectAnswerId + ", " + mCurrentCorrectAnswer);
        Log.d(TAG, "YYY61b mCurrentCorrectAnswerId, selectedAnswerId: " + mCurrentCorrectAnswerId + ", " + selectedAnswerId);
        boolean isCorrect = (mCurrentCorrectAnswerId == selectedAnswerId);
        Log.d(TAG, "YYY20c Current DB question number, status: " + mCurrentQuestion.getQuestionNumber() + ", " + isCorrect);
        QuestionInfo questionInfo = null;
        final boolean flowerCollectionMode = GlobalSettings.inst().isFlowerCollectionMode();
        if (!flowerCollectionMode) {
            questionInfo = mQuestionInfoRepository.getQuestionByNumberOfCourse(GlobalSettings.inst().getCourseId(), mLessonId, mCurrentQuestion.getQuestionNumber());
            questionInfo.setTried(true);
            questionInfo.setDone(isCorrect);
            if (!isCorrect) {
                questionInfo.setBeenWrong(true);
            }
            mQuestionInfoRepository.update(questionInfo);
            Log.d(TAG, "YYY-22a");
        }
        if (isCorrect) {
            if (mExerciseRemainingQuestionDatas.size() <= mCurrentQuestionIndex) {
                Log.e(TAG, "-B- BUG: checkAnswer(): mQuestions.size() <= mCurrentQuestionIndex: " + mExerciseRemainingQuestionDatas.size() + ", " + mCurrentQuestionIndex);
            } else {
                mExerciseRemainingQuestionDatas.remove(mCurrentQuestionIndex);
                ++mTotalCorrectAnswers;
                if (!flowerCollectionMode) {
                    if (questionInfo.getExerciseNumber() == mExerciseNumber) {
                        ++mTotalCorrectAnswersInExercise;
                    }
                } else {
                    ++mTotalCorrectAnswersInExercise;
                }
            }
        }
        mLastQuestionWasCorrect = isCorrect;

        if (flowerCollectionMode) {
            mExerciseIsDone = (mExerciseRemainingQuestionDatas.size() == 0) ? true:false;
        } else if (mLessonInfo != null) {
            // Update LessonInfo
            final int exerciseNum = questionInfo.getExerciseNumber();
            final List<QuestionInfo> allQuestionInfosInLesson = mQuestionInfoRepository.getAllEntriesOfLessonOfCourse(GlobalSettings.inst().getCourseId(), mLessonId);
            boolean exerciseIsDone = true;
            boolean lessonIsDone = true;
            for (QuestionInfo currentQuestionInfo : allQuestionInfosInLesson) {
                if (currentQuestionInfo.getExerciseNumber() == exerciseNum && !currentQuestionInfo.getDone()) {
                    exerciseIsDone = false;
                }
                if (!currentQuestionInfo.getDone()) {
                    lessonIsDone = false;
                    if (currentQuestionInfo.getExerciseNumber() == exerciseNum) {
                        exerciseIsDone = false;
                    }
                }
            }
            switch (exerciseNum) {
                case 1:
                    mLessonInfo.setExercise1IsDone(exerciseIsDone);
                    break;
                case 2:
                    mLessonInfo.setExercise2IsDone(exerciseIsDone);
                    break;
                case 3:
                    mLessonInfo.setExercise3IsDone(exerciseIsDone);
                    break;
                case 4:
                    mLessonInfo.setExercise4IsDone(exerciseIsDone);
                    break;
                case 5:
                    mLessonInfo.setExercise5IsDone(exerciseIsDone);
                    break;
                case 6:
                    mLessonInfo.setExercise6IsDone(exerciseIsDone);
                    break;
                default:
                    Log.e(TAG, "-B- BUG: Unexpected exercise number: " + exerciseNum);
            }

            mLessonInfo.setDone(lessonIsDone);
            if (lessonIsDone) {
                mLessonInfo.setAlreadyDoneOnce(true);
                // update stars
                final int numOfStars = mLessonInfo.getNumOfStars();
                if (numOfStars < 5) {
                    mLessonInfo.setNumOfStars(numOfStars + 1);
                }

                // update activated state
                final int currentStageNumber = GlobalSettings.inst().getCurrentStageNumber();
                final List<Integer> numOfLessonsList = GlobalSettings.inst().getStageNumOfLessons();
                int nextLessonNumber = mLessonInfo.getLessonNumber() + 1;
                final int currentNumOfLessons = numOfLessonsList.get(currentStageNumber - 1);
                int nextStageNumber = currentStageNumber;
                if (nextLessonNumber > currentNumOfLessons) {
                    nextLessonNumber = 1;
                    nextStageNumber = currentStageNumber + 1;
                    // TODO show some dialog here? double-check that all the stages and lessons are done?
                    // currently resetting to the first stage
                    if (nextStageNumber > GlobalSettings.inst().getNumOfStages()) {
                        nextStageNumber = 1;
                    }
                }
                LessonInfo nextLessonInfo = mLessonInfoRepository.getLessonInfoByIdOfCourse(GlobalSettings.inst().getCourseId(), nextStageNumber, nextLessonNumber);
                if (nextLessonInfo == null) {
                    // TODO show some dialog here? double-check that all the lessons are done?
                    // currently resetting to the first lesson
                } else {
                    nextLessonInfo.setActivated(true);
                    mLessonInfoRepository.update(nextLessonInfo);
                }
            }
            mExerciseIsDone = exerciseIsDone;
            mLessonIsDone = lessonIsDone;
            mLessonInfoRepository.update(mLessonInfo);
        }
        return isCorrect;
    }

    public boolean checkAnswerType9() {
        Log.d(TAG, "checkAnswerType9");
        final int selectedAnswerIdLeft = getSelectedAnswerIdLeft();
        final int selectedAnswerIdRight = getSelectedAnswerIdRight();
        int correctAnswer;
        switch (selectedAnswerIdLeft) {
            case 1:
                correctAnswer = mCurrentQuestion.getRightAnswer1();
                break;
            case 2:
                correctAnswer = mCurrentQuestion.getRightAnswer2();
                break;
            case 3:
                correctAnswer = mCurrentQuestion.getRightAnswer3();
                break;
            case 4:
                correctAnswer = mCurrentQuestion.getRightAnswer4();
                break;
            case 5:
                correctAnswer = mCurrentQuestion.getRightAnswer5();
                break;
            default:
                Log.e(TAG, "-B- BUG: checkAnswerType9() - unexpected selectedAnswerIdLeft: " + selectedAnswerIdLeft);
                return false;
        }
        final boolean isCorrect = (correctAnswer == selectedAnswerIdRight);
        boolean isDone = false;
        if (isCorrect) {
            ++mCurrentCorrectPairsType9;
            if (mCurrentCorrectPairsType9 > 4) {
                isDone = true;
            }
        } else {
            mCurrentQuestionAllCorrectType9 = false;
        }
        QuestionInfo questionInfo = null;
        final boolean flowerCollectionMode = GlobalSettings.inst().isFlowerCollectionMode();
        if (!flowerCollectionMode) {
            questionInfo = mQuestionInfoRepository.getQuestionByNumberOfCourse(GlobalSettings.inst().getCourseId(), mLessonId, mCurrentQuestion.getQuestionNumber());
            questionInfo.setTried(true);
            questionInfo.setDone(isDone);
            if (!isCorrect) {
                questionInfo.setBeenWrong(true);
            }
            mQuestionInfoRepository.update(questionInfo);
            Log.d(TAG, "YYY-22b");
        }
        if (isDone) {
            if (mExerciseRemainingQuestionDatas.size() <= mCurrentQuestionIndex) {
                Log.e(TAG, "-B- BUG: checkAnswer(): mQuestions.size() <= mCurrentQuestionIndex: " + mExerciseRemainingQuestionDatas.size() + ", " + mCurrentQuestionIndex);
            } else {
                mExerciseRemainingQuestionDatas.remove(mCurrentQuestionIndex);
                ++mTotalCorrectAnswers;
                if (!flowerCollectionMode) {
                    if (questionInfo.getExerciseNumber() == mExerciseNumber) {
                        ++mTotalCorrectAnswersInExercise;
                    }
                } else {
                    ++mTotalCorrectAnswersInExercise;
                }
            }
        }
        if (!isDone) {
            return isCorrect;
        }
        mLastQuestionWasCorrect = mCurrentQuestionAllCorrectType9;

        if (flowerCollectionMode) {
            mExerciseIsDone = (mExerciseRemainingQuestionDatas.size() == 0) ? true:false;
        } else if (mLessonInfo != null) {
            // Update LessonInfo
            final int exerciseNum = questionInfo.getExerciseNumber();
            final List<QuestionInfo> allQuestionInfosInLesson = mQuestionInfoRepository.getAllEntriesOfLessonOfCourse(GlobalSettings.inst().getCourseId(), mLessonId);
            boolean exerciseIsDone = true;
            boolean lessonIsDone = true;
            for (QuestionInfo currentQuestionInfo : allQuestionInfosInLesson) {
                if (currentQuestionInfo.getExerciseNumber() == exerciseNum && !currentQuestionInfo.getDone()) {
                    exerciseIsDone = false;
                }
                if (!currentQuestionInfo.getDone()) {
                    lessonIsDone = false;
                    if (currentQuestionInfo.getExerciseNumber() == exerciseNum) {
                        exerciseIsDone = false;
                    }
                }
            }
            switch (exerciseNum) {
                case 1:
                    mLessonInfo.setExercise1IsDone(exerciseIsDone);
                    break;
                case 2:
                    mLessonInfo.setExercise2IsDone(exerciseIsDone);
                    break;
                case 3:
                    mLessonInfo.setExercise3IsDone(exerciseIsDone);
                    break;
                case 4:
                    mLessonInfo.setExercise4IsDone(exerciseIsDone);
                    break;
                case 5:
                    mLessonInfo.setExercise5IsDone(exerciseIsDone);
                    break;
                case 6:
                    mLessonInfo.setExercise6IsDone(exerciseIsDone);
                    break;
                default:
                    Log.e(TAG, "-B- BUG: Unexpected exercise number: " + exerciseNum);
            }

            mLessonInfo.setDone(lessonIsDone);
            if (lessonIsDone) {
                mLessonInfo.setAlreadyDoneOnce(true);
                // update stars
                final int numOfStars = mLessonInfo.getNumOfStars();
                if (numOfStars < 5) {
                    mLessonInfo.setNumOfStars(numOfStars + 1);
                }

                // update activated state
                final int currentStageNumber = GlobalSettings.inst().getCurrentStageNumber();
                final List<Integer> numOfLessonsList = GlobalSettings.inst().getStageNumOfLessons();
                int nextLessonNumber = mLessonInfo.getLessonNumber() + 1;
                final int currentNumOfLessons = numOfLessonsList.get(currentStageNumber - 1);
                int nextStageNumber = currentStageNumber;
                if (nextLessonNumber > currentNumOfLessons) {
                    nextLessonNumber = 1;
                    nextStageNumber = currentStageNumber + 1;
                    // TODO show some dialog here? double-check that all the stages and lessons are done?
                    // currently resetting to the first stage
                    if (nextStageNumber > GlobalSettings.inst().getNumOfStages()) {
                        nextStageNumber = 1;
                    }
                }
                LessonInfo nextLessonInfo = mLessonInfoRepository.getLessonInfoByIdOfCourse(GlobalSettings.inst().getCourseId(), nextStageNumber, nextLessonNumber);
                if (nextLessonInfo == null) {
                    // TODO show some dialog here? double-check that all the lessons are done?
                    // currently resetting to the first lesson
                } else {
                    nextLessonInfo.setActivated(true);
                    mLessonInfoRepository.update(nextLessonInfo);
                }
            }
            mExerciseIsDone = exerciseIsDone;
            mLessonIsDone = lessonIsDone;
            mLessonInfoRepository.update(mLessonInfo);
        }
        return isCorrect;
    }

    public boolean isAnswerSelected() {
        return mAnswerIsSelected;
    }

    public void setAnswerIsSelected(boolean isSelected) {
        this.mAnswerIsSelected = isSelected;
    }

    public void setResultStatus(int val) {
        this.mResultStatus.setValue(val);
    }

    public void setSelectButtonEnabled(boolean val) {
        this.mSelectButtonEnabled.setValue(val);
    }
    public void setSelectButtonActivated(boolean val) {
        this.mSelectButtonActivated.setValue(val);
    }
    public void setSelectButtonBackgroundResource(int val) {
        this.mSelectButtonBackgroundResource.setValue(val);
    }
    public void setSelectButtonTextColor(@ColorInt int val) {
        this.mSelectButtonTextColor.setValue(val);
    }
    public void setSelectButtonText(String val) {
        this.mSelectButtonText.setValue(val);
    }

    public LiveData<Boolean> getButtonLessonOptionEnabled1() {
        return mButtonLessonOptionEnabled1;
    }
    public LiveData<Boolean> getButtonLessonOptionEnabled2() {
        return mButtonLessonOptionEnabled2;
    }
    public LiveData<Boolean> getButtonLessonOptionEnabled3() {
        return mButtonLessonOptionEnabled3;
    }
    public LiveData<Boolean> getButtonLessonOptionEnabled4() {
        return mButtonLessonOptionEnabled4;
    }

    public LiveData<Boolean> getButtonLessonOptionEnabledA1() {
        return mButtonLessonOptionEnabledA1;
    }
    public LiveData<Boolean> getButtonLessonOptionEnabledA2() {
        return mButtonLessonOptionEnabledA2;
    }
    public LiveData<Boolean> getButtonLessonOptionEnabledA3() {
        return mButtonLessonOptionEnabledA3;
    }
    public LiveData<Boolean> getButtonLessonOptionEnabledA4() {
        return mButtonLessonOptionEnabledA4;
    }
    public LiveData<Boolean> getButtonLessonOptionEnabledA5() {
        return mButtonLessonOptionEnabledA5;
    }

    public LiveData<Boolean> getButtonLessonOptionEnabledB1() {
        return mButtonLessonOptionEnabledB1;
    }
    public LiveData<Boolean> getButtonLessonOptionEnabledB2() {
        return mButtonLessonOptionEnabledB2;
    }
    public LiveData<Boolean> getButtonLessonOptionEnabledB3() {
        return mButtonLessonOptionEnabledB3;
    }
    public LiveData<Boolean> getButtonLessonOptionEnabledB4() {
        return mButtonLessonOptionEnabledB4;
    }
    public LiveData<Boolean> getButtonLessonOptionEnabledB5() {
        return mButtonLessonOptionEnabledB5;
    }

    public LiveData<Integer> getResultStatus() {
        return this.mResultStatus;
    }

    public LiveData<Boolean> getSelectButtonEnabled() {
        return mSelectButtonEnabled;
    }
    public LiveData<Boolean> getSelectButtonActivated() {
        return mSelectButtonActivated;
    }
    public LiveData<Integer> getSelectButtonBackgroundResource() {
        return mSelectButtonBackgroundResource;
    }
    public LiveData<Integer> getSelectButtonTextColor() {
        return mSelectButtonTextColor;
    }
    public LiveData<String> getSelectButtonText() {
        return mSelectButtonText;
    }

    public LiveData<Integer> getButtonLessonOptionTextColor1() {
        return mButtonLessonOptionTextColor1;
    }
    public LiveData<Integer> getButtonLessonOptionTextColor2() {
        return mButtonLessonOptionTextColor2;
    }
    public LiveData<Integer> getButtonLessonOptionTextColor3() {
        return mButtonLessonOptionTextColor3;
    }
    public LiveData<Integer> getButtonLessonOptionTextColor4() {
        return mButtonLessonOptionTextColor4;
    }

    public LiveData<Integer> getButtonLessonOptionBackgroundResource1() {
        return mButtonLessonOptionBackgroundResource1;
    }
    public LiveData<Integer> getButtonLessonOptionBackgroundResource2() {
        return mButtonLessonOptionBackgroundResource2;
    }
    public LiveData<Integer> getButtonLessonOptionBackgroundResource3() {
        return mButtonLessonOptionBackgroundResource3;
    }
    public LiveData<Integer> getButtonLessonOptionBackgroundResource4() {
        return mButtonLessonOptionBackgroundResource4;
    }

    public void setAllButtonLessonOptionStates(ButtonOptionState state) {
//        final Integer questionType = mCurrentQuestionType.getValue();
//        if (questionType == null || questionType == 9) {
//            for (int optionId = 1; optionId <= 5; ++optionId) {
//                setButtonLessonOptionState(optionId, state, true);
//                setButtonLessonOptionState(optionId, state, false);
//            }
//        }
//        if (questionType == null || questionType != 9) {
//            for (int optionId = 1; optionId <= 4; ++optionId) {
//                setButtonLessonOptionState(optionId, state, false);
//            }
//        }
        // for all question types (9 and the others)
        for (int optionId = 1; optionId <= 5; ++optionId) {
            setButtonLessonOptionState(optionId, state, true);
            setButtonLessonOptionState(optionId, state, false);
        }
    }

    public void setButtonLessonOptionState(int optionId, ButtonOptionState state, boolean leftOptions) {
        int bgResourceId;
        int textColor;
        boolean isEnabled;
        switch (state) {
            case BLUE:
                bgResourceId = R.drawable.background_option_selected_blue;
                textColor = ContextCompat.getColor(mApplication.getApplicationContext(), R.color.lesson_option_text_color_selected_blue);
                isEnabled = true;
                break;
            case GREEN:
                bgResourceId = R.drawable.background_option_selected_green;
                textColor = ContextCompat.getColor(mApplication.getApplicationContext(), R.color.lesson_option_text_color_selected_green);
                isEnabled = true;
                break;
            case RED:
                bgResourceId = R.drawable.background_option_selected_red;
                textColor = ContextCompat.getColor(mApplication.getApplicationContext(), R.color.lesson_option_text_color_selected_red);
                isEnabled = true;
                break;
            case DISABLED:
                bgResourceId = R.drawable.background_option_disabled;
                textColor = ContextCompat.getColor(mApplication.getApplicationContext(), R.color.lesson_option_text_color_disabled);
                isEnabled = false;
                break;
            default:
                bgResourceId = R.drawable.background_option_unselected;
                textColor = ContextCompat.getColor(mApplication.getApplicationContext(), R.color.lesson_option_text_color_unselected);
                isEnabled = true;
                break;
        }
        Integer questionType = mCurrentQuestionType.getValue();
        if (questionType == null || questionType == 9) {
            if (leftOptions) {
                switch (optionId) {
                    case 1:
                        this.mButtonLessonOptionBackgroundResourceA1.setValue(bgResourceId);
                        this.mButtonLessonOptionTextColorA1.setValue(textColor);
                        this.mButtonLessonOptionEnabledA1.setValue(isEnabled);
                        break;
                    case 2:
                        this.mButtonLessonOptionBackgroundResourceA2.setValue(bgResourceId);
                        this.mButtonLessonOptionTextColorA2.setValue(textColor);
                        this.mButtonLessonOptionEnabledA2.setValue(isEnabled);
                        break;
                    case 3:
                        this.mButtonLessonOptionBackgroundResourceA3.setValue(bgResourceId);
                        this.mButtonLessonOptionTextColorA3.setValue(textColor);
                        this.mButtonLessonOptionEnabledA3.setValue(isEnabled);
                        break;
                    case 4:
                        this.mButtonLessonOptionBackgroundResourceA4.setValue(bgResourceId);
                        this.mButtonLessonOptionTextColorA4.setValue(textColor);
                        this.mButtonLessonOptionEnabledA4.setValue(isEnabled);
                        break;
                    case 5:
                        this.mButtonLessonOptionBackgroundResourceA5.setValue(bgResourceId);
                        this.mButtonLessonOptionTextColorA5.setValue(textColor);
                        this.mButtonLessonOptionEnabledA5.setValue(isEnabled);
                        break;
                }
            } else {
                switch (optionId) {
                    case 1:
                        this.mButtonLessonOptionBackgroundResourceB1.setValue(bgResourceId);
                        this.mButtonLessonOptionTextColorB1.setValue(textColor);
                        this.mButtonLessonOptionEnabledB1.setValue(isEnabled);
                        break;
                    case 2:
                        this.mButtonLessonOptionBackgroundResourceB2.setValue(bgResourceId);
                        this.mButtonLessonOptionTextColorB2.setValue(textColor);
                        this.mButtonLessonOptionEnabledB2.setValue(isEnabled);
                        break;
                    case 3:
                        this.mButtonLessonOptionBackgroundResourceB3.setValue(bgResourceId);
                        this.mButtonLessonOptionTextColorB3.setValue(textColor);
                        this.mButtonLessonOptionEnabledB3.setValue(isEnabled);
                        break;
                    case 4:
                        this.mButtonLessonOptionBackgroundResourceB4.setValue(bgResourceId);
                        this.mButtonLessonOptionTextColorB4.setValue(textColor);
                        this.mButtonLessonOptionEnabledB4.setValue(isEnabled);
                        break;
                    case 5:
                        this.mButtonLessonOptionBackgroundResourceB5.setValue(bgResourceId);
                        this.mButtonLessonOptionTextColorB5.setValue(textColor);
                        this.mButtonLessonOptionEnabledB5.setValue(isEnabled);
                        break;
                }
            }
        }
        if (questionType == null || questionType != 9) {
            switch (optionId) {
                case 1:
                    this.mButtonLessonOptionBackgroundResource1.setValue(bgResourceId);
                    this.mButtonLessonOptionTextColor1.setValue(textColor);
                    this.mButtonLessonOptionEnabled1.setValue(isEnabled);
                    break;
                case 2:
                    this.mButtonLessonOptionBackgroundResource2.setValue(bgResourceId);
                    this.mButtonLessonOptionTextColor2.setValue(textColor);
                    this.mButtonLessonOptionEnabled2.setValue(isEnabled);
                    break;
                case 3:
                    this.mButtonLessonOptionBackgroundResource3.setValue(bgResourceId);
                    this.mButtonLessonOptionTextColor3.setValue(textColor);
                    this.mButtonLessonOptionEnabled3.setValue(isEnabled);
                    break;
                case 4:
                    this.mButtonLessonOptionBackgroundResource4.setValue(bgResourceId);
                    this.mButtonLessonOptionTextColor4.setValue(textColor);
                    this.mButtonLessonOptionEnabled4.setValue(isEnabled);
                    break;
            }
        }
    }

    public LiveData<Integer> getButtonLessonOptionBackgroundResourceA1() {
        return mButtonLessonOptionBackgroundResourceA1;
    }
    public LiveData<Integer> getButtonLessonOptionBackgroundResourceA2() {
        return mButtonLessonOptionBackgroundResourceA2;
    }
    public LiveData<Integer> getButtonLessonOptionBackgroundResourceA3() {
        return mButtonLessonOptionBackgroundResourceA3;
    }
    public LiveData<Integer> getButtonLessonOptionBackgroundResourceA4() {
        return mButtonLessonOptionBackgroundResourceA4;
    }
    public LiveData<Integer> getButtonLessonOptionBackgroundResourceA5() {
        return mButtonLessonOptionBackgroundResourceA5;
    }

    public LiveData<Integer> getButtonLessonOptionBackgroundResourceB1() {
        return mButtonLessonOptionBackgroundResourceB1;
    }
    public LiveData<Integer> getButtonLessonOptionBackgroundResourceB2() {
        return mButtonLessonOptionBackgroundResourceB2;
    }
    public LiveData<Integer> getButtonLessonOptionBackgroundResourceB3() {
        return mButtonLessonOptionBackgroundResourceB3;
    }
    public LiveData<Integer> getButtonLessonOptionBackgroundResourceB4() {
        return mButtonLessonOptionBackgroundResourceB4;
    }
    public LiveData<Integer> getButtonLessonOptionBackgroundResourceB5() {
        return mButtonLessonOptionBackgroundResourceB5;
    }

    public LiveData<Integer> getButtonLessonOptionTextColorA1() {
        return mButtonLessonOptionTextColorA1;
    }
    public LiveData<Integer> getButtonLessonOptionTextColorA2() {
        return mButtonLessonOptionTextColorA2;
    }
    public LiveData<Integer> getButtonLessonOptionTextColorA3() {
        return mButtonLessonOptionTextColorA3;
    }
    public LiveData<Integer> getButtonLessonOptionTextColorA4() {
        return mButtonLessonOptionTextColorA4;
    }
    public LiveData<Integer> getButtonLessonOptionTextColorA5() {
        return mButtonLessonOptionTextColorA5;
    }

    public LiveData<Integer> getButtonLessonOptionTextColorB1() {
        return mButtonLessonOptionTextColorB1;
    }
    public LiveData<Integer> getButtonLessonOptionTextColorB2() {
        return mButtonLessonOptionTextColorB2;
    }
    public LiveData<Integer> getButtonLessonOptionTextColorB3() {
        return mButtonLessonOptionTextColorB3;
    }
    public LiveData<Integer> getButtonLessonOptionTextColorB4() {
        return mButtonLessonOptionTextColorB4;
    }
    public LiveData<Integer> getButtonLessonOptionTextColorB5() {
        return mButtonLessonOptionTextColorB5;
    }

    public String getSelectedAnswerText() {
        return mSelectedAnswerText;
    }

    public void resetQuestion() {
        setSelectedAnswerText("");
        setSelectedAnswerId(0);
        setSelectedAnswerIdLeft(0);
        setSelectedAnswerIdRight(0);
        setAllButtonLessonOptionStates(ButtonOptionState.UNSELECTED);
    }

    public void resetExercise() {
        resetLists();
        resetQuestion();
        final List<QuestionInfo> allQuestionInfosInLesson = mQuestionInfoRepository.getAllEntriesOfLessonOfCourse(GlobalSettings.inst().getCourseId(), mLessonId);
        final int currentExerciseNum = GlobalSettings.inst().getCurrentExerciseNumber();
        for (QuestionInfo questionInfo : allQuestionInfosInLesson) {
            if (questionInfo.getExerciseNumber() != currentExerciseNum) {
                continue;
            }
            if (questionInfo.getDone()) {
                questionInfo.setDone(false);
                Log.d(TAG, "YYY-21a");
                mQuestionInfoRepository.update(questionInfo);
                Log.d(TAG, "YYY-22c");
            }
        }
    }

    public void setSelectedAnswerText(String selectedAnswerText) {
        this.mSelectedAnswerText = selectedAnswerText;
    }

    public int getSelectedAnswerId() {
        return mSelectedAnswerId;
    }
    public int getSelectedAnswerIdLeft() {
        return mSelectedAnswerIdLeft;
    }
    public int getSelectedAnswerIdRight() {
        return mSelectedAnswerIdRight;
    }

    public void setSelectedAnswerId(int selectedAnswerId) {
        this.mSelectedAnswerId = selectedAnswerId;
    }
    public void setSelectedAnswerIdLeft(int selectedAnswerId) {
        this.mSelectedAnswerIdLeft = selectedAnswerId;
    }
    public void setSelectedAnswerIdRight(int selectedAnswerId) {
        this.mSelectedAnswerIdRight = selectedAnswerId;
    }

    public List<QuestionData> getExerciseRemainingQuestionDatas() {
        return mExerciseRemainingQuestionDatas;
    }

    //    private int mLessonNumber = 0;
    private Pair<Integer, Integer> mLessonId = new Pair<>(0, 0);
    //    private final MutableLiveData<Integer> mExerciseNumber;
    private final MutableLiveData<Integer> mNumberOfQuestions;
    private int mNumberOfQuestionsInExercise;
    private int mTotalCorrectAnswers;
    private int mTotalCorrectAnswersInExercise;
    private int mExerciseNumber = 0;
    private boolean mExerciseIsDone = false;
    private boolean mLessonIsDone = false;
    private LessonInfo mLessonInfo;

    private QuestionData mCurrentQuestion = null;
    private int mCurrentCorrectPairsType9 = 0;
    private boolean mCurrentQuestionAllCorrectType9 = true;
    private final List<QuestionData> mExerciseRemainingQuestionDatas = new ArrayList<>();
    private List<QuestionData> mLessonAllQuestionDatas = null;

    private final MutableLiveData<Integer> mCurrentQuestionType;
    private int mCurrentQuestionIndex = 0;
    private boolean mLastQuestionWasCorrect = false;
    private final MutableLiveData<Integer> mCurrentQuestionNumber;

    private final MutableLiveData<String> mCurrentQuestionSound;
    private final MutableLiveData<String> mCurrentQuestionImage;
    private final MutableLiveData<String> mCurrentQuestionText;

    private String mCurrentCorrectAnswer = "";
    private int mCurrentCorrectAnswerId = 0;
    private int mSelectedOptionId = 0;
    private int mLastSelectedOptionId = 0;
    private int mLastSelectedOptionIdLeft = 0;
    private int mLastSelectedOptionIdRight = 0;

    private final MutableLiveData<String> mCurrentOption1;
    private final MutableLiveData<String> mCurrentOption2;
    private final MutableLiveData<String> mCurrentOption3;
    private final MutableLiveData<String> mCurrentOption4;

    // next ones are for question type 9
    private final MutableLiveData<String> mCurrentOptionA1;
    private final MutableLiveData<String> mCurrentOptionA2;
    private final MutableLiveData<String> mCurrentOptionA3;
    private final MutableLiveData<String> mCurrentOptionA4;
    private final MutableLiveData<String> mCurrentOptionA5;
    private final MutableLiveData<String> mCurrentOptionB1;
    private final MutableLiveData<String> mCurrentOptionB2;
    private final MutableLiveData<String> mCurrentOptionB3;
    private final MutableLiveData<String> mCurrentOptionB4;
    private final MutableLiveData<String> mCurrentOptionB5;

    private String mCurrentOptionSound1;
    private String mCurrentOptionSound2;
    private String mCurrentOptionSound3;
    private String mCurrentOptionSound4;
    private String mCurrentOptionSound5;

//    private boolean mInitialized = false;
    private final Application mApplication;
//    private final Context mContext;
    private final QuestionDataRepository mQuestionDataRepository;
    private final QuestionInfoRepository mQuestionInfoRepository;
    private final LessonInfoRepository mLessonInfoRepository;

    private LessonTracker mLessonTracker;
//    private CourseFSM mCourseFSM;

    private boolean mAnswerIsSelected = false;
    private String mSelectedAnswerText = "";
    private int mSelectedAnswerId = 0;
    private int mSelectedAnswerIdLeft = 0;
    private int mSelectedAnswerIdRight = 0;

    private final MutableLiveData<Integer> mResultStatus;

    private final MutableLiveData<Boolean> mSelectButtonEnabled;
    private final MutableLiveData<Boolean> mSelectButtonActivated;
    private final MutableLiveData<Integer> mSelectButtonBackgroundResource;
    private final MutableLiveData<Integer> mSelectButtonTextColor;
    private final MutableLiveData<String> mSelectButtonText;

    private final MutableLiveData<Boolean> mButtonLessonOptionEnabled1;
    private final MutableLiveData<Boolean> mButtonLessonOptionEnabled2;
    private final MutableLiveData<Boolean> mButtonLessonOptionEnabled3;
    private final MutableLiveData<Boolean> mButtonLessonOptionEnabled4;

    private final MutableLiveData<Boolean> mButtonLessonOptionEnabledA1;
    private final MutableLiveData<Boolean> mButtonLessonOptionEnabledA2;
    private final MutableLiveData<Boolean> mButtonLessonOptionEnabledA3;
    private final MutableLiveData<Boolean> mButtonLessonOptionEnabledA4;
    private final MutableLiveData<Boolean> mButtonLessonOptionEnabledA5;

    private final MutableLiveData<Boolean> mButtonLessonOptionEnabledB1;
    private final MutableLiveData<Boolean> mButtonLessonOptionEnabledB2;
    private final MutableLiveData<Boolean> mButtonLessonOptionEnabledB3;
    private final MutableLiveData<Boolean> mButtonLessonOptionEnabledB4;
    private final MutableLiveData<Boolean> mButtonLessonOptionEnabledB5;


    private final MutableLiveData<Integer> mButtonLessonOptionBackgroundResource1;
    private final MutableLiveData<Integer> mButtonLessonOptionBackgroundResource2;
    private final MutableLiveData<Integer> mButtonLessonOptionBackgroundResource3;
    private final MutableLiveData<Integer> mButtonLessonOptionBackgroundResource4;
    private final MutableLiveData<Integer> mButtonLessonOptionTextColor1;
    private final MutableLiveData<Integer> mButtonLessonOptionTextColor2;
    private final MutableLiveData<Integer> mButtonLessonOptionTextColor3;
    private final MutableLiveData<Integer> mButtonLessonOptionTextColor4;

    private final MutableLiveData<Integer> mButtonLessonOptionBackgroundResourceA1;
    private final MutableLiveData<Integer> mButtonLessonOptionBackgroundResourceA2;
    private final MutableLiveData<Integer> mButtonLessonOptionBackgroundResourceA3;
    private final MutableLiveData<Integer> mButtonLessonOptionBackgroundResourceA4;
    private final MutableLiveData<Integer> mButtonLessonOptionBackgroundResourceA5;

    private final MutableLiveData<Integer> mButtonLessonOptionBackgroundResourceB1;
    private final MutableLiveData<Integer> mButtonLessonOptionBackgroundResourceB2;
    private final MutableLiveData<Integer> mButtonLessonOptionBackgroundResourceB3;
    private final MutableLiveData<Integer> mButtonLessonOptionBackgroundResourceB4;
    private final MutableLiveData<Integer> mButtonLessonOptionBackgroundResourceB5;

    private final MutableLiveData<Integer> mButtonLessonOptionTextColorA1;
    private final MutableLiveData<Integer> mButtonLessonOptionTextColorA2;
    private final MutableLiveData<Integer> mButtonLessonOptionTextColorA3;
    private final MutableLiveData<Integer> mButtonLessonOptionTextColorA4;
    private final MutableLiveData<Integer> mButtonLessonOptionTextColorA5;

    private final MutableLiveData<Integer> mButtonLessonOptionTextColorB1;
    private final MutableLiveData<Integer> mButtonLessonOptionTextColorB2;
    private final MutableLiveData<Integer> mButtonLessonOptionTextColorB3;
    private final MutableLiveData<Integer> mButtonLessonOptionTextColorB4;
    private final MutableLiveData<Integer> mButtonLessonOptionTextColorB5;

//    .setBackgroundResource(R.drawable.background_option_unselected);
//    .setTextColor(ContextCompat.getColor(this, R.color.lesson_option_text_color_unselected));

    private static final String TAG = LessonViewModel.class.getSimpleName();
}
// a á b c ç ć d e é f g ǵ ğ h ḣ ı i j k ḱ l ĺ m n o ö p ṕ q r s ş ś š t ṫ u ü w x y z ź
// A Á B C Ç Ć D E É F G Ǵ Ğ H Ḣ I İ J K Ḱ L Ĺ M N O Ö P Ṕ Q R S Ş Ś Š T Ṫ U Ü W X Y Z Ź
// áçćéǵğḣıḱĺöṕşśšṫüź
// ÁÇĆÉǴĞḢİḰĹÖṔŞŚŠṪÜŹ
//Adıǵe Адыгэ
