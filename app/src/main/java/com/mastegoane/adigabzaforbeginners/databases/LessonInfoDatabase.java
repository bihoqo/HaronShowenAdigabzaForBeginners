package com.mastegoane.adigabzaforbeginners.databases;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {LessonInfo.class}, version = 7, exportSchema = false)
public abstract class LessonInfoDatabase extends RoomDatabase {
    private static LessonInfoDatabase mInstance;

    public abstract LessonInfoDao lessonInfoDao();

    public static synchronized LessonInfoDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(),
                    LessonInfoDatabase.class, "lesson_info_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mInstance;
    }
}
