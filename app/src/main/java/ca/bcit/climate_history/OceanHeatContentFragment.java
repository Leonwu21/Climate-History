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

/**
 * Class for fragment to display the ocean heat content.
 * @author Benedict Halim
 * @author Leon Wu
 * @version 1.0
 */
public class OceanHeatContentFragment extends Fragment {

    /**
     * Reads in file, parses the information, and stores it in a list of DataEntry objects.
     * @param filename The name of text file to read.
     * @param data The list of DataEntry objects.
     * @throws IOException Throw exception if the file is not found.
     */
    public void readFile(String filename, List<DataEntry> data) throws IOException {
        ArrayList<String> years = new ArrayList<>();
        ArrayList<Double> nh = new ArrayList<>();
        ArrayList<Double> sh = new ArrayList<>();

        InputStream is = null;

        //Try to open files. If one of the files not found, throw exception.
        try {
            is = getContext().getAssets().open(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;

        //Read through the file and feed into arrays based on ; delimiter.
        while ((line = reader.readLine()) != null) {
            String [] nums = line.split(";");
            years.add(nums[0]);
            nh.add(Double.parseDouble(nums[3]));
            sh.add(Double.parseDouble(nums[5]));
        }

        //Iterate through the arrays to feed into graph.
        for (int i = 0; i < years.size(); i++) {
            data.add(new OceanHeatContentFragment.CustomDataEntry(years.get(i), nh.get(i), sh.get(i)));
        }
    }

    /**
     * Inflates the view with the fragment. Also creates the graph.
     * @param inflater The layout to inflate with.
     * @param container The container.
     * @param savedInstanceState The saved state of the instance.
     * @return The view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_ocean_heat_content, container, false);
        AnyChartView anyChartView = (AnyChartView) rootView.findViewById(R.id.oceanHeatContent_graph);

        // Set up the lines
        Cartesian cartesian = AnyChart.line();
        cartesian.animation(true);
        cartesian.padding(10d, 20d, 5d, 20d);
        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        // Set up the x and y-axis
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.title("Ocean Heat Content Changes");
        cartesian.yAxis(0).title("Zettajoules");
        cartesian.xAxis(0).title("Year");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        // Read from data
        List<DataEntry> seriesData = new ArrayList<>();
        try {
            readFile("OceanHeatContent.txt", seriesData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Maps the series of data
        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");

        // Plot data for northern hemisphere's ocean heat content.
        Line series1 = cartesian.line(series1Mapping);
        series1.name("Northern Hemisphere");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        // Plot data for southern hemisphere's ocean heat content.
        Line series2 = cartesian.line(series2Mapping);
        series2.name("Southern Hemisphere");
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

    /**
     * CustomDataEntry object to be used for the graph.
     */
    private static class CustomDataEntry extends ValueDataEntry {

        /**
         * Helper method to enter the data into the graph.
         * @param x The message to be fed into the graph.
         * @param value The first value to be fed into the graph.
         * @param value2 The second value to be fed into the graph.
         */
        CustomDataEntry(String x, Number value, Number value2) {
            super(x, value);
            setValue("value2", value2);
        }
    }
}
