package com.greg.geoquiz.questions.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.google.common.collect.ImmutableList;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to load questions from a data source.
 */
public class InMemoryQuestionsRepositoryImpl implements QuestionsRepository {

    private final QuestionsServiceApi mQuestionsServiceApi;

    /**
     * The actual cache of questions.
     */
    @VisibleForTesting
    List<Question> mCachedQuestions;

    public InMemoryQuestionsRepositoryImpl(@NonNull QuestionsServiceApi questionsServiceApi) {
        mQuestionsServiceApi = checkNotNull(questionsServiceApi);
    }

    @Override
    public void getAllQuestions(@NonNull final LoadQuestionsCallback callback) {
        checkNotNull(callback);

        if (mCachedQuestions == null) {
            mQuestionsServiceApi.getAllQuestions(new QuestionsServiceApi.QuestionsServiceCallback<List<Question>>() {
                @Override
                public void onLoaded(List<Question> questions) {
                    mCachedQuestions = ImmutableList.copyOf(questions);
                    callback.onQuestionsLoaded(mCachedQuestions);
                }
            });
        }
    }

    @Override
    public void refreshData() {
        mCachedQuestions = null;
    }
}
