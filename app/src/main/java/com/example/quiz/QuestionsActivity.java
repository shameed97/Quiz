package com.example.quiz;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Locale;

public class QuestionsActivity extends AppCompatActivity {

    private static final long START_TIME_IN_MILLIS = 300000;//300000
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private AlertDialog.Builder builder;
    private RadioGroup group;
    private Button bt;
    private int count = 0;
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        bt = findViewById(R.id.bt);
        builder = new AlertDialog.Builder(this);
        mTextViewCountDown = findViewById(R.id.timer);
        startTimer();

        //Add Question1 Fragment
        fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            Q1Fragment q1Fragment = new Q1Fragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, q1Fragment, null);
            fragmentTransaction.commit();
        }
        //EndAdd Question1 Fragment

    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                builder.setTitle("Time Up :");
                builder.setMessage("Better Luck Next Time.\n\nEliminated from this Game...");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        QuestionsActivity.super.onBackPressed();

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        }.start();

    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }

    @Override
    public void onBackPressed() {
        builder.setTitle("Something went wrong:");
        builder.setMessage("If you go back you eliminated from this game");
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();

            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                QuestionsActivity.super.onResume();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void moveNext(View view) {
       /* Q1Fragment a = (Q1Fragment) getSupportFragmentManager().findFragmentById(R.id.f1);
        Q2Fragment b = (Q2Fragment) getSupportFragmentManager().findFragmentById(R.id.f2);
        Q3Fragment c = (Q3Fragment) getSupportFragmentManager().findFragmentById(R.id.f3);

        if (a == null)
        {
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, new Q2Fragment(), null).commit();

        }
        else if (b == null) {
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, new Q3Fragment(), null).commit();
        }*/

        count++;
        Log.d("sha", "count" + count);
        if (count == 1) {
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, new Q2Fragment(), null).commit();

        }
        if (count == 2) {
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, new Q3Fragment(), null).commit();
        }
        if (count == 3) {
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, new Q4Fragment(), null).commit();
        }
        if (count == 4) {
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, new Q5Fragment(), null).commit();
        }
        if (count == 5) {
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, new Q6Fragment(), null).commit();
        }
        if (count == 6) {
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, new Q7Fragment(), null).commit();
        }
        if (count == 7) {
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, new Q8Fragment(), null).commit();
        }
        if (count == 8) {
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, new Q9Fragment(), null).commit();
        }
        if (count == 9) {
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, new Q10Fragment(), null).commit();
        }
    }

}
