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
	}
	
//	public void showDailyChartUsingSwingWorker() {
//		BackgroundTask task = new BackgroundTask(chartModel);
//		task.execute();
//	}
//
//	
//	private class BackgroundTask extends SwingWorker<Integer, Integer>{
//
//		private ChartView model;
////		private ArrayList<String[]> dailyData;
//		BackgroundTask(ChartView model){
//			this.model = model;
////			this.dailyData = dailyData;
//		}
//		
//		@Override
//		protected Integer doInBackground() throws Exception {
//			// TODO Auto-generated method stub
////			model.addCandel(); //add data from dailyData
//			for(String[] str: dailyData) {
//				model.addCandel(str);
//			}
//			return null;
//		}
//	}
}
