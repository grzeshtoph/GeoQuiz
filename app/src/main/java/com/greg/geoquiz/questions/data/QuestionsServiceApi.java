package com.greg.geoquiz.questions.data;

import java.util.List;

/**
 * Interface of the Questions API that is used by this app. All data requests should be piped
 * through this interface.
 */
public interface QuestionsServiceApi {
    /**
     * Interface for standard callback for questions API.
     * @param <T>
     */
    interface QuestionsServiceCallback<T> {
        void onLoaded(T questions);
    }

    /**
     * Gets all of the questions from the endpoint.
     * @param callback callback to be called, once data is loaded
     */
    void getAllQuestions(QuestionsServiceCallback<List<Question>> callback);
}
