import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JPanel;

class DividerMoveListener implements ComponentListener {
	
	private ChartView chart;

	@Override
	public void componentResized(ComponentEvent e) {
			JPanel jp = (JPanel) e.getComponent();
			if(jp.countComponents() > 0) {
				chart = (ChartView) jp.getComponents()[0];
				chart.resize(e.getComponent().getSize().height - 20);
			}
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}