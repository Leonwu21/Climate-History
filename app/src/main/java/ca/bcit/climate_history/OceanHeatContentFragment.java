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

public class OceanHeatContentFragment extends Fragment {

    /**
     * Reads in file, parses the information, and stores it in a list of DataEntry objects
     * @param filename name of text file to read
     * @param data list of DataEntry objects
     * @throws IOException if file not found
     */
    public void readFile(String filename, List<DataEntry> data) throws IOException {
        ArrayList<String> years = new ArrayList<>();
        ArrayList<Double> wo = new ArrayList<>();
        ArrayList<Double> wose = new ArrayList<>();
        ArrayList<Double> nh = new ArrayList<>();
        ArrayList<Double> nhse = new ArrayList<>();
        ArrayList<Double> sh = new ArrayList<>();
        ArrayList<Double> shse = new ArrayList<>();

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
            wo.add(Double.parseDouble(nums[1]));
            wose.add(Double.parseDouble(nums[2]));
            nh.add(Double.parseDouble(nums[3]));
            nhse.add(Double.parseDouble(nums[4]));
            sh.add(Double.parseDouble(nums[5]));
            shse.add(Double.parseDouble(nums[6]));
        }
        for (int i = 0; i < years.size(); i++) {
            data.add(new OceanHeatContentFragment.CustomDataEntry(years.get(i), wo.get(i), wose.get(i),
                    nh.get(i), nhse.get(i), sh.get(i), shse.get(i)));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_ocean_heat_content, container, false);
        AnyChartView anyChartView = (AnyChartView) rootView.findViewById(R.id.ohc_graph);

        Cartesian cartesian = AnyChart.line();
        cartesian.animation(true);
        cartesian.padding(10d, 20d, 5d, 20d);
        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.title("Ocean Heat Content Changes");
        cartesian.yAxis(0).title("zettajoules");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> seriesData = new ArrayList<>();
        try {
            readFile("OceanHeatContent.txt", seriesData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");
        Mapping series4Mapping = set.mapAs("{ x: 'x', value: 'value4' }");
        Mapping series5Mapping = set.mapAs("{ x: 'x', value: 'value5' }");
        Mapping series6Mapping = set.mapAs("{ x: 'x', value: 'value6' }");
        Mapping series7Mapping = set.mapAs("{ x: 'x', value: 'value7' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Year");
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
        series2.name("WO");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series3 = cartesian.line(series3Mapping);
        series3.name("WOse");
        series3.hovered().markers().enabled(true);
        series3.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series3.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series4 = cartesian.line(series4Mapping);
        series4.name("NH");
        series4.hovered().markers().enabled(true);
        series4.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series4.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series5 = cartesian.line(series5Mapping);
        series5.name("NHse");
        series5.hovered().markers().enabled(true);
        series5.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series5.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series6 = cartesian.line(series6Mapping);
        series6.name("SH");
        series6.hovered().markers().enabled(true);
        series6.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series6.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series7 = cartesian.line(series7Mapping);
        series7.name("SHse");
        series7.hovered().markers().enabled(true);
        series7.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series7.tooltip()
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

        CustomDataEntry(String x, Number value, Number value2, Number value3, Number value4,
                        Number value5, Number value6) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
            setValue("value4", value4);
            setValue("value5", value5);
            setValue("value6", value6);
        }
    }
}
