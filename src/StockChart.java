import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class StockChart {
	private JPanel chartPanel;
	private ChartDataCrawler cr;
	private ChartView jfreeCandlestickChart;

	StockChart(final SearchPane searchPane){

		ChartDataController actionListener = new ChartDataController(this, searchPane);
		chartPanel = new JPanel();
		cr = new ChartDataCrawler();
		
		searchPane.searchResults.addMouseListener(actionListener);
		searchPane.stockBookmark.addMouseListener(actionListener);
	}
	
	public void updateChart(String stockName, String stockCode) {
		chartPanel.removeAll();
		jfreeCandlestickChart = new ChartView(stockName);
		jfreeCandlestickChart.resize(chartPanel.getSize().height - 20);
		new ChartDataFeeder(cr, jfreeCandlestickChart, stockCode).drawDailyChart();
        chartPanel.add(jfreeCandlestickChart);
        
        // update GUI
        SwingUtilities.updateComponentTreeUI(chartPanel);
	}
	
	public JPanel getPanel() {
		return chartPanel;
	}
}
