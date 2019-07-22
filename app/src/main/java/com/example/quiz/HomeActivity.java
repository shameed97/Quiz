package com.example.quiz;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private Dialog dialog;
    private Button button1, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        dialog = new Dialog(this);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button2.setEnabled(false);


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

                startActivity(new Intent(HomeActivity.this, QuestionsActivity.class));
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    // End  Code Round1 button with Dialog window
    /*@Override
    public void onBackPressed() {
        button1.setEnabled(false);
        button2.setEnabled(true);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Its Working...", Toast.LENGTH_SHORT).show();
            }
        });
        super.onBackPressed();
    }*/

}
