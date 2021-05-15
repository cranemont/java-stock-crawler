import java.awt.*;
import java.util.TreeMap;
import javax.swing.*;

public class ShowWindow extends JFrame {
	JFrame f;
	int width;
	int height;
	TreeMap<String, String> stockData;
	SearchPane searchPane;
	StockChart stockChart;
	StockNews stockNews;
	
	ShowWindow(){
		width = 1200;
		height = 700;
		stockData = new StockData().getStockData();
		searchPane = new SearchPane(stockData);
		stockChart = new StockChart(searchPane);
		stockNews = new StockNews(searchPane);
		
		f = new JFrame("Stock Information");
		
		JMenuBar mb = new JMenuBar();
		JMenu settings = new JMenu("Settings");
		JMenu exit = new JMenu("Exit");
		
		mb.add(settings);
		mb.add(exit);
		
		f.setJMenuBar(mb);
		f.setSize(width, height);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(SplitPanel(), BorderLayout.CENTER);
		
		f.setLocationByPlatform(true);
		f.setVisible(true);
		f.show();
	}
	
	private JSplitPane SplitPanel() { // right Panel(search)
		JPanel search = searchPane.getPanel();
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, SplitByRow(), search);
		split.setPreferredSize(new Dimension(width/5, height));
		split.setDividerSize(4);
		split.setDividerLocation(4*width/5);
		split.setEnabled(false); // fix right panel divider
		return split;
	}
	
	private JSplitPane SplitByRow() { // left Panel(chart, news)
		JPanel chart = stockChart.getPanel();
		JPanel news = stockNews.getPanel();
		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, chart, news);
		split.setPreferredSize(new Dimension(width, height/2));
		split.setDividerLocation(height/2);
		return split;
	}
	
	public static void main(String args[]) {
		new ShowWindow();
	}
}
