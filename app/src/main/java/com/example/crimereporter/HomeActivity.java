package com.example.crimereporter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    Button reportMissPersonButton, reportCrimeButton, reportSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        reportCrimeButton = (Button)findViewById(R.id.reportCrimeButton);
        reportMissPersonButton = (Button)findViewById(R.id.reportMissPersonButton);
        reportSearchButton = (Button)findViewById(R.id.reportSearchButton);

        reportCrimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,ReportActivity.class);
                startActivity(intent);
            }
        });
    }
}
