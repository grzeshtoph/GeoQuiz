package com.greg.geoquiz.questions.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test case for {@link QuestionsRepositories}
 */
@RunWith(MockitoJUnitRunner.class)
public class QuestionsRepositoriesTest {
    @Mock
    private QuestionsServiceApi mQuestionsServiceApi;

    @Test
    public void test_getInMemoryRepoInstance_once() throws Exception {
        QuestionsRepository resultRepo = QuestionsRepositories.getInMemoryRepoInstance(mQuestionsServiceApi);

        assertThat(resultRepo, is(instanceOf(InMemoryQuestionsRepositoryImpl.class)));
    }

    @Test
    public void test_getInMemoryRepoInstance_twice() throws Exception {
        QuestionsRepository resultRepo = QuestionsRepositories.getInMemoryRepoInstance(mQuestionsServiceApi);

        assertThat(resultRepo, is(instanceOf(InMemoryQuestionsRepositoryImpl.class)));

        QuestionsRepository resultRepo1 = QuestionsRepositories.getInMemoryRepoInstance(mQuestionsServiceApi);

        assertThat(resultRepo, is(sameInstance(resultRepo1)));
    }

    @Test(expected = NullPointerException.class)
    public void test_getInMemoryRepoInstance_nullServiceApi() throws Exception {
        QuestionsRepositories.getInMemoryRepoInstance(null);
    }
}
