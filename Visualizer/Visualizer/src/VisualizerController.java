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

	/**
	 * Constructor for the controller
	 * 
	 * @param filePath the path to the file used in the visualizer
	 */
	public VisualizerController(File f) {
		vv = new VisualizerView(this);
		init(f);
	}
	
	/**
	 * Initialize the VisualizerController's fields and start the animation
	 * 
	 * @param f the audio file that the user selected
	 */
	public void init(File f) {
		if (f != null) {
			try {
				asr = new AudioSampleReader(f);
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
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
				File f = vv.loadFile();
				init(f);
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
