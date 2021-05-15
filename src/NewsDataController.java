import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class NewsDataController implements MouseListener{
    private final NewsCrawler crawler;
    private final NewsDataModel model;
    private final StockNews stockNews;

    NewsDataController(StockNews view, NewsDataModel model) {
        crawler = new NewsCrawler();
        this.model = model;
        this.stockNews = view;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() == 2) {
            // add action when searchResults' item is double-clicked
            String stockName = stockNews.getStockName();
            model.updateJson(crawler.searchNews(stockName, "sim"));
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

