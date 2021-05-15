import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class StockChart {
	private JPanel chartPanel;
	private JTextArea ta;
	private String stockName;
	private String stockCode;
	
	StockChart(final SearchPane searchPane){
		chartPanel = new JPanel();
		ta = new JTextArea(); 
		
		searchPane.searchResults.addMouseListener(new MouseAdapter( ) {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					// add action when searchResults' item is double-clicked
					stockName = searchPane.getStockName();
					stockCode = searchPane.getStockCode();
					ta.append(stockName + "\n");
					ta.append(stockCode + "\n");
				}
			}
		});
		
		searchPane.stockBookmark.addMouseListener(new MouseAdapter( ) {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					// add action when stockBookmark's item is double-clicked
					stockName = searchPane.getStockName();
					stockCode = searchPane.getStockCode();
					ta.append(stockName + "\n");
					ta.append(stockCode + "\n");
				}
			}
		});
		chartPanel.add(ta);
	}
	
	public JPanel getPanel() {
		return chartPanel;
	}
}
