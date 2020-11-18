package src;
import javax.swing.JFrame;

/**
 * The class that starts up the visualizer
 * 
 * @author Spencer LaChance
 *
 */
@SuppressWarnings("serial")
public class VisualizerMain extends JFrame {
	private void init() {
		setTitle("Spencer's Visualizer");
		VisualizerController vc = new VisualizerController(null);
		setContentPane(vc.getView());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		VisualizerMain app = new VisualizerMain();
		app.init();
	}
}