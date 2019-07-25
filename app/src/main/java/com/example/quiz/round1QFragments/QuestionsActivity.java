package com.example.quiz.round1QFragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quiz.HomeActivity;
import com.example.quiz.Mysingleton;
import com.example.quiz.R;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class QuestionsActivity extends AppCompatActivity {

    private static final long START_TIME_IN_MILLIS = 300000;//300000
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private AlertDialog.Builder builder;
    private int count = 0;
    private FragmentManager fragmentManager;
    private String team_name;
    private Button button;
    private String markSelect_url = "http://192.168.43.11/tech/markReturn.php";
    private Dialog dialog,dialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        //Getting Team Name from HomeActivity
        Intent intent = getIntent();
        team_name = intent.getStringExtra("team_name");
        //End Getting Team Name from HomeActivity
        button = findViewById(R.id.bt);
        builder = new AlertDialog.Builder(this);
        dialog=new Dialog(this);
        dialog1=new Dialog(this);
        mTextViewCountDown = findViewById(R.id.timer);
        startTimer();

        //Add Question1 Fragment
        fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("sha", team_name);
            Q1Fragment q1Fragment = new Q1Fragment();
            q1Fragment.setArguments(bundle);
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
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();

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
                QuestionsActivity.super.onResume();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void moveNext(View view) {
        //Placing Fragments by Button Click
        count++;
        //Send Team name to Fragments using bundle
        Bundle bundle = new Bundle();
        bundle.putString("sha", team_name);
        //End Send Team name to Fragments using bundle

        if (count == 1) {
            new Q1Fragment().onDetach();
            Q2Fragment q2Fragment = new Q2Fragment();
            q2Fragment.setArguments(bundle);
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, q2Fragment, null).commit();

        }
        if (count == 2) {
            new Q2Fragment().onDetach();
            Q3Fragment q3Fragment = new Q3Fragment();
            q3Fragment.setArguments(bundle);
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, q3Fragment, null).commit();
        }
        if (count == 3) {
            new Q3Fragment().onDetach();
            Q4Fragment q4Fragment = new Q4Fragment();
            q4Fragment.setArguments(bundle);
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, q4Fragment, null).commit();
        }
        if (count == 4) {
            new Q4Fragment().onDetach();
            Q5Fragment q5Fragment = new Q5Fragment();
            q5Fragment.setArguments(bundle);
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, q5Fragment, null).commit();
        }
        if (count == 5) {
            new Q5Fragment().onDetach();
            Q6Fragment q6Fragment = new Q6Fragment();
            q6Fragment.setArguments(bundle);
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, q6Fragment, null).commit();
        }
        if (count == 6) {
            new Q6Fragment().onDetach();
            Q7Fragment q7Fragment = new Q7Fragment();
            q7Fragment.setArguments(bundle);
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, q7Fragment, null).commit();
        }
        if (count == 7) {
            new Q7Fragment().onDetach();
            Q8Fragment q8Fragment = new Q8Fragment();
            q8Fragment.setArguments(bundle);
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, q8Fragment, null).commit();
        }
        if (count == 8) {
            new Q8Fragment().onDetach();
            Q9Fragment q9Fragment = new Q9Fragment();
            q9Fragment.setArguments(bundle);
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, q9Fragment, null).commit();
        }
        if (count == 9) {
            new Q9Fragment().onDetach();
            Q10Fragment q10Fragment = new Q10Fragment();
            q10Fragment.setArguments(bundle);
            this.fragmentManager.beginTransaction().replace(R.id.fragment_container, q10Fragment, null).commit();
            button.setText("Finish");
        }
        if (count == 10) {

            //final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, markSelect_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        TextView pop_textView,d1Text;
                        Button Butround2,ButExit;
                        String result=response;
                          dialog.setContentView(R.layout.select_popup);
                          dialog1.setContentView(R.layout.sad_popup);
                          dialog.setCancelable(false);
                          dialog1.setCancelable(false);
                          pop_textView=dialog.findViewById(R.id.scoreDis);
                          Butround2=dialog.findViewById(R.id.butRound2);
                          d1Text=dialog1.findViewById(R.id.d1score);
                          ButExit=dialog1.findViewById(R.id.butExit);
                         pop_textView.setText(result);
                         d1Text.setText(result);
                          int high_Score=25;
                          if (high_Score<=Integer.parseInt(result))
                        {
                            Butround2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent=new Intent(QuestionsActivity.this, HomeActivity.class);
                                    intent.putExtra("team_name",team_name);
                                    startActivity(intent);
                                    dialog.dismiss();
                                    finish();
                                }
                            });
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.show();
                        }
                        else
                        {
                            ButExit.setOnClickListener(new View.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                @Override
                                public void onClick(View v) {
                                    finishAffinity();
                                }
                            });
                            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog1.show();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
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

            Mysingleton.getInstance(QuestionsActivity.this).addtoRequest(stringRequest);

        }
        //End Placing Fragments by Button Click
    }


}
