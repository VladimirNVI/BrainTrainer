package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView textViewTimer;
    private TextView textViewPoints;
    private TextView textViewExample;
    private TextView textViewAnswer1;
    private TextView textViewAnswer2;
    private TextView textViewAnswer3;
    private TextView textViewAnswer4;
    private int currentPoint;
    private int level;
    private int maxResult;
    private String goodAnswer;

    private SharedPreferences preferences;
    private static final String PREFERENCES_KEY = "maxPoint";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        currentPoint = 0;
        level = 0;
        maxResult = preferences.getInt(PREFERENCES_KEY,0);
        updatePoints();
        setTimer();
        generateRandomExample();
        textViewSetOnClick();


    }

    private void initViews() {
        textViewTimer = findViewById(R.id.textViewTimer);
        textViewPoints = findViewById(R.id.textViewPoints);
        textViewExample = findViewById(R.id.textViewExample);
        ;
        textViewAnswer1 = findViewById(R.id.textViewAnswer1);
        textViewAnswer2 = findViewById(R.id.textViewAnswer2);
        textViewAnswer3 = findViewById(R.id.textViewAnswer3);
        textViewAnswer4 = findViewById(R.id.textViewAnswer4);

    }

    private void textViewSetOnClick() {

        textViewAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGoodAnswer((TextView) v);
            }
        });

        textViewAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGoodAnswer((TextView) v);
            }
        });

        textViewAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGoodAnswer((TextView) v);
            }
        });

        textViewAnswer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGoodAnswer((TextView) v);
            }
        });

    }


    private void checkGoodAnswer(TextView textViewAnswer) {
        if (goodAnswer.equals(textViewAnswer.getText().toString().trim())) {
            Toast.makeText(MainActivity.this, "Верно!", Toast.LENGTH_SHORT).show();
            currentPoint++;
            level++;
            updatePoints();
            generateRandomExample();

        } else {
            Toast.makeText(MainActivity.this, "Неверно!", Toast.LENGTH_SHORT).show();
            level++;
            updatePoints();
            generateRandomExample();
        }
    }

    private void updatePoints() {
        String result = String.format("%s / %s", String.valueOf(currentPoint), String.valueOf(level));
        textViewPoints.setText(result);
    }


    private void setTimer() {
        CountDownTimer timer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                seconds++;
                String textTimer = "00:";
                int colorResId;
                if (seconds >= 10) {
                    textViewTimer.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.green));
                    textViewTimer.setText(String.format("%s%s", textTimer, Integer.toString(seconds)));
                } else {
                    textViewTimer.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.red));
                    textViewTimer.setText(String.format("%s0%s", textTimer, Integer.toString(seconds)));

                }
            }

            @Override
            public void onFinish() {

                textViewTimer.setText("00:00");
                maxResultCounter();
                Intent intent = ResultActivity.getIntent(MainActivity.this, currentPoint, maxResult);
                startActivity(intent);
            }
        };
        timer.start();
    }

    private void maxResultCounter() {
        if (currentPoint > maxResult) {
            maxResult = currentPoint;
            preferences.edit().putInt(PREFERENCES_KEY,maxResult).apply();

        }
    }


    private void generateRandomExample() {
        Random random = new Random();
        int a = random.nextInt(199) - 99; // Случайное число от -100 до 100
        int b = random.nextInt(199) - 99; // Случайное число от -100 до 100
        int operation = random.nextInt(2); // 0 - сложение, 1 - вычитание

        String example;
        int answer;

        if (operation == 0) {
            example = a + " + " + b;
            answer = a + b; // Сложение
        } else {
            example = a + " - " + b;
            answer = a - b; // Вычитание
        }

        textViewExample.setText(example);
        goodAnswer = (String.valueOf(answer));

        int textViewNumber = random.nextInt(4) + 1;
        switch (textViewNumber) {
            case 4:
                textViewAnswer1.setText(String.valueOf(answer));
                textViewAnswer2.setText(randomFailedAnswer());
                textViewAnswer3.setText(randomFailedAnswer());
                textViewAnswer4.setText(randomFailedAnswer());
                break;
            case 3:
                textViewAnswer2.setText(String.valueOf(answer));
                textViewAnswer1.setText(randomFailedAnswer());
                textViewAnswer3.setText(randomFailedAnswer());
                textViewAnswer4.setText(randomFailedAnswer());
                break;
            case 2:
                textViewAnswer3.setText(String.valueOf(answer));
                textViewAnswer2.setText(randomFailedAnswer());
                textViewAnswer1.setText(randomFailedAnswer());
                textViewAnswer4.setText(randomFailedAnswer());
                break;
            case 1:
                textViewAnswer4.setText(String.valueOf(answer));
                textViewAnswer3.setText(randomFailedAnswer());
                textViewAnswer2.setText(randomFailedAnswer());
                textViewAnswer1.setText(randomFailedAnswer());

        }

    }

    private String randomFailedAnswer() {
        Random random = new Random();
        int failedAnswer = random.nextInt(199) - 99; // Случайное число от -100 до 100
        return String.valueOf(failedAnswer);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
}