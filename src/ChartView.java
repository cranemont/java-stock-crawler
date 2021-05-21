import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.time.*;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import java.text.DateFormat;
import java.text.ParseException;

public class ChartView extends JPanel{
	private OHLCSeries ohlcSeries;
	private TimeSeries volumeSeries;
	private static final DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
	private double open = 0.0;
    private double close = 0.0;
    private double low = 0.0;
    private double high = 0.0;
    private String stockName;
    
	ChartView(String stockName){
		this.stockName = stockName;
		final JFreeChart candlestickChart = createChart();
		final ChartPanel chartPanel = new ChartPanel(candlestickChart);
		
        chartPanel.setPreferredSize(new Dimension(900, 320));
        // Enable zooming
        chartPanel.setMouseZoomable(true);
        chartPanel.setMouseWheelEnabled(true);
        add(chartPanel, BorderLayout.CENTER);
	}
	
	private JFreeChart createChart() {
		
		OHLCSeriesCollection candlestickDataset = new OHLCSeriesCollection();
		ohlcSeries = new OHLCSeries("Price");
		candlestickDataset.addSeries(ohlcSeries);
		
		NumberAxis priceAxis = new NumberAxis("Price");
        priceAxis.setAutoRangeIncludesZero(false);
        priceAxis.setLabelPaint(Color.white);
        priceAxis.setTickLabelPaint(Color.white);
        
        // Create candlestick chart renderer
        CandlestickRenderer candlestickRenderer = new CandlestickRenderer();
        candlestickRenderer.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_SMALLEST);

        // Create candlestickSubplot
        XYPlot candlestickSubplot = new XYPlot(candlestickDataset, null, priceAxis, candlestickRenderer);
        candlestickSubplot.setBackgroundPaint(Color.white);
        
        DateAxis dateAxis = new DateAxis();
        dateAxis.setLabelPaint(Color.white);
        dateAxis.setTickLabelPaint(Color.white);
        dateAxis.setDateFormatOverride(new SimpleDateFormat("MM-dd"));
        
        CombinedDomainXYPlot mainPlot = new CombinedDomainXYPlot(dateAxis);
        mainPlot.add(candlestickSubplot);
        
        JFreeChart chart = new JFreeChart(stockName, JFreeChart.DEFAULT_TITLE_FONT, mainPlot, true);
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
			ohlcSeries.add(new Day(date), open, high, low, close);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
