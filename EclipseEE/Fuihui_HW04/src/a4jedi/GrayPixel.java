package a4jedi;

public class GrayPixel implements Pixel {

	private double level;

	public GrayPixel(double level) {
		this.level = level;
	}
	
	public double getRed() {
		return level;
	}

	public double getGreen() {
		return level;
	}

	public double getBlue() {
		return level;
	}

	public double getBrightness() {
		return level;
	}
}
