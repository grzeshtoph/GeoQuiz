package com.greg.geoquiz.questions.data;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Main entry point for accessing questions data.
 */
public interface QuestionsRepository {
    /**
     * Loading questions callback.
     */
    interface LoadQuestionsCallback {
        void onQuestionsLoaded(List<Question> questions);
    }

    /**
     * Gets list of questions from the Questions Service API.
     * @param callback callback to be executed, once questions are loaded
     */
    void getAllQuestions(@NonNull LoadQuestionsCallback callback);

    /**
     * Refreshes the cached data
     */
    void refreshData();
}
