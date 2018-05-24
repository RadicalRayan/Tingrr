package com.example.rayan.tingrr_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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


    private String[] dogNames;

    FloatingActionButton fab;
    TextView infoTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Tingrr Matches");

        SharedPreferences mPrefs = getSharedPreferences("shared prefs", 0);
        String rawStoredDogs = mPrefs.getString("storedDogs", "");

        infoTxt = (TextView) findViewById(R.id.txtMatches);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            if (rawStoredDogs.equals("")) {
                infoTxt.setVisibility(View.VISIBLE);
            } else {
                //should list stored dogs and save to collection data
                if (rawStoredDogs.charAt(0)==';') {
                    rawStoredDogs = rawStoredDogs.substring(1);
                }
                dogNames = rawStoredDogs.split(";");

                mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(this);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new RecycleViewActivity(getDataSet());
                mRecyclerView.setAdapter(mAdapter);
            }
            /*
            dogNames[0] = "Golden Retriever";
            dogNames[1] = "Golden Retriever";
            dogNames[2] = "Golden Retriever";
            dogNames[3] = "Golden Retriever";
            */
        } else {
            dogNames = new String[4];
            dogNames[0] = extras.getString("Dog1");
            dogNames[1] = extras.getString("Dog2");
            dogNames[2] = extras.getString("Dog3");
            dogNames[3] = extras.getString("Dog4");

            mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new RecycleViewActivity(getDataSet());
            mRecyclerView.setAdapter(mAdapter);
        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btn_home) {
            showMatches();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMatches() {
        Intent showMatches = new Intent (this, MainActivity.class);
        startActivity(showMatches);
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
        Dog[] allDogs = new Dog[29];
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
                String size = inDog.nextToken();
                String space = inDog.nextToken();
                String ability = inDog.nextToken();
                String energyLevel = inDog.nextToken();
                String grooming = inDog.nextToken();
                String climate = inDog.nextToken();
                String allergenic = inDog.nextToken();
                String kids = inDog.nextToken();
                name = name.replace("_", " ");
                allDogNames[j] = name;
                Dog newDog = new Dog(name, size, space, energyLevel, grooming, climate, allergenic, kids);
                allDogs[j] = newDog;
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

        Drawable[] fourDogPics = new Drawable[dogNames.length];
        String[] fourDes = new String[dogNames.length];
        for (int k = 0; k < fourDogPics.length; k++) {
            for (int i = 0; i < allDogNames.length; i++) {
                if (allDogNames[i].equals(dogNames[k])) {
                    fourDogPics[k] = dogPics[i];
                }
                if (allDogs[i].description().equals(dogNames[k])) {
                    fourDes[k] = allDogs[i].description();
                }
            }
        }

        ArrayList results = new ArrayList<CardObject>();
        //Drawable image =  ContextCompat.getDrawable(this, R.drawable.snow_mia);
        for (int i = 0; i < dogNames.length; i++) {
            CardObject obj = new CardObject(dogNames[i], fourDes[i], fourDogPics[i]);
            results.add(i, obj);
        }
        return results;
    }
}
