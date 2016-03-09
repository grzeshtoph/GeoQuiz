package com.greg.geoquiz.questions.data;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test case for {@link Question}
 */
public class QuestionTest {
    @Test
    public void test_createThreeQuestions() throws Exception {
        Question q1 = new Question("q1", true);
        Question q2 = new Question("q2", false);
        Question q3 = new Question("q3", true);

        assertThat(q1.getQuestionText(), is(equalTo("q1")));
        assertThat(q1.isAnswerTrue(), is(true));
        assertThat(q1.getId(),is(notNullValue()));

        assertThat(q2.getQuestionText(), is(equalTo("q2")));
        assertThat(q2.isAnswerTrue(), is(false));
        assertThat(q2.getId(),is(notNullValue()));

        assertThat(q3.getQuestionText(), is(equalTo("q3")));
        assertThat(q3.isAnswerTrue(), is(true));
        assertThat(q3.getId(),is(notNullValue()));

        assertThat(q1.getId(), is(not(equalTo(q2.getId()))));
        assertThat(q2.getId(), is(not(equalTo(q3.getId()))));
        assertThat(q3.getId(), is(not(equalTo(q1.getId()))));
    }

    @Test
    public void test_questionsEqualsAndHashCodes() throws Exception {
        Question q1 = new Question("q1", true);
        Question q2 = new Question("q2", false);
        Question q3 = new Question(q1.getQuestionText(), q1.isAnswerTrue());
        Field mIdField = Question.class.getDeclaredField("mId");
        mIdField.setAccessible(true);
        mIdField.set(q3, q1.getId());

        assertThat(q1, is(equalTo(q1)));
        assertThat(q1, is(not(equalTo(null))));
        assertThat(q1, is(not(equalTo(new Object()))));
        assertThat(q2, is(not(equalTo(q1))));
        assertThat(q3, is(equalTo(q1)));
        assertThat(q1, is(equalTo(q3)));

        assertThat(q1.hashCode(), is(not(equalTo(q2.hashCode()))));
        assertThat(q3.hashCode(), is(not(equalTo(q2.hashCode()))));
        assertThat(q1.hashCode(), is(equalTo(q1.hashCode())));
    }
}
