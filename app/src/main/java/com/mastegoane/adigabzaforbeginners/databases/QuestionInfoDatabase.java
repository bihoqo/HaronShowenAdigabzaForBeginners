package com.mastegoane.adigabzaforbeginners.databases;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {QuestionInfo.class}, version = 7, exportSchema = false)
public abstract class QuestionInfoDatabase extends RoomDatabase {

    private static QuestionInfoDatabase mInstance;

    public abstract QuestionInfoDao questionInfoDao();

    public static synchronized QuestionInfoDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(),
                    QuestionInfoDatabase.class, "question_info_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mInstance;
    }

//    private static final String TAG = QuestionInfoDatabase.class.getSimpleName();
}
