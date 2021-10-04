package ca.bcit.climate_history;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class ArcticSeaIceMinimum extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arctic_sea_ice_minimum);
    }

    // Navigates back to MainActivity on button click
    public void onBackClick(View v) {
        finish();
    }
}