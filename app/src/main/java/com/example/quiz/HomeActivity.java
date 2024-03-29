package com.example.quiz;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.example.quiz.round1QFragments.QuestionsActivity;
import com.example.quiz.round2QFragments.Question2Activity;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private Dialog dialog, dialog2;
    public Button button1, button2;
    String team_name;
    public SharedPreferencesConfig sharedPreferences;
    private String markSelect_url = "http://192.168.43.11/tech/markReturn.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        dialog = new Dialog(this);
        dialog2 = new Dialog(this);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);

        sharedPreferences = new SharedPreferencesConfig(getApplicationContext());
        //Code for enabling and Disabling button
        StringRequest stringRequest = new StringRequest(Request.Method.POST, markSelect_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String check = response;
                if (25 <= Integer.parseInt(check)) {
                    button1.setEnabled(false);
                    button2.setEnabled(true);
                } else {
                    button2.setEnabled(false);
                    button1.setEnabled(true);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("sha", error.toString());
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
        Mysingleton.getInstance(this).addtoRequest(stringRequest);

        //End Code for Enabling and Disabling button

        //Getting Team Name from LoginActivity
        Intent intent = getIntent();
        team_name = intent.getStringExtra("team_name");
        //End Getting Team Name from LoginActivity

    }

    //Code Round1 button with Dialog window
    public void round1Start(View view) {

        Button play;
        TextView close;
        dialog.setContentView(R.layout.round1_popup);
        close = dialog.findViewById(R.id.txtClose);
        play = dialog.findViewById(R.id.butPlay);
        dialog.setCancelable(false);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, QuestionsActivity.class);
                intent.putExtra("team_name", team_name);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    // End  Code Round1 button with Dialog window

    //Code Round2 button with Dialog window
    public void round2Start(View view) {
        Button play;
        TextView close;
        dialog2.setContentView(R.layout.round2_popup);
        close = dialog2.findViewById(R.id.txtClose);
        play = dialog2.findViewById(R.id.butPlay);
        dialog2.setCancelable(false);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, Question2Activity.class);
                intent.putExtra("team_name", team_name);
                startActivity(intent);
                dialog2.dismiss();
            }
        });
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.show();
    }


}
// End  Code Round2 button with Dialog window