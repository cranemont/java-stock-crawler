import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChartDataController implements MouseListener{

	private StockChart view;
	private SearchPane searchPane;
	
	ChartDataController(StockChart view, SearchPane searchPane){
		this.view = view;
		this.searchPane = searchPane;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getClickCount() == 2) {
			// add action when items are double-clicked
			view.updateChart(searchPane.getStockName(), searchPane.getStockCode());
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
