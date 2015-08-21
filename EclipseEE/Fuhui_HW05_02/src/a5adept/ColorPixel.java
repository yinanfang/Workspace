package a5adept;

public class ColorPixel implements Pixel {
	
	private double red;
	private double green;
	private double blue;

	public ColorPixel(double red, double green, double blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public double getRed() {
		return red;
	}

	public double getGreen() {
		return green;
	}

	public double getBlue() {
		return blue;
	}

	public double getBrightness() {
		return 0.2126*red + 0.7152*green + 0.0722*blue;
	}

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
