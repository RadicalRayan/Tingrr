package com.example.rayan.tingrr_1;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class DogInfo extends AppCompatActivity {
    ImageView dogPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_info);

        dogPic = (ImageView) findViewById(R.id.imgDog);

        String dogName = "Golden Retriever";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            dogName = extras.getString("Dog Name");
        }
        setTitle(dogName);

        String line = null;
        String[] allDogNames = new String[29];
        Drawable[] dogPics = new Drawable[29];
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

        for (int i = 0; i < allDogNames.length; i++) {
            if (allDogNames[i].equals(dogName)) {
                dogPic.setImageDrawable(dogPics[i]);
            }
        }
    }
}
