package com.mastegoane.adigabzaforbeginners.datastructures;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;
import androidx.collection.ArrayMap;
import com.mastegoane.adigabzaforbeginners.utils.GlobalSettings;
import java.util.Map;

/* snippets from doc:
Each lesson well have 4-6 exercises.
Each exercise will have 10-15 questions.

The first lesson will be enabled. After lesson 1 is done, the next lesson will be enabled.

When user success to resolve all questions in exercise 1 of lesson 1, main activity is displayed.
There is a color indication that exercise 1 is done (squares bar).
User clicks lesson 1 to work on exercise 2 of lesson 1.

User finished all exercises of lesson 1. User gets 1 star.
He can do the exercises again and get more stars - up to 5 stars.
Lesson 2 now is enabled.

User success to finish 5 times all exercises of lesson 1. User get 5 stars.
There is color indication, black border.

Stars of each lesson are local. Top toolbar shows the sum of all stars from all lessons.


User will get this question again if his answer is wrong.


stars and flowers on upper toolbar on main activity
stars on each lesson on main activity
exercise squares bar (checked/unchecked) on each lesson on main activity

flowers:
First time starts with 5 flowers.
Whenever a user answers incorrectly he will lose one flower.
If he loses all the flowers, he can not continue to practice.
A dialog will be opened. If he answers yes then previous exercises will be opened (see below)
  - and he will automatically(?) get 5 flowers.
  If he answers no then he will go back to main activity.
  He won't be able to continue unless he chooses to redo previous exercises or exits app and waits for 5 hours.
He needs to collect flowers, he needs to get questions from previous exercises, and questions he has answered incorrectly.

If he close the app after he lose all flowers and go back to app
after more than 5 hours, he will have 5 flowers.
 */

/*
######################
# Actions and effects:
######################
================
= Main activity:
================
Click lesson:
    If lesson is lock: do nothing
    else if lesson is done: do nothing
    else go to lesson activity

Click flower:
    ??

==================
= Lesson activity:
==================
Click 'x' or back:
    Go back to main activity
    ?? display a confirmation dialog "Are you sure?" before going back

Click select:
    ?? update numOfStars
    ?? update numOfFlowers
    update exercise done
    update lesson done
    update stage done
    update lesson locked (for all relevant lessons)

Click flower:
    ??

##############################
# Affected interface elements:
##############################
================
= Main activity:
================
Locks on lessons
numOfStars on top toolbar
numOfFlowers on top toolbar
Progress per lesson (filled/empty squares)

==================
= Lesson activity:
==================
Progress bar
numOfFlowers alongside the progress bar


###########
# Triggers:
###########
numOfStars reaches 0
    ?
?? numOfStars reaches 5
    ?numOfFlowers reaches 0
?? numOfFlowers reaches 5
    ?
Exercise is done
    ?
Lesson is done
    ?
Stage is done
    ?
Lesson is unlocked
    ?
Answer is correct
    ?
Answer is wrong
    ?
 */

public class CourseFSM {
    // TODO remove dependency on Context after moving to ROOM (from SharedPref)
    public CourseFSM(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences("sp_AdigabzaForBeginnersPreferences", Context.MODE_PRIVATE);
//        mCourseId2IsFirstTime = new ArrayMap<>();
//        mStateChanged = new MutableLiveData<>(true);

        mCourseId = CourseId.NONE;
//        Map<String, Integer> mStageLesson2NumStars = new ArrayMap<>();
//        int mNumFlowers;
//        Map<Integer, Boolean> mStage2IsDone = new ArrayMap<>();
//        Map<String, Boolean> mStageLesson2IsDone = new ArrayMap<>();
//        Map<String, Boolean> mStageLesson2IsLocked = new ArrayMap<>();
//        Map<String, Boolean> mExercise2IsDone = new ArrayMap<>();
//        String mCurrentLessonId;
//        String mNextLessonId;
    }

