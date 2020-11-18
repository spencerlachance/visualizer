package designs;

import javax.swing.JPanel;

import src.VisualizerModel;

/**
 * Parent class for all Visualizer designs.
 * 
 * @author Spencer LaChance
 */
public abstract class VisualizerDesign extends JPanel {

	protected VisualizerModel vm;

	protected static int FRAME_HEIGHT;
	protected static int FRAME_WIDTH;

	/**
	 * Constructor for a VisualizerDesign.
	 * 
	 * @param vm     The model with all of the needed data
	 * @param width  Width of the application window
	 * @param height Height of the application window
	 */
	public VisualizerDesign(VisualizerModel vm, int width, int height) {
		this.vm = vm;
		FRAME_WIDTH = width;
		FRAME_HEIGHT = height;
	}
}
