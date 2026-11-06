package com.mastegoane.adigabzaforbeginners.databases;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "question_data")
public class QuestionData {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private final int courseId;
    private final int stageNumber;
    private final int lessonNumber;

    private final int questionType;
    private final int exerciseNumber;
    private final int questionNumber;
    private final String questionSubText;
    private final String questionSubTextEnglish;
    private final String questionSubTextArabic;
    private final String questionSubTextTurkish;
    private final String questionAudio;
    private final String questionAudioFile;
    private final String questionImage;

    private final int rightAnswer1;
    private final int rightAnswer2;
    private final int rightAnswer3;
    private final int rightAnswer4;
    private final int rightAnswer5;

    private final String answer1;
    private final String answer2;
    private final String answer3;
    private final String answer4;
    private final String answer5;

    private final String answerAudio1;
    private final String answerAudio2;
    private final String answerAudio3;
    private final String answerAudio4;
    private final String answerAudio5;

    private final String answerEnglish1;
    private final String answerEnglish2;
    private final String answerEnglish3;
    private final String answerEnglish4;
    private final String answerEnglish5;

    private final String answerArabic1;
    private final String answerArabic2;
    private final String answerArabic3;
    private final String answerArabic4;
    private final String answerArabic5;

    private final String answerTurkish1;
    private final String answerTurkish2;
    private final String answerTurkish3;
    private final String answerTurkish4;
    private final String answerTurkish5;

    public QuestionData(int courseId, int stageNumber, int lessonNumber, int questionType, int exerciseNumber, int questionNumber,
                        String questionAudio, String questionAudioFile, String questionImage,
                        int rightAnswer1, int rightAnswer2, int rightAnswer3, int rightAnswer4, int rightAnswer5,
                        String answer1, String answer2, String answer3, String answer4, String answer5,
                        String answerAudio1, String answerAudio2, String answerAudio3, String answerAudio4, String answerAudio5,
                        String questionSubText, String questionSubTextEnglish, String questionSubTextArabic, String questionSubTextTurkish,
                        String answerEnglish1, String answerArabic1, String answerTurkish1,
                        String answerEnglish2, String answerArabic2, String answerTurkish2,
                        String answerEnglish3, String answerArabic3, String answerTurkish3,
                        String answerEnglish4, String answerArabic4, String answerTurkish4,
                        String answerEnglish5, String answerArabic5, String answerTurkish5) {
        this.courseId = courseId;
        this.stageNumber = stageNumber;
        this.lessonNumber = lessonNumber;
        this.questionType = questionType;
        this.exerciseNumber = exerciseNumber;
        this.questionNumber = questionNumber;
        this.questionAudio = questionAudio;
        this.questionAudioFile = questionAudioFile;
        this.questionImage = questionImage;

        this.rightAnswer1 = rightAnswer1;
        this.rightAnswer2 = rightAnswer2;
        this.rightAnswer3 = rightAnswer3;
        this.rightAnswer4 = rightAnswer4;
        this.rightAnswer5 = rightAnswer5;

        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.answer5 = answer5;

        this.answerAudio1 = answerAudio1;
        this.answerAudio2 = answerAudio2;
        this.answerAudio3 = answerAudio3;
        this.answerAudio4 = answerAudio4;
        this.answerAudio5 = answerAudio5;

        this.questionSubText = questionSubText;
        this.questionSubTextEnglish = questionSubTextEnglish;
        this.questionSubTextArabic = questionSubTextArabic;
        this.questionSubTextTurkish = questionSubTextTurkish;

        this.answerEnglish1 = answerEnglish1;
        this.answerArabic1 = answerArabic1;
        this.answerTurkish1 = answerTurkish1;

        this.answerEnglish2 = answerEnglish2;
        this.answerArabic2 = answerArabic2;
        this.answerTurkish2 = answerTurkish2;

        this.answerEnglish3 = answerEnglish3;
        this.answerArabic3 = answerArabic3;
        this.answerTurkish3 = answerTurkish3;

        this.answerEnglish4 = answerEnglish4;
        this.answerArabic4 = answerArabic4;
        this.answerTurkish4 = answerTurkish4;

        this.answerEnglish5 = answerEnglish5;
        this.answerArabic5 = answerArabic5;
        this.answerTurkish5 = answerTurkish5;
    }

    public int getId() {
        return id;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getStageNumber() {
        return stageNumber;
    }

    public int getLessonNumber() {
        return lessonNumber;
    }

    public int getQuestionType() {
        return questionType;
    }

    public int getExerciseNumber() {
        return exerciseNumber;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public String getQuestionAudio() {
        return questionAudio;
    }

    public String getQuestionAudioFile() {
        return questionAudioFile;
    }

    public String getQuestionImage() {
        return questionImage;
    }

    public int getRightAnswer1() {
        return rightAnswer1;
    }
    public int getRightAnswer2() {
        return rightAnswer2;
    }
    public int getRightAnswer3() {
        return rightAnswer3;
    }
    public int getRightAnswer4() {
        return rightAnswer4;
    }
    public int getRightAnswer5() {
        return rightAnswer5;
    }

    public String getAnswer1() {
        return answer1;
    }
    public String getAnswer2() {
        return answer2;
    }
    public String getAnswer3() {
        return answer3;
    }
    public String getAnswer4() {
        return answer4;
    }
    public String getAnswer5() {
        return answer5;
    }

    public String getAnswerAudio1() {
        return answerAudio1;
    }
    public String getAnswerAudio2() {
        return answerAudio2;
    }
    public String getAnswerAudio3() {
        return answerAudio3;
    }
    public String getAnswerAudio4() {
        return answerAudio4;
    }
    public String getAnswerAudio5() {
        return answerAudio5;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionSubText() {
        return questionSubText;
    }

    public String getQuestionSubTextEnglish() {
        return questionSubTextEnglish;
    }

    public String getQuestionSubTextArabic() {
        return questionSubTextArabic;
    }

    public String getQuestionSubTextTurkish() {
        return questionSubTextTurkish;
    }

    public String getAnswerEnglish1() {
        return answerEnglish1;
    }
    public String getAnswerEnglish2() {
        return answerEnglish2;
    }
    public String getAnswerEnglish3() {
        return answerEnglish3;
    }
    public String getAnswerEnglish4() {
        return answerEnglish4;
    }
    public String getAnswerEnglish5() {
        return answerEnglish5;
    }

    public String getAnswerArabic1() {
        return answerArabic1;
    }
    public String getAnswerArabic2() {
        return answerArabic2;
    }
    public String getAnswerArabic3() {
        return answerArabic3;
    }
    public String getAnswerArabic4() {
        return answerArabic4;
    }
    public String getAnswerArabic5() {
        return answerArabic5;
    }

    public String getAnswerTurkish1() {
        return answerTurkish1;
    }
    public String getAnswerTurkish2() {
        return answerTurkish2;
    }
    public String getAnswerTurkish3() {
        return answerTurkish3;
    }
    public String getAnswerTurkish4() {
        return answerTurkish4;
    }
    public String getAnswerTurkish5() {
        return answerTurkish5;
    }
}
