import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jfree.chart.*;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.time.*;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;

public class ChartView extends JPanel{
	private OHLCSeries ohlcSeries;
	private TimeSeries volumeSeries;
	private static final DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
	private double open = 0.0;
    private double close = 0.0;
    private double low = 0.0;
    private double high = 0.0;
    private double volume = 0.0;
    private String stockName;
    private JFreeChart chart;
    private TimeSeries t1;
    final ChartPanel chartPanel;
    
	ChartView(String stockName){
		this.stockName = stockName;
		final JFreeChart candlestickChart = createChart();
		chartPanel = new ChartPanel(candlestickChart);

        chartPanel.setPreferredSize(new Dimension(900, 320));
        // Enable zooming
        chartPanel.setMouseZoomable(true);
        chartPanel.setMouseWheelEnabled(true);
        add(chartPanel, BorderLayout.CENTER);
	}
	
	private JFreeChart createChart() {
		
		// create candlestick subplot
		OHLCSeriesCollection candleStickDataset = new OHLCSeriesCollection();
		ohlcSeries = new OHLCSeries("Price");
		candleStickDataset.addSeries(ohlcSeries);
		
		NumberAxis candleStickYAxis = new NumberAxis("Price");
		candleStickYAxis.setAutoRangeIncludesZero(false);
		candleStickYAxis.setLabelPaint(Color.white);
		candleStickYAxis.setTickLabelPaint(Color.white);
        
        CandlestickRenderer candlestickRenderer = new CandlestickRenderer();
        candlestickRenderer.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_SMALLEST);
        
        // create volume subplot
        TimeSeriesCollection volumeDataset = new TimeSeriesCollection();
        volumeSeries = new TimeSeries("Volume");
        volumeDataset.addSeries(volumeSeries);
        
        NumberAxis volumeYAxis = new NumberAxis("Volume");
        volumeYAxis.setAutoRangeIncludesZero(false);
        volumeYAxis.setLabelPaint(Color.white);
        volumeYAxis.setTickLabelPaint(Color.white);
        
        XYBarRenderer timeRenderer = new XYBarRenderer();
        timeRenderer.setShadowVisible(false);
        timeRenderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator("{1} | 거래량={2}",
                new SimpleDateFormat("yy년 MM월 dd일"), new DecimalFormat("0")));
        
        // create moving average
        t1 = new TimeSeries("30-ma");
        TimeSeries dataset3 = MovingAverage.createMovingAverage(t1, "LT", 30, 0);
        TimeSeriesCollection movingAvg30Dataset = new TimeSeriesCollection();
        movingAvg30Dataset.addSeries(dataset3);
        
        XYPlot candlestickSubplot = new XYPlot(candleStickDataset, null, candleStickYAxis, candlestickRenderer);
        candlestickSubplot.setBackgroundPaint(Color.white);
        candlestickSubplot.setRangePannable(true);
        candlestickSubplot.setDataset(1, movingAvg30Dataset);
        candlestickSubplot.setRenderer(1, new StandardXYItemRenderer());
        
        XYPlot volumeSubplot = new XYPlot(volumeDataset, null, volumeYAxis, timeRenderer);
        volumeSubplot.setBackgroundPaint(Color.white);
        
        DateAxis dateAxis = new DateAxis();
        SegmentedTimeline timeline = SegmentedTimeline.newMondayThroughFridayTimeline();
        dateAxis.setTimeline(timeline);
        dateAxis.setLabelPaint(Color.white);
        dateAxis.setTickLabelPaint(Color.white);
        dateAxis.setDateFormatOverride(new SimpleDateFormat("MM-dd"));
        
        CombinedDomainXYPlot mainPlot = new CombinedDomainXYPlot(dateAxis);
        mainPlot.add(candlestickSubplot, 3);
        mainPlot.add(volumeSubplot, 1);
        
        chart = new JFreeChart(stockName, JFreeChart.DEFAULT_TITLE_FONT, mainPlot, true);
        chart.removeLegend();
        chart.getTitle().setPaint(Color.white);
        return chart;
	}
	
	public void addCandel(String[] trade) {
		try {
			Date date = df.parse(trade[0]);
			open = Double.parseDouble(trade[3].replace(",", ""));
			high = Double.parseDouble(trade[4].replace(",", ""));
			low = Double.parseDouble(trade[5].replace(",", ""));
			close = Double.parseDouble(trade[1].replace(",", ""));
			volume = Double.parseDouble(trade[6].replace(",", ""));

			ohlcSeries.add(new Day(date), open, high, low, close);
			volumeSeries.add(new Day(date), volume);
			t1.add(new Day(date), close);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void resize(int height) {
		if(height > 100) {
			chartPanel.setPreferredSize(new Dimension(900, height));
			SwingUtilities.updateComponentTreeUI(chartPanel);
		}
	}
}
