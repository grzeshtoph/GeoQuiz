package com.greg.geoquiz.questions.data;

import android.os.Handler;
import android.support.v4.util.ArrayMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the Questions Service API that adds a latency simulating network.
 */
public class QuestionsServiceApiImpl implements QuestionsServiceApi {
    private static final int SERVICE_LATENCY_IN_MILLIS = 2000;
    private final ArrayMap<String, Question> questionsServiceData =
            QuestionsServiceApiEndpoint.loadPersistedQuestions();

    @Override
    public void getAllQuestions(final QuestionsServiceCallback<List<Question>> callback) {
        //Simulate network by delaying the execution.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Question> questions = new ArrayList<>(questionsServiceData.values());
                callback.onLoaded(questions);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }
}
