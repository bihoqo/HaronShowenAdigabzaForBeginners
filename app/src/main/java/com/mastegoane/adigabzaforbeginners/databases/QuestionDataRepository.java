package com.mastegoane.adigabzaforbeginners.databases;

import android.app.Application;
import android.os.AsyncTask;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class QuestionDataRepository {
    private final QuestionDataDao mQuestionDataDao;

    public QuestionDataRepository(Application application) {
        QuestionDataDatabase questionDataDatabase = QuestionDataDatabase.getInstance(application);
        mQuestionDataDao = questionDataDatabase.questionDataDao();
    }

    public void insert(QuestionData questionData) {
        new InsertQuestionDataAsyncTask(mQuestionDataDao).execute(questionData);
    }

    public void insertList(List<QuestionData> questionDatas) {
        new InsertQuestionDataListAsyncTask(mQuestionDataDao).execute(questionDatas);
    }

    public void update(QuestionData questionData) {
        new UpdateQuestionDataAsyncTask(mQuestionDataDao).execute(questionData);
    }

    public void delete(QuestionData questionData) {
        new DeleteQuestionDataAsyncTask(mQuestionDataDao).execute(questionData);
    }

    public void deleteAllEntriesOfCourse(int courseId) {
        new DeleteAllQuestionDataOfCourseAsyncTask(mQuestionDataDao).execute(courseId);
    }

    public List<QuestionData> getAllEntriesOfCourse(int courseId) {
        GetAllEntriesOfCourseAsyncTask getEntries = new GetAllEntriesOfCourseAsyncTask(mQuestionDataDao);
        getEntries.execute(courseId);
        try {
            return getEntries.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCountOfCourse(int courseId) {
        GetCountOfCourseAsyncTask count = new GetCountOfCourseAsyncTask(mQuestionDataDao);
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


    public LiveData<List<QuestionData>> getAllEntriesOfLessonOfCourse(int courseId, Pair<Integer, Integer> lessonId) {
        GetAllEntriesOfLessonOfCourseAsyncTask getEntries = new GetAllEntriesOfLessonOfCourseAsyncTask(mQuestionDataDao);
        getEntries.execute(courseId, lessonId.first, lessonId.second);
        try {
            return getEntries.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public QuestionData getQuestionByNumberOfCourse(int courseId, Pair<Integer, Integer> lessonId, int qNumber) {
        GetQuestionByNumberOfCourseAsyncTask getQuestion = new GetQuestionByNumberOfCourseAsyncTask(mQuestionDataDao);
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

    private static class InsertQuestionDataAsyncTask extends AsyncTask<QuestionData, Void, Void> {
        private final QuestionDataDao mQuestionDataDao;

        private InsertQuestionDataAsyncTask(QuestionDataDao questionDataDao) {
            this.mQuestionDataDao = questionDataDao;
        }

        @Override
        protected Void doInBackground(QuestionData... questionDatas) {
            mQuestionDataDao.insert(questionDatas[0]);
            return null;
        }
    }

    private static class InsertQuestionDataListAsyncTask extends AsyncTask<List<QuestionData>, Void, Void> {
        private final QuestionDataDao mQuestionDataDao;

        private InsertQuestionDataListAsyncTask(QuestionDataDao questionDataDao) {
            this.mQuestionDataDao = questionDataDao;
        }

        @Override
        protected Void doInBackground(List<QuestionData>... questionDatas) {
            for (QuestionData lessData : questionDatas[0]) {
                mQuestionDataDao.insert(lessData);
            }
            return null;
        }
    }

    private static class UpdateQuestionDataAsyncTask extends AsyncTask<QuestionData, Void, Void> {
        private final QuestionDataDao mQuestionDataDao;

        private UpdateQuestionDataAsyncTask(QuestionDataDao questionDataDao) {
            this.mQuestionDataDao = questionDataDao;
        }

        @Override
        protected Void doInBackground(QuestionData... questionDatas) {
            mQuestionDataDao.update(questionDatas[0]);
            return null;
        }
    }

    private static class DeleteQuestionDataAsyncTask extends AsyncTask<QuestionData, Void, Void> {
        private final QuestionDataDao mQuestionDataDao;

        private DeleteQuestionDataAsyncTask(QuestionDataDao questionDataDao) {
            this.mQuestionDataDao = questionDataDao;
        }

        @Override
        protected Void doInBackground(QuestionData... questionDatas) {
            mQuestionDataDao.delete(questionDatas[0]);
            return null;
        }
    }

    private static class DeleteAllQuestionDataOfCourseAsyncTask extends AsyncTask<Integer, Void, Void> {
        private final QuestionDataDao mQuestionDataDao;

        private DeleteAllQuestionDataOfCourseAsyncTask(QuestionDataDao questionDataDao) {
            this.mQuestionDataDao = questionDataDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            mQuestionDataDao.deleteAllEntriesOfCourse(integers[0]);
            return null;
        }
    }

    private static class GetQuestionByNumberOfCourseAsyncTask extends AsyncTask<Integer, Void, QuestionData> {
        private final QuestionDataDao mQuestionDataDao;

        private GetQuestionByNumberOfCourseAsyncTask(QuestionDataDao questionDataDao) {
            this.mQuestionDataDao = questionDataDao;
        }

        @Override
        protected QuestionData doInBackground(Integer... integers) {
            return mQuestionDataDao.getQuestionByNumberOfCourse(integers[0], integers[1], integers[2], integers[3]);
        }
    }

    private static class GetAllEntriesOfCourseAsyncTask extends AsyncTask<Integer, Void, List<QuestionData>> {
        private final QuestionDataDao mQuestionDataDao;

        private GetAllEntriesOfCourseAsyncTask(QuestionDataDao questionDataDao) {
            this.mQuestionDataDao = questionDataDao;
        }

        @Override
        protected List<QuestionData> doInBackground(Integer... integers) {
            return mQuestionDataDao.getAllEntriesOfCourse(integers[0]);
        }
    }

    private static class GetCountOfCourseAsyncTask extends AsyncTask<Integer, Void, Integer> {
        private final QuestionDataDao mQuestionDataDao;

        private GetCountOfCourseAsyncTask(QuestionDataDao questionDataDao) {
            this.mQuestionDataDao = questionDataDao;
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            return mQuestionDataDao.getCountOfCourse(integers[0]);
        }
    }

    private static class GetAllEntriesOfLessonOfCourseAsyncTask extends AsyncTask<Integer, Void, LiveData<List<QuestionData>>> {
        private final QuestionDataDao mQuestionDataDao;

        private GetAllEntriesOfLessonOfCourseAsyncTask(QuestionDataDao questionDataDao) {
            this.mQuestionDataDao = questionDataDao;
        }

        @Override
        protected LiveData<List<QuestionData>> doInBackground(Integer... integers) {
            return mQuestionDataDao.getAllEntriesOfLessonOfCourse(integers[0], integers[1], integers[2]);
        }
    }
}
