package com.greg.geoquiz.questions.data;

import android.os.Handler;

import com.greg.geoquiz.Injection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Test case for {@link QuestionsServiceApiImpl}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Injection.class)
public class QuestionsServiceApiImplTest {
    @Mock
    private QuestionsServiceApi.QuestionsServiceCallback<List<Question>> mCallback;
    @Mock
    private Handler mHandler;
    @Captor
    private ArgumentCaptor<Runnable> mRunnableCaptor;

    private QuestionsServiceApi mQuestionsServiceApi;

    @Before
    public void setUp() throws Exception {
        mQuestionsServiceApi = new QuestionsServiceApiImpl();
        mockStatic(Injection.class);
    }

    @Test
    public void test_getAllQuestions() throws Exception {
        when(Injection.provideHandler()).thenReturn(mHandler);

        mQuestionsServiceApi.getAllQuestions(mCallback);

        verify(mHandler).postDelayed(mRunnableCaptor.capture(), eq(2000L));
        mRunnableCaptor.getValue().run();
        verify(mCallback).onLoaded(eq(new ArrayList<>(
                QuestionsServiceApiEndpoint.loadPersistedQuestions().values())));
    }
}
