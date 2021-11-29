package ca.bcit.climate_history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ArcticSeaIceMinimumFragment extends Fragment {

    /**
     * Reads in file, parses the information, and stores it in a list of DataEntry objects
     * @param filename name of text file to read
     * @param data list of DataEntry objects
     * @throws IOException if file not found
     */
    public void readFile(String filename, List<DataEntry> data) throws IOException {
        ArrayList<String> years = new ArrayList<>();
        ArrayList<Double> extent = new ArrayList<>();
        ArrayList<Double> area = new ArrayList<>();

        InputStream is = null;
        try {
            is = getContext().getAssets().open(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = reader.readLine()) != null) {
            String [] nums = line.split(";");
            years.add(nums[0]);
            extent.add(Double.parseDouble(nums[1]));
            area.add(Double.parseDouble(nums[2]));
        }
        for (int i = 0; i < years.size(); i++) {
            data.add(new ArcticSeaIceMinimumFragment.CustomDataEntry(years.get(i), extent.get(i), area.get(i)));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_arctic_sea_ice_minimum, container, false);
        AnyChartView anyChartView = (AnyChartView) rootView.findViewById(R.id.arcticSeaIceMinimum_graph);

        Cartesian cartesian = AnyChart.line();
        cartesian.animation(true);
        cartesian.padding(10d, 20d, 5d, 20d);
        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.title("Average September Minimum Extent/Area");
        cartesian.yAxis(0).title("Million Square km");
        cartesian.xAxis(0).title("Year");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> seriesData = new ArrayList<>();
        try {
            readFile("ArcticSeaIceMinimum.txt", seriesData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Extent");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series2 = cartesian.line(series2Mapping);
        series2.name("Area");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);

        return rootView;
    }

    // Custom DataEntry object for graph
    private static class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2) {
            super(x, value);
            setValue("value2", value2);
        }

    }
}
