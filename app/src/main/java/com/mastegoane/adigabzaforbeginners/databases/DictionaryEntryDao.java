package com.mastegoane.adigabzaforbeginners.databases;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface DictionaryEntryDao {

    @Insert
    void insert(DictionaryEntry dictionaryEntry);

    @Update
    void update(DictionaryEntry dictionaryEntry);

    @Delete
    void delete(DictionaryEntry dictionaryEntry);

    @Query("DELETE FROM dictionary")
    void deleteAllEntries();

//    @Query("SELECT * FROM word_dictionary")
//    List<DictionaryEntry> getAllEntries();

    @Query("SELECT * FROM dictionary")
    LiveData<List<DictionaryEntry>> getAllEntries();

    @Query("SELECT COUNT(id) FROM dictionary")
    int getCount();

//    @Query("SELECT COUNT(cyrillic) FROM dictionary")
//    int getCount();
}
