package com.example.rayan.tingrr_1;

import android.content.Context;
import android.graphics.Color;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    Button opt1;
    Button opt2;
    Button opt3;
    Button submit;
    public int selected = 0;
    public boolean submitted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

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
                opt2.setBackgroundColor(Color.parseColor("black"));
                opt3.setBackgroundColor(Color.parseColor("white"));
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitted = true;
            }
        });

        String dogFile = "DogInfo.txt";
        String QFile = "QuestionsActivity.txt";
        Dog[] dogs = parseDogs(dogFile, getApplicationContext());
        Question[] questions = parseQuestions(QFile, getApplicationContext());
        Scanner reader = new Scanner(System.in);
        for (int i = 0; i < questions.length; i++) {
            submitted = false;
            txtQuestion.setText(questions[i].getQuestion());
            String[] options = questions[i].getOptions();
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

            while (submitted = false) {
                Log.d("Questions", "Waiting for button press");
            }

            String in = "";
            if (selected == 0) {
                in = opt1.getText().toString();
            } else if (selected == 1) {
                in = opt2.getText().toString();
            } else if (selected == 2) {
                in = opt3.getText().toString();
            }
            for(Dog dog: dogs) {
                dog.increment(in, questions[i].getKey());
                //System.out.println(dog);
            }
        }
        reader.close();
        SortDogs sortThem = new SortDogs();
        sortThem.sort(dogs, 0, dogs.length-1);
        System.out.println("Matches:");
        for (Dog dog: dogs) {
            System.out.println(dog);
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
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
        return allDogs;
    }
    public static Question[] parseQuestions(String fileName, Context context) {
        String line = null;
        Question[] questions = new Question[0];;
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
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
        return questions;
    }
}
