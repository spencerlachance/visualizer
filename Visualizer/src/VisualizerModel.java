import java.io.IOException;

import ddf.minim.analysis.*;

/**
 * The model of the visualizer which contains and deals with the data used to create the animation
 * 
 * @author Spencer LaChance
 *
 */
public class VisualizerModel {
	
	private double[] avgSamples;
	private float sampleRate;
	AudioSampleReader asr;
	
	private static int SAMPLE_WINDOW_LENGTH = 512;
	
	/**
	 * Constructor for the model
	 * 
	 * @param asr	The AudioSampleReader object used to read data from the file
	 */
	public VisualizerModel(AudioSampleReader asr) {
		this.asr = asr;
		this.sampleRate = (int) asr.getFormat().getSampleRate();

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
	 * Looks at a window of samples around the current sample, performs a Fourier Transform on them,
	 * and returns an array of frequency spectrum data for the window
	 * 
	 * @return	An abbreviated array of frequency spectrum data of length 1000
	 */
	public int[] getFreqSpectrum() {
		FourierTransform fft = new FFT(SAMPLE_WINDOW_LENGTH, sampleRate);
		float[] fftData = new float[SAMPLE_WINDOW_LENGTH];
		
		int start = (int) (sampleRate / 1000 * asr.getClipPosition());
		int j = 0;
		for (int i = start - SAMPLE_WINDOW_LENGTH / 2; i < start + SAMPLE_WINDOW_LENGTH / 2; i++) {
			if (i < 0 || i > avgSamples.length) {
				fftData[j] = 0;
				j++;
				continue;
			}
			fftData[j] = (float) avgSamples[i];
			j++;
		}
		
		fft.forward(fftData);
		
		int[] spectrum = new int[1000];
		int nyquist = (int) sampleRate / 2;
		float interval = nyquist / 1000;
		for (int i = 0; i < 1000; i++) {
			spectrum[i] = (int) fft.getFreq(i * interval);
		}
		return spectrum;
	}
}
