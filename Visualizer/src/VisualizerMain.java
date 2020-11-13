import javax.swing.JFrame;

/**
 * The class that gets the input file path from the user and then runs the
 * visualizer with it
 * 
 * @author Spencer LaChance
 *
 */
@SuppressWarnings("serial")
public class VisualizerMain extends JFrame {

	public static void main(String[] args) {
		VisualizerMain app = new VisualizerMain();
		app.init();
	}

	private void init() {
		setTitle("Spencer's Visualizer");
		VisualizerView vv = new VisualizerView();
		setContentPane(vv);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
}