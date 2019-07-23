package com.example.quiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    String Name, Roll_1, Roll_2;
    private EditText team, roll1, roll2;
    private AlertDialog.Builder builder;
    private String url = "http://192.168.43.11/tech/insert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        builder = new AlertDialog.Builder(this);
        team = findViewById(R.id.team_name);
        roll1 = findViewById(R.id.roll_1);
        roll2 = findViewById(R.id.roll_2);
    }

    public void playGame(View view) {
        Name = team.getText().toString();
        Roll_1 = roll1.getText().toString();
        Roll_2 = roll2.getText().toString();

        if (Name.equals("") || Roll_1.equals("") || Roll_2.equals("")) {
            builder.setTitle("Something Went Wrong :");
            builder.setMessage("Please fill all the fields....");
            builder.setCancelable(false);
            displayAlert("input_error");
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String code = jsonObject.getString("code");
                        String message = jsonObject.getString("message");
                        builder.setTitle("Login Information...");
                        builder.setMessage(message);
                        builder.setCancelable(false);
                        displayAlert(code);
                    } catch (JSONException e) {
                        e.printStackTrace();
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
                    params.put("team", Name);
                    params.put("roll_1", Roll_1);
                    params.put("roll_2", Roll_2);
                    return params;
                }
            };

            Mysingleton.getInstance(LoginActivity.this).addtoRequest(stringRequest);
        }
    }


    //For Alert Dialog
    public void displayAlert(final String code) {
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (code.equals("input_error")) {
                    team.setText("");
                    roll1.setText("");
                    roll2.setText("");
                } else if (code.equals("reg_success")) {

                    Intent intent=new Intent(new Intent(LoginActivity.this, HomeActivity.class));
                    intent.putExtra("team_name",Name);
                    startActivity(intent);
                }

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    //End Alert Dialog
}
