package com.mastegoane.adigabzaforbeginners.databases;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface QuestionDataDao {
    @Update
    void update(QuestionData questionData);

    @Insert
    void insert(QuestionData questionData);

    @Delete
    void delete(QuestionData questionData);

    @Query("DELETE FROM question_data WHERE courseId=:cId")
    void deleteAllEntriesOfCourse(int cId);

    @Query("SELECT * FROM question_data WHERE courseId=:cId")
    List<QuestionData> getAllEntriesOfCourse(int cId);

    @Query("SELECT * FROM question_data WHERE courseId=:cId AND stageNumber=:sNumber AND lessonNumber=:lNumber")
    LiveData<List<QuestionData>> getAllEntriesOfLessonOfCourse(int cId, int sNumber, int lNumber);

//    @Query("SELECT * FROM question_data WHERE questionType=1")
//    LiveData<List<QuestionData>> getAllEntriesLesson1();
//
//    @Query("SELECT * FROM question_data WHERE questionType=2")
//    LiveData<List<QuestionData>> getAllEntriesLesson2();

    @Query("SELECT * FROM question_data WHERE courseId=:cId AND stageNumber=:sNumber AND lessonNumber=:lNumber AND questionNumber=:qNumber")
    QuestionData getQuestionByNumberOfCourse(int cId, int sNumber, int lNumber, int qNumber);

    @Query("SELECT COUNT(id) FROM question_data WHERE courseId=:cId")
    int getCountOfCourse(int cId);
}
