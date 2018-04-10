package com.example.rayan.tingrr_1;

import android.content.Context;
import android.graphics.Color;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;


public class QuestionsActivity extends AppCompatActivity {

    TextView txtQuestion;
    ProgressBar progress;
    Button opt1;
    Button opt2;
    Button opt3;
    Button submit;
    public int selected = 0;
    public int questionNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        String dogFile = "DogInfo";
        String QFile = "Questions";
        final Dog[] dogs = parseDogs(dogFile, getApplicationContext());
        final Question[] questions = parseQuestions(QFile, getApplicationContext());

        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setProgress(0);
        opt1 = (Button) findViewById(R.id.btnOne);
        opt2 = (Button) findViewById(R.id.btnTwo);
        opt3 = (Button) findViewById(R.id.btnThree);
        txtQuestion = (TextView) findViewById(R.id.question);
        submit = (Button) findViewById(R.id.btnSubmit);
        opt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected = 0;
                opt1.setBackgroundColor(Color.parseColor("black"));
                opt2.setBackgroundColor(Color.parseColor("white"));
                opt3.setBackgroundColor(Color.parseColor("white"));
            }
        });
        opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected = 1;
                opt1.setBackgroundColor(Color.parseColor("white"));
                opt2.setBackgroundColor(Color.parseColor("black"));
                opt3.setBackgroundColor(Color.parseColor("white"));
            }
        });
        opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected = 2;
                opt1.setBackgroundColor(Color.parseColor("white"));
                opt2.setBackgroundColor(Color.parseColor("white"));
                opt3.setBackgroundColor(Color.parseColor("black"));
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String in = "";
                if (selected == 0) {
                    in = opt1.getText().toString();
                } else if (selected == 1) {
                    in = opt2.getText().toString();
                } else if (selected == 2) {
                    in = opt3.getText().toString();
                }

                questionNumber++;
                if (questionNumber < questions.length) {
                    nextQuestion(questions);

                    for (Dog dog : dogs) {
                        dog.increment(in, questions[questionNumber].getKey());
                        //System.out.println(dog);
                    }

                    Double completion = (((double) questionNumber + 1) / 9) * 100;
                    progress.setProgress((int) (Math.round(completion)));
                }
            }
        });

        /*
        Sorting algo

        SortDogs sortThem = new SortDogs();
        sortThem.sort(dogs, 0, dogs.length-1);
        System.out.println("Matches:");
        for (Dog dog: dogs) {
            System.out.println(dog);
        }
        */
    }

    public void nextQuestion(Question[] qs) {
        txtQuestion.setText(qs[questionNumber].getQuestion());
        String[] options = qs[questionNumber].getOptions();
        if (options.length==2) {
            opt1.setText(options[0]);
            opt2.setText(options[1]);
            opt3.setVisibility(View.INVISIBLE);
        } else if (options.length==3) {
            opt1.setText(options[0]);
            opt2.setText(options[1]);
            opt3.setVisibility(View.VISIBLE);
            opt3.setText(options[2]);
        }
    }

    public static Dog[] parseDogs(String fileName, Context context) {
        String line = null;
        Dog[] allDogs = new Dog[29];
        int i = 0;
        try {
            InputStream fIn = context.getResources().getAssets().open(fileName);
            InputStreamReader fileReader = new InputStreamReader(fIn);
            BufferedReader b = new BufferedReader(fileReader);
            while((line = b.readLine()) != null) {
                StringTokenizer inDog = new StringTokenizer(line);
                String name = inDog.nextToken();
                String size = inDog.nextToken();
                String space = inDog.nextToken();
                String ability = inDog.nextToken();
                String energyLevel = inDog.nextToken();
                String grooming = inDog.nextToken();
                String climate = inDog.nextToken();
                String allergenic = inDog.nextToken();
                String kids = inDog.nextToken();
                Dog newDog = new Dog(name, size, space, energyLevel, grooming, climate, allergenic, kids);
                allDogs[i] = newDog;
                //System.out.println(newDog);
                i++;
            }
            b.close();
        }catch(FileNotFoundException ex) {
            Log.d("dog paarse",
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            Log.d("dog parse",
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
        return allDogs;
    }
    public static Question[] parseQuestions(String fileName, Context context) {
        String line = null;
        Question[] questions = new Question[0];
        try {
            InputStream fIn = context.getResources().getAssets().open(fileName);
            InputStreamReader fileReader = new InputStreamReader(fIn);
            BufferedReader b = new BufferedReader(fileReader);
            line = b.readLine();
            StringTokenizer lineOne = new StringTokenizer(line);
            int numQuestions = Integer.parseInt(lineOne.nextToken());
            questions = new Question[numQuestions];
            for (int i = 0; i < questions.length; i++) {
                line = b.readLine();
                StringTokenizer input = new StringTokenizer(line);
                String question = "";
                String next = input.nextToken();
                while (!next.equals("?")) {
                    question += " " + next;
                    next = input.nextToken();
                }
                question += "?";
                int keyValue = Integer.parseInt(input.nextToken());
                int numChoices = Integer.parseInt(input.nextToken());
                String[] options = new String[numChoices];
                String[] values = new String[numChoices];
                for (int j = 0; j < numChoices; j++) {
                    line = b.readLine();
                    StringTokenizer choice = new StringTokenizer(line);
                    options[j] = choice.nextToken();
                    values[j] = choice.nextToken();
                }
                Question Q = new Question(question, keyValue, numChoices, options, values);
                questions[i] = Q;
                //System.out.println(Q);
            }
            b.close();
        }catch(FileNotFoundException ex) {
            Log.e("questions",
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            Log.e("questions",
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
        return questions;
    }
}
