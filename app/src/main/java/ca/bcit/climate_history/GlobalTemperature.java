package ca.bcit.climate_history;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class GlobalTemperature extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_temperature);
    }

    // Navigates back to MainActivity on button click
    public void onBackClick(View v) {
        finish();
    }
}