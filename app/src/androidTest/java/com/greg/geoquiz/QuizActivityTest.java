package com.greg.geoquiz;

import android.support.test.espresso.ViewAssertion;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.widget.TextView;

import com.greg.geoquiz.questions.data.Question;
import com.greg.geoquiz.questions.data.QuestionsServiceApiEndpoint;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

/**
 * UI test suite for {@link QuizActivity}
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class QuizActivityTest {
    @Rule
    public ActivityTestRule<QuizActivity> mQuizAtivityTestRule =
            new ActivityTestRule<>(QuizActivity.class);

    private List<Question> questions = new ArrayList<>(QuestionsServiceApiEndpoint.loadPersistedQuestions().values());

    @Test
    public void test_answerQuestions() throws Exception {
        assertLoadingQuestionsStage();

        onView(withId(R.id.false_button)).check(matches(isEnabled()));
        onView(withId(R.id.true_button)).check(matches(isEnabled()));
        onView(withId(R.id.next_button)).check(matches(isEnabled()));
        onView(withId(R.id.question_text_view)).check(matches(withOneOfTheQuestionsText())).check(matches(isEnabled()));
        onView(withId(R.id.true_button)).perform(click());

        onView(withText(R.string.incorrect_toast)).inRoot(withDecorView(not(mQuizAtivityTestRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    private void assertLoadingQuestionsStage() throws InterruptedException {
        onView(withId(R.id.false_button)).check(matches(not(isEnabled())));
        onView(withId(R.id.true_button)).check(matches(not(isEnabled())));
        onView(withId(R.id.next_button)).check(matches(not(isEnabled())));
        onView(withId(R.id.question_text_view)).check(matches(withText("Loading questions...")))
                .check(matches(not(isEnabled())));
        Thread.sleep(3000L);

    }

    private Matcher<View> withOneOfTheQuestionsText() {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                return item instanceof TextView
                        && isOneOfTheQuestions((String) ((TextView)item).getText());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("has the question text from one of the supported ones ?");
            }

            private boolean isOneOfTheQuestions(String text) {
                for (Question question : questions) {
                    if (question.getQuestionText().equals(text))
                        return true;
                }
                return false;
            }
        };
    }
}
