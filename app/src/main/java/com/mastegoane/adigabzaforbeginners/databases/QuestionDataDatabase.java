package com.mastegoane.adigabzaforbeginners.databases;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {QuestionData.class}, version = 8, exportSchema = false)
public abstract class QuestionDataDatabase extends RoomDatabase {

    private static QuestionDataDatabase mInstance;

    public abstract QuestionDataDao questionDataDao();

    public static synchronized QuestionDataDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(),
                    QuestionDataDatabase.class, "question_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mInstance;
    }

//    private static final String TAG = QuestionDataDatabase.class.getSimpleName();
}
