package com.example.carebridge;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

public class DescriptionActivity extends AppCompatActivity {

     private final String TAG = "DescriptionActivity";
     private TextView syptomtext;
     private TextView destext;
     private ImageView desimage;
     private ImageButton menuButton;
     private TextToSpeech tts;
     private  String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    // Initialization successful, you can now use the TextToSpeech object
                    int result = tts.setLanguage(Locale.getDefault()); // Set language
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e(TAG, "Unsupported language for text to speech!");
                    }
                } else {
                    // Initialization failed
                    Toast.makeText(DescriptionActivity.this, "Failed to initialize text to speech!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_des);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        menuButton = (ImageButton)findViewById(R.id.menuBtn);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(DescriptionActivity.this, menuButton);
                popup.getMenuInflater().inflate(R.menu.speak_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int menuId = item.getItemId();
                        if (tts != null) {
                            if (menuId == R.id.startSpeech) {
                                tts.speak(content, TextToSpeech.QUEUE_FLUSH, null);
                            } else if (menuId == R.id.stopSpeech) {
                                if (tts.isSpeaking()) tts.stop();
                            }
                        } else {
                            Toast.makeText(DescriptionActivity.this, "Uninitialzied text to speech!", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

        Intent intent = getIntent();
        setTitle(intent.getStringExtra("destitle"));

        desimage = findViewById(R.id.desImageViewId);
        syptomtext = findViewById(R.id.symptomText);
        destext = findViewById(R.id.descriptionText);

        desimage.setImageResource(intent.getIntExtra("desimage",0));
        Spanned symptom = Html.fromHtml(intent.getStringExtra("dessymp"));
        Spanned treatment = Html.fromHtml(intent.getStringExtra("destext"));
        content = symptom.toString() + treatment.toString();
        syptomtext.setText(symptom);
        destext.setText(treatment);
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.shutdown();
        }
        super.onDestroy();
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
