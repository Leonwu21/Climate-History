package ca.bcit.climate_history;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCo2Click(View v) {
        Intent i = new Intent(this, CarbonDioxide.class);
        startActivity(i);
    }
    public void onGtClick(View v) {
        Intent i = new Intent(this, GlobalTemperature.class);
        startActivity(i);
    }
    public void onAsimClick(View v) {
        Intent i = new Intent(this, ArcticSeaIceMinimum.class);
        startActivity(i);
    }
    public void onIsClick(View v) {
        Intent i = new Intent(this, IceSheets.class);
        startActivity(i);
    }
    public void onSlClick(View v) {
        Intent i = new Intent(this, SeaLevel.class);
        startActivity(i);
    }
    public void onOhcClick(View v) {
        Intent i = new Intent(this, OceanHeatContent.class);
        startActivity(i);
    }
}