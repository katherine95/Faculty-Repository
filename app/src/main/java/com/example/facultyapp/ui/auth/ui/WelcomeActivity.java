package com.example.facultyapp.ui.auth.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Window;
import android.view.WindowManager;

import com.example.facultyapp.R;
import com.example.facultyapp.settings.Settings;
import com.example.facultyapp.ui.main.ui.LecturerActivity;
import com.example.facultyapp.ui.main.ui.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.cardViewStudent)
    CardView cardViewStudent;
    @BindView(R.id.cardViewLecturer)
    CardView cardViewLecturer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        cardViewStudent.setOnClickListener(view -> {
            Intent student = new Intent(getApplicationContext(), AuthActivity.class);
            student.putExtra("type", "0");
            startActivity(student);
        });

        cardViewLecturer.setOnClickListener(view -> {
            Intent lecturer = new Intent(getApplicationContext(), AuthActivity.class);
            lecturer.putExtra("type", "1");
            startActivity(lecturer);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Settings.isLoggedIn()) {
            if (Settings.isStudent()) {
                Intent auth = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(auth);
                finish();
            } else {
                Intent auth = new Intent(getApplicationContext(), LecturerActivity.class);
                startActivity(auth);
                finish();
            }
        }
    }
}
