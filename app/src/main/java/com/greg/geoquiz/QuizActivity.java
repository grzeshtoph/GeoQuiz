package com.greg.geoquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.collect.ImmutableList;
import com.greg.geoquiz.questions.data.Question;
import com.greg.geoquiz.questions.data.QuestionsRepository;

import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = QuizActivity.class.getName();

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mPreviousButton;
    private ImageButton mNextButton;
    private TextView mQuestionTextView;

    List<Question> questions = null;
    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setText(R.string.questions_default);
        Injection.provideQuestionsRepository().getAllQuestions(new QuestionsRepository.LoadQuestionsCallback() {
            @Override
            public void onQuestionsLoaded(List<Question> questions) {
                QuizActivity.this.questions = ImmutableList.copyOf(questions);
                updateQuestion();

                mTrueButton = (Button) findViewById(R.id.true_button);
                mTrueButton.setEnabled(true);
                mTrueButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer(true);
                    }
                });

                mFalseButton = (Button) findViewById(R.id.false_button);
                mFalseButton.setEnabled(true);
                mFalseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer(false);
                    }
                });

                mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
                mPreviousButton.setEnabled(true);
                mPreviousButton.setOnClickListener(new ProgressQuestionOnClickListener(false));

                mNextButton = (ImageButton) findViewById(R.id.next_button);
                mNextButton.setEnabled(true);
                mNextButton.setOnClickListener(new ProgressQuestionOnClickListener(true));

                mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
                mQuestionTextView.setEnabled(true);
                mQuestionTextView.setOnClickListener(new ProgressQuestionOnClickListener(true));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Injection.provideQuestionsRepository().refreshData();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateQuestion() {
        mQuestionTextView.setText(questions.get(mCurrentIndex).getQuestionText());
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean isAnswerTrue = questions.get(mCurrentIndex).isAnswerTrue();
        int messageResId = (userPressedTrue == isAnswerTrue) ? R.string.correct_toast :
                R.string.incorrect_toast;
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    private class ProgressQuestionOnClickListener implements View.OnClickListener {
        private final boolean mForward;

        public ProgressQuestionOnClickListener(boolean forward) {
            mForward = forward;
        }

        @Override
        public void onClick(View v) {
            final int questionsSize = questions.size();

            mCurrentIndex = mForward ? (mCurrentIndex + 1) % questionsSize
                    :  (mCurrentIndex == 0 ? questionsSize - 1 : mCurrentIndex - 1);

            updateQuestion();
        }
    }
}
