import java.util.ArrayList;
//import javax.swing.SwingWorker;

public class ChartDataFeeder {
	private ChartDataCrawler crawler;
	private ChartView chartModel;
	private ArrayList<String[]> dailyData;
	private String stockCode;
	
	ChartDataFeeder(ChartDataCrawler crawler, ChartView chartModel, String stockCode){
		this.crawler = crawler;
		this.chartModel = chartModel;
		this.stockCode = stockCode;
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
		parseData();
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
//		BackgroundTask(ChartView model){
//			this.model = model;
//		}
//		
//		@Override
//		protected Integer doInBackground() throws Exception {
//			// TODO Auto-generated method stub
//			//while, 
////			model.addCandel(); //add data from dailyData
//			parseData();
//			for(String[] str: dailyData) {
//				model.addCandel(str);
//			}
//			return null;
//		}
//	}
}
