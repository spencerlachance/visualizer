package src;
import java.io.IOException;

import ddf.minim.analysis.*;

/**
 * The model of the visualizer which contains and deals with the data used to
 * create the animation
 * 
 * @author Spencer LaChance
 *
 */
public class VisualizerModel {

	private double[] avgSamples;
	private float sampleRate;
	AudioSampleReader asr;

	private static int SAMPLE_WINDOW_LENGTH = 2048;
	private static int NUM_SAMPLES = 1000; // The number of sample values to display in the visualizer
	private static int MAX_FREQ = 10000; // The highest frequency that will be displayed

	/**
	 * Constructor for the model
	 * 
	 * @param asr The AudioSampleReader object used to read data from the file
	 */
	public VisualizerModel(AudioSampleReader asr) {
		this.asr = asr;
		this.sampleRate = (int) asr.getFormat().getSampleRate();

		// The AudioSampleReader returns left and right sample valuess, so take their 
		// average to get an overall value for the sample.
		long numSamples = asr.getSampleCount();
		double[] leftSamples = new double[(int) numSamples];
		double[] rightSamples = new double[(int) numSamples];

		try {
			asr.getStereoSamples(leftSamples, rightSamples);
		} catch (IOException e) {
			e.printStackTrace();
		}

		avgSamples = new double[(int) numSamples];

		for (int i = 0; i < avgSamples.length; i++) {
			avgSamples[i] = (leftSamples[i] + rightSamples[i]) / 2;
		}
	}

	/**
	 * Looks at a window of samples around the current sample, performs a Fourier 
	 * Transform on them, and returns an array of frequency spectrum data for the 
	 * window. This frequency data will be used to render each frame of the visualizer 
	 * animation.
	 * 
	 * @return	An abbreviated array of frequency spectrum data of length NUM_SAMPLES
	 */
	public double[] getFreqSpectrum() {
		FourierTransform fft = new FFT(SAMPLE_WINDOW_LENGTH, sampleRate);
		float[] fftData = new float[SAMPLE_WINDOW_LENGTH];
		
		// Get the index of the current sample.
		// (Divide by 1000 because `getClipPosition()` returns a timestamp in 
		// milliseconds, but `sampleRate` is in Hz)
		int start = (int) (sampleRate / 1000 * asr.getClipPosition());

		// Get the window of samples from the song.
		int j = 0;
		for (int i = start - SAMPLE_WINDOW_LENGTH / 2; i < start + SAMPLE_WINDOW_LENGTH / 2; i++) {
			if (i < 0 || i >= avgSamples.length) {
				fftData[j] = 0;
				j++;
				continue;
			}
			fftData[j] = (float) avgSamples[i];
			j++;
		}
		
		
		// Apply the transform.
		fft.forward(fftData);
		
		// Use the transform to get the frequency data for the frame.
		double[] spectrum = new double[NUM_SAMPLES];
		float interval = MAX_FREQ / NUM_SAMPLES;
		for (int i = 0; i < NUM_SAMPLES; i++) {
			spectrum[i] = fft.getFreq(i * interval);
		}
		return spectrum;
	}
}
