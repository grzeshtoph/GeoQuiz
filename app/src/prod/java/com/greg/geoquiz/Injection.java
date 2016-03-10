package com.greg.geoquiz;

import android.os.Handler;

import com.greg.geoquiz.questions.data.QuestionsRepositories;
import com.greg.geoquiz.questions.data.QuestionsRepository;
import com.greg.geoquiz.questions.data.QuestionsServiceApiImpl;

/**
 * Enables injection of production implementations.
 */
public class Injection {
    /**
     * Provides a new instance of a questions repository instance.
     *
     * @return a new instance of a questions repository instance
     */
    public static QuestionsRepository provideQuestionsRepository() {
        return QuestionsRepositories.getInMemoryRepoInstance(new QuestionsServiceApiImpl());
    }

    /**
     * Provide the instance of OS handler.
     *
     * @return OS handler
     */
    public static Handler provideHandler() {
        return new Handler();
    }
}
