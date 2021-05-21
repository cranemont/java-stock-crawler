import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class ChartDataCrawler {
	private String dailyURL;
	private String realtimeURL;
	private String selector;
	private String page = null;
	private Document dailyDoc = null;
	private Document realtimeDoc = null;
	private ArrayList<String[]> stockDataValues;
	
	ChartDataCrawler(){
//		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//		Date time = new Date();
//		String currentTime = format.format(time);
//		currentTime += "180000";
		this.stockDataValues = new ArrayList<String[]>();
		this.page = "&page=";
		this.selector = "span.tah";
//		this.stockCode = stockCode;
//		dailyURL = "https://finance.naver.com/item/sise_day.nhn?code=" + stockCode;
//		realtimeURL = "https://finance.naver.com/item/sise_time.nhn?code=" + stockCode + "&thistime=20210517180000";// + currentTime;

//		dailyDoc = getDoc(dailyURL);
//		realtimeDoc = getDoc(realtimeURL);
	}
	
	public void fetchDailyStockData(String stockCode) {
		int pages = 2;
		dailyURL = "https://finance.naver.com/item/sise_day.nhn?code=" + stockCode;
		dailyDoc = getDoc(dailyURL);
		stockDataValues.clear();
		
		Elements values = dailyDoc.select(selector);
		String[] str = new String[7];
		while(values.size() == 70 && pages < 28) {
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
	
	public String getRealtimeStockData() {
		int pages = 2;
		String stockDataValues = "";
		Elements values = realtimeDoc.select(selector);
		while(values.size()/7 == 10 && pages <= 40) {
			stockDataValues += values.text() + "|";
			realtimeDoc = getDoc(realtimeURL + page + pages);
			values = realtimeDoc.select(selector);
			pages += 1;
		}
		return stockDataValues;
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

