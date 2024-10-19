package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    private static final String EXTRA_CURRENTPOINT = "currentPoint";
    private static final String EXTRA_MAXPOINT = "maxPoint";

    private TextView textViewResult;
    private Button restartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initViews();
        int currentPoint = getIntent().getIntExtra(EXTRA_CURRENTPOINT,0);
        int maxPoint = getIntent().getIntExtra(EXTRA_MAXPOINT,0);
        String result = String.format("Ваш резултат: %s\nМаксимальный резултат: %s", currentPoint, maxPoint);
        textViewResult.setText(result);

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = MainActivity.getIntent(ResultActivity.this);
               startActivity(intent);
            }
        });



    }

    public static Intent getIntent (Context context, int currentPoint, int maxPoint){
        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra(EXTRA_CURRENTPOINT,currentPoint);
        intent.putExtra(EXTRA_MAXPOINT,maxPoint);
        return intent;
    }

    private void initViews(){
        textViewResult = findViewById(R.id.textViewResult);
        restartButton = findViewById(R.id.restartButton);

    }


}