package com.example.quiz.round2QFragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.quiz.Mysingleton;
import com.example.quiz.R;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Question2Activity extends AppCompatActivity {

    private static final long START_TIME_IN_MILLIS = 300000;
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private AlertDialog.Builder builder;
    private int count = 0;
    private FragmentManager fragmentManager;
    private String team_name;
    private Button button;
    private String markSelect_url = "http://192.168.43.11/tech/markReturn2.php";
    private Dialog dialog;
    private String result;

    private final String NOTIFICATION_CHANNEL = "result notification";
    private final int NOTIFICATION_ID = 001;
    private static int NOTIFICATION_TIME = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);

        //Getting Team Name from HomeActivity
        Intent intent = getIntent();
        team_name = intent.getStringExtra("team_name");
        //End Getting Team Name from HomeActivity
        button = findViewById(R.id.bt);
        builder = new AlertDialog.Builder(this);
        mTextViewCountDown = findViewById(R.id.timer);
        dialog = new Dialog(this);
        startTimer();

        //Add Question1 Fragment
        fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("sha", team_name);
            RQ1Fragment rq1Fragment = new RQ1Fragment();
            rq1Fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, rq1Fragment, null);
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
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();

                    }
                });

                try {
                    if (!Question2Activity.this.isFinishing()) {
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }

                } catch (Exception e) {
                    Log.d("res", "Error" + e);
                }

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
        builder.setCancelable(false);
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
                Question2Activity.super.onResume();
            }
        });
        try {
            if (!Question2Activity.this.isFinishing()) {
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

        } catch (Exception e) {
            Log.d("res", "Error" + e);
        }

    }

    public void moveNext(View view) {
        //Placing Fragments by Button Click
        count++;
        //Send Team name to Fragments using bundle
        Bundle bundle = new Bundle();
        bundle.putString("sha", team_name);
        //End Send Team name to Fragments using bundle

        if (count == 1) {
            new RQ1Fragment().onDetach();
            RQ2Fragment rq2Fragment = new RQ2Fragment();
            rq2Fragment.setArguments(bundle);
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, rq2Fragment, null).commit();

        }
        if (count == 2) {
            new RQ2Fragment().onDetach();
            RQ3Fragment rq3Fragment = new RQ3Fragment();
            rq3Fragment.setArguments(bundle);
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, rq3Fragment, null).commit();
        }
        if (count == 3) {
            new RQ3Fragment().onDetach();
            RQ4Fragment rq4Fragment = new RQ4Fragment();
            rq4Fragment.setArguments(bundle);
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, rq4Fragment, null).commit();
        }
        if (count == 4) {
            new RQ4Fragment().onDetach();
            RQ5Fragment rq5Fragment = new RQ5Fragment();
            rq5Fragment.setArguments(bundle);
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, rq5Fragment, null).commit();
        }
        if (count == 5) {
            new RQ5Fragment().onDetach();
            RQ6Fragment rq6Fragment = new RQ6Fragment();
            rq6Fragment.setArguments(bundle);
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, rq6Fragment, null).commit();
        }
        if (count == 6) {
            new RQ6Fragment().onDetach();
            RQ7Fragment rq7Fragment = new RQ7Fragment();
            rq7Fragment.setArguments(bundle);
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, rq7Fragment, null).commit();
        }
        if (count == 7) {
            new RQ7Fragment().onDetach();
            RQ8Fragment rq8Fragment = new RQ8Fragment();
            rq8Fragment.setArguments(bundle);
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, rq8Fragment, null).commit();
        }
        if (count == 8) {
            new RQ8Fragment().onDetach();
            RQ9Fragment rq9Fragment = new RQ9Fragment();
            rq9Fragment.setArguments(bundle);
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, rq9Fragment, null).commit();
        }
        if (count == 9) {
            new RQ9Fragment().onDetach();
            RQ10Fragment rq10Fragment = new RQ10Fragment();
            rq10Fragment.setArguments(bundle);
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, rq10Fragment, null).commit();
        }
        if (count == 10) {
            new RQ10Fragment().onDetach();
            RQ11Fragment rq11Fragment = new RQ11Fragment();
            rq11Fragment.setArguments(bundle);
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, rq11Fragment, null).commit();
        }
        if (count == 11) {
            new RQ11Fragment().onDetach();
            RQ12Fragment rq12Fragment = new RQ12Fragment();
            rq12Fragment.setArguments(bundle);
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, rq12Fragment, null).commit();
        }
        if (count == 12) {
            new RQ12Fragment().onDetach();
            RQ13Fragment rq13Fragment = new RQ13Fragment();
            rq13Fragment.setArguments(bundle);
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, rq13Fragment, null).commit();
        }
        if (count == 13) {
            new RQ13Fragment().onDetach();
            RQ14Fragment rq14Fragment = new RQ14Fragment();
            rq14Fragment.setArguments(bundle);
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, rq14Fragment, null).commit();
        }
        if (count == 14) {
            new RQ14Fragment().onDetach();
            RQ15Fragment rq15Fragment = new RQ15Fragment();
            rq15Fragment.setArguments(bundle);
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, rq15Fragment, null).commit();
            button.setText("Finish");
        }
        if (count == 15) {
            mCountDownTimer.cancel();
            //final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, markSelect_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    int highScore = 20;
                    result = response;
                    Button Butround2;
                    dialog.setContentView(R.layout.round3_select);
                    dialog.setCancelable(false);
                    Butround2 = dialog.findViewById(R.id.but_round3);

                    Butround2.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            finishAffinity();
                        }
                    });
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                    if (highScore <= Integer.parseInt(result)) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                displayNotification();
                            }
                        },NOTIFICATION_TIME);

                    } else {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                displayNotificationError();
                            }
                        },NOTIFICATION_TIME);

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("res", error.toString());
                    error.printStackTrace();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("team_name", team_name);
                    return params;
                }
            };

            Mysingleton.getInstance(Question2Activity.this).addtoRequest(stringRequest);

        }
        //End Placing Fragments by Button Click
    }

    //Code for Notification
    public void displayNotification() {
        NotificationCompat.Builder builder1 = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL);
        builder1.setSmallIcon(R.drawable.logi_kutty);
        builder1.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        builder1.setSound(Uri.parse("android.resource://com.example.quiz/" + R.raw.noti));
        builder1.setContentTitle("Result Notification :");
        builder1.setContentText("your Score is" + result);
        builder1.setSubText("Selected To Third Round...!");
        builder1.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder1.build());

    }

    public void displayNotificationError() {
        NotificationCompat.Builder builder1 = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL);
        builder1.setSmallIcon(R.drawable.logi_kutty);
        builder1.setSound(Uri.parse("android.resource://com.example.quiz/" + R.raw.noti));
        builder1.setContentTitle("Result Notification :");
        builder1.setContentText("your Score is  :" + result);
        builder1.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        builder1.setSubText("Not Selected To Third Round...!");
        builder1.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder1.build());

    }

    //End Code for Notification

}
