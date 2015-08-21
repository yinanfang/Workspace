package a5novice;

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

	@Override
	public char asChar() {
		if (getBrightness()<0.1){
			return ' ';
		}else if(getBrightness()>=0.1&&getBrightness()<0.2){
			return '.';
		}else if(getBrightness()>=0.2&&getBrightness()<0.3){
			return ':';
		}else if(getBrightness()>=0.3&&getBrightness()<0.4){
			return '*';
		}else if(getBrightness()>=0.4&&getBrightness()<0.5){
			return '+';
		}else if(getBrightness()>=0.5&&getBrightness()<0.6){
			return '?';
		}else if(getBrightness()>=0.6&&getBrightness()<0.7){
			return 'X';
		}else if(getBrightness()>=0.7&&getBrightness()<0.8){
			return '%';
		}else if(getBrightness()>=0.8&&getBrightness()<0.9){
			return '#';
		}else{
			return '@';
		}
	}
}

