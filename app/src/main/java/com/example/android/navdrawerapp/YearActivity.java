package com.example.android.navdrawerapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class YearActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year);

        Button jan = findViewById(R.id.jan);

        jan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent janIntent = new Intent(YearActivity.this, BSITActivity.class);
                Toast.makeText(view.getContext(), "Open the class of Jan intake", Toast.LENGTH_SHORT).show();
                startActivity(janIntent);
            }
        });

        Button may = findViewById(R.id.may);

        may.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent janIntent = new Intent(YearActivity.this, BSITActivity.class);
                Toast.makeText(view.getContext(), "Open the class of May intake", Toast.LENGTH_SHORT).show();
                startActivity(janIntent);
            }
        });

        Button sept = findViewById(R.id.sept);

        sept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent janIntent = new Intent(YearActivity.this, BSITActivity.class);
                Toast.makeText(view.getContext(), "Open the class of September intake", Toast.LENGTH_SHORT).show();
                startActivity(janIntent);
            }
        });
    }
}
