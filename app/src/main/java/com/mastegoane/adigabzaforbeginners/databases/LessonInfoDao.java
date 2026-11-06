package com.mastegoane.adigabzaforbeginners.databases;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface LessonInfoDao {
    @Insert
    void insert(LessonInfo lessonInfo);

    @Update
    void update(LessonInfo lessonInfo);

    @Delete
    void delete(LessonInfo lessonInfo);

    @Query("DELETE FROM lesson_info WHERE courseId=:cId")
    void deleteAllEntriesOfCourse(int cId);

    @Query("SELECT * FROM lesson_info WHERE courseId=:cId")
    LiveData<List<LessonInfo>> getAllEntriesOfCourse(int cId);

    @Query("SELECT * FROM lesson_info WHERE courseId=:cId AND stageNumber=:sNumber AND lessonNumber=:lNumber")
    LessonInfo getEntryOfCourse(int cId, int sNumber, int lNumber);

    @Query("SELECT COUNT(id) FROM lesson_info WHERE courseId=:cId")
    int getCountOfCourse(int cId);
}
