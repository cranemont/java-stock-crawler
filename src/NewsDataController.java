import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class NewsDataController implements MouseListener{
    private final NewsCrawler crawler;
    private final NewsDataParser parser;
    private final StockNews stockNews;
    private NewsDataModel model;

    NewsDataController(StockNews view, NewsDataModel model) {
        crawler = new NewsCrawler();
        this.model = model;
        this.stockNews = view;
        this.parser = new NewsDataParser();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() == 2) {
            // add action when searchResults' item is double-clicked
            String stockName = stockNews.getStockName();
            parser.parseJson(crawler.searchNews(stockName, "sim"));
            model = parser.getDataModel();
            stockNews.updateNewsData(model);
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
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
}

