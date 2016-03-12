package com.greg.geoquiz.questions.data;

import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Test case for {@link InMemoryQuestionsRepositoryImpl}
 */
@RunWith(MockitoJUnitRunner.class)
public class InMemoryQuestionsRepositoryImplTest {
    private static final List<Question> QUESTIONS = Lists.newArrayList(
            new Question("Question1", true),
            new Question("Question2", false),
            new Question("Question3", true));
    @Mock
    private QuestionsServiceApi mQuestionsServiceApi;
    @Mock
    private QuestionsRepository.LoadQuestionsCallback mLoadQuestionsCallback;
    @Captor
    private ArgumentCaptor<QuestionsServiceApi.QuestionsServiceCallback<List<Question>>> mQuestionsServiceCallbackCaptor;

    private InMemoryQuestionsRepositoryImpl mQuestionsRepository;

    @Before
    public void setUp() throws Exception {
        mQuestionsRepository = new InMemoryQuestionsRepositoryImpl(mQuestionsServiceApi);
    }

    @Test(expected = NullPointerException.class)
    public void test_getAllQuestions_nullCallback() throws Exception {
        mQuestionsRepository.getAllQuestions(null);
    }

    @Test
    public void test_getAllQuestions_once() throws Exception {
        assertThat("Assert cached questions being null", mQuestionsRepository.mCachedQuestions, is(nullValue()));

        mQuestionsRepository.getAllQuestions(mLoadQuestionsCallback);
        // capture the input callback for the question service API
        verify(mQuestionsServiceApi).getAllQuestions(mQuestionsServiceCallbackCaptor.capture());
        // force call on the callback captor
        mQuestionsServiceCallbackCaptor.getValue().onLoaded(QUESTIONS);
        // verify the callback for the question service API was called
        verify(mLoadQuestionsCallback).onQuestionsLoaded(eq(QUESTIONS));

        assertThat("Assert cached questions are there", mQuestionsRepository.mCachedQuestions, is(equalTo(QUESTIONS)));
    }

    @Test
    public void test_getAllQuestions_twice() throws Exception {
        assertThat("Assert cached questions being null", mQuestionsRepository.mCachedQuestions, is(nullValue()));

        //1st call
        mQuestionsRepository.getAllQuestions(mLoadQuestionsCallback);
        // capture the input callback for the question service API
        verify(mQuestionsServiceApi).getAllQuestions(mQuestionsServiceCallbackCaptor.capture());
        // force call on the callback captor
        mQuestionsServiceCallbackCaptor.getValue().onLoaded(QUESTIONS);
        // verify the callback for the question service API was called
        verify(mLoadQuestionsCallback).onQuestionsLoaded(eq(QUESTIONS));

        List<Question> cachedQuestions = mQuestionsRepository.mCachedQuestions;
        assertThat("Assert cached questions are there", cachedQuestions, is(equalTo(QUESTIONS)));

        //2nd call
        mQuestionsRepository.getAllQuestions(mLoadQuestionsCallback);
        // verify for questions service API is called
        verifyNoMoreInteractions(mQuestionsServiceApi);

        assertThat("Assert cached questions are the same as first time", cachedQuestions, is(sameInstance(cachedQuestions)));
    }

    @Test
    public void test_getAllQuestions_twice_withCacheRefresh() throws Exception {
        assertThat("Assert cached questions being null", mQuestionsRepository.mCachedQuestions, is(nullValue()));

        //1st call
        mQuestionsRepository.getAllQuestions(mLoadQuestionsCallback);
        // capture the input callback for the question service API
        verify(mQuestionsServiceApi).getAllQuestions(mQuestionsServiceCallbackCaptor.capture());
        // force call on the callback captor
        mQuestionsServiceCallbackCaptor.getValue().onLoaded(QUESTIONS);
        // verify the callback for the question service API was called
        verify(mLoadQuestionsCallback).onQuestionsLoaded(eq(QUESTIONS));

        assertThat("Assert cached questions are there", mQuestionsRepository.mCachedQuestions, is(equalTo(QUESTIONS)));

        // reset the mocks
        reset(mQuestionsServiceApi, mLoadQuestionsCallback);

        // clear the cache
        mQuestionsRepository.refreshData();

        //2nd call
        mQuestionsRepository.getAllQuestions(mLoadQuestionsCallback);
        // capture the input callback for the question service API
        verify(mQuestionsServiceApi).getAllQuestions(mQuestionsServiceCallbackCaptor.capture());
        // force call on the callback captor
        mQuestionsServiceCallbackCaptor.getValue().onLoaded(QUESTIONS);
        // verify the callback for the question service API was called
        verify(mLoadQuestionsCallback).onQuestionsLoaded(eq(QUESTIONS));

        assertThat("Assert cached questions are there", mQuestionsRepository.mCachedQuestions, is(equalTo(QUESTIONS)));
    }
}
