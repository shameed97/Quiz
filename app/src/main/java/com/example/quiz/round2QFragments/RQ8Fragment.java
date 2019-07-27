package com.example.quiz.round2QFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.quiz.Mysingleton;
import com.example.quiz.R;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class RQ8Fragment extends Fragment {

    private RadioGroup group;
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4;
    private String mark_url = "http://192.168.43.11/tech/mark2.php";
    String team;
    String mark = "2";
    public RQ8Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_rq8, container, false);
        team = getArguments().getString("sha");
        group = view.findViewById(R.id.radioGroup);
        radioButton1 = view.findViewById(R.id.rbt1);
        radioButton2 = view.findViewById(R.id.rbt2);
        radioButton3 = view.findViewById(R.id.rbt3);
        radioButton4 = view.findViewById(R.id.rbt4);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.rbt1:
                        radioButton2.setEnabled(false);
                        radioButton3.setEnabled(false);
                        radioButton4.setEnabled(false);
                        break;

                    case R.id.rbt2:
                        radioButton1.setEnabled(false);
                        radioButton3.setEnabled(false);
                        radioButton4.setEnabled(false);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, mark_url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("sha", response);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("response", error.toString());
                                error.printStackTrace();

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("round1", String.valueOf(Integer.parseInt(mark)));
                                params.put("team_name", team);
                                return params;
                            }
                        };
                        Mysingleton.getInstance(getContext()).addtoRequest(stringRequest);
                        break;

                    case R.id.rbt3:
                        radioButton2.setEnabled(false);
                        radioButton1.setEnabled(false);
                        radioButton4.setEnabled(false);
                        break;

                    case R.id.rbt4:
                        radioButton2.setEnabled(false);
                        radioButton3.setEnabled(false);
                        radioButton1.setEnabled(false);
                        break;

                }

            }
        });
        return view;
    }

}
