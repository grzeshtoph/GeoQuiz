package com.greg.geoquiz.questions.data;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Questions repository factory.
 */
public class QuestionsRepositories {
    private QuestionsRepositories() {
    }

    private static QuestionsRepository repository = null;

    /**
     * Gets the in-memory-repo or creates a new one, if not yet created.
     *
     * @param questionsServiceApi questions service API
     * @return
     */
    public synchronized static QuestionsRepository getInMemoryRepoInstance(@NonNull QuestionsServiceApi questionsServiceApi) {
        checkNotNull(questionsServiceApi);
        if (repository == null) {
            repository = new InMemoryQuestionsRepositoryImpl(questionsServiceApi);
        }
        return repository;
    }
}
