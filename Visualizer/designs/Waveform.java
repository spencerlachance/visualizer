package designs;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

import src.VisualizerModel;

/**
 * Creates a more aesthetically pleasing animation of the song's frequency spectral plot centered in
 * the middle of the window
 * 
 * @author Spencer LaChance
 *
 */
@SuppressWarnings("serial")
public class Waveform extends VisualizerDesign {
	
	public Waveform(VisualizerModel vm, int width, int height) {
		super(vm, width, height);
	}

	/**
	 * Gets frequency spectrum data for a specific point in time from the model and then translates
	 * it into an mirrored image of the spectral plot centered in the window
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		double[] spectrum = vm.getFreqSpectrum();

		Random randy = new Random();
		int a;
		for (int i = 1; i <= spectrum.length; i++) {
			a = (int) (5 * spectrum[i - 1]);
			g.setColor(new Color(randy.nextInt(255), randy.nextInt(255), randy.nextInt(255)));
			g.fillRect(i, FRAME_HEIGHT / 2 - a, 1, a);
			g.fillRect(i, FRAME_HEIGHT / 2, 1, a);
		}
	}
}