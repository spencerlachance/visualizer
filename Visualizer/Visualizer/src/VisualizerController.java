package src;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComboBox;
import javax.swing.Timer;

/**
 * The controller for the visualizer that communicates between the user, view,
 * and model
 * 
 * @author Spencer LaChance
 *
 */
public class VisualizerController implements ActionListener {

	private Timer timer;
	private AudioSampleReader asr;
	private VisualizerModel vm;
	private VisualizerView vv;
	private boolean state; // true = playing, false = paused
	private File f;

	/**
	 * Constructor for the controller
	 * 
	 * @param filePath the path to the file used in the visualizer
	 */
	public VisualizerController(File f) {
		vv = new VisualizerView(this);
		f = null;
		init();
	}
	
	/**
	 * Initialize the VisualizerController's fields and start the animation
	 */
	public void init() {
		if (f != null) {
			try {
				asr = new AudioSampleReader(f);
			} catch (UnsupportedAudioFileException e) {
				f = null;
				vv.showErrorMessage();
				return;
			} catch (IOException e) {
				f = null;
				vv.showErrorMessage();
				return;
			}
			
			vm = new VisualizerModel(asr);
			state = false;
			asr.createClip();

			timer = new Timer(10, this);
			timer.setInitialDelay(0);
			timer.setActionCommand("TICK");
			
			start();
		}
	}

	/**
	 * Starts the animation
	 */
	public void start() {
		timer.restart();
		asr.startClip();
		state = true;
	}

	/**
	 * What happens every timer tick, the Choose File button is pressed, or a design
	 * is selected from the drop-down
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "TICK":
				vv.repaint();
				break;
			case "FILE CHOSEN":
				f = vv.loadFile();
				init();
				if (f != null) {
					vv.start(vm);
				}
				break;
			case "DESIGN CHANGE":
				// Parse the design index from the ComboBox selection
				char choice = ((String) ((JComboBox) e.getSource()).getSelectedItem()).charAt(0);
				vv.selectDesign(Character.getNumericValue(choice) - 1);
				break;
			case "PLAY/PAUSE":
				if (f != null) {
					if (state) {
						timer.stop();
						state = false;
					} else {
						timer.start();
						state = true;
					}
					vv.togglePlayPause(state);
				}
			}
	}

	/**
	 * Getter for the Model object
	 * 
	 * @return The model with all of the data for the visualizer
	 */
	public VisualizerModel getModel() {
		return vm;
	}
	
	/**
	 * Getter for the View object
	 * 
	 * @return the View
	 */
	public VisualizerView getView() {
		return vv;
	}
}
