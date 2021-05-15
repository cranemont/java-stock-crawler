import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.TreeMap;

import javax.swing.*;

public class SearchPane {
	private JPanel searchPanel;
	private JTextField inputTextField;
	private JButton addBookmark;
	private JButton delBookmark;
	public JList<String> searchResults;
	public JList<String> stockBookmark;
	final DefaultListModel<String> stockList;
	final DefaultListModel<String> bookmarkList;
	final TreeMap<String, String> stockData;
	
	private int maxBookmark = 5;
	private String stockName;
	private String stockCode;
	
	SearchPane(TreeMap<String, String> StockData){
		searchPanel = new JPanel();
		inputTextField = new JTextField(15);
		addBookmark = new JButton("관심종목 추가");
		delBookmark = new JButton("관심종목 제거");
		searchResults = new JList<String>();
		stockBookmark = new JList<String>();
		searchActionListener actionListners = new searchActionListener();
		this.stockData = StockData;
		
		stockList = new DefaultListModel<String>();
		searchResults.setModel(stockList);
		searchResults.setVisibleRowCount(10);
		
		inputTextField.addActionListener(actionListners);
		
		searchResults.addMouseListener(new MouseAdapter( ) {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					stockName = searchResults.getSelectedValue();
					stockCode = stockData.get(stockName);
				}
			}
		});
		
		bookmarkList = new DefaultListModel<String>();
		stockBookmark.setModel(bookmarkList);
		stockBookmark.setVisibleRowCount(10);
		
		addBookmark.addActionListener(actionListners);
		delBookmark.addActionListener(actionListners);

		stockBookmark.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					stockName = stockBookmark.getSelectedValue();
					stockCode = stockData.get(stockName);
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(searchResults);
		scrollPane.setPreferredSize(new Dimension(200, 200));
		searchPanel.setLayout(new FlowLayout());
		searchPanel.add(new JLabel("종목명을 입력하세요"));
		searchPanel.add(inputTextField);
		searchPanel.add(addBookmark);
		searchPanel.add(delBookmark);
		searchPanel.add(scrollPane);
		
		stockBookmark.setPreferredSize(new Dimension(200, 200));
		searchPanel.add(stockBookmark);
	}
	
	public String getStockName() {
		return stockName;
	}
	
	public String getStockCode() {
		return stockCode;
	}
	
	public JPanel getPanel() {
		return searchPanel;
	}
	
	class searchActionListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if(inputTextField.equals(e.getSource())) { // put TextField data to the TreeMap
				stockList.clear();
				String inputText = inputTextField.getText();
				if(!inputText.equals("")) {
					Iterator<String> keys = stockData.keySet().iterator();
					while(keys.hasNext()) {
						String key = keys.next();
						if(key.contains(inputText)) {
							stockList.addElement(key);
						}
					}
					inputTextField.setText(null);
				}
			}
			else if(addBookmark.equals(e.getSource())) { // add bookmark
				if(searchResults.getSelectedValue() != null) {
					int flag = 0;
					String selectedValue = searchResults.getSelectedValue();
					for(int i=0; i<bookmarkList.getSize(); i++) {
						if(bookmarkList.getElementAt(i).equals(selectedValue)) {
							flag = 1;
							break;
						}
					}
					if(flag == 0 && bookmarkList.size() < maxBookmark) {
						bookmarkList.addElement(selectedValue);
					}
				}
			}
			else if(delBookmark.equals(e.getSource())) { // delete bookmark
				if(stockBookmark.getSelectedValue() != null) {
					String selectedValue = stockBookmark.getSelectedValue();
					for(int i=0; i<bookmarkList.getSize(); i++) {
						if(bookmarkList.getElementAt(i).equals(selectedValue)) {
							bookmarkList.remove(i);
							break;
						}
					}
				}
			}
		}
	}
}
