package ca.bcit.climate_history;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

/**
 * Main activity page.
 * @author Benedict Halim
 * @author Leon Wu
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * The layout drawer.
     */
    DrawerLayout drawerlayout;

    /**
     * Toggles the action bar.
     */
    ActionBarDrawerToggle actionBarDrawerToggle;

    /**
     * Creates the view. Also sets up the hamburger menu.
     * @param savedInstanceState The saved state of the instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerlayout = findViewById(R.id.drawer_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerlayout,
                R.string.nav_open_drawer,
                R.string.nav_close_drawer);
        drawerlayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment = new MainFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame, fragment);
        ft.commit();
    }

    /**
     * Sets what happens when a certain item is selected on the menu bar.
     * @param item The menu item to be selected.
     * @return True if menu item selected is valid. Otherwise, false.
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        Intent intent = null;

        switch(id) {
            case R.id.nav_carbonDioxide:
                fragment = new CarbonDioxideFragment();
                break;
            case R.id.nav_globalTemperature:
                fragment = new GlobalTemperatureFragment();
                break;
            case R.id.nav_arcticSeaIce:
                fragment = new ArcticSeaIceMinimumFragment();
                break;
            case R.id.nav_iceSheets:
                fragment = new IceSheetsFragment();
                break;
            case R.id.nav_seaLevel:
                fragment = new SeaLevelFragment();
                break;
            case R.id.nav_oceanHeatContent:
                fragment = new OceanHeatContentFragment();
                break;
            default:
                fragment = new MainFragment();
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        } else {
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * When the back button is pressed, close the hamburger menu if it's open.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Go to the option that's selected.
     * @param item The item to be selected.
     * @return True if the item is selected. Otherwise, false.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}