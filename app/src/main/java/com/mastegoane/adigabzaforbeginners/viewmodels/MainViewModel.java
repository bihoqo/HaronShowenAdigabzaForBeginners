package com.mastegoane.adigabzaforbeginners.viewmodels;

import android.app.Application;
import android.util.ArrayMap;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.mastegoane.adigabzaforbeginners.databases.DictionaryEntry;
import com.mastegoane.adigabzaforbeginners.databases.DictionaryRepository;
import com.mastegoane.adigabzaforbeginners.databases.LessonInfo;
import com.mastegoane.adigabzaforbeginners.databases.LessonInfoRepository;
import com.mastegoane.adigabzaforbeginners.databases.QuestionDataRepository;
import com.mastegoane.adigabzaforbeginners.databases.QuestionInfo;
import com.mastegoane.adigabzaforbeginners.databases.QuestionInfoRepository;
import com.mastegoane.adigabzaforbeginners.datastructures.CourseTracker;
import com.mastegoane.adigabzaforbeginners.utils.GlobalSettings;
import com.mastegoane.adigabzaforbeginners.utils.LessonLoader;
import com.mastegoane.adigabzaforbeginners.utils.SoundPlayer;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainViewModel extends AndroidViewModel {

    public MainViewModel(@NonNull Application application) {
        super(application);
        mApplication = application;
        // Initialize SoundPlayer Singleton
        SoundPlayer.initInstance(mApplication.getAssets());
        mQuestionDataRepository = new QuestionDataRepository(mApplication);
        mDictionaryRepository = new DictionaryRepository(mApplication);
        mQuestionInfoRepository = new QuestionInfoRepository(mApplication);
        mLessonInfoRepository = new LessonInfoRepository(mApplication);

        mStageNumbers = new ArrayList<>();
        mStageTitles = new ArrayList<>();
        mStageLesson2SubTitle = new ArrayMap<>();
        mStageNumOfLessons = new ArrayList<>();
        mLanguage = GlobalSettings.inst().getLanguage();

        initConstants();
//        populateDatabases();
    }

    public void initConstants() {
        mStageNumbers.clear();
        mStageNumbers.add(1);
        mStageNumbers.add(2);
        mStageNumbers.add(3);
        mStageNumbers.add(4);
//        mStageNumbers.add(5);
//        mStageNumbers.add(6);
//        mStageNumbers.add(7);

/*
Stage 1	Алыфбэ              Alphabets           	الأبجدية	    Alfabe
Stage 2	Фэсапщи	            Welcome             	أهلا وسهلا	    Hoşgeldiniz
Stage 3	Псэунгъэр сыд фэд	How are you         	كيف الحال	    Nasılsın
Stage 4	Сыд мыщ иуасэ?      How much does it cost?	كم يكلف؟	    Fiyatı ne kadar?
Stage 5	Тхьапщ сыхьатыр?	What's the time?    	كم الساعة؟	    Saat kaç?
Stage 6	Сыд уищыкӀагъэр?	What do you want?   	ماذا تحتاج؟	    Ne istiyorsun?
Stage 7	Унагъом ищыӀакӀ	    Family life         	الحياة العائلية	Aile hayatı
*/
        // TODO these should be re-initialized when alphabet is changed and when language is changed
        mStageTitles.clear();
        mStageLesson2SubTitle.clear();

        switch (mLanguage) {
            case ARABIC:
                mStageTitles.add("الأبجدية");
                mStageTitles.add("أهلا وسهلا");
                mStageTitles.add("المزيد ...");
                mStageTitles.add("المزيد ...");
//                mStageTitles.add("كيف الحال؟");
//                mStageTitles.add("كم يكلف؟");
//                mStageTitles.add("كم الساعة؟");
//                mStageTitles.add("ماذا تحتاج؟");
//                mStageTitles.add("الحياة العائلية");

                if (GlobalSettings.inst().isCyrillic()) {
                    mStageLesson2SubTitle.put("1,1", "الأبجدية 1\nА М Э С Ы И Е У О К Т");
                    mStageLesson2SubTitle.put("1,2", "الأبجدية 2\nI Ш Щ");
                    mStageLesson2SubTitle.put("1,3", "الأبجدية 3\nЯ Й П Р Н");
                    mStageLesson2SubTitle.put("1,4", "الأبجدية 4\nЗ Х Ч Ц");
                    mStageLesson2SubTitle.put("1,5", "الأبجدية 5\nЦу Д Л Б Г");
                    mStageLesson2SubTitle.put("1,6", "الأبجدية 6\nГу Ж Ф В Къ");
                    mStageLesson2SubTitle.put("1,7", "الأبجدية 7\nКI КIу Дж Гъ");
                    mStageLesson2SubTitle.put("1,8", "الأبجدية 8\nХь Хъ Лъ Жь Жъ Жъу Шъ Шъу ШI ШIу");
                    mStageLesson2SubTitle.put("1,9", "الأبجدية 9\nЦI Чъ ЧI ТI ПI ЛI Дз");
                    mStageLesson2SubTitle.put("1,10", "الأبجدية 10\nА Б В ...");
                } else {
                    mStageLesson2SubTitle.put("1,1", "الأبجدية 1\nA M E S I Yİ YE U WE Ḱ T");
                    mStageLesson2SubTitle.put("1,2", "الأبجدية 2\nI Ş");
                    mStageLesson2SubTitle.put("1,3", "الأبجدية 3\nYA Y P R N");
                    mStageLesson2SubTitle.put("1,4", "الأبجدية 4\nZ X Ç Ś");
                    mStageLesson2SubTitle.put("1,5", "الأبجدية 5\nÇÜ D L B Ǵ");
                    mStageLesson2SubTitle.put("1,6", "الأبجدية 6\nGU J F V K");
                    mStageLesson2SubTitle.put("1,7", "الأبجدية 7\nĆ QU C Ğ");
                    mStageLesson2SubTitle.put("1,8", "الأبجدية 8\nH Ḣ Ĺ J JÜ Ş ŞÜ Ş' ŞÜ'I");
                    mStageLesson2SubTitle.put("1,9", "الأبجدية 9\nŠ Ç Ć Ṫ Ṕ Ĺ' Ź");
                    mStageLesson2SubTitle.put("1,10", "الأبجدية 10\nA B V ...");
                }

                mStageLesson2SubTitle.put("2,1", "أهلا وسهلا");
                mStageLesson2SubTitle.put("2,2", "اسم العائلة");
                mStageLesson2SubTitle.put("2,3", "يومك سعيد");
                mStageLesson2SubTitle.put("2,4", "أين تعيش؟");

                mStageLesson2SubTitle.put("3,1", "More content in the next version");
//                mStageLesson2SubTitle.put("3,1", "كيف حالك؟");
//                mStageLesson2SubTitle.put("3,2", "هل أنت ستشرب الشاي؟");

                mStageLesson2SubTitle.put("4,1", "More content in the next version");
                break;
            case TURKISH:
                mStageTitles.add("Alfabe");
                mStageTitles.add("Hoşgeldiniz");
                mStageTitles.add("Daha ...");
                mStageTitles.add("Daha ...");
//                mStageTitles.add("Nasılsın?");
//                mStageTitles.add("Fiyatı ne kadar?");
//                mStageTitles.add("Saat kaç?");
//                mStageTitles.add("Ne istiyorsun?");
//                mStageTitles.add("Aile hayatı");

                if (GlobalSettings.inst().isCyrillic()) {
                    mStageLesson2SubTitle.put("1,1", "Alfabe 1\nА М Э С Ы И Е У О К Т");
                    mStageLesson2SubTitle.put("1,2", "Alfabe 2\nI Ш Щ");
                    mStageLesson2SubTitle.put("1,3", "Alfabe 3\nЯ Й П Р Н");
                    mStageLesson2SubTitle.put("1,4", "Alfabe 4\nЗ Х Ч Ц");
                    mStageLesson2SubTitle.put("1,5", "Alfabe 5\nЦу Д Л Б Г");
                    mStageLesson2SubTitle.put("1,6", "Alfabe 6\nГу Ж Ф В Къ");
                    mStageLesson2SubTitle.put("1,7", "Alfabe 7\nКI КIу Дж Гъ");
                    mStageLesson2SubTitle.put("1,8", "Alfabe 8\nХь Хъ Лъ Жь Жъ Жъу Шъ Шъу ШI ШIу");
                    mStageLesson2SubTitle.put("1,9", "Alfabe 9\nЦI Чъ ЧI ТI ПI ЛI Дз");
                    mStageLesson2SubTitle.put("1,10", "Alfabe 10\nА Б В ...");
                } else {
                    mStageLesson2SubTitle.put("1,1", "Alfabe 1\nA M E S I Yİ YE U WE Ḱ T");
                    mStageLesson2SubTitle.put("1,2", "Alfabe 2\nI Ş");
                    mStageLesson2SubTitle.put("1,3", "Alfabe 3\nYA Y P R N");
                    mStageLesson2SubTitle.put("1,4", "Alfabe 4\nZ X Ç Ś");
                    mStageLesson2SubTitle.put("1,5", "Alfabe 5\nÇÜ D L B Ǵ");
                    mStageLesson2SubTitle.put("1,6", "Alfabe 6\nGU J F V K");
                    mStageLesson2SubTitle.put("1,7", "Alfabe 7\nĆ QU C Ğ");
                    mStageLesson2SubTitle.put("1,8", "Alfabe 8\nH Ḣ Ĺ J JÜ Ş ŞÜ Ş' ŞÜ'I");
                    mStageLesson2SubTitle.put("1,9", "Alfabe 9\nŠ Ç Ć Ṫ Ṕ Ĺ' Ź");
                    mStageLesson2SubTitle.put("1,10", "Alfabe 10\nA B V ...");
                }

                mStageLesson2SubTitle.put("2,1", "Hoş geldin");
                mStageLesson2SubTitle.put("2,2", "ِAile adı");
                mStageLesson2SubTitle.put("2,3", "İyi günler");
                mStageLesson2SubTitle.put("2,4", "Nerede yaşıyorsun?");

                mStageLesson2SubTitle.put("3,1", "More content in the next version");
//                mStageLesson2SubTitle.put("3,1", "Nasılsın?");
//                mStageLesson2SubTitle.put("3,2", "Çay sen içer misin?");

                mStageLesson2SubTitle.put("4,1", "More content in the next version");
                break;
            default:
                mStageTitles.add("Alphabet");
                mStageTitles.add("Welcome");
                mStageTitles.add("More ...");
                mStageTitles.add("More ...");
//                mStageTitles.add("How are you?");
//                mStageTitles.add("How much does it cost?");
//                mStageTitles.add("What's the time?");
//                mStageTitles.add("What do you want?");
//                mStageTitles.add("Family life");

                if (GlobalSettings.inst().isCyrillic()) {
                    mStageLesson2SubTitle.put("1,1", "Alphabet 1\nА М Э С Ы И Е У О К Т");
                    mStageLesson2SubTitle.put("1,2", "Alphabet 2\nI Ш Щ");
                    mStageLesson2SubTitle.put("1,3", "Alphabet 3\nЯ Й П Р Н");
                    mStageLesson2SubTitle.put("1,4", "Alphabet 4\nЗ Х Ч Ц");
                    mStageLesson2SubTitle.put("1,5", "Alphabet 5\nЦу Д Л Б Г");
                    mStageLesson2SubTitle.put("1,6", "Alphabet 6\nГу Ж Ф В Къ");
                    mStageLesson2SubTitle.put("1,7", "Alphabet 7\nКI КIу Дж Гъ");
                    mStageLesson2SubTitle.put("1,8", "Alphabet 8\nХь Хъ Лъ Жь Жъ Жъу Шъ Шъу ШI ШIу");
                    mStageLesson2SubTitle.put("1,9", "Alphabet 9\nЦI Чъ ЧI ТI ПI ЛI Дз");
                    mStageLesson2SubTitle.put("1,10", "Alphabet 10\nА Б В ...");
                } else {
                    mStageLesson2SubTitle.put("1,1", "Alphabet 1\nA M E S I Yİ YE U WE Ḱ T");
                    mStageLesson2SubTitle.put("1,2", "Alphabet 2\nI Ş");
                    mStageLesson2SubTitle.put("1,3", "Alphabet 3\nYA Y P R N");
                    mStageLesson2SubTitle.put("1,4", "Alphabet 4\nZ X Ç Ś");
                    mStageLesson2SubTitle.put("1,5", "Alphabet 5\nÇÜ D L B Ǵ");
                    mStageLesson2SubTitle.put("1,6", "Alphabet 6\nGU J F V K");
                    mStageLesson2SubTitle.put("1,7", "Alphabet 7\nĆ QU C Ğ");
                    mStageLesson2SubTitle.put("1,8", "Alphabet 8\nH Ḣ Ĺ J JÜ Ş ŞÜ Ş' ŞÜ'I");
                    mStageLesson2SubTitle.put("1,9", "Alphabet 9\nŠ Ç Ć Ṫ Ṕ Ĺ' Ź");
                    mStageLesson2SubTitle.put("1,10", "Alphabet 10\nA B V ...");
                }

                mStageLesson2SubTitle.put("2,1", "Welcome");
                mStageLesson2SubTitle.put("2,2", "Family name");
                mStageLesson2SubTitle.put("2,3", "Have a good day");
                mStageLesson2SubTitle.put("2,4", "Where do you live?");

                mStageLesson2SubTitle.put("3,1", "More content in the next version");
//                mStageLesson2SubTitle.put("3,1", "How are you?");
//                mStageLesson2SubTitle.put("3,2", "Will you drink tea?");

                mStageLesson2SubTitle.put("4,1", "More content in the next version");
        }
        mStageNumOfLessons.clear();
        mStageNumOfLessons.add(10);
        mStageNumOfLessons.add(4);
        mStageNumOfLessons.add(1);
        mStageNumOfLessons.add(1);
//        mStageNumOfLessons.add(0);
//        mStageNumOfLessons.add(0);
//        mStageNumOfLessons.add(0);
        GlobalSettings.inst().setStageNumOfLessons(mStageNumOfLessons);
    }

    public List<Integer> getStageNumbers() {
        return mStageNumbers;
    }

    public List<String> getStageTitles() {
        return mStageTitles;
    }

    public Map<String, String> getStageLesson2SubTitle() {
        return mStageLesson2SubTitle;
    }

    public int getNumOfStages() {
        return mStageNumOfLessons.size();
    }

    public List<Integer> getStageNumOfLessons() {
        return mStageNumOfLessons;
    }

    public void populateDatabases() {
        final int dictionaryCount = mDictionaryRepository.getCount();
        final int questionDataCount = mQuestionDataRepository.getCountOfCourse(1) + mQuestionDataRepository.getCountOfCourse(2);
        final int questionInfoCount = mQuestionInfoRepository.getCountOfCourse(1) + mQuestionInfoRepository.getCountOfCourse(2);
        final int lessonInfoCount = mLessonInfoRepository.getCountOfCourse(1) + mLessonInfoRepository.getCountOfCourse(2);
        Log.d(TAG, "Existing item count in mDictionaryRepository: " + dictionaryCount);
        Log.d(TAG, "Existing item count in mQuestionDataRepository: " + questionDataCount);
        Log.d(TAG, "Existing item count in mQuestionInfoRepository: " + questionInfoCount);
        Log.d(TAG, "Existing item count in mLessonInfoRepository: " + lessonInfoCount);
        final int totalDbItemsCount = dictionaryCount + questionDataCount + questionInfoCount + lessonInfoCount;
        Log.d(TAG, "Actually read totalDbItemsCount: " + totalDbItemsCount);
        // TODO update both counts below (small and regular/full) if input text files get updated
//        if (GlobalSettings.inst().getSmallDb() && totalDbItemsCount == 630) {
        if (GlobalSettings.inst().getSmallDb() && totalDbItemsCount == 1050) {
            // small DB
            return;
//        } else if (totalDbItemsCount == 4530) {
//        } else if (totalDbItemsCount == 4530) {
        } else if (totalDbItemsCount == 7455) {
            // full DB
            return;
        }
        Log.d(TAG, "Deleting all content in DBs");
        mDictionaryRepository.deleteAllEntries();
        mQuestionDataRepository.deleteAllEntriesOfCourse(1);
        mQuestionDataRepository.deleteAllEntriesOfCourse(2);
        mQuestionInfoRepository.deleteAllEntriesOfCourse(1);
        mQuestionInfoRepository.deleteAllEntriesOfCourse(2);
        mLessonInfoRepository.deleteAllEntriesOfCourse(1);
        mLessonInfoRepository.deleteAllEntriesOfCourse(2);
//        mQuestionsLoaderAsyncTask0 = new QuestionsLoaderAsyncTask(mMainViewModel);
//        final String fileName0 = "text/game0_words.csv";
//        Log.d(TAG, "loading data from: " + fileName0);
//        mQuestionsLoaderAsyncTask0.execute(new android.util.Pair<>("0", fileName0));

        // load course #1 (Cyrillic) files:
        loadLesson(1, 1, 1, "text/course_1/stage1_lesson1.csv");
        loadLesson(1, 1, 2, "text/course_1/stage1_lesson2.csv");
        loadLesson(1, 1, 3, "text/course_1/stage1_lesson3.csv");
        loadLesson(1, 1, 4, "text/course_1/stage1_lesson4.csv");
        loadLesson(1, 1, 5, "text/course_1/stage1_lesson5.csv");
        loadLesson(1, 1, 6, "text/course_1/stage1_lesson6.csv");
        loadLesson(1, 1, 7, "text/course_1/stage1_lesson7.csv");
        loadLesson(1, 1, 8, "text/course_1/stage1_lesson8.csv");
        loadLesson(1, 1, 9, "text/course_1/stage1_lesson9.csv");
        loadLesson(1, 1, 10, "text/course_1/stage1_lesson10.csv");

        loadLesson(1, 2, 1, "text/course_1/stage2_lesson1.csv");
        loadLesson(1, 2, 2, "text/course_1/stage2_lesson2.csv");
        loadLesson(1, 2, 3, "text/course_1/stage2_lesson3.csv");
        loadLesson(1, 2, 4, "text/course_1/stage2_lesson4.csv");

//        loadLesson(1, 3, 1, "text/course_1/stage3_lesson1.csv");
        loadLesson(1, 3, 1, "text/course_1/stage3_lesson1_demo.csv");

        loadLesson(1, 4, 1, "text/course_1/stage4_lesson1_demo.csv");
//        loadLesson(1, 3, 2, "text/course_1/stage3_lesson2.csv");
//        loadLesson(1, 3, 2, "text/course_1/stage3_lesson2.csv");

//        loadLesson(1, 3, 2, "text/course_1/question_type_8_example.csv");
//        loadLesson(1, 3, 3, "text/course_1/stage3_lesson3_test.csv");
//        loadLesson(2, 3, 1, "text/course_1/stage3_lesson2.csv");

        // load course #2 (Latin) files:
        loadLesson(2, 1, 1, "text/course_2/stage1_lesson1.csv");
        loadLesson(2, 1, 2, "text/course_2/stage1_lesson2.csv");
        loadLesson(2, 1, 3, "text/course_2/stage1_lesson3.csv");
        loadLesson(2, 1, 4, "text/course_2/stage1_lesson4.csv");
        loadLesson(2, 1, 5, "text/course_2/stage1_lesson5.csv");
        loadLesson(2, 1, 6, "text/course_2/stage1_lesson6.csv");
        loadLesson(2, 1, 7, "text/course_2/stage1_lesson7.csv");
        loadLesson(2, 1, 8, "text/course_2/stage1_lesson8.csv");
        loadLesson(2, 1, 9, "text/course_2/stage1_lesson9.csv");
        loadLesson(2, 1, 10, "text/course_2/stage1_lesson10.csv");
//        loadLesson(2, 1, 7, "text/course_2/stage1_lesson7.csv");

        loadLesson(2, 2, 1, "text/course_2/stage2_lesson1.csv");
        loadLesson(2, 2, 2, "text/course_2/stage2_lesson2.csv");
        loadLesson(2, 2, 3, "text/course_2/stage2_lesson3.csv");
        loadLesson(2, 2, 4, "text/course_2/stage2_lesson4.csv");

//        loadLesson(2, 3, 1, "text/course_2/stage3_lesson1.csv");
        loadLesson(2, 3, 1, "text/course_2/stage3_lesson1_demo.csv");

        loadLesson(2, 4, 1, "text/course_2/stage4_lesson1_demo.csv");
//        loadLesson(2, 3, 2, "text/course_2/stage3_lesson2.csv");
//        loadLesson(2, 3, 2, "text/course_2/stage3_lesson2.csv");
//        loadLesson(2, 3, 2, "text/course_2/question_type_8_example.csv");
//        loadLesson(2, 3, 3, "text/course_2/stage3_lesson3_test.csv");
//        loadLesson(2, 3, 1, "text/course_2/stage3_lesson2.csv");

        // Update LessonInfoRepository: set the first lesson in the first stage to "activated" if it is not
        // do this for both courses (1 & 2)
        LessonInfo firstLessonInfo = mLessonInfoRepository.getLessonInfoByIdOfCourse(1, 1, 1);
        if (!firstLessonInfo.isActivated()) {
            firstLessonInfo.setActivated(true);
            mLessonInfoRepository.update(firstLessonInfo);
        }
        firstLessonInfo = mLessonInfoRepository.getLessonInfoByIdOfCourse(2, 1, 1);
        if (!firstLessonInfo.isActivated()) {
            firstLessonInfo.setActivated(true);
            mLessonInfoRepository.update(firstLessonInfo);
        }
    }

    private void loadLesson(int courseId, int stageNumber, int lessonNumber, String fileName) {
        Pair<Integer, Integer> lessonId = new Pair<Integer, Integer>(stageNumber, lessonNumber);
        final LessonLoader lessonLoader = new LessonLoader(courseId, mQuestionDataRepository, mQuestionInfoRepository, mLessonInfoRepository, mDictionaryRepository);
        try {
            Log.d(TAG, "Loading lesson file: " + fileName);
            final InputStream inputStreamLesson = mApplication.getAssets().open(fileName);
            lessonLoader.loadLesson(lessonId, inputStreamLesson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetDatabases() {
        mDictionaryRepository.deleteAllEntries();
        mQuestionDataRepository.deleteAllEntriesOfCourse(1);
        mQuestionDataRepository.deleteAllEntriesOfCourse(2);
        mQuestionInfoRepository.deleteAllEntriesOfCourse(1);
        mQuestionInfoRepository.deleteAllEntriesOfCourse(2);
        mLessonInfoRepository.deleteAllEntriesOfCourse(1);
        mLessonInfoRepository.deleteAllEntriesOfCourse(2);
        populateDatabases();
    }

    public LiveData<List<DictionaryEntry>> getDictionaryEntries() {
        return mDictionaryRepository.getAllEntries();
    }

    public LiveData<List<QuestionInfo>> getQuestionInfoEntries() {
        return mQuestionInfoRepository.getAllEntriesOfCourse(GlobalSettings.inst().getCourseId());
    }

    public LiveData<List<LessonInfo>> getLessonInfoEntries() {
        return mLessonInfoRepository.getAllEntriesOfCourse(GlobalSettings.inst().getCourseId());
    }

    public void refreshQuestionInfos(List<QuestionInfo> questionInfos) {
        Log.d(TAG, "refreshQuestionInfos()");
        mCourseTracker.refreshQuestionInfos(questionInfos);
    }

    public Map<String, Integer> getStageLessonExercise2IsDoneMap() {
        return mCourseTracker.getStageLessonExercise2IsDoneMap();
    }

    public Map<String, Integer> getStageLesson2NumStarsMap() {
        return mCourseTracker.getStageLesson2NumStarsMap();
    }

    public Map<String, Boolean> getStageLesson2IsActivatedMap() {
        return mCourseTracker.getStageLesson2IsActivatedMap();
    }

    public void refreshLessonInfos(List<LessonInfo> lessonInfos) {
        Log.d(TAG, "refreshLessonInfos()");
        if (lessonInfos == null) {
            Log.e(TAG, "-B- BUG: refreshLessonInfos(): lessonInfos == null");
            return;
        }
        mCourseTracker.refreshLessonInfos(lessonInfos);
    }

//    public void refreshLessonInfo(LessonInfo lessonInfo) {
//        Log.d(TAG, "refreshLessonInfo()");
//        if (lessonInfo == null) {
//            Log.e(TAG, "-B- BUG: refreshLessonInfo(): lessonInfo == null");
//            return;
//        }
//        mCourseTracker.refreshLessonInfo(lessonInfo);
//    }

    public void resetLesson(Pair<Integer, Integer> lessonIdToReset) {
        Log.d(TAG, "YYY21a resetLesson()");
        List<QuestionInfo> questionInfos = mQuestionInfoRepository.getAllEntriesOfLessonOfCourse(GlobalSettings.inst().getCourseId(), lessonIdToReset);
        if (questionInfos == null) {
            Log.d(TAG, "YYY21b resetLesson()");
            Log.e(TAG, "-B- BUG: resetLesson(): " + lessonIdToReset.toString() + " has no lessons!");
        } else {
            Log.d(TAG, "YYY21c resetLesson()");
            Log.d(TAG, "YYY21 questionInfos for lesson #" + lessonIdToReset.toString() + ": NOT null");
            for (QuestionInfo questionInfo : questionInfos) {
                questionInfo.setDone(false);
                Log.d(TAG, "YYY-21b");
                mQuestionInfoRepository.update(questionInfo);
                Log.d(TAG, "YYY-22d");
            }
            refreshQuestionInfos(questionInfos);
        }
        Log.d(TAG, "YYY21d resetLesson()");
        LessonInfo lessonInfo = mLessonInfoRepository.getLessonInfoByIdOfCourse(GlobalSettings.inst().getCourseId(), lessonIdToReset.first, lessonIdToReset.second);
        lessonInfo.setDone(false);
        lessonInfo.setExercise1IsDone(false);
        lessonInfo.setExercise2IsDone(false);
        lessonInfo.setExercise3IsDone(false);
        lessonInfo.setExercise4IsDone(false);
        lessonInfo.setExercise5IsDone(false);
        lessonInfo.setExercise6IsDone(false);
        mLessonInfoRepository.update(lessonInfo);
//        mCourseTracker.refreshLessonInfos(lessonInfos);
        mCourseTracker.refreshLessonInfo(lessonInfo);
    }

//    public int getNumOfStages() {
//        return mCourseTracker.getNumOfStages();
//    }

//    public int getStageNumOfLessons(int stage) {
//        return mCourseTracker.getStageNumOfLessons(stage);
//    }

//    public Set<Integer> getStageLessons(int stage) {
//        return mCourseTracker.getStageLessons(stage);
//    }

//    public int getLessonNumOfQuestions(int lesson) {
//        return mCourseTracker.getLessonNumOfQuestions(lesson);
//    }

    public boolean getLessonHasExercises(Pair<Integer, Integer> lessonId) {
        return mCourseTracker.getLessonHasExercises(lessonId);
    }

    public List<Integer> getLessonExercises(Pair<Integer, Integer> lessonId) {
        return mCourseTracker.getLessonExercises(lessonId);
    }

//    public int getLessonNumOfStars(Pair<Integer, Integer> lessonId) {
//        return mCourseTracker.getLessonNumOfStars(lessonId);
//    }

    public int getTotalNumOfStars() {
        return mCourseTracker.getTotalNumOfStars();
    }

    public boolean getExerciseIsDone(Pair<Integer, Integer> lessonId, int exercise) {
        return mCourseTracker.getExerciseIsDone(lessonId, exercise);
    }

    public boolean getLessonIsDone(Pair<Integer, Integer> lessonId) {
        boolean allDone = true;
        final List<Integer> exercises = mCourseTracker.getLessonExercises(lessonId);
        if (exercises == null) {
            return false;
        }
        for (int exerciseNumber : exercises) {
            if (!mCourseTracker.getExerciseIsDone(lessonId, exerciseNumber)) {
                allDone = false;
                break;
            }
        }
        return allDone;
    }

    public void onFinish() {
        SoundPlayer.deleteInstance();
    }

//    public void insertDictionaryEntry(DictionaryEntry dictionaryEntry) {
//        mDictionaryRepository.insert(dictionaryEntry);
//    }

    private final Application mApplication;
    private final DictionaryRepository mDictionaryRepository;
    private final QuestionDataRepository mQuestionDataRepository;
    private final QuestionInfoRepository mQuestionInfoRepository;
    private final LessonInfoRepository mLessonInfoRepository;
    private final CourseTracker mCourseTracker = new CourseTracker();

    private GlobalSettings.Language mLanguage;
    private final List<Integer> mStageNumbers;
    private final List<String> mStageTitles;
    private final Map<String, String> mStageLesson2SubTitle;
    private final List<Integer> mStageNumOfLessons;

    private static final String TAG = MainViewModel.class.getSimpleName();
}

// a á b c ç ć d e é f g ǵ ğ h ḣ ı i j k ḱ l ĺ m n o ö p ṕ q r s ş ś š t ṫ u ü w x y z ź
// A Á B C Ç Ć D E É F G Ǵ Ğ H Ḣ I İ J K Ḱ L Ĺ M N O Ö P Ṕ Q R S Ş Ś Š T Ṫ U Ü W X Y Z Ź
// áçćéǵğḣıḱĺöṕşśšṫüź
// ÁÇĆÉǴĞḢİḰĹÖṔŞŚŠṪÜŹ
//Adıǵe Адыгэ
