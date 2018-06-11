package com.example.android.navdrawerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LecturerProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_profile);

        Button lecturerpage = findViewById(R.id.lecturer_create_profile);

        lecturerpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lecturerclass = new Intent(LecturerProfileActivity.this, YearActivity.class);
                startActivity(lecturerclass);
            }
        });
    }
}
