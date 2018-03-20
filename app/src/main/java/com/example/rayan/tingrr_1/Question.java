package com.example.rayan.tingrr_1;

/**
 * Created by FredQ on 3/20/2018.
 */

public class Question {
    private int numOptions;
    private String question;
    private int keyValue;
    private String[] options;
    private String[] values;


    public Question(String newQuestion, int newKeyValue,  int newNumOptions, String[] newOptions, String[] newValues){
        numOptions = newNumOptions;
        keyValue = newKeyValue;
        question = newQuestion;
        options = newOptions;
        values = newValues;
    }
    public String getQuestion() {
        return question;
    }
    public String[] getOptions() {
        return options;
    }
    public int getKey() {
        return keyValue;
    }
    public String toString() {
        return (question + keyValue + numOptions);
    }

}