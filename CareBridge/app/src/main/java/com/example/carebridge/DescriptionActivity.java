package com.example.carebridge;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DescriptionActivity extends AppCompatActivity {

     TextView syptomtext;
     TextView destext;
     ImageView desimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_des);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        setTitle(intent.getStringExtra("destitle"));

        desimage = findViewById(R.id.desImageViewId);
        syptomtext = findViewById(R.id.symptomText);
        destext = findViewById(R.id.descriptionText);

        desimage.setImageResource(intent.getIntExtra("desimage",0));
        syptomtext.setText(Html.fromHtml(intent.getStringExtra("dessymp")));
        destext.setText(Html.fromHtml(intent.getStringExtra("destext")));
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
