package com.mastegoane.adigabzaforbeginners.datastructures;

import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import androidx.core.util.Pair;
import com.mastegoane.adigabzaforbeginners.databases.QuestionInfo;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LessonTracker {
    public LessonTracker() {
        //        mStages = new ArraySet<>();
//        mStage2Lessons = new ArrayMap<>();
//        mLessons = new ArraySet<>();
//        mLesson2Questions = new ArrayMap<>();
        mLesson2Exercises = new ArrayMap<>();
        mExercise2IsDone = new ArrayMap<>();
    }

    public void refresh(List<QuestionInfo> questionInfos) {
//        Log.d(TAG, "refresh");
        // required data for views:
        // lesson status: activated, not activated
        //      OR active lesson number
        // num of exercises per lesson
        // num of finished exercises per lesson
        //      OR current (active) exercise
        //      OR status of exercise: not started, started, finished.
        //      OR status of exercise: finished, not finished.

//        mStages.clear();
//        mStage2Lessons.clear();
//        mLessons.clear();
//        mLesson2Questions.clear();
        mLesson2Exercises.clear();
        mExercise2IsDone.clear();
        for (QuestionInfo questionInfo : questionInfos) {
//            final int stage = questionInfo.getStage();
//            final int lesson = questionInfo.getLesson();
            final Pair<Integer, Integer> lessonId = questionInfo.getLessonId();
            final int exercise = questionInfo.getExerciseNumber();
//            final int question = questionInfo.getQuestionNumber();
//            final QuestionInfo.QuestionStatus questionStatus = questionInfo.getStatus();
            final boolean questionIsDone = questionInfo.getDone();
//            Log.d(TAG, "lesson, question: " + lesson + ", " + question);
//            mStages.add(stage);

//            Set<Integer> lessons = mStage2Lessons.get(stage);
//            if (lessons == null) {
//                Log.d(TAG, "lessons == null");
//                lessons = new ArraySet<>();
//            }
//            lessons.add(lesson);
//            mStage2Lessons.put(stage, lessons);
//            mLessons.add(lesson);
//            Log.d(TAG, "put: " + stage + ", " + lessons.size());

//            List<Integer> questions = mLesson2Questions.get(lesson);
//            if (questions == null) {
//                questions = new ArrayList<>();
//            }
//            questions.add(question);
//            mLesson2Questions.put(lesson, questions);

//            Set<Integer> exercises = mLesson2Exercises.get(lesson);
            Set<Integer> exercises = mLesson2Exercises.get(lessonId);
            if (exercises == null) {
                exercises = new ArraySet<>();
            }
            exercises.add(exercise);
//            mLesson2Exercises.put(lesson, exercises);
            mLesson2Exercises.put(lessonId, exercises);

//            ExerciseStatus exerciseStatus = mExercise2Status.get(question);
//            if (exerciseStatus == null) {
//                switch (questionStatus) {
//                    case 0:
//                        exerciseStatus = ExerciseStatus.NOT_STARTED;
//                        break;
//                    default:
//                        exerciseStatus = ExerciseStatus.STARTED_ALL_CORRECT;
//                }
//            } else {
//                if (exerciseStatus == ExerciseStatus.NOT_STARTED) {
//                    switch (questionStatus) {
//                        case 0:
//                            exerciseStatus = ExerciseStatus.NOT_STARTED;
//                            break;
//                        default:
//                            exerciseStatus = ExerciseStatus.STARTED_ALL_CORRECT;
//                    }
//                }
//            }

            final String exerciseKey = lessonId.toString() + "," + exercise;
            Boolean exerciseIsDone = mExercise2IsDone.get(exerciseKey);
//            Log.d(TAG, "exerciseKey, question, questionIsDone: " + exerciseKey + ", " + question + ", " + questionIsDone);
            if (exerciseIsDone == null) {
                exerciseIsDone = true;
            }
            if (exerciseIsDone == true) {
                if (!questionIsDone) {
                    exerciseIsDone = false;
                }
            }

//            mExercise2Status.put(new ExerciseKey(lesson, exercise), exerciseStatus);
//            mExercise2Status.put("" + lesson + "," + exercise, exerciseStatus);
            mExercise2IsDone.put(exerciseKey, exerciseIsDone);
        }
    }

//    private Map<Integer, Set<Integer>> mLesson2Exercises;
    private final Map<Pair<Integer, Integer>, Set<Integer>> mLesson2Exercises;
    //    private Map<Integer, ExerciseStatus> mExercise2Status;
//    private Map<ExerciseKey, ExerciseStatus> mExercise2Status;
//    private Map<String, ExerciseStatus> mExercise2StatusOLD;
    private final Map<String, Boolean> mExercise2IsDone;

    private static final String TAG = LessonTracker.class.getSimpleName();
}
