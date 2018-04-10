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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecycleViewActivity(getDataSet());
        mRecyclerView.setAdapter(mAdapter);

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

    private ArrayList<CardObject> getDataSet() {
        ArrayList results = new ArrayList<CardObject>();
        Drawable image =  ContextCompat.getDrawable(this, R.drawable.snow_mia);
        for (int i = 0; i < 4; i++) {
            CardObject obj = new CardObject("Golden Retriever" + i, "description text. description text. description text. description text. description text. description text.", image);
            results.add(i, obj);
        }
        return results;
    }
}
