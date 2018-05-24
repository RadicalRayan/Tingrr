package com.example.rayan.tingrr_1;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    private String[] dogNames = new String[4];

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            dogNames[0] = "Golden Retriever";
            dogNames[1] = "Golden Retriever";
            dogNames[2] = "Golden Retriever";
            dogNames[3] = "Golden Retriever";
        } else {
            dogNames[0] = extras.getString("Dog1");
            dogNames[1] = extras.getString("Dog2");
            dogNames[2] = extras.getString("Dog3");
            dogNames[3] = extras.getString("Dog4");
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecycleViewActivity(getDataSet());
        mRecyclerView.setAdapter(mAdapter);

        /*
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDogDes();
            }
        });
        */

        fab = (FloatingActionButton) findViewById(R.id.fabBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewQuiz();
            }
        });
    }

    private void startNewQuiz() {
        Intent startQuiz = new Intent (this, QuestionsActivity.class);
        startActivity(startQuiz);
    }

    private void openDogDes() {
        Intent startQuiz = new Intent (this, DogInfo.class);
        startActivity(startQuiz);
    }

    private ArrayList<CardObject> getDataSet() {

        String line = null;
        String[] allDogNames = new String[29];
        Drawable[] dogPics = new Drawable[29];
        for (int i = 1; i <= 29; i++) {
            //Drawable image =  ContextCompat.getDrawable(this, R.drawable.i);
            Drawable image = ContextCompat.getDrawable(this, getResources().getIdentifier("a" + i, "drawable", getPackageName()));
            dogPics[i-1] = image;
        }
        int j = 0;
        try {
            InputStream fIn = getResources().getAssets().open("DogInfo");
            InputStreamReader fileReader = new InputStreamReader(fIn);
            BufferedReader b = new BufferedReader(fileReader);
            while((line = b.readLine()) != null) {
                StringTokenizer inDog = new StringTokenizer(line);
                String name = inDog.nextToken();
                inDog.nextToken();
                inDog.nextToken();
                inDog.nextToken();
                inDog.nextToken();
                inDog.nextToken();
                inDog.nextToken();
                inDog.nextToken();
                inDog.nextToken();
                name = name.replace("_", " ");
                allDogNames[j] = name;
                j++;
            }
            b.close();
        }catch(FileNotFoundException ex) {
            Log.d("dog paarse",
                    "Unable to open file '" +
                            "DogInfo" + "'");
        }
        catch(IOException ex) {
            Log.d("dog parse",
                    "Error reading file '"
                            + "DogInfo" + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }

        Drawable[] fourDogPics = new Drawable[4];
        for (int i = 0; i < allDogNames.length; i++) {
            if (allDogNames[i].equals(dogNames[0]))
                fourDogPics[0] = dogPics[i];
        }
        for (int i = 0; i < allDogNames.length; i++) {
            if (allDogNames[i].equals(dogNames[1]))
                fourDogPics[1] = dogPics[i];
        }
        for (int i = 0; i < allDogNames.length; i++) {
            if (allDogNames[i].equals(dogNames[2]))
                fourDogPics[2] = dogPics[i];
        }
        for (int i = 0; i < allDogNames.length; i++) {
            if (allDogNames[i].equals(dogNames[3]))
                fourDogPics[3] = dogPics[i];
        }

        ArrayList results = new ArrayList<CardObject>();
        Drawable image =  ContextCompat.getDrawable(this, R.drawable.snow_mia);
        for (int i = 0; i < 4; i++) {
            CardObject obj = new CardObject(dogNames[i], "description text. description text. description text. description text. description text. description text.", fourDogPics[i]);
            results.add(i, obj);
        }
        return results;
    }
}
