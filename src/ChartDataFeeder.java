import java.util.ArrayList;
import javax.swing.SwingWorker;

public class ChartDataFeeder {
	private ChartDataCrawler crawler;
	private ChartView chartModel;
	private ArrayList<String[]> dailyData;
	private String stockCode;
	
	ChartDataFeeder(ChartDataCrawler crawler, ChartView chartModel, String stockCode){
		this.crawler = crawler;
		this.chartModel = chartModel;
		this.stockCode = stockCode;
		parseData();
	}
	
	public void parseData() {
		crawler.fetchDailyStockData(stockCode);
		this.dailyData = crawler.getDailyStockData();
		
		// processing suspended stocks data
		int flag = 0;
		int idx = 0;
		String[] prev = null;
		for(String[] str: dailyData) {
			if(flag == 1) {
				prev[3] = str[1];
				prev[4] = str[1];
				prev[5] = str[1];
				flag = 0;
			}
			if(str[3].equals("0") && idx != dailyData.size()-1) {
				prev = str;
				flag = 1;
			}
			idx++;
		}
	}
	
	public void dailyDataPrinter() {
		System.out.println(dailyData.size());
		for(String[] strList: dailyData) {
			for(String str: strList) {
				System.out.println(str);
			}
		}
	}
	
	public void drawDailyChart() {
		for(String[] str: dailyData) {
			chartModel.addCandel(str);
		}
		runMovingAverageDrawingThread();
	}

	public void runMovingAverageDrawingThread() {
		BackgroundTask ma20 = new BackgroundTask(0, 20);
		BackgroundTask ma60 = new BackgroundTask(1, 60);
		BackgroundTask ma120 = new BackgroundTask(2, 120);
		ma20.execute();
		ma60.execute();
		ma120.execute();
	}
	
	private class BackgroundTask extends SwingWorker<Integer, Integer>{
		
		private int id;
		private int period;
		
		BackgroundTask(int id, int period){
			this.id = id;
			this.period = period;
		}
		
		@Override
		protected Integer doInBackground() throws Exception {
			chartModel.addMovingAverage(id, period);
			return null;
		}
	}
}
