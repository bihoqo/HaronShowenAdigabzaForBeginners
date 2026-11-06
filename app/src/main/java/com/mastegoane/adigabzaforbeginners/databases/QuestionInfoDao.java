package com.mastegoane.adigabzaforbeginners.databases;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface QuestionInfoDao {
    @Insert
    void insert(QuestionInfo questionInfo);

    @Update
    void update(QuestionInfo questionInfo);

    @Delete
    void delete(QuestionInfo questionInfo);

    @Query("DELETE FROM question_info WHERE courseId=:cId")
    void deleteAllEntriesOfCourse(int cId);

    @Query("SELECT * FROM question_info WHERE courseId=:cId")
    LiveData<List<QuestionInfo>> getAllEntriesOfCourse(int cId);

    @Query("SELECT * FROM question_info WHERE courseId=:cId AND stageNumber=:sNumber AND lessonNumber=:lNumber")
    List<QuestionInfo> getAllEntriesOfLessonOfCourse(int cId, int sNumber, int lNumber);

    @Query("SELECT * FROM question_info WHERE courseId=:cId AND stageNumber=:sNumber AND lessonNumber=:lNumber AND questionNumber=:qNumber")
    QuestionInfo getQuestionByNumberOfCourse(int cId, int sNumber, int lNumber, int qNumber);

    @Query("SELECT COUNT(id) FROM question_info WHERE courseId=:cId")
    int getCountOfCourse(int cId);

    @Query("SELECT COUNT(id) FROM question_info WHERE courseId=:cId AND stageNumber=:sNumber AND lessonNumber=:lNumber")
    int getCountOfLessonOfCourse(int cId, int sNumber, int lNumber);

//    @Query("SELECT COUNT(id) FROM question_info WHERE stageNumber=:sNumber AND lessonNumber=:lNumber  AND exerciseNumber=:eNumber")
//    int getCountOfExercise(int sNumber, int lNumber, int eNumber);
}
