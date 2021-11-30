package ca.bcit.climate_history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/**
 * Main page fragment view.
 * @author Benedict Halim
 * @author Leon Wu
 * @version 1.0
 */
public class MainFragment extends Fragment {

    /**
     * Inflates the view with the fragment.
     * @param inflater The layout to inflate with.
     * @param container The container.
     * @param savedInstanceState The saved state of the instance.
     * @return The view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
