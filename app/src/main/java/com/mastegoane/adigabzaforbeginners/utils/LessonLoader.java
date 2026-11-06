package com.mastegoane.adigabzaforbeginners.utils;

import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import androidx.collection.ArrayMap;
import androidx.core.util.Pair;
import com.mastegoane.adigabzaforbeginners.databases.DictionaryEntry;
import com.mastegoane.adigabzaforbeginners.databases.DictionaryRepository;
import com.mastegoane.adigabzaforbeginners.databases.QuestionData;
import com.mastegoane.adigabzaforbeginners.databases.LessonInfo;
import com.mastegoane.adigabzaforbeginners.databases.LessonInfoRepository;
import com.mastegoane.adigabzaforbeginners.databases.QuestionDataRepository;
import com.mastegoane.adigabzaforbeginners.databases.QuestionInfo;
import com.mastegoane.adigabzaforbeginners.databases.QuestionInfoRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD.LFFO_Answer1;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD.LFFO_Answer2;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD.LFFO_Answer3;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD.LFFO_Answer4;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD.LFFO_AnswerAudio1;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD.LFFO_AnswerAudio2;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD.LFFO_AnswerAudio3;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD.LFFO_AnswerAudio4;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD.LFFO_ExerciseNumber;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD.LFFO_QuestionAudio;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD.LFFO_QuestionAudioFile;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD.LFFO_QuestionImage;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD.LFFO_QuestionNumber;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD.LFFO_RightAnswer;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD.LFFO_Type;

import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_Answer1;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_Answer1Arabic;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_Answer1English;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_Answer1Turkish;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_Answer2;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_Answer2Arabic;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_Answer2English;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_Answer2Turkish;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_Answer3;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_Answer3Arabic;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_Answer3English;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_Answer3Turkish;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_Answer4;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_AnswerAudio1;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_AnswerAudio2;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_AnswerAudio3;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_AnswerAudio4;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_ExerciseNumber;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_QuestionAudio;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_QuestionAudioFile;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_QuestionImage;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_QuestionNumber;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_QuestionSubText;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_QuestionSubTextArabic;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_QuestionSubTextEnglish;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_QuestionSubTextTurkish;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_RightAnswer;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS_OLD2.LFFN_Type;

import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_Answer1;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_AnswerArabic1;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_AnswerEnglish1;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_AnswerTurkish1;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_Answer2;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_AnswerArabic2;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_AnswerEnglish2;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_AnswerTurkish2;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_Answer3;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_AnswerArabic3;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_AnswerEnglish3;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_AnswerTurkish3;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_Answer4;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_AnswerArabic4;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_AnswerEnglish4;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_AnswerTurkish4;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_Answer5;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_AnswerArabic5;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_AnswerEnglish5;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_AnswerTurkish5;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_Max;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_AnswerAudio1;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_AnswerAudio2;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_AnswerAudio3;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_AnswerAudio4;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_AnswerAudio5;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_ExerciseNumber;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_QuestionAudio;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_QuestionAudioFile;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_QuestionImage;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_QuestionNumber;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_QuestionSubText;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_QuestionSubTextArabic;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_QuestionSubTextEnglish;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_QuestionSubTextTurkish;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_RightAnswer1;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_RightAnswer2;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_RightAnswer3;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_RightAnswer4;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_RightAnswer5;
import static com.mastegoane.adigabzaforbeginners.utils.LessonLoader.LESSON_FILE_FIELDS.LFF_Type;

public class LessonLoader {
    public LessonLoader(int courseId, QuestionDataRepository questionDataRepository, QuestionInfoRepository questionInfoRepository, LessonInfoRepository lessonInfoRepository, DictionaryRepository dictionaryRepository) {
        mCourseId = courseId;
        mQuestionDataRepository = questionDataRepository;
        mQuestionInfoRepository = questionInfoRepository;
        mLessonInfoRepository = lessonInfoRepository;
        mDictionaryRepository = dictionaryRepository;
        mCyrillic2Latin = new ArrayMap<>();
    }

    public enum LESSON_FILE_FIELDS_OLD {
        LFFO_Type(0),
        LFFO_ExerciseNumber(1),
        LFFO_QuestionNumber(2),

        LFFO_QuestionAudio(3),
        LFFO_QuestionAudioFile(4),
        LFFO_QuestionImage(5),
        LFFO_RightAnswer(6),
        LFFO_Answer1(7),
        LFFO_Answer2(8),
        LFFO_Answer3(9),
        LFFO_Answer4(10),
        LFFO_AnswerAudio1(11),
        LFFO_AnswerAudio2(12),
        LFFO_AnswerAudio3(13),
        LFFO_AnswerAudio4(14);
        private final int code;
        LESSON_FILE_FIELDS_OLD(int code) {this.code = code;}
        public int code() { return this.code;}
    }
    public enum LESSON_FILE_FIELDS_OLD2 {
        LFFN_Type(0),
        LFFN_ExerciseNumber(1),
        LFFN_QuestionNumber(2),
        LFFN_QuestionSubText(3),
        LFFN_QuestionSubTextEnglish(4),
        LFFN_QuestionSubTextArabic(5),
        LFFN_QuestionSubTextTurkish(6),
        LFFN_QuestionAudio(7),
        LFFN_QuestionAudioFile(8),
        LFFN_QuestionImage(9),
        LFFN_RightAnswer(10),
        LFFN_Answer1(11),
        LFFN_Answer2(12),
        LFFN_Answer3(13),
        LFFN_Answer4(14),
        LFFN_AnswerAudio1(15),
        LFFN_AnswerAudio2(16),
        LFFN_AnswerAudio3(17),
        LFFN_AnswerAudio4(18),
        LFFN_Answer1English(19),
        LFFN_Answer2English(20),
        LFFN_Answer3English(21),
        LFFN_Answer1Arabic(22),
        LFFN_Answer2Arabic(23),
        LFFN_Answer3Arabic(24),
        LFFN_Answer1Turkish(25),
        LFFN_Answer2Turkish(26),
        LFFN_Answer3Turkish(27);
        private final int code;
        LESSON_FILE_FIELDS_OLD2(int code) {this.code = code;}
        public int code() { return this.code;}
    }

    public enum LESSON_FILE_FIELDS {
        LFF_Type(0),
        LFF_ExerciseNumber(1),
        LFF_QuestionNumber(2),
        LFF_QuestionSubText(3),
        LFF_QuestionSubTextEnglish(4),
        LFF_QuestionSubTextArabic(5),
        LFF_QuestionSubTextTurkish(6),
        LFF_QuestionAudio(7),
        LFF_QuestionAudioFile(8),
        LFF_QuestionImage(9),
        LFF_RightAnswer1(10),
        LFF_RightAnswer2(11),
        LFF_RightAnswer3(12),
        LFF_RightAnswer4(13),
        LFF_RightAnswer5(14),
        LFF_Answer1(15),
        LFF_Answer2(16),
        LFF_Answer3(17),
        LFF_Answer4(18),
        LFF_Answer5(19),
        LFF_AnswerAudio1(20),
        LFF_AnswerAudio2(21),
        LFF_AnswerAudio3(22),
        LFF_AnswerAudio4(23),
        LFF_AnswerAudio5(24),
        LFF_AnswerEnglish1(25),
        LFF_AnswerEnglish2(26),
        LFF_AnswerEnglish3(27),
        LFF_AnswerEnglish4(28),
        LFF_AnswerEnglish5(29),
        LFF_AnswerArabic1(30),
        LFF_AnswerArabic2(31),
        LFF_AnswerArabic3(32),
        LFF_AnswerArabic4(33),
        LFF_AnswerArabic5(34),
        LFF_AnswerTurkish1(35),
        LFF_AnswerTurkish2(36),
        LFF_AnswerTurkish3(37),
        LFF_AnswerTurkish4(38),
        LFF_AnswerTurkish5(39),
        LFF_Max(40);
        private final int code;
        LESSON_FILE_FIELDS(int code) {this.code = code;}
        public int code() { return this.code;}
    }

