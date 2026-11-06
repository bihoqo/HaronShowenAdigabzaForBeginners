package com.mastegoane.adigabzaforbeginners.databases;

import androidx.core.util.Pair;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lesson_info")
public class LessonInfo {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int courseId;
    private final int stageNumber;
    private final int lessonNumber;
    private final String lessonTitle;
    private boolean isActivated;
    private boolean isDone;
    private boolean isAlreadyDoneOnce;
    private int numOfStars;

    private int numOfExercises;
    private boolean exercise1IsDone;
    private boolean exercise2IsDone;
    private boolean exercise3IsDone;
    private boolean exercise4IsDone;
    private boolean exercise5IsDone;
    private boolean exercise6IsDone;

    public LessonInfo(int courseId, int stageNumber, int lessonNumber, String lessonTitle, boolean isActivated, boolean isDone, boolean isAlreadyDoneOnce,
                      int numOfStars, int numOfExercises,
                      boolean exercise1IsDone, boolean exercise2IsDone,boolean exercise3IsDone,
                      boolean exercise4IsDone, boolean exercise5IsDone, boolean exercise6IsDone) {
        this.courseId = courseId;
        this.stageNumber = stageNumber;
        this.lessonNumber = lessonNumber;
        this.lessonTitle = lessonTitle;
        this.isActivated = isActivated;
        this.isDone = isDone;
        this.isAlreadyDoneOnce = isAlreadyDoneOnce;
        this.numOfStars = numOfStars;
        this.numOfExercises = numOfExercises;
        this.exercise1IsDone = exercise1IsDone;
        this.exercise2IsDone = exercise2IsDone;
        this.exercise3IsDone = exercise3IsDone;
        this.exercise4IsDone = exercise4IsDone;
        this.exercise5IsDone = exercise5IsDone;
        this.exercise6IsDone = exercise6IsDone;
    }

    public int getNumOfExercises() {
        return numOfExercises;
    }

    public void setNumOfExercises(int numOfExercises) {
        this.numOfExercises = numOfExercises;
    }

    public boolean isExercise1IsDone() {
        return exercise1IsDone;
    }

    public void setExercise1IsDone(boolean exercise1IsDone) {
        this.exercise1IsDone = exercise1IsDone;
    }

    public boolean isExercise2IsDone() {
        return exercise2IsDone;
    }

    public void setExercise2IsDone(boolean exercise2IsDone) {
        this.exercise2IsDone = exercise2IsDone;
    }

    public boolean isExercise3IsDone() {
        return exercise3IsDone;
    }

    public void setExercise3IsDone(boolean exercise3IsDone) {
        this.exercise3IsDone = exercise3IsDone;
    }

    public boolean isExercise4IsDone() {
        return exercise4IsDone;
    }

    public void setExercise4IsDone(boolean exercise4IsDone) {
        this.exercise4IsDone = exercise4IsDone;
    }

    public boolean isExercise5IsDone() {
        return exercise5IsDone;
    }

    public void setExercise5IsDone(boolean exercise5IsDone) {
        this.exercise5IsDone = exercise5IsDone;
    }

    public boolean isExercise6IsDone() {
        return exercise6IsDone;
    }

    public void setExercise6IsDone(boolean exercise6IsDone) {
        this.exercise6IsDone = exercise6IsDone;
    }

    public int getId() {
        return id;
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

    public Pair<Integer, Integer> getLessonId() {
        return new Pair<>(stageNumber, lessonNumber);
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public boolean isDone() {
        return isDone;
    }

    public boolean isAlreadyDoneOnce() {
        return isAlreadyDoneOnce;
    }

    public int getNumOfStars() {
        return numOfStars;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setActivated(boolean activated) {
        this.isActivated = activated;
    }

    public void setDone(boolean done) {
        this.isDone = done;
    }

    public void setAlreadyDoneOnce(boolean done) {
        this.isAlreadyDoneOnce = done;
    }

    public void setNumOfStars(int numOfStars) {
        this.numOfStars = numOfStars;
    }
}
