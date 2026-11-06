package com.mastegoane.adigabzaforbeginners.databases;

import android.app.Application;
import android.os.AsyncTask;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LessonInfoRepository {
    private final LessonInfoDao mLessonInfoDao;

    public LessonInfoRepository(Application application) {
        LessonInfoDatabase lessonInfoDatabase = LessonInfoDatabase.getInstance(application);
        mLessonInfoDao = lessonInfoDatabase.lessonInfoDao();
    }

    public void insert(LessonInfo lessonInfo) {
        new LessonInfoRepository.InsertLessonInfoAsyncTask(mLessonInfoDao).execute(lessonInfo);
    }

    public void insertList(List<LessonInfo> lessonInfos) {
        new LessonInfoRepository.InsertLessonInfoListAsyncTask(mLessonInfoDao).execute(lessonInfos);
    }

    public void update(LessonInfo lessonInfo) {
        new LessonInfoRepository.UpdateLessonInfoAsyncTask(mLessonInfoDao).execute(lessonInfo);
    }

    public void delete(LessonInfo lessonInfo) {
        new LessonInfoRepository.DeleteLessonInfoAsyncTask(mLessonInfoDao).execute(lessonInfo);
    }

    public void deleteAllEntriesOfCourse(int courseId) {
        new LessonInfoRepository.DeleteAllLessonInfoOfCourseAsyncTask(mLessonInfoDao).execute(courseId);
    }

    public LiveData<List<LessonInfo>> getAllEntriesOfCourse(int courseId) {
        GetAllEntriesOfCourseAsyncTask entries = new LessonInfoRepository.GetAllEntriesOfCourseAsyncTask(mLessonInfoDao);
        entries.execute(courseId);
        try {
            return entries.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LessonInfo getLessonInfoByIdOfCourse(int courseId, int stageNumber, int lessonNumber) {
        LessonInfoRepository.GetLessonInfoByIdOfCourseAsyncTask getLessonInfo = new LessonInfoRepository.GetLessonInfoByIdOfCourseAsyncTask(mLessonInfoDao);
        getLessonInfo.execute(courseId, stageNumber, lessonNumber);
        try {
            return getLessonInfo.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCountOfCourse(int courseId) {
        LessonInfoRepository.GetCountOfCourseAsyncTask count = new LessonInfoRepository.GetCountOfCourseAsyncTask(mLessonInfoDao);
        count.execute(courseId);
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

    private static class InsertLessonInfoAsyncTask extends AsyncTask<LessonInfo, Void, Void> {
        private final LessonInfoDao mLessonInfoDao;

        private InsertLessonInfoAsyncTask(LessonInfoDao lessonInfoDao) {
            this.mLessonInfoDao = lessonInfoDao;
        }

        @Override
        protected Void doInBackground(LessonInfo... lessonInfos) {
            mLessonInfoDao.insert(lessonInfos[0]);
            return null;
        }
    }

    private static class InsertLessonInfoListAsyncTask extends AsyncTask<List<LessonInfo>, Void, Void> {
        private final LessonInfoDao mLessonInfoDao;

        private InsertLessonInfoListAsyncTask(LessonInfoDao lessonInfoDao) {
            this.mLessonInfoDao = lessonInfoDao;
        }

        @Override
        protected Void doInBackground(List<LessonInfo>... lessonInfoLists) {
            for (LessonInfo lessonInfo : lessonInfoLists[0]) {
                mLessonInfoDao.insert(lessonInfo);
            }
            return null;
        }
    }

    private static class UpdateLessonInfoAsyncTask extends AsyncTask<LessonInfo, Void, Void> {
        private final LessonInfoDao mLessonInfoDao;

        private UpdateLessonInfoAsyncTask(LessonInfoDao lessonInfoDao) {
            this.mLessonInfoDao = lessonInfoDao;
        }

        @Override
        protected Void doInBackground(LessonInfo... lessonInfos) {
            mLessonInfoDao.update(lessonInfos[0]);
            return null;
        }
    }

    private static class DeleteLessonInfoAsyncTask extends AsyncTask<LessonInfo, Void, Void> {
        private final LessonInfoDao mLessonInfoDao;

        private DeleteLessonInfoAsyncTask(LessonInfoDao lessonInfoDao) {
            this.mLessonInfoDao = lessonInfoDao;
        }

        @Override
        protected Void doInBackground(LessonInfo... lessonInfos) {
            mLessonInfoDao.delete(lessonInfos[0]);
            return null;
        }
    }

    private static class DeleteAllLessonInfoOfCourseAsyncTask extends AsyncTask<Integer, Void, Void> {
        private final LessonInfoDao mLessonInfoDao;

        private DeleteAllLessonInfoOfCourseAsyncTask(LessonInfoDao lessonInfoDao) {
            this.mLessonInfoDao = lessonInfoDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            mLessonInfoDao.deleteAllEntriesOfCourse(integers[0]);
            return null;
        }
    }

    private static class GetAllEntriesOfCourseAsyncTask extends AsyncTask<Integer, Void, LiveData<List<LessonInfo>>> {
        private final LessonInfoDao mLessonInfoDao;

        private GetAllEntriesOfCourseAsyncTask(LessonInfoDao lessonInfoDao) {
            this.mLessonInfoDao = lessonInfoDao;
        }

        @Override
        protected LiveData<List<LessonInfo>> doInBackground(Integer... integers) {
            return mLessonInfoDao.getAllEntriesOfCourse(integers[0]);
        }
    }

    private static class GetLessonInfoByIdOfCourseAsyncTask extends AsyncTask<Integer, Void, LessonInfo> {
        private final LessonInfoDao mLessonInfoDao;

        private GetLessonInfoByIdOfCourseAsyncTask(LessonInfoDao lessonInfoDao) {
            this.mLessonInfoDao = lessonInfoDao;
        }

        @Override
        protected LessonInfo doInBackground(Integer... integers) {
            return mLessonInfoDao.getEntryOfCourse(integers[0], integers[1], integers[2]);
        }
    }

    private static class GetCountOfCourseAsyncTask extends AsyncTask<Integer, Void, Integer> {
        private final LessonInfoDao mLessonInfoDao;

        private GetCountOfCourseAsyncTask(LessonInfoDao lessonInfoDao) {
            this.mLessonInfoDao = lessonInfoDao;
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            return mLessonInfoDao.getCountOfCourse(integers[0]);
        }
    }

}