    public void loadLesson(Pair<Integer, Integer> lessonId, InputStream inputStreamLesson) {
        // TODO perform this in a separate async thread
        Log.d(TAG, "loading lesson: courseId, stage, lesson: " + mCourseId + ", " + lessonId.first + ", " + lessonId.second);
        BufferedReader readerLesson = new BufferedReader(new InputStreamReader(inputStreamLesson));
        try {
            // Read lesson data
            Map<String, Integer> exercise2NumOfLoadedQuestions = new ArrayMap<>();
            Map<Pair<Integer, Integer>, Integer> lesson2NumOfExercises = new ArrayMap<>();
            Set<String> encounteredExerciseSet = new ArraySet<>();
            String line;
            List<QuestionData> questionDataList = new ArrayList<>();
            List<QuestionInfo> questionInfoList = new ArrayList<>();
            Set<String> cyrillicSet = new ArraySet<>();
            Map<String, String> cyrillic2Sound = new ArrayMap<>();
            Map<String, String> cyrillic2Id = new ArrayMap<>();

            while((line = readerLesson.readLine()) != null) {
                line = line.trim();
                String[] tokens = TextUtils.split(line, "\\|");
                if (tokens.length == 0) {
                    // skip empty line
                    continue;
                }
                if (tokens[0].startsWith("#")) {
                    // skip a comment line
                    continue;
                }

                int expectedNumOfColumns = LFF_Max.ordinal();
                if (tokens.length != expectedNumOfColumns) {
                    Log.e(TAG, "loadLesson(): (lesson #" + lessonId.toString() + ") unexpected line (wrong number of columns - expected: " + expectedNumOfColumns + "): " + line);
                    continue;
                }

                String questionType;
                String exerciseNumber;
                String questionNumber;

                String questionSubText = "";
                String questionSubTextEnglish = "";
                String questionSubTextArabic = "";
                String questionSubTextTurkish = "";

                String questionAudio;
                String questionAudioFile;
                String questionImage;
                String rightAnswer1;
                String rightAnswer2;
                String rightAnswer3;
                String rightAnswer4;
                String rightAnswer5;

                String answer1;
                String answer2;
                String answer3;
                String answer4;
                String answer5;

                String answerAudio1;
                String answerAudio2;
                String answerAudio3;
                String answerAudio4;
                String answerAudio5;

                String answerEnglish1 = "";
                String answerEnglish2 = "";
                String answerEnglish3 = "";
                String answerEnglish4 = "";
                String answerEnglish5 = "";
                String answerArabic1 = "";
                String answerArabic2 = "";
                String answerArabic3 = "";
                String answerArabic4 = "";
                String answerArabic5 = "";
                String answerTurkish1 = "";
                String answerTurkish2 = "";
                String answerTurkish3 = "";
                String answerTurkish4 = "";
                String answerTurkish5 = "";

                questionType = tokens[LFF_Type.code()].trim();
                exerciseNumber = tokens[LFF_ExerciseNumber.code()].trim();
                questionNumber = tokens[LFF_QuestionNumber.code()].trim();

                questionSubText = tokens[LFF_QuestionSubText.code()].trim();
                questionSubTextEnglish = tokens[LFF_QuestionSubTextEnglish.code()].trim();
                questionSubTextArabic = tokens[LFF_QuestionSubTextArabic.code()].trim();
                questionSubTextTurkish = tokens[LFF_QuestionSubTextTurkish.code()].trim();

                questionAudio = tokens[LFF_QuestionAudio.code()].trim();
                questionAudioFile = tokens[LFF_QuestionAudioFile.code()].trim();
                questionImage = tokens[LFF_QuestionImage.code()].trim();
                rightAnswer1 = tokens[LFF_RightAnswer1.code()].trim();
                rightAnswer2 = tokens[LFF_RightAnswer2.code()].trim();
                rightAnswer3 = tokens[LFF_RightAnswer3.code()].trim();
                rightAnswer4 = tokens[LFF_RightAnswer4.code()].trim();
                rightAnswer5 = tokens[LFF_RightAnswer5.code()].trim();

                answer1 = tokens[LFF_Answer1.code()].trim();
                answer2 = tokens[LFF_Answer2.code()].trim();
                answer3 = tokens[LFF_Answer3.code()].trim();
                answer4 = tokens[LFF_Answer4.code()].trim();
                answer5 = tokens[LFF_Answer5.code()].trim();

                answerAudio1 = tokens[LFF_AnswerAudio1.code()].trim();
                answerAudio2 = tokens[LFF_AnswerAudio2.code()].trim();
                answerAudio3 = tokens[LFF_AnswerAudio3.code()].trim();
                answerAudio4 = tokens[LFF_AnswerAudio4.code()].trim();
                answerAudio5 = tokens[LFF_AnswerAudio5.code()].trim();

                answerEnglish1 = tokens[LFF_AnswerEnglish1.code()].trim();
                answerEnglish2 = tokens[LFF_AnswerEnglish2.code()].trim();
                answerEnglish3 = tokens[LFF_AnswerEnglish3.code()].trim();
                answerEnglish4 = tokens[LFF_AnswerEnglish4.code()].trim();
                answerEnglish5 = tokens[LFF_AnswerEnglish5.code()].trim();
                answerArabic1 = tokens[LFF_AnswerArabic1.code()].trim();
                answerArabic2 = tokens[LFF_AnswerArabic2.code()].trim();
                answerArabic3 = tokens[LFF_AnswerArabic3.code()].trim();
                answerArabic4 = tokens[LFF_AnswerArabic4.code()].trim();
                answerArabic5 = tokens[LFF_AnswerArabic5.code()].trim();
                answerTurkish1 = tokens[LFF_AnswerTurkish1.code()].trim();
                answerTurkish2 = tokens[LFF_AnswerTurkish2.code()].trim();
                answerTurkish3 = tokens[LFF_AnswerTurkish3.code()].trim();
                answerTurkish4 = tokens[LFF_AnswerTurkish4.code()].trim();
                answerTurkish5 = tokens[LFF_AnswerTurkish5.code()].trim();

                // Insert lesson data to repository
                final int questionTypeInt = Integer.parseInt(questionType);
                // TODO currently skipping type 9 - will not be supported in first production release
                if (questionTypeInt == 9) {
                    continue;
                }
                final int exerciseNumberInt = Integer.parseInt(exerciseNumber);
                final int questionNumberInt = Integer.parseInt(questionNumber);
                int rightAnswerInt1 = 0;
                if (!rightAnswer1.isEmpty()) {
                    rightAnswerInt1 = Integer.parseInt(rightAnswer1);
                }
                int rightAnswerInt2 = 0;
                if (!rightAnswer2.isEmpty()) {
                    rightAnswerInt2 = Integer.parseInt(rightAnswer2);
                }
                int rightAnswerInt3 = 0;
                if (!rightAnswer3.isEmpty()) {
                    rightAnswerInt3 = Integer.parseInt(rightAnswer3);
                }
                int rightAnswerInt4 = 0;
                if (!rightAnswer4.isEmpty()) {
                    rightAnswerInt4 = Integer.parseInt(rightAnswer4);
                }
                int rightAnswerInt5 = 0;
                if (!rightAnswer5.isEmpty()) {
                    rightAnswerInt5 = Integer.parseInt(rightAnswer5);
                }

                final String exerciseKey = lessonId.toString() + "," + exerciseNumberInt;
                if (GlobalSettings.inst().getSmallDb()) {
                    Integer numOfLoadedQuestions = exercise2NumOfLoadedQuestions.get(exerciseKey);
                    if (numOfLoadedQuestions == null) {
                        numOfLoadedQuestions = 1;
                    } else {
                        ++numOfLoadedQuestions;
                    }
                    if (numOfLoadedQuestions > GlobalSettings.inst().getMaxQuestionsPerExercise()) {
                        continue;
                    }
                    exercise2NumOfLoadedQuestions.put(exerciseKey, numOfLoadedQuestions);
                }

                if (!encounteredExerciseSet.contains(exerciseKey)) {
                    encounteredExerciseSet.add(exerciseKey);
                    Integer numOfExercises = lesson2NumOfExercises.get(lessonId);
                    if (numOfExercises == null) {
                        lesson2NumOfExercises.put(lessonId, 1);
                    } else {
                        lesson2NumOfExercises.put(lessonId, numOfExercises + 1);
                    }
                }

                QuestionData questionData = new QuestionData(mCourseId, lessonId.first, lessonId.second, questionTypeInt, exerciseNumberInt, questionNumberInt,
                        questionAudio, questionAudioFile, questionImage,
                        rightAnswerInt1, rightAnswerInt2, rightAnswerInt3, rightAnswerInt4, rightAnswerInt5,
                        answer1, answer2, answer3, answer4, answer5,
                        answerAudio1, answerAudio2, answerAudio3, answerAudio4, answerAudio5,
                        questionSubText, questionSubTextEnglish, questionSubTextArabic, questionSubTextTurkish,
                        answerEnglish1, answerArabic1, answerTurkish1,
                        answerEnglish2, answerArabic2, answerTurkish2,
                        answerEnglish3, answerArabic3, answerTurkish3,
                        answerEnglish4, answerArabic4, answerTurkish4,
                        answerEnglish5, answerArabic5, answerTurkish5);

                if (mCourseId == 1) {
                    // TODO next is only for converting Cyrillic to Latin when content is updated - disable in production
                    // next is for dev purposes for initial conversion to Latin
//                    printLatinVersion(questionData);
                }
                questionDataList.add(questionData);
                QuestionInfo questionInfo = new QuestionInfo(mCourseId, lessonId.first, lessonId.second, exerciseNumberInt, questionNumberInt, false, false, false);
                questionInfoList.add(questionInfo);

                if (mCourseId == 1) {
                    final String fullQuestionIdStr = "s:" + lessonId.first + " l:" + lessonId.second + " e:" + exerciseNumber + " q:" + questionNumber;
                    switch (questionTypeInt) {
                        case 1:
                        case 3:
                            cyrillicSet.add(answer1);
                            cyrillicSet.add(answer2);
                            cyrillicSet.add(answer3);
                            cyrillicSet.add(answer4);
                            cyrillic2Id.put(answer1, fullQuestionIdStr);
                            cyrillic2Id.put(answer2, fullQuestionIdStr);
                            cyrillic2Id.put(answer3, fullQuestionIdStr);
                            cyrillic2Id.put(answer4, fullQuestionIdStr);
                            break;
                        case 2:
                            cyrillicSet.add(answer1);
                            cyrillicSet.add(answer2);
                            cyrillicSet.add(answer3);
                            cyrillicSet.add(answer4);
                            cyrillic2Sound.put(answer1, answerAudio1);
                            cyrillic2Sound.put(answer2, answerAudio2);
                            cyrillic2Sound.put(answer3, answerAudio3);
                            cyrillic2Sound.put(answer4, answerAudio4);
                            cyrillic2Id.put(answer1, fullQuestionIdStr);
                            cyrillic2Id.put(answer2, fullQuestionIdStr);
                            cyrillic2Id.put(answer3, fullQuestionIdStr);
                            cyrillic2Id.put(answer4, fullQuestionIdStr);
                            break;
                        case 4:
                        case 5:
                            cyrillicSet.add(answer1);
                            cyrillicSet.add(answer2);
                            cyrillicSet.add(answer3);
                            cyrillic2Id.put(answer1, fullQuestionIdStr);
                            cyrillic2Id.put(answer2, fullQuestionIdStr);
                            cyrillic2Id.put(answer3, fullQuestionIdStr);
                            break;
                        case 6:
                            cyrillicSet.add(questionSubText);
                            cyrillic2Id.put(questionSubText, fullQuestionIdStr);
                            break;
                        case 7:
                        case 8:
                            cyrillicSet.add(questionSubText);
                            cyrillicSet.add(answer1);
                            cyrillicSet.add(answer2);
                            cyrillicSet.add(answer3);
                            cyrillic2Id.put(questionSubText, fullQuestionIdStr);
                            cyrillic2Id.put(answer1, fullQuestionIdStr);
                            cyrillic2Id.put(answer2, fullQuestionIdStr);
                            cyrillic2Id.put(answer3, fullQuestionIdStr);
                            break;
                        case 9:
                            cyrillicSet.add(answer1);
                            cyrillicSet.add(answer2);
                            cyrillicSet.add(answer3);
                            cyrillicSet.add(answer4);
                            cyrillicSet.add(answer5);
                            cyrillic2Id.put(answer1, fullQuestionIdStr);
                            cyrillic2Id.put(answer2, fullQuestionIdStr);
                            cyrillic2Id.put(answer3, fullQuestionIdStr);
                            cyrillic2Id.put(answer4, fullQuestionIdStr);
                            cyrillic2Id.put(answer5, fullQuestionIdStr);
                            break;
                        default:
                            Log.d(TAG, "-B- BUG: Question type not supported: " + questionTypeInt + " Line: " + line);
                            break;
                    }
                }
            }
            mQuestionDataRepository.insertList(questionDataList);
            mQuestionInfoRepository.insertList(questionInfoList);
            List<LessonInfo> lessonInfos = new ArrayList<>();
            for (Pair<Integer, Integer> currentLessonId : lesson2NumOfExercises.keySet()) {
                Integer numOfExercises = lesson2NumOfExercises.get(currentLessonId);
                LessonInfo lessonInfo = new LessonInfo(mCourseId, currentLessonId.first, currentLessonId.second, "N/A",
                        false, false, false,
                        0, numOfExercises, false, false, false,
                        false, false, false);
                lessonInfos.add(lessonInfo);
            }
            mLessonInfoRepository.insertList(lessonInfos);

            // TODO for generating Cyrillic-to-Latin mapping for reviewing and fixing typos in Latin.
//            printCyrillic2LatinMap();

            // populate dictionary only from course #1 (Cyrillic)
            if (mCourseId == 1) {
                // Insert data into dictionary DB
                // Insert Cyrillic2Latin and Cyrillic2Sound data.
                // Assumptions:
                // 1) Every Cyrillic word has a Latin word.
                // 2) Sound may be missing for some words.
                List<DictionaryEntry> dictionaryEntries = new ArrayList<>();
                for (String cyrillic : cyrillicSet) {
                    final String fullId = cyrillic2Id.get(cyrillic);
                    String sound = cyrillic2Sound.get(cyrillic);
                    if (sound == null) {
                        sound = "";
                    }
                    // not providing Latin conversion here into the DB - Cyrillic-to-Latin will be done on-the-fly where ever needed
                    final DictionaryEntry dictionaryEntry = new DictionaryEntry(fullId, cyrillic, "", sound);
                    dictionaryEntries.add(dictionaryEntry);
                }
                mDictionaryRepository.insertList(dictionaryEntries);
            }

            Log.d(TAG, "loadLesson(" + lessonId.toString() + ") total # of questions: " + questionDataList.size());
            readerLesson.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printLatinVersion(QuestionData questionData) {
        //#Type|ExerciseNumber|QuestionNumber|
        // QuestionSubText|QuestionSubTextEnglish|QuestionSubTextArabic|QuestionSubTextTurkish|
        // QuestionAudio|QuestionAudioFile|QuestionImage|
        // RightAnswer1|RightAnswer2|RightAnswer3|RightAnswer4|RightAnswer5|
        // Answer1|Answer2|Answer3|Answer4|Answer5|
        // AnswerAudio1|AnswerAudio2|AnswerAudio3|AnswerAudio4|AnswerAudio5|
        // AnswerEnglish1|AnswerEnglish2|AnswerEnglish3|AnswerEnglish4|AnswerEnglish5|
        // AnswerArabic1|AnswerArabic2|AnswerArabic3|AnswerArabic4|AnswerArabic5|
        // AnswerTurkish1|AnswerTurkish2|AnswerTurkish3|AnswerTurkish4|AnswerTurkish5

        Log.d(TAG, "Original Cyrillic:   "
                + questionData.getQuestionType() + "|" + questionData.getExerciseNumber() + "|" + questionData.getQuestionNumber() + "|"
                + questionData.getQuestionSubText() + "|" + questionData.getQuestionSubTextEnglish() + "|" + questionData.getQuestionSubTextArabic() + "|" + questionData.getQuestionSubTextTurkish() + "|"
                + questionData.getQuestionAudio() + "|" + questionData.getQuestionAudioFile() + "|" + questionData.getQuestionImage() + "|"
                + questionData.getRightAnswer1() + "|" + questionData.getRightAnswer2() + "|" + questionData.getRightAnswer3() + "|" + questionData.getRightAnswer4() + "|" + questionData.getRightAnswer5() + "|"
                + questionData.getAnswer1() + "|" + questionData.getAnswer2() + "|" + questionData.getAnswer3() + "|" + questionData.getAnswer4() + "|" + questionData.getAnswer5() + "|"
                + questionData.getAnswerAudio1() + "|" + questionData.getAnswerAudio2() + "|" + questionData.getAnswerAudio3() + "|" + questionData.getAnswerAudio4() + "|" + questionData.getAnswerAudio5() + "|"
                + questionData.getAnswerEnglish1() + "|" + questionData.getAnswerEnglish2() + "|" + questionData.getAnswerEnglish3() + "|" + questionData.getAnswerEnglish4() + "|" + questionData.getAnswerEnglish5() + "|"
                + questionData.getAnswerArabic1() + "|" + questionData.getAnswerArabic2() + "|" + questionData.getAnswerArabic3() + "|" + questionData.getAnswerArabic4() + "|" + questionData.getAnswerArabic5() + "|"
                + questionData.getAnswerTurkish1() + "|" + questionData.getAnswerTurkish2() + "|" + questionData.getAnswerTurkish3() + "|" + questionData.getAnswerTurkish4() + "|" + questionData.getAnswerTurkish5());

        final String questionSubText = AdigaCharacterUtils.inst().cyrillic2Latin(questionData.getQuestionSubText());
        final String answer1Latin = AdigaCharacterUtils.inst().cyrillic2Latin(questionData.getAnswer1());
        final String answer2Latin = AdigaCharacterUtils.inst().cyrillic2Latin(questionData.getAnswer2());
        final String answer3Latin = AdigaCharacterUtils.inst().cyrillic2Latin(questionData.getAnswer3());
        final String answer4Latin = AdigaCharacterUtils.inst().cyrillic2Latin(questionData.getAnswer4());
        final String answer5Latin = AdigaCharacterUtils.inst().cyrillic2Latin(questionData.getAnswer5());
        Log.d(TAG, "Latin from Cyrillic: "
                + questionData.getQuestionType() + "|" + questionData.getExerciseNumber() + "|" + questionData.getQuestionNumber() + "|"
                + questionSubText + "|" + questionData.getQuestionSubTextEnglish() + "|" + questionData.getQuestionSubTextArabic() + "|" + questionData.getQuestionSubTextTurkish() + "|"
                + questionData.getQuestionAudio() + "|" + questionData.getQuestionAudioFile() + "|" + questionData.getQuestionImage() + "|"
                + questionData.getRightAnswer1() + "|" + questionData.getRightAnswer2() + "|" + questionData.getRightAnswer3() + "|" + questionData.getRightAnswer4() + "|" + questionData.getRightAnswer5() + "|"
                + answer1Latin + "|" + answer2Latin + "|" + answer3Latin + "|" + answer4Latin + "|" + answer5Latin + "|"
                + questionData.getAnswerAudio1() + "|" + questionData.getAnswerAudio2() + "|" + questionData.getAnswerAudio3() + "|" + questionData.getAnswerAudio4() + "|" + questionData.getAnswerAudio5() + "|"
                + questionData.getAnswerEnglish1() + "|" + questionData.getAnswerEnglish2() + "|" + questionData.getAnswerEnglish3() + "|" + questionData.getAnswerEnglish4() + "|" + questionData.getAnswerEnglish5() + "|"
                + questionData.getAnswerArabic1() + "|" + questionData.getAnswerArabic2() + "|" + questionData.getAnswerArabic3() + "|" + questionData.getAnswerArabic4() + "|" + questionData.getAnswerArabic5() + "|"
                + questionData.getAnswerTurkish1() + "|" + questionData.getAnswerTurkish2() + "|" + questionData.getAnswerTurkish3() + "|" + questionData.getAnswerTurkish4() + "|" + questionData.getAnswerTurkish5());

        if (!questionSubText.isEmpty()) {
//            Log.d(TAG, "Cyrillic vs. Latin Q: " + questionData.getQuestionSubText() + "\t" + questionSubText);
            mCyrillic2Latin.put(questionData.getQuestionSubText(), questionSubText);
        }
        if (!answer1Latin.isEmpty()) {
//            Log.d(TAG, "Cyrillic vs. Latin 1: " + questionData.getAnswer1() + "\t" + answer1Latin);
            mCyrillic2Latin.put(questionData.getAnswer1(), answer1Latin);
        }
        if (!answer2Latin.isEmpty()) {
//            Log.d(TAG, "Cyrillic vs. Latin 2: " + questionData.getAnswer2() + "\t" + answer2Latin);
            mCyrillic2Latin.put(questionData.getAnswer2(), answer2Latin);
        }
        if (!answer3Latin.isEmpty()) {
//            Log.d(TAG, "Cyrillic vs. Latin 3: " + questionData.getAnswer3() + "\t" + answer3Latin);
            mCyrillic2Latin.put(questionData.getAnswer3(), answer3Latin);
        }
        if (!answer4Latin.isEmpty()) {
//            Log.d(TAG, "Cyrillic vs. Latin 4: " + questionData.getAnswer4() + "\t" + answer4Latin);
            mCyrillic2Latin.put(questionData.getAnswer4(), answer4Latin);
        }
        if (!answer5Latin.isEmpty()) {
//            Log.d(TAG, "Cyrillic vs. Latin 5: " + questionData.getAnswer5() + "\t" + answer5Latin);
            mCyrillic2Latin.put(questionData.getAnswer5(), answer5Latin);
        }
    }

    private void printCyrillic2LatinMap() {
        for(String cyrillic : mCyrillic2Latin.keySet()) {
            Log.d(TAG, "Cyrillic vs. Latin: " + cyrillic + "\t" + mCyrillic2Latin.get(cyrillic));
        }
    }

    final private int mCourseId;
    final private QuestionDataRepository mQuestionDataRepository;
    final private QuestionInfoRepository mQuestionInfoRepository;
    final private LessonInfoRepository mLessonInfoRepository;
    final private DictionaryRepository mDictionaryRepository;
    final private Map<String, String> mCyrillic2Latin;
    private static final String TAG = LessonLoader.class.getSimpleName();
}
