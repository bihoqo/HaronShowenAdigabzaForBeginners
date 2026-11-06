package com.mastegoane.adigabzaforbeginners.datastructures;

import androidx.collection.ArrayMap;
import androidx.core.util.Pair;
import com.mastegoane.adigabzaforbeginners.databases.LessonInfo;
import com.mastegoane.adigabzaforbeginners.databases.QuestionInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CourseTracker {

    public CourseTracker() {
    }

    public void refreshQuestionInfos(List<QuestionInfo> questionInfos) {
        // required data for views:
        // lesson status: activated, not activated
        //      OR active lesson number
        // num of exercises per lesson
        // num of finished exercises per lesson
        //      OR current (active) exercise
        //      OR status of exercise: not started, started, finished.
        //      OR status of exercise: finished, not finished.

        mLessonId2Exercises.clear();
        for (QuestionInfo questionInfo : questionInfos) {
            final Pair<Integer, Integer> lessonId = questionInfo.getLessonId();
            final int exercise = questionInfo.getExerciseNumber();
            List<Integer> exercises = mLessonId2Exercises.get(lessonId);
            if (exercises == null) {
                exercises = new ArrayList<>();
            }
            if (!exercises.contains(exercise)) {
                exercises.add(exercise);
            }
            mLessonId2Exercises.put(lessonId, exercises);
        }
    }

    public Map<String, Integer> getStageLessonExercise2IsDoneMap() {
        return mStageLessonExercise2IsDone;
    }

    public Map<String, Integer> getStageLesson2NumStarsMap() {
        return mStageLesson2NumStars;
    }

    public Map<String, Boolean> getStageLesson2IsActivatedMap() {
        return mStageLesson2IsActivated;
    }

    public void refreshLessonInfos(List<LessonInfo> lessonInfos) {
        mStageLesson2IsActivated.clear();
        mStageLessonExercise2IsDone.clear();
        mStageLesson2NumStars.clear();
        for (LessonInfo lessonInfo : lessonInfos) {
            refreshLessonInfo(lessonInfo);
//            final Pair<Integer, Integer> lessonId = lessonInfo.getLessonId();
//            final String lessonIdStr = "" + lessonInfo.getStageNumber() + "," + lessonInfo.getLessonNumber();
//            mStageLesson2IsActivated.put(lessonIdStr, lessonInfo.isActivated());
//            final int numOfStars = lessonInfo.getNumOfStars();
//            mStageLesson2NumStars.put(lessonIdStr, numOfStars);
//
//            for (int exerciseNum = 1; exerciseNum <= 6; ++exerciseNum) {
//                final int numOfExercises = lessonInfo.getNumOfExercises();
//                final String exerciseId = lessonIdStr + "," + exerciseNum;
//                if (numOfExercises >= exerciseNum) {
//                    switch (exerciseNum) {
//                        case 1:
//                            mStageLessonExercise2IsDone.put(exerciseId, lessonInfo.isExercise1IsDone() ? 1 : 0);
//                            break;
//                        case 2:
//                            mStageLessonExercise2IsDone.put(exerciseId, lessonInfo.isExercise2IsDone() ? 1 : 0);
//                            break;
//                        case 3:
//                            mStageLessonExercise2IsDone.put(exerciseId, lessonInfo.isExercise3IsDone() ? 1 : 0);
//                            break;
//                        case 4:
//                            mStageLessonExercise2IsDone.put(exerciseId, lessonInfo.isExercise4IsDone() ? 1 : 0);
//                            break;
//                        case 5:
//                            mStageLessonExercise2IsDone.put(exerciseId, lessonInfo.isExercise5IsDone() ? 1 : 0);
//                            break;
//                        case 6:
//                            mStageLessonExercise2IsDone.put(exerciseId, lessonInfo.isExercise6IsDone() ? 1 : 0);
//                            break;
//                    }
//                } else {
//                    mStageLessonExercise2IsDone.put(exerciseId, -1);
//                }
//            }
        }
    }

    public void refreshLessonInfo(LessonInfo lessonInfo) {
//        mStageLesson2IsActivated.clear();
//        mStageLessonExercise2IsDone.clear();
//        mStageLesson2NumStars.clear();

        final Pair<Integer, Integer> lessonId = lessonInfo.getLessonId();
        final String lessonIdStr = "" + lessonInfo.getStageNumber() + "," + lessonInfo.getLessonNumber();
        mStageLesson2IsActivated.put(lessonIdStr, lessonInfo.isActivated());
        final int numOfStars = lessonInfo.getNumOfStars();
        mStageLesson2NumStars.put(lessonIdStr, numOfStars);

        for (int exerciseNum = 1; exerciseNum <= 6; ++exerciseNum) {
            final int numOfExercises = lessonInfo.getNumOfExercises();
            final String exerciseId = lessonIdStr + "," + exerciseNum;
            if (numOfExercises >= exerciseNum) {
                switch (exerciseNum) {
                    case 1:
                        mStageLessonExercise2IsDone.put(exerciseId, lessonInfo.isExercise1IsDone() ? 1 : 0);
                        break;
                    case 2:
                        mStageLessonExercise2IsDone.put(exerciseId, lessonInfo.isExercise2IsDone() ? 1 : 0);
                        break;
                    case 3:
                        mStageLessonExercise2IsDone.put(exerciseId, lessonInfo.isExercise3IsDone() ? 1 : 0);
                        break;
                    case 4:
                        mStageLessonExercise2IsDone.put(exerciseId, lessonInfo.isExercise4IsDone() ? 1 : 0);
                        break;
                    case 5:
                        mStageLessonExercise2IsDone.put(exerciseId, lessonInfo.isExercise5IsDone() ? 1 : 0);
                        break;
                    case 6:
                        mStageLessonExercise2IsDone.put(exerciseId, lessonInfo.isExercise6IsDone() ? 1 : 0);
                        break;
                }
            } else {
                mStageLessonExercise2IsDone.put(exerciseId, -1);
            }
        }
    }

    public boolean getLessonHasExercises(Pair<Integer, Integer> lessonId) {
        final List<Integer> exercises = mLessonId2Exercises.get(lessonId);
        if (exercises == null) {
            return false;
        }
        return (exercises.size() > 0);
    }

    public List<Integer> getLessonExercises(Pair<Integer, Integer> lessonId) {
        return mLessonId2Exercises.get(lessonId);
    }

//    public int getLessonNumOfStars(Pair<Integer, Integer> lessonId) {
//        final String lessonIdStr = "" + lessonId.first + "," + lessonId.second;
//        final Integer numOfStars = mStageLesson2NumStars.get(lessonIdStr);
//        if (numOfStars == null) {
//            return 0;
//        }
//        return numOfStars;
//    }

    public int getTotalNumOfStars() {
        int totalNumOfStars = 0;
        for (String lessonIdStr : mStageLesson2NumStars.keySet()) {
            final Integer numOfStars = mStageLesson2NumStars.get(lessonIdStr);
            if (numOfStars != null) {
                totalNumOfStars += numOfStars;
            }
        }
        return totalNumOfStars;
    }

    public boolean getExerciseIsDone(Pair<Integer, Integer> lessonId, int exercise) {
        final String lessonIdStr = "" + lessonId.first + "," + lessonId.second;
        final String exerciseIdStr = lessonIdStr + "," + exercise;
        final Integer exerciseStatus = mStageLessonExercise2IsDone.get(exerciseIdStr);
        if (exerciseStatus == null || exerciseStatus != 1) {
            return false;
        }
        return true;
    }

    private final Map<Pair<Integer, Integer>, List<Integer>> mLessonId2Exercises = new ArrayMap<>();;
    private final Map<String, Boolean> mStageLesson2IsActivated = new ArrayMap<>();;
    private final Map<String, Integer> mStageLessonExercise2IsDone = new ArrayMap<>();;
    private final Map<String, Integer> mStageLesson2NumStars = new ArrayMap<>();;

    private static final String TAG = CourseTracker.class.getSimpleName();
}
