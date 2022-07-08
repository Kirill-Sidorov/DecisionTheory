package sidorov.customcomponents;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import sidorov.common.chart.ChartData;
import sidorov.common.chart.Dot;
import sidorov.common.chart.Function;

import javax.swing.JFrame;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ChartFrame extends JFrame {

    public ChartFrame(ChartData chartData) {
        setLayout(null);

        List<XYSeries> seriesList = new ArrayList<>();
        for (Function function : chartData.functions) {
            XYSeries series = new XYSeries(function.name);
            for (Dot dot : function.dots) {
                series.add(dot.x, dot.y, false);
            }
            seriesList.add(series);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();

        for (XYSeries series : seriesList) {
            dataset.addSeries(series);
        }

        JFreeChart chart = ChartFactory.createXYLineChart(
                chartData.title,
                chartData.xAxisName,
                chartData.yAxisName,
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false);

        chart.getPlot().setBackgroundPaint(Color.WHITE);
        ChartPanel chartPanel = new ChartPanel(chart);

        setContentPane(chartPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("График");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
