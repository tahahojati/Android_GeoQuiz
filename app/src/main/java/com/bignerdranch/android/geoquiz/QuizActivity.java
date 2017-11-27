package com.bignerdranch.android.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private Button mTruButton;
    private Button mFalseButton;
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private Button mCheatButton;
    private static final int REQUEST_CODE_CHEAT = 0 ;
    private TextView mQuestionTextView;
    //private boolean mIsCheater = false;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };
    private boolean[] mCheatingArray;

    private int mCurrentIndex = 0;
    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }
    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0 ;
        if(mCheatingArray[mCurrentIndex]){
            messageResId = R.string.judgment_toast;
        } else if(userPressedTrue == answerIsTrue){
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean("IS_CHEATER",mCheatingArray[mCurrentIndex]);
        savedInstanceState.putBooleanArray("CHEATING_ARRAY", mCheatingArray);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);
        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mCheatingArray = savedInstanceState.getBooleanArray("CHEATING_ARRAY");
        } else {
            mCheatingArray = new boolean[mQuestionBank.length];
        }
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mTruButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mPrevButton = (ImageButton) findViewById(R.id.previous_button);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getTextResId());
        View.OnClickListener nextListener = new View.OnClickListener(){
            public void onClick(View v){
                ++mCurrentIndex;
                mCurrentIndex %= mQuestionBank.length;
                updateQuestion();
            }
        };
        mTruButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(true);
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(false);
            }
        });
        mNextButton.setOnClickListener(nextListener);
        mPrevButton.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v){
                --mCurrentIndex;
                mCurrentIndex += mQuestionBank.length;
                mCurrentIndex %= mQuestionBank.length;
                updateQuestion();
            }
        });
        mQuestionTextView.setOnClickListener(nextListener);
        mCheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivityForResult(CheatActivity.newIntent(QuizActivity.this, mQuestionBank[mCurrentIndex].isAnswerTrue()),REQUEST_CODE_CHEAT);
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_OK){
            return;
        }
        mCheatingArray[mCurrentIndex] = mCheatingArray[mCurrentIndex] || CheatActivity.wasAnswerShown(data);
    }
}
