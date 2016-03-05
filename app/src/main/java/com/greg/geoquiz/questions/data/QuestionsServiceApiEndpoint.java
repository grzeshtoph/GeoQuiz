package com.greg.geoquiz.questions.data;

import android.support.v4.util.ArrayMap;

/**
 * This is the endpoint for the questions data source. Typically, it would be a SQLite DB and/or
 * a server API. In this example, it is faked by creating the in-memory data on the fly.
 */
public final class QuestionsServiceApiEndpoint {
    private final static ArrayMap<String, Question> DATA;

    static {
        DATA = new ArrayMap(5);
        addQuestion("Is the Pacific Ocean larger than the Atlantic Ocean?", true);
        addQuestion("Does the Suez Canal connects the Red Sea and the Indian Ocean?", false);
        addQuestion("Is the source of the Nile River in Egypt?", false);
        addQuestion("Is the Amazon River the longest river in the Americas?", true);
        addQuestion("Is Lake Baikal the world's oldest and deepest freshwater lake?", true);
    }

    private static void addQuestion(String questionText, boolean answerTrue) {
        Question question = new Question(questionText, answerTrue);
        DATA.put(question.getId(), question);
    }

    public static ArrayMap<String, Question> loadPersistedQuestions() {
        return DATA;
    }
}
