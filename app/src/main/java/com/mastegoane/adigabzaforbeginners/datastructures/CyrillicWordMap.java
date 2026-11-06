package com.mastegoane.adigabzaforbeginners.datastructures;

import android.util.Log;
import androidx.collection.ArrayMap;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.mastegoane.adigabzaforbeginners.databases.DictionaryEntry;
import com.mastegoane.adigabzaforbeginners.utils.AdigaCharacterUtils;
import java.util.List;
import java.util.Map;
import java.util.Set;

// This is a singleton class
public class CyrillicWordMap {
    private CyrillicWordMap() {
        mCyrillic2Id = new ArrayMap<>();
        mCyrillic2Latin = new ArrayMap<>();
        mCyrillic2Sound = new ArrayMap<>();
        mCyrillicWords = new MutableLiveData<>();
    }

    public static synchronized CyrillicWordMap inst() {
        return mInstance;
    }

    public void updateData(List<DictionaryEntry> dictionaryEntries) {
        if (dictionaryEntries != null) {
            for (DictionaryEntry dictionaryEntry : dictionaryEntries) {
                final String cyrillic = dictionaryEntry.getCyrillic();
                mCyrillic2Id.put(cyrillic, dictionaryEntry.getFullId());
                mCyrillic2Latin.put(cyrillic, dictionaryEntry.getLatin());
                mCyrillic2Sound.put(cyrillic, dictionaryEntry.getAudio());
            }
            mCyrillicWords.setValue(mCyrillic2Latin.keySet());
        } else {
            Log.d(TAG, "YYY3b data NOT found");
        }
    }

    public String getLatin(String cyrillic) {
        String latin = mCyrillic2Latin.get(cyrillic);
        if (latin == null || latin.isEmpty()) {
            latin = AdigaCharacterUtils.inst().cyrillic2Latin(cyrillic);
        }
        return latin;
    }

    public String getFullId(String cyrillic) {
        return mCyrillic2Id.get(cyrillic);
    }

    public String getSound(String cyrillic) {
        return mCyrillic2Sound.get(cyrillic);
    }

    public LiveData<Set<String>> getCyrillicWords() {
        return mCyrillicWords;
    }

    private static final CyrillicWordMap mInstance = new CyrillicWordMap();
    private final Map<String, String> mCyrillic2Id;
    private final Map<String, String> mCyrillic2Latin;
    private final Map<String, String> mCyrillic2Sound;
    private final MutableLiveData<Set<String>> mCyrillicWords;
    private static final String TAG = CyrillicWordMap.class.getSimpleName();
}
