package view;

import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import model.Cell;
import model.Grid;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Samuel Chan, William Francis, Sahil Gupta
 * @purpose Creates the cell chart used in the simulation
 * @assumptions Correctly specified properties in the .properties file
 * @dependencies Resource/properties file
 * @usage Called in SimulationViewer to display graph that maps the relative counts for each state
 */

public class CellChart {

    private static final int HEX_CONVERSION = 255;
    private static final int DURATION = 100;
    private static final int INSET_TOP = 10;
    private static final int INSET_RIGHT = 10;
    private static final int INSET_BOTTOM = 500;
    private static final int INSET_LEFT = 50;

    private List<XYChart.Series<Number, Number>> seriesList;
    private int startTime = 0;
    private int endTime = DURATION;
    private int currTime = 0;

    /**
     * Creates the chart itself on the grid, sets initial values and properties
     * @param grid
     * @return the linechart
     */
    public LineChart initChart(Grid grid) {

        ResourceBundle myResources;
        String resources = "resources";
        myResources = ResourceBundle.getBundle(resources);



        NumberAxis xAxis = new NumberAxis(startTime, endTime, 1);
        xAxis.setLabel(myResources.getString("GRAPH_X"));
        NumberAxis yAxis = new NumberAxis(0, grid.getNumCols() * grid.getNumRows(), 1);
        yAxis.setLabel(myResources.getString("GRAPH_Y"));
        LineChart lineChart = new javafx.scene.chart.LineChart(xAxis, yAxis);
        lineChart.setPadding(new Insets(INSET_TOP, INSET_RIGHT, INSET_BOTTOM, INSET_LEFT));
        int numStates = grid.getPossibleStateMap().size();
        String style = "";
        seriesList = new ArrayList<>();
        for(int i = 0; i < numStates; i++) {
            XYChart.Series<Number,Number> currSeries = new XYChart.Series<>();
            currSeries.setName(grid.getPossibleStateMap().get(i).getStateName());
            seriesList.add(currSeries);
            lineChart.getData().add(currSeries);
            Color currColor = grid.getPossibleStateMap().get(i).getColor();
            String currHex = convertToHex(currColor);
            int currNum = i + 1;
            style += "CHART_COLOR_" + currNum + ":" + currHex + ";";
        }
        style = style.substring(0, style.length() -1);

        lineChart.setStyle(style);

        lineChart.setMaxWidth(Integer.parseInt(myResources.getString("chartMaxWidth")));
        lineChart.setMaxHeight(Integer.parseInt(myResources.getString("chartMaxHeight")));
        lineChart.setMinHeight(1000);

        return lineChart;
    }

    /**
     * @purpose Used to convert a color to its hex equivalent
     * @param color
     */
    public String convertToHex(Color c) {
        String hexr = Integer.toHexString((int) (c.getRed() * HEX_CONVERSION));
        String hexg = Integer.toHexString((int) (c.getGreen() * HEX_CONVERSION));
        String hexb = Integer.toHexString((int) (c.getBlue() * HEX_CONVERSION));
        String hex = "#";
        if(hexr.length() == 1) {
            hexr = "0" + hexr;
        }
        hex += hexr;
        if(hexg.length() == 1) {
            hexg = "0" + hexg;
        }
        hex += hexg;
        if(hexb.length() == 1) {
            hexb = "0" + hexb;
        }
        hex += hexb;
        return hex;
    }

    /**
     * @purpose Used to update the line chart through each step of the simulation
     * @param grid
     * @param root
     */
    public void updateLineChart(Grid grid, BorderPane root) {
        if(currTime == endTime) {
            endTime += DURATION;
            startTime += DURATION;
            LineChart lineChart = initChart(grid);
            root.setCenter(lineChart);
        }
            currTime++;

            int[] counts = new int[seriesList.size()];

            Cell[][] currentGrid = grid.getCurrentGrid();

            for(int i = 0; i < currentGrid.length; i++) {
                for (int j = 0; j < currentGrid[0].length; j++) {
                    Cell currCell = currentGrid[i][j];
                    counts[currCell.getCurrState().getType()]++;
                }
            }
            for(int i = 0; i < seriesList.size(); i++) {
                XYChart.Series<Number, Number> currSeries = seriesList.get(i);
                currSeries.getData().add(new XYChart.Data<>(currTime, counts[i]));
            }
    }
}
