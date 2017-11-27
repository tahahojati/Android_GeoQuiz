package com.bignerdranch.android.geoquiz;

/**
 * Created by ProfessorTaha on 9/19/2017.
 */

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public Question(int testResId, boolean answerTrue){
        mTextResId = testResId;
        mAnswerTrue = answerTrue;
    }
}
