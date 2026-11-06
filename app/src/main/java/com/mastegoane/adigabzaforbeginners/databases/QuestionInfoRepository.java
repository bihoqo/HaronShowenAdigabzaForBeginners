package com.mastegoane.adigabzaforbeginners.databases;

import android.app.Application;
import android.os.AsyncTask;
import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class QuestionInfoRepository {
    private final QuestionInfoDao mQuestionInfoDao;

    public QuestionInfoRepository(Application application) {
        QuestionInfoDatabase questionInfoDatabase = QuestionInfoDatabase.getInstance(application);
        mQuestionInfoDao = questionInfoDatabase.questionInfoDao();
    }

    public void insert(QuestionInfo questionInfo) {
        new InsertQuestionInfoAsyncTask(mQuestionInfoDao).execute(questionInfo);
    }

    public void insertList(List<QuestionInfo> questionInfos) {
        new InsertQuestionInfoListAsyncTask(mQuestionInfoDao).execute(questionInfos);
    }

    public void update(QuestionInfo questionInfo) {
        new UpdateQuestionInfoAsyncTask(mQuestionInfoDao).execute(questionInfo);
    }

    public void delete(QuestionInfo questionInfo) {
        new DeleteQuestionInfoAsyncTask(mQuestionInfoDao).execute(questionInfo);
    }

    public void deleteAllEntriesOfCourse(int courseId) {
        new DeleteAllQuestionInfoOfCourseAsyncTask(mQuestionInfoDao).execute(courseId);
    }

    public LiveData<List<QuestionInfo>> getAllEntriesOfCourse(int courseId) {
        GetAllEntriesOfCourseAsyncTask task = new GetAllEntriesOfCourseAsyncTask(mQuestionInfoDao);
        task.execute(courseId);
        try {
            return task.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<QuestionInfo> getAllEntriesOfLessonOfCourse(int courseId, Pair<Integer, Integer> lessonId) {
        GetAllEntriesOfLessonOfCourseAsyncTask task = new GetAllEntriesOfLessonOfCourseAsyncTask(mQuestionInfoDao);
        task.execute(courseId, lessonId.first, lessonId.second);
        try {
            return task.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCountOfCourse(int courseId) {
        GetCountOfCourseAsyncTask count = new QuestionInfoRepository.GetCountOfCourseAsyncTask(mQuestionInfoDao);
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

    public int getQuestionsCountOfCourse(int courseId, Pair<Integer, Integer> lessonId) {
        GetQuestionsCountOfCourseAsyncTask questionsCount = new QuestionInfoRepository.GetQuestionsCountOfCourseAsyncTask(mQuestionInfoDao);
        questionsCount.execute(courseId, lessonId.first, lessonId.second);
        try {
            return questionsCount.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public QuestionInfo getQuestionByNumberOfCourse(int courseId, Pair<Integer, Integer> lessonId, int qNumber) {
        GetQuestionByNumberOfCourseAsyncTask getQuestion = new QuestionInfoRepository.GetQuestionByNumberOfCourseAsyncTask(mQuestionInfoDao);
        getQuestion.execute(courseId, lessonId.first, lessonId.second, qNumber);
        try {
            return getQuestion.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Async Tasks - database operations cannot be performed on the main thread (app will crash), so need to do them on separate threads.

    private static class InsertQuestionInfoAsyncTask extends AsyncTask<QuestionInfo, Void, Void> {
        private final QuestionInfoDao mQuestionInfoDao;

        private InsertQuestionInfoAsyncTask(QuestionInfoDao questionInfoDao) {
            this.mQuestionInfoDao = questionInfoDao;
        }

        @Override
        protected Void doInBackground(QuestionInfo... questionInfos) {
            mQuestionInfoDao.insert(questionInfos[0]);
            return null;
        }
    }

    private static class InsertQuestionInfoListAsyncTask extends AsyncTask<List<QuestionInfo>, Void, Void> {
        private final QuestionInfoDao mQuestionInfoDao;

        private InsertQuestionInfoListAsyncTask(QuestionInfoDao questionInfoDao) {
            this.mQuestionInfoDao = questionInfoDao;
        }

        @Override
        protected Void doInBackground(List<QuestionInfo>... questionInfoLists) {
            for (QuestionInfo questionInfo : questionInfoLists[0]) {
                mQuestionInfoDao.insert(questionInfo);
            }
            return null;
        }
    }

    private static class UpdateQuestionInfoAsyncTask extends AsyncTask<QuestionInfo, Void, Void> {
        private final QuestionInfoDao mQuestionInfoDao;

        private UpdateQuestionInfoAsyncTask(QuestionInfoDao questionInfoDao) {
            this.mQuestionInfoDao = questionInfoDao;
        }

        @Override
        protected Void doInBackground(QuestionInfo... questionInfos) {
            mQuestionInfoDao.update(questionInfos[0]);
            return null;
        }
    }

    private static class DeleteQuestionInfoAsyncTask extends AsyncTask<QuestionInfo, Void, Void> {
        private final QuestionInfoDao mQuestionInfoDao;

        private DeleteQuestionInfoAsyncTask(QuestionInfoDao questionInfoDao) {
            this.mQuestionInfoDao = questionInfoDao;
        }

        @Override
        protected Void doInBackground(QuestionInfo... questionInfos) {
            mQuestionInfoDao.delete(questionInfos[0]);
            return null;
        }
    }

    private static class DeleteAllQuestionInfoOfCourseAsyncTask extends AsyncTask<Integer, Void, Void> {
        private final QuestionInfoDao mQuestionInfoDao;

        private DeleteAllQuestionInfoOfCourseAsyncTask(QuestionInfoDao questionInfoDao) {
            this.mQuestionInfoDao = questionInfoDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            mQuestionInfoDao.deleteAllEntriesOfCourse(integers[0]);
            return null;
        }
    }

    private static class GetAllEntriesOfCourseAsyncTask extends AsyncTask<Integer, Void, LiveData<List<QuestionInfo>>> {
        private final QuestionInfoDao mQuestionInfoDao;

        private GetAllEntriesOfCourseAsyncTask(QuestionInfoDao questionInfoDao) {
            this.mQuestionInfoDao = questionInfoDao;
        }

        @Override
        protected LiveData<List<QuestionInfo>> doInBackground(Integer... integers) {
            return mQuestionInfoDao.getAllEntriesOfCourse(integers[0]);
        }
    }

    private static class GetAllEntriesOfLessonOfCourseAsyncTask extends AsyncTask<Integer, Void, List<QuestionInfo>> {
        private final QuestionInfoDao mQuestionInfoDao;

        private GetAllEntriesOfLessonOfCourseAsyncTask(QuestionInfoDao questionInfoDao) {
            this.mQuestionInfoDao = questionInfoDao;
        }

        @Override
        protected List<QuestionInfo> doInBackground(Integer... integers) {
            return mQuestionInfoDao.getAllEntriesOfLessonOfCourse(integers[0], integers[1], integers[2]);
        }
    }

    private static class GetCountOfCourseAsyncTask extends AsyncTask<Integer, Void, Integer> {
        private final QuestionInfoDao mQuestionInfoDao;

        private GetCountOfCourseAsyncTask(QuestionInfoDao questionInfoDao) {
            this.mQuestionInfoDao = questionInfoDao;
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            return mQuestionInfoDao.getCountOfCourse(integers[0]);
        }
    }

    private static class GetQuestionsCountOfCourseAsyncTask extends AsyncTask<Integer, Void, Integer> {
        private final QuestionInfoDao mQuestionInfoDao;

        private GetQuestionsCountOfCourseAsyncTask(QuestionInfoDao questionInfoDao) {
            this.mQuestionInfoDao = questionInfoDao;
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            return mQuestionInfoDao.getCountOfLessonOfCourse(integers[0], integers[1], integers[2]);
        }
    }

    private static class GetQuestionByNumberOfCourseAsyncTask extends AsyncTask<Integer, Void, QuestionInfo> {
        private final QuestionInfoDao mQuestionInfoDao;

        private GetQuestionByNumberOfCourseAsyncTask(QuestionInfoDao questionInfoDao) {
            this.mQuestionInfoDao = questionInfoDao;
        }

        @Override
        protected QuestionInfo doInBackground(Integer... integers) {
            return mQuestionInfoDao.getQuestionByNumberOfCourse(integers[0], integers[1], integers[2], integers[3]);
        }
    }
}
