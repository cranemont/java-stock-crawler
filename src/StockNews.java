import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StockNews {
	private JPanel newsPanel;
	private NewsDataModel newsModel;
	private SearchPane parentPane;
	
	StockNews(final SearchPane searchPane) {
		newsModel = new NewsDataModel(this);
		NewsDataController newsDataEventListener = new NewsDataController(this, newsModel);
		
		newsPanel = new JPanel();
		newsPanel.setAlignmentY(Component.LEFT_ALIGNMENT);
		newsPanel.setLayout(new BoxLayout(newsPanel, BoxLayout.Y_AXIS));
		
		searchPane.searchResults.addMouseListener(newsDataEventListener);
		searchPane.stockBookmark.addMouseListener(newsDataEventListener);
		parentPane = searchPane;
	}
	
	public JPanel getPanel() {
		return newsPanel;
	}

	public String getStockName() {
		return parentPane.getStockName();
	}

	void updateNewsData(String[] titles, String[] links, int display) {
		newsPanel.removeAll();
		newsPanel.revalidate();
		newsPanel.repaint();

		JLabel curLabel;
		for (int i = 0; i < display; i++) {
			curLabel = new JLabel(titles[i]);
			newsPanel.add(curLabel);
		}
	}
}
