package com.example.rayan.tingrr_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class DogInfo extends AppCompatActivity {
    ImageView dogPic;
    Switch saveDog;
    TextView description;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_info);

        String dogName = "Golden Retriever";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            dogName = extras.getString("Dog Name");
        }
        setTitle(dogName);

        dogPic = (ImageView) findViewById(R.id.imgDog);
        saveDog = (Switch) findViewById(R.id.switch1);
        description = (TextView) findViewById(R.id.txtDogDes);
        SharedPreferences mPrefs = getSharedPreferences("shared prefs", 0);
        String rawStoredDogs = mPrefs.getString("storedDogs", "");
        String[] storedDogs = rawStoredDogs.split(";");
        if (checkList(storedDogs, dogName) >=0 ) {
            saveDog.setChecked(true);
        }

        final String finalDogName = dogName;
        saveDog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences mPrefs = getSharedPreferences("shared prefs", 0);
                String rawStoredDogs = mPrefs.getString("storedDogs", "");
                String[] storedDogs = rawStoredDogs.split(";");

                if (saveDog.isChecked()) {
                    if (checkList(storedDogs, finalDogName) < 0) {
                        String newStoredDogs = "";
                        for (int i = 0; i < storedDogs.length; i++) {
                            newStoredDogs = newStoredDogs + storedDogs[i] + ";";
                        }
                        newStoredDogs+=finalDogName;
                        SharedPreferences.Editor mEditor = mPrefs.edit();
                        mEditor.putString("storedDogs", newStoredDogs).apply(); //try commit?
                    }
                } else if (!saveDog.isChecked()) {
                    if (checkList(storedDogs, finalDogName) >= 0) {
                        String newStoredDogs = "";
                        for (String dog : storedDogs) {
                            if (!dog.equals(finalDogName)) {
                                newStoredDogs += dog + ";";
                            }
                        }
                        if (newStoredDogs.length() > 2) { //perhaps remove
                            newStoredDogs = newStoredDogs.substring(0, newStoredDogs.length() - 2);
                        }
                        SharedPreferences.Editor mEditor = mPrefs.edit();
                        mEditor.putString("storedDogs", newStoredDogs).apply(); //try commit?
                    }
                }
            }
        });

        String line = null;
        String[] allDogNames = new String[29];
        Drawable[] dogPics = new Drawable[29];
        Dog[] allDogs = new Dog[29];
        for (int i = 1; i <= 29; i++) {
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
                Dog newDog = new Dog(name, size, space, energyLevel, grooming, climate, allergenic, kids);
                allDogs[j] = newDog;
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

        int correctIndex = 0;
        for (int i = 0; i < allDogNames.length; i++) {
            if (allDogNames[i].equals(dogName)) {
                dogPic.setImageDrawable(dogPics[i]);
                correctIndex = i;
            }
        }

        description.setText(allDogs[correctIndex].description());

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

    private int checkList(String[] list, String name) {
        for(int i = 0; i< list.length; i++) {
            if (list[i].equals(name)) {
                return i;
            }
        }
        return -1;
    }
}
