import java.awt.*;
import java.util.TreeMap;
import javax.swing.*;

// gui skin lib import.
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.JMarsDarkTheme;

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

		// setting gui skin. (MaterialLookAndFeel.)
		try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel(new JMarsDarkTheme()));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

		setUIFont(new javax.swing.plaf.FontUIResource("맑은 고딕", Font.PLAIN, 16));

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

	public static void setUIFont(javax.swing.plaf.FontUIResource f) {
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource)
				UIManager.put(key, f);
		}
	}
	
	public static void main(String args[]) {
		new ShowWindow();
	}
}
