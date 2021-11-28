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

public class IceSheetsFragment extends Fragment {

    /**
     * Reads in file, parses the information, and stores it in a list of DataEntry objects
     * @param filename1 name of first text file to read
     * @param filename2 name of second text file to read
     * @param data list of DataEntry objects
     * @throws IOException if file not found
     */
    public void readFile(String filename1, String filename2, List<DataEntry> data) throws IOException {
        ArrayList<String> years = new ArrayList<>();
        ArrayList<Double> massVar1 = new ArrayList<>();
        ArrayList<Double> massVar2 = new ArrayList<>();

        InputStream is1 = null;
        InputStream is2 = null;
        try {
            is1 = getContext().getAssets().open(filename1);
            is2 = getContext().getAssets().open(filename2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(is1));
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(is2));
        String line;
        while ((line = reader1.readLine()) != null) {
            String [] nums = line.split(";");
            years.add(nums[0]);
            massVar1.add(Double.parseDouble(nums[1]));
        }
        String line2;
        while ((line2 = reader2.readLine()) != null) {
            String [] nums = line2.split(";");
            massVar2.add(Double.parseDouble(nums[1]));
        }
        for (int i = 0; i < years.size(); i++) {
            data.add(new IceSheetsFragment.CustomDataEntry(years.get(i), massVar1.get(i), massVar2.get(i)));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnyChartView anyChartView = getView().findViewById(R.id.is_graph);

        Cartesian cartesian = AnyChart.line();
        cartesian.animation(true);
        cartesian.padding(10d, 20d, 5d, 20d);
        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.title("Antarctica/Greenland Mass Variation");
        cartesian.yAxis(0).title("Mass (gigatonnes)");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> seriesData = new ArrayList<>();
        try {
            readFile("IceSheetsAntarctica.txt", "IceSheetsGreenland.txt", seriesData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Antarctica");
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
        series2.name("Greenland");
        series2.stroke("1 red");
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

        return inflater.inflate(R.layout.fragment_ice_sheets, container, false);
    }

    // Custom DataEntry object for graph
    static class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2) {
            super(x, value);
            setValue("value2", value2);
        }

    }
}
