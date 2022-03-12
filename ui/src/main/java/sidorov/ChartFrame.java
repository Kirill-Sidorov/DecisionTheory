package sidorov;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import sidorov.lab4.Function;

import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.List;

public class ChartFrame extends JFrame {

    public ChartFrame(List<Function> functions) {
        setLayout(null);

        List<XYSeries> seriesList = new ArrayList<>();
        for (int i = 0; i < functions.size(); i++) {
            seriesList.add(new XYSeries("g" + (i+1)));
        }

        for (double p = 0; p <= 1; p += 0.1) {
            for (int i = 0; i < functions.size(); i++) {
                XYSeries xySeries = seriesList.get(i);
                xySeries.add(p, functions.get(i).calc(p), false);
            }
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        for (XYSeries xySeries : seriesList) {
            dataset.addSeries(xySeries);
        }

        JFreeChart chart = ChartFactory.createXYLineChart(
                null,
                "p1",
                "g(p1)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false);

        chart.getXYPlot().getDomainAxis().setRange(0, 1);
        ChartPanel chartPanel = new ChartPanel(chart);

        setContentPane(chartPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("График");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
