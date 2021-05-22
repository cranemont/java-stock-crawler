import java.awt.*;
import java.util.TreeMap;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// gui skin lib import.
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.JMarsDarkTheme;

public class ShowWindow extends JFrame {
	JFrame f;
	static public int width = 1200;
	static public int height = 700;
	TreeMap<String, String> stockData;
	SearchPane searchPane;
	StockChart stockChart;
	StockNews stockNews;
	
	ShowWindow(){

		// setting gui skin. (MaterialLookAndFeel.)
		try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel(new JMarsDarkTheme()));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

		setUIFont(new javax.swing.plaf.FontUIResource("맑은 고딕", Font.PLAIN, 16));

		stockData = new StockData().getStockData();
		searchPane = new SearchPane(stockData);
		stockChart = new StockChart(searchPane);
		stockNews = new StockNews(searchPane);
		
		f = new JFrame("Stock Information");
		
		JMenuBar mb = new JMenuBar();
		JMenu settings = new JMenu("Settings");
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new MenuClickListener());
		exit.setActionCommand("exit");
		
		mb.add(settings);
		mb.add(exit);

		FrameDragListener frameMoveDragListener = new FrameDragListener();
		mb.addMouseListener(frameMoveDragListener);
		mb.addMouseMotionListener(frameMoveDragListener);
		
		f.setJMenuBar(mb);
		f.setSize(width, height);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(SplitPanel(), BorderLayout.CENTER);

		f.setUndecorated(true);
		f.setLocationByPlatform(true);
		f.setVisible(true);
		f.show();
	}
	
	private JSplitPane SplitPanel() { // right Panel(search)
		JPanel search = searchPane.getPanel();
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, SplitByRow(), search);
		split.setPreferredSize(new Dimension(width/5, height));
		split.setDividerSize(4);
		split.setDividerLocation(4*width/5);
		split.setEnabled(false); // fix right panel divider
		return split;
	}
	
	private JSplitPane SplitByRow() { // left Panel(chart, news)
		JPanel chart = stockChart.getPanel();
		JScrollPane news = stockNews.getPanel();
		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, chart, news);
		split.setPreferredSize(new Dimension(width, height/2));
		split.setDividerLocation(height/2);
		return split;
	}

	private void setUIFont(javax.swing.plaf.FontUIResource f) {
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource)
				UIManager.put(key, f);
		}
	}

	private class FrameDragListener extends MouseAdapter {
        private Point mouseOffset;

        @Override
        public void mousePressed(MouseEvent e) {
            Component c = SwingUtilities.getRoot(e.getComponent());
            Point mouseDownCoord = e.getLocationOnScreen();
            if (SwingUtilities.isLeftMouseButton(e)) {
                mouseOffset = new Point(mouseDownCoord.x - c.getX(), mouseDownCoord.y - c.getY());
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Component c = SwingUtilities.getRoot(e.getComponent());

            if (SwingUtilities.isLeftMouseButton(e)) {
                Point currCoords = e.getLocationOnScreen();
                c.setLocation(currCoords.x - mouseOffset.x, currCoords.y - mouseOffset.y);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mouseOffset = null;
        }
    }

	private class MenuClickListener implements ActionListener {
	
		@Override
		public void actionPerformed(ActionEvent e) {
			String s = e.getActionCommand();
			
			if (s.equals("exit")) {
				disposeFrame();
			}
		}
	
		private void disposeFrame() {
			f.setVisible(false);
			f.dispose();
		}
	}

	public static void main(String args[]) {
		new ShowWindow();
	}
}
