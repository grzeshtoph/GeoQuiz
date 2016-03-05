package com.greg.geoquiz.questions.data;

import android.support.annotation.NonNull;

import java.util.Objects;
import java.util.UUID;

/**
 * Immutable mode class for a Question.
 */
public class Question {
    private final String mId;
    @NonNull
    private final String mQuestionText;
    @NonNull
    private final boolean mAnswerTrue;

    public Question(@NonNull String questionText, @NonNull boolean answerTrue) {
        this.mId = UUID.randomUUID().toString();
        this.mQuestionText = questionText;
        this.mAnswerTrue = answerTrue;
    }

    public String getId() {
        return mId;
    }

    public String getQuestionText() {
        return mQuestionText;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(mId, question.mId) &&
                Objects.equals(mQuestionText, question.mQuestionText) &&
                Objects.equals(mAnswerTrue, question.mAnswerTrue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mQuestionText, mAnswerTrue);
    }
}
