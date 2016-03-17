package com.greg.geoquiz.questions.data;

import org.junit.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;

/**
 * Test case for {@link QuestionsServiceApiEndpoint}
 */
public class QuestionsServiceApiEndpointTest {
    @Test
    public void test_loadPersistedQuestions() throws Exception {
        Map<String, Question> data1 = QuestionsServiceApiEndpoint.loadPersistedQuestions();
        Map<String, Question> data2 = QuestionsServiceApiEndpoint.loadPersistedQuestions();

        assertThat(data1, is(notNullValue()));
        assertThat(data1.size(), is(equalTo(5)));
        assertThat(data1, is(not(sameInstance(data2))));
        assertThat(data1, is(equalTo(data2)));

        Question[] questions = data1.values().toArray(new Question[data1.values().size()]);
        assertQuestion(data1, questions[0]);
        assertQuestion(data1, questions[1]);
        assertQuestion(data1, questions[2]);
        assertQuestion(data1, questions[3]);
        assertQuestion(data1, questions[4]);
    }

    private void assertQuestion(Map<String, Question> data, Question question) {
        assertThat(question.getQuestionText(), not(isEmptyOrNullString()));
        assertThat(question.getId(), is(notNullValue()));
        Question question1 = data.get(question.getId());
        assertThat(question, is(sameInstance(question1)));
    }
}
