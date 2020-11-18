package src;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

import designs.Circles;
import designs.SpectralPlot;
import designs.Waveform;

enum Design {
	WAVEFORM,
	PLOT,
	CIRCLES,
}

/**
 * Displays the visualizer animation
 * 
 * @author Spencer LaChance
 *
 */
public class VisualizerView extends JPanel {

	private JButton playButton;
	private JPanel visualizerPanel;
	private JFileChooser chooser;
	private JButton fileButton;
	private int currentDesign; // corresponds to the numbers in the drop-down
	private VisualizerModel vm;

	private static int FRAME_WIDTH = 1000;
	private static int FRAME_HEIGHT = 950;

	/**
	 * Constructor for the view
	 * 
	 * @param vc the VisualizerController that will listen for the events from the UI
	 */
	public VisualizerView(VisualizerController vc) {
		super();
		currentDesign = 0;

		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setLayout(new BorderLayout());
		visualizerPanel = new TitleCard();
		visualizerPanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		this.add(visualizerPanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		this.add(buttonPanel, BorderLayout.SOUTH);

		chooser = new JFileChooser();
		fileButton = new JButton("Choose File");
		fileButton.setActionCommand("FILE CHOSEN");
		fileButton.addActionListener(vc);
		buttonPanel.add(fileButton);

		playButton = new JButton("►");
		playButton.setActionCommand("PLAY/PAUSE");
		playButton.addActionListener(vc);
		buttonPanel.add(playButton);

		String[] options = {
			String.format("%d. Waveform", Design.WAVEFORM.ordinal() + 1), 
			String.format("%d. Spectral Plot", Design.PLOT.ordinal() + 1),
			String.format("%d. Circles", Design.CIRCLES.ordinal() + 1), 
		};
		JComboBox<String> designChooser = new JComboBox<>(options);
		designChooser.setActionCommand("DESIGN CHANGE");
		designChooser.addActionListener(vc);
		buttonPanel.add(designChooser);
	}

	/**
	 * Starts the animation
	 * 
	 * @param vm The model with the data needed to visually represent the audio
	 */
	public void start(VisualizerModel vm) {
		this.vm = vm;
		selectDesign(currentDesign);
		playButton.setText("❚❚");
	}
	
	/**
	 * Open the FileChooser and retrieve the file that the user selects
	 * 
	 * @return the file
	 */
	public File loadFile() {
		chooser.showOpenDialog(this);
		return chooser.getSelectedFile();
	}

	/**
	 * How the view reacts to the play button being toggled
	 * 
	 * @param state The current state of the animation after the button is pressed
	 *              (true = playing, false = paused)
	 */
	public void togglePlayPause(boolean state) {
		if (state) {
			playButton.setText("❚❚");
		} else {
			playButton.setText("►");
		}
	}

	/**
	 * Changes the type of animation displayed based on what the user selects from
	 * the drop-down
	 * 
	 * @param choice A number corresponding to an animation type
	 */
	public void selectDesign(int choice) {
		if (vm == null) {
			return;
		}

		togglePlayPause(false);
		this.remove(visualizerPanel);
		Design enumChoice = Design.values()[choice];
		switch (enumChoice) {
			case CIRCLES:
				visualizerPanel = new Circles(vm, FRAME_WIDTH, FRAME_HEIGHT);
				break;
			case PLOT:
				visualizerPanel = new SpectralPlot(vm, FRAME_WIDTH, FRAME_HEIGHT);
				break;
			case WAVEFORM:
				visualizerPanel = new Waveform(vm, FRAME_WIDTH, FRAME_HEIGHT);
		}
		visualizerPanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		this.add(visualizerPanel);
		togglePlayPause(true);
	}
	
	/**
	 * Display an error message
	 */
	public void showErrorMessage() {
		this.vm = null;
		playButton.setText("►");
		
		this.remove(visualizerPanel);
		visualizerPanel = new ErrorCard();
		visualizerPanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		this.add(visualizerPanel);
	}

	/**
	 * The text that shows before the user selects a file
	 * 
	 * @author Spencer LaChance
	 *
	 */
	private class TitleCard extends JPanel {

		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			g.drawString("Click the button below and choose a WAV, AU, or AIFF/AIFF-C file.", 
				FRAME_WIDTH / 2 - 200, FRAME_HEIGHT / 2);
		}
	}
	
	/**
	 * The text that shows when there is an error reading the audio file
	 * 
	 * @author Spencer LaChance
	 *
	 */
	private class ErrorCard extends JPanel {
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.drawString("An error occurred, please try another file. Make sure it's either in the"
					+ " WAV, AU, or AIFF/AIFF-c format.", FRAME_WIDTH / 2 - 250, FRAME_HEIGHT / 2);
		}
	}
}