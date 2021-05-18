import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class ChartDataCrawler {
	private String dailyURL;
	private String realtimeURL;
	private String stockCode;
	private String selector;
	private String page = null;
	private Document dailyDoc = null;
	private Document realtimeDoc = null;
	
	ChartDataCrawler(String stockCode){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date time = new Date();
		String currentTime = format.format(time);
		currentTime += "180000";
		
//		this.stockDataValues = "";
		this.page = "&page=";
		this.selector = "span.tah";
		this.stockCode = stockCode;
		dailyURL = "https://finance.naver.com/item/sise_day.nhn?code=" + stockCode;
		realtimeURL = "https://finance.naver.com/item/sise_time.nhn?code=" + stockCode + "&thistime=20210517180000";// + currentTime;

		dailyDoc = getDoc(dailyURL);
		realtimeDoc = getDoc(realtimeURL);
	}

	public String getDailyStockData() {
		int pages = 2;
		String stockDataValues = "";
		Elements dates = dailyDoc.select(selector);
		while(dates.size()/7 == 10 && pages < 28) {
			for(Element element: dates) {
				stockDataValues += element.text();
//				System.out.println(element.text());
			}
			dailyDoc = getDoc(dailyURL + page + pages);
			dates = dailyDoc.select(selector);
			pages += 1;
		}
		return stockDataValues;
	}
	
	public String getRealtimeStockData() {
		int pages = 2;
		String stockDataValues = "";
		Elements dates = realtimeDoc.select(selector);
		while(dates.size()/7 == 10 && pages <= 40) {
			for(Element element: dates) {
				stockDataValues += element.text();
//				System.out.println(element.text());
			}
			realtimeDoc = getDoc(realtimeURL + page + pages);
			dates = realtimeDoc.select(selector);
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
	
//	public static void main(String args[]) {
//		ChartDataCrawler chart = new ChartDataCrawler("950130");
//		System.out.println(chart.getDailyStockData());
//		System.out.println(chart.getRealtimeStockData());
//	}
}
