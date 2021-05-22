import java.awt.Component;
import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class StockNews {
	private JScrollPane newsScorllPane;
	private JPanel newsMainPanel;
	private NewsDataModel newsModel;
	private SearchPane parentPane;
	private final int displayNews = 10;
	private NewsPanel[] newsPanes;

	StockNews(final SearchPane searchPane) {
		NewsDataController newsDataEventListener = new NewsDataController(this, newsModel);
		
		newsMainPanel = new JPanel();
		newsMainPanel.setLayout(new BoxLayout(newsMainPanel, BoxLayout.Y_AXIS));
		newsMainPanel.setAlignmentY(Component.LEFT_ALIGNMENT);
		newsMainPanel.setMaximumSize(new Dimension(860, 350));

		newsScorllPane = new JScrollPane(newsMainPanel,
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		searchPane.searchResults.addMouseListener(newsDataEventListener);
		searchPane.stockBookmark.addMouseListener(newsDataEventListener);
		parentPane = searchPane;

		newsPanes = new NewsPanel[displayNews];
		for (int i = 0; i < displayNews; i++) {
			newsPanes[i] = new NewsPanel();
			newsMainPanel.add(newsPanes[i]);
		}
	}
	
	public JScrollPane getPanel() {
		return newsScorllPane;
	}

	public String getStockName() {
		return parentPane.getStockName();
	}

	void updateNewsData(NewsDataModel model) {
		for (int i = 0; i < model.display; i++) {
			String title = model.items[i].title;
			String desc = model.items[i].description;
			String date = model.items[i].pubDate;
			newsPanes[i].updateNews(title, desc, date);
		}
	}

	class NewsPanel extends JPanel {
		private JLabel dateLbl;
		private Date date;
		private JTextArea titleTA;
		private String title;
		private JTextArea descTA;
		private String desc;

		NewsPanel() {
			setAlignmentY(Component.LEFT_ALIGNMENT);
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

			Color primaryback = UIManager.getColor("Panel.background");
			Font NanumFont = FontController.loadFontFile("NanumBarunGothic.ttf");
			Font NanumBoldFont = FontController.loadFontFile("NanumBarunGothicBold.ttf");
			FontController.registerFont(NanumFont);

			Font NanumTitleFont = NanumBoldFont.deriveFont(16f);
			titleTA = new JTextArea();
			titleTA.setFont(NanumTitleFont);
			titleTA.setEditable(false);
			titleTA.setWrapStyleWord(true);
			titleTA.setLineWrap(true);
			titleTA.setBackground(primaryback);

			Font NanumDescFont = NanumFont.deriveFont(12f);
			descTA = new JTextArea();
			descTA.setFont(NanumDescFont);
			descTA.setEditable(false);
			descTA.setWrapStyleWord(true);
			descTA.setLineWrap(true);
			descTA.setBackground(primaryback);

			add(titleTA);
			add(descTA);
		}

		public void updateNews(String title, String desc, String date) {
			titleTA.setText(title);
			descTA.setText(desc);
		}
		
	}
}
