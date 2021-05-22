import java.awt.Component;
import java.net.URI;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.util.Date;
import java.util.Locale;

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
		newsMainPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		newsMainPanel.setMaximumSize(new Dimension(860, 350));

		newsScorllPane = new JScrollPane(newsMainPanel,
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		newsScorllPane.getVerticalScrollBar().setUnitIncrement(20);
		
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
			String link = model.items[i].originallink;
			newsPanes[i].updateNews(title, desc, date, link);
		}
	}

	class NewsPanel extends JPanel {
		private JTextArea dateTA;
		private JTextArea titleTA;
		private JTextArea descTA;
		private URI browseURI;
		private JTextArea browseTA;

		NewsPanel() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setAlignmentX(JPanel.LEFT_ALIGNMENT);

			Color primaryback = UIManager.getColor("Panel.background");
			Font NanumFont = FontController.loadFontFile("NanumBarunGothic.ttf");
			Font NanumBoldFont = FontController.loadFontFile("NanumBarunGothicBold.ttf");

			Font NanumTitleFont = NanumBoldFont.deriveFont(18f);
			titleTA = new JTextArea();
			titleTA.setFont(NanumTitleFont);
			titleTA.setEditable(false);
			titleTA.setWrapStyleWord(true);
			titleTA.setLineWrap(true);
			titleTA.setBackground(primaryback);

			Font NanumDescFont = NanumFont.deriveFont(14f);
			descTA = new JTextArea();
			descTA.setFont(NanumDescFont);
			descTA.setEditable(false);
			descTA.setWrapStyleWord(true);
			descTA.setLineWrap(true);
			descTA.setBackground(primaryback);

			Font NanumDateFont = NanumFont.deriveFont(12f);
			dateTA = new JTextArea();
			dateTA.setFont(NanumDateFont);
			dateTA.setEditable(false);
			dateTA.setWrapStyleWord(true);
			dateTA.setLineWrap(true);
			dateTA.setBackground(primaryback);
			dateTA.setForeground(Color.CYAN);

			Font NanumBrowseFont = NanumFont.deriveFont(12f);
			browseTA = new JTextArea();
			browseTA.setFont(NanumBrowseFont);
			browseTA.setBackground(primaryback);
			browseTA.setEditable(false);
			browseTA.addMouseListener(new browseButtonClickListener());
			browseTA.setForeground(Color.orange);
			
			add(titleTA);
			add(dateTA);
			add(descTA);
			add(browseTA);
		}
		
		public void updateNews(String title, String desc, String datestr, String link) {
			titleTA.setText(title);
			descTA.setText(desc);
			
			try {
				dateTA.setText(modifyDateLayout(datestr));
				browseURI = new URL(link).toURI();
				browseTA.setText("[더 보기]");
			} catch (java.net.MalformedURLException e) {
				JOptionPane.showMessageDialog(null, link + " is MalformedURL!", "URL error", JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
			
		}

		private String modifyDateLayout(String dateStr) throws java.text.ParseException {
			Date date = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US).parse(dateStr);
			return new SimpleDateFormat("[MMM dd, yyyy HH:mm]").format(date);
		}

		private class browseButtonClickListener implements MouseListener {

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (e.getButton() == MouseEvent.BUTTON1
					&&  Desktop.isDesktopSupported()) {
						Desktop.getDesktop().browse(browseURI);
					}
				}
				catch (Exception ex){
					ex.printStackTrace();
					System.exit(1);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
				browseTA.setCursor(cursor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				browseTA.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));	
			}


		} 
		

	}
}
