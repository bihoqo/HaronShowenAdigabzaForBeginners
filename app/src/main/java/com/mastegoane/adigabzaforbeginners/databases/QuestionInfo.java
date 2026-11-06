package com.mastegoane.adigabzaforbeginners.databases;

import androidx.core.util.Pair;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "question_info")
public class QuestionInfo {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private final int courseId;
    private final int stageNumber;
    private final int lessonNumber;
    private final int exerciseNumber;
    private final int questionNumber;
    private boolean isDone;
    private boolean isTried;
    // next is true if the question has been answered wrongly at least once, even if it has been answered correctly eventually.
    private boolean beenWrong;

    public QuestionInfo(int courseId, int stageNumber, int lessonNumber, int exerciseNumber, int questionNumber, boolean isDone, boolean isTried, boolean beenWrong) {
        this.courseId = courseId;
        this.stageNumber = stageNumber;
        this.lessonNumber = lessonNumber;
        this.exerciseNumber = exerciseNumber;
        this.questionNumber = questionNumber;
        this.isDone = isDone;
        this.isTried = isTried;
        this.beenWrong = beenWrong;
    }

    public int getId() {
        return id;
    }

    public Pair<Integer, Integer> getLessonId() {
        return new Pair<>(stageNumber, lessonNumber);
    }

    public int getCourseId() {
        return courseId;
    }

    public int getStageNumber() {
        return stageNumber;
    }

    public int getLessonNumber() {
        return lessonNumber;
    }

    public int getExerciseNumber() {
        return exerciseNumber;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public boolean getDone() {
        return isDone;
    }

    public void setDone(boolean s) {
        isDone = s;
    }

    public boolean getTried() {
        return isTried;
    }

    public void setTried(boolean s) {
        isTried = s;
    }

    public boolean getBeenWrong() {
        return beenWrong;
    }

    public void setBeenWrong(boolean s) {
        beenWrong = s;
    }

    public void setId(int id) {
        this.id = id;
    }
}
