package ru.hh.superscoring.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class Chart {

  public static File createBarChart(DefaultCategoryDataset dataset) throws IOException, FontFormatException {
    ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
    JFreeChart chart = ChartFactory.createStackedBarChart(
        "",
        "Набрано баллов",
        "Количество тестируемых",
        dataset,
        PlotOrientation.VERTICAL,
        true, true, false);


    Font robo = Font.createFont(Font.PLAIN, new File("src/main/resources/Roboto-Regular.ttf"));
    chart.getLegend().setItemFont(robo.deriveFont(17f));
    chart.setBackgroundPaint(Color.white);

    CategoryPlot plot = chart.getCategoryPlot();
    plot.setBackgroundPaint(Color.white);
    plot.setDomainGridlinePaint(Color.white);
    plot.setRangeGridlinePaint(Color.white);

    BarRenderer renderer = (BarRenderer) plot.getRenderer();
    renderer.setSeriesPaint(0, new Color(0xd9, 0x11, 0x24));
    renderer.setSeriesPaint(1, new Color(0x2a, 0xd2, 0xc9));
    renderer.setShadowPaint(Color.white);
    renderer.setDrawBarOutline(false);

    CategoryAxis domainAxis = plot.getDomainAxis();
    domainAxis.setLabelFont(robo.deriveFont(15f));
    domainAxis.setTickLabelFont(robo.deriveFont(15f));

    ValueAxis rangeAxis = plot.getRangeAxis();
    rangeAxis.setLabelFont(robo.deriveFont(15f));
    rangeAxis.setTickLabelFont(robo.deriveFont(15f));
    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

    int width = 640;
    int height = 480;
    File file = new File("chart.jpg");
    ChartUtils.saveChartAsJPEG(file, chart, width, height);
    return file;
  }
}