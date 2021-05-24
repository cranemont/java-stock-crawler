import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class ChartDataCrawler {
	private String dailyURL;
	private String selector;
	private String page = null;
	private Document dailyDoc = null;
	private ArrayList<String[]> stockDataValues;
	private int fetchPageMax;
	
	ChartDataCrawler(){
		this.stockDataValues = new ArrayList<String[]>();
		this.page = "&page=";
		this.selector = "span.tah";
		fetchPageMax = 23;
	}
	
	public void setFetchPage(int pages) {
		this.fetchPageMax = pages;
	}
	
	public void fetchDailyStockData(String stockCode) {
		int pages = 2;
		dailyURL = "https://finance.naver.com/item/sise_day.nhn?code=" + stockCode;
		dailyDoc = getDoc(dailyURL);
		stockDataValues.clear();
		
		Elements values = dailyDoc.select(selector);
		String[] str = new String[7];
		while(values.size() == 70 && pages < fetchPageMax) {
			int idx = 0;
			for(Element el: values) {
				str[idx++] = el.text();
				if(idx == 7) {
					this.stockDataValues.add(str);
					str = new String[7];
					idx = 0;
				}
			}
			dailyDoc = getDoc(dailyURL + page + pages);
			values = dailyDoc.select(selector);
			pages += 1;
		}
	}
	
	public ArrayList<String[]> getDailyStockData(){
		return this.stockDataValues;
	}
	
	public Document getDoc(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return doc;
	}
}

