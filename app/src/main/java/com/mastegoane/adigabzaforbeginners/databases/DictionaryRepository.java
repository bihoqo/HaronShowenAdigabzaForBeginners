package com.mastegoane.adigabzaforbeginners.databases;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DictionaryRepository {
    private final DictionaryEntryDao mDictionaryEntryDao;

    public DictionaryRepository(Application application) {
        DictionaryDatabase dictionaryDatabase = DictionaryDatabase.getInstance(application);
        mDictionaryEntryDao = dictionaryDatabase.dictionaryEntryDao();
    }

    public void insert(DictionaryEntry dictionaryEntry) {
        new InsertDictionaryEntryAsyncTask(mDictionaryEntryDao).execute(dictionaryEntry);
    }

    public void insertList(List<DictionaryEntry> dictionaryEntries) {
        new InsertDictionaryEntryListAsyncTask(mDictionaryEntryDao).execute(dictionaryEntries);
    }

    public void update(DictionaryEntry dictionaryEntry) {
        new UpdateDictionaryEntryAsyncTask(mDictionaryEntryDao).execute(dictionaryEntry);
    }

    public void delete(DictionaryEntry dictionaryEntry) {
        new DeleteDictionaryEntryAsyncTask(mDictionaryEntryDao).execute(dictionaryEntry);
    }

    public void deleteAllEntries() {
        new DeleteAllDictionaryEntryAsyncTask(mDictionaryEntryDao).execute();
    }

    public LiveData<List<DictionaryEntry>> getAllEntries() {
        // TODO add AsyncTask class here like in the other methods
        return mDictionaryEntryDao.getAllEntries();
    }

    public int getCount() {
        DictionaryRepository.GetCountAsyncTask count = new DictionaryRepository.GetCountAsyncTask(mDictionaryEntryDao);
        count.execute();
        try {
            return count.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Async Tasks - database operations cannot be performed on the main thread (app will crash), so need to do them on separate threads.

    private static class InsertDictionaryEntryAsyncTask extends AsyncTask<DictionaryEntry, Void, Void> {
        private final DictionaryEntryDao mDictionaryEntryDao;

        private InsertDictionaryEntryAsyncTask(DictionaryEntryDao dictionaryEntryDao) {
            this.mDictionaryEntryDao = dictionaryEntryDao;
        }

        @Override
        protected Void doInBackground(DictionaryEntry... dictionaryEntries) {
            mDictionaryEntryDao.insert(dictionaryEntries[0]);
            return null;
        }
    }

    private static class InsertDictionaryEntryListAsyncTask extends AsyncTask<List<DictionaryEntry>, Void, Void> {
        private final DictionaryEntryDao mDictionaryEntryDao;

        private InsertDictionaryEntryListAsyncTask(DictionaryEntryDao dictionaryEntryDao) {
            this.mDictionaryEntryDao = dictionaryEntryDao;
        }

        @Override
        protected Void doInBackground(List<DictionaryEntry>... dictionaryEntryLists) {
            for (DictionaryEntry dictionaryEntry : dictionaryEntryLists[0]) {
                mDictionaryEntryDao.insert(dictionaryEntry);
            }
            return null;
        }
    }

    private static class UpdateDictionaryEntryAsyncTask extends AsyncTask<DictionaryEntry, Void, Void> {
        private final DictionaryEntryDao mDictionaryEntryDao;

        private UpdateDictionaryEntryAsyncTask(DictionaryEntryDao dictionaryEntryDao) {
            this.mDictionaryEntryDao = dictionaryEntryDao;
        }

        @Override
        protected Void doInBackground(DictionaryEntry... dictionaryEntries) {
            mDictionaryEntryDao.update(dictionaryEntries[0]);
            return null;
        }
    }

    private static class DeleteDictionaryEntryAsyncTask extends AsyncTask<DictionaryEntry, Void, Void> {
        private final DictionaryEntryDao mDictionaryEntryDao;

        private DeleteDictionaryEntryAsyncTask(DictionaryEntryDao dictionaryEntryDao) {
            this.mDictionaryEntryDao = dictionaryEntryDao;
        }

        @Override
        protected Void doInBackground(DictionaryEntry... dictionaryEntries) {
            mDictionaryEntryDao.delete(dictionaryEntries[0]);
            return null;
        }
    }

    private static class DeleteAllDictionaryEntryAsyncTask extends AsyncTask<Void, Void, Void> {
        private final DictionaryEntryDao mDictionaryEntryDao;

        private DeleteAllDictionaryEntryAsyncTask(DictionaryEntryDao dictionaryEntryDao) {
            this.mDictionaryEntryDao = dictionaryEntryDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDictionaryEntryDao.deleteAllEntries();
            return null;
        }
    }

    private static class GetCountAsyncTask extends AsyncTask<Void, Void, Integer> {
        private final DictionaryEntryDao mDictionaryEntryDao;

        private GetCountAsyncTask(DictionaryEntryDao dictionaryEntryDao) {
            this.mDictionaryEntryDao = dictionaryEntryDao;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return mDictionaryEntryDao.getCount();
        }
    }
}