    public void selectCourse(CourseId courseId) {
        mCourseId = courseId;
        // TODO temporarily save all states in sharedPref, later move to ROOM
        final boolean isFirstTime = mSharedPreferences.getBoolean("sp_CourseFSM_isFirstTime" + mCourseId.toString(), true);
//        mCourseId2IsFirstTime.put(courseId, isFirstTime);

        if (courseId == CourseId.CYRILLIC_WEST) {
            GlobalSettings.inst().setCyrillic(true);

//            Map<String, Integer> mStageLesson2NumStars = new ArrayMap<>();
//            int mNumFlowers;
//            Map<Integer, Boolean> mStage2IsDone = new ArrayMap<>();
//            Map<String, Boolean> mStageLesson2IsDone = new ArrayMap<>();
//            Map<String, Boolean> mStageLesson2IsLocked = new ArrayMap<>();
//            Map<String, Boolean> mExercise2IsDone = new ArrayMap<>();
//            String mCurrentLessonId;
//            String mNextLessonId;
        } else {
            Toast.makeText(mContext, "Course not implemented yet: " + courseId.toString(), Toast.LENGTH_SHORT).show();
            return;
        }

        if (isFirstTime) {
            // TODO do the first time stuff here, e.g. initial state etc.
            initializeFirstTime();
        } else {
            // TODO do the other stuff here if not a first time, e.g. restore state etc.
            restoreState();
        }
    }

    private void initializeFirstTime() {
        final SharedPreferences.Editor sharedPrefEditor = mSharedPreferences.edit();
        sharedPrefEditor.putBoolean("sp_CourseFSM_isFirstTime" + mCourseId.toString(), false);

        // TODO implement
//        Map<String, Integer> mStageLesson2NumStars = new ArrayMap<>();
//        int mNumFlowers;
//        Map<Integer, Boolean> mStage2IsDone = new ArrayMap<>();
//        Map<String, Boolean> mStageLesson2IsDone = new ArrayMap<>();
//        Map<String, Boolean> mStageLesson2IsLocked = new ArrayMap<>();
//        Map<String, Boolean> mExercise2IsDone = new ArrayMap<>();
//        String mCurrentLessonId;
//        String mNextLessonId;


        // set lesson, question, exercise, flowers, progress-bar,
        // total # of questions in question?, correctly answered questions, wrongly answered questions, unanswered questions
        // # of correctly answered questions, # of wrongly answered questions, # of unanswered answered questions
//        sharedPrefEditor.putBoolean("CourseFSM_isFirstTime" + mCourseId.toString(), false);

        sharedPrefEditor.apply();
//        triggerStateChanged();
    }

    private void restoreState() {
        // TODO implement
//        triggerStateChanged();
    }

//    private void triggerStateChanged() {
//        // To trigger the LiveData observer (the actual value of mStateChanged is irrelevant).
//        final Boolean currentState = mStateChanged.getValue();
//        if (currentState == null || !currentState) {
//            mStateChanged.setValue(true);
//        } else {
//            mStateChanged.setValue(false);
//        }
//    }

//    public CourseStep getNextStep() {
//        return CourseStep.NEXT_QUESTION;
//    }
//
//    public void gotoNextStep() {
//    }

//    public LiveData<Boolean> getStateChanged() {
//        return mStateChanged;
//    }

    public enum CourseId {NONE, CYRILLIC_WEST, LATIN_WEST}
//    public enum CourseStep {NEXT_QUESTION, LESSON_DONE, ALERT_NO_MORE_FLOWERS, NEXT_MISSED_QUESTION, MAIN_PAGE}

//    private final MutableLiveData<Boolean> mStateChanged;
    private final Context mContext;

    private final SharedPreferences mSharedPreferences;

//    private final Map<CourseId, Boolean> mCourseId2IsFirstTime;

