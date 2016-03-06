package com.greg.geoquiz;

import com.greg.geoquiz.questions.data.QuestionsRepositories;
import com.greg.geoquiz.questions.data.QuestionsRepository;
import com.greg.geoquiz.questions.data.QuestionsServiceApiImpl;

/**
 * Enables injection of production implementations.
 */
public class Injection {
    public static QuestionsRepository provideQuestionsRepository() {
        return QuestionsRepositories.getInMemoryRepoInstance(new QuestionsServiceApiImpl());
    }
}
