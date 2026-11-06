package com.mastegoane.adigabzaforbeginners.databases;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {DictionaryEntry.class}, version = 4, exportSchema = false)
public abstract class DictionaryDatabase extends RoomDatabase {

    private static DictionaryDatabase mInstance;

    public abstract DictionaryEntryDao dictionaryEntryDao();

    public static synchronized DictionaryDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(),
                    DictionaryDatabase.class, "dictionary_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mInstance;
    }

//    private static final String TAG = DictionaryDatabase.class.getSimpleName();
}