    /*
    #########
    # States:
    #########
    Language: English, Arabic, Turkish
        Scope: global
        Access: GlobalSettings

    Alphabet: Cyrillic, Latin
        Scope: course
        Object: GlobalSettings
        Access: each alphabet has its own content and tracking

    // TODO add to shared preferences
    Course: CYRILLIC_WEST, LATIN_WEST
        Scope: global
        Access: GlobalSettings

    numOfStars: 0-5 (per lesson)
        Scope: Lesson
        Access: CourseTracker & LessonTracker

    numOfFlowers: 0-5
        Scope: course
        Access: GlobalSettings

    Stage done:
        Scope: stage
        Access: CourseTracker

    Lesson done:
        Scope: lesson
        Access: CourseTracker & LessonTracker

    Lesson locked:
        Scope: lesson
        Access: CourseTracker & LessonTracker

    Exercise done:
        Scope: exercise
        Access: LessonTracker

    Current lesson:
        Scope: global
        Access: CourseTracker & LessonTracker

    Next lesson:
        Scope: global
        Access: LessonTracker
    */
//    Language: English, Arabic, Turkish
//    Scope: global
//    Access: GlobalSettings
//    private GlobalSettings.Language mLanguage;

//    Alphabet: Cyrillic, Latin
//    Scope: course
//    Object: GlobalSettings
//    Access: each alphabet has its own content and tracking
//    private boolean mCyrillic;

//    Course: CYRILLIC_WEST, LATIN_WEST
//    Scope: global
//    Access: GlobalSettings
    private CourseId mCourseId;

//    numOfStars: 0-5 (per lesson)
//    Scope: Lesson
//    Access: CourseTracker & LessonTracker
    private Map<String, Integer> mStageLesson2NumStars = new ArrayMap<>();

//    numOfFlowers: 0-5
//    Scope: course
//    Access: GlobalSettings
    private int mNumFlowers;

//    Stage done:
//    Scope: stage
//    Access: CourseTracker
    private Map<Integer, Boolean> mStage2IsDone = new ArrayMap<>();

//    Lesson done:
//    Scope: lesson
//    Access: CourseTracker & LessonTracker
    private Map<String, Boolean> mStageLesson2IsDone = new ArrayMap<>();

//    Lesson locked:
//    Scope: lesson
//    Access: CourseTracker & LessonTracker
    private Map<String, Boolean> mStageLesson2IsLocked = new ArrayMap<>();

//    Exercise done:
//    Scope: exercise
//    Access: LessonTracker
    private Map<String, Boolean> mExercise2IsDone = new ArrayMap<>();

//    Current lesson:
//    Scope: global
//    Access: CourseTracker & LessonTracker
    private String mCurrentLessonId;

//    Next lesson:
//    Scope: global
//    Access: LessonTracker
    private String mNextLessonId;

    private static final String TAG = CourseFSM.class.getSimpleName();
}

/*
//    numOfStars: 0-5 (per lesson)
//    Scope: Lesson
//    Access: CourseTracker & LessonTracker
    private Map<String, Integer> mStageLesson2NumStars = new ArrayMap<>();

//    numOfFlowers: 0-5
//    Scope: course
//    Access: GlobalSettings
    private int mNumFlowers;

//    Stage done:
//    Scope: stage
//    Access: CourseTracker
    private Map<Integer, Boolean> mStage2IsDone = new ArrayMap<>();

//    Lesson done:
//    Scope: lesson
//    Access: CourseTracker & LessonTracker
    private Map<String, Boolean> mStageLesson2IsDone = new ArrayMap<>();

//    Lesson locked:
//    Scope: lesson
//    Access: CourseTracker & LessonTracker
    private Map<String, Boolean> mStageLesson2IsLocked = new ArrayMap<>();

//    Exercise done:
//    Scope: exercise
//    Access: LessonTracker
    private Map<String, Boolean> mExercise2IsDone = new ArrayMap<>();

//    Current lesson:
//    Scope: global
//    Access: CourseTracker & LessonTracker
    private String mCurrentLessonId;

//    Next lesson:
//    Scope: global
//    Access: LessonTracker
    private String mNextLessonId;

 */
