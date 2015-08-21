package a5adept;

public class maintest2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GrayFrame f = new GrayFrame(3,3);
		f.setPixel(0,0, new GrayPixel(1.0));
		f.setPixel(1,0, new GrayPixel(0.15));
		f.setPixel(2,0, new GrayPixel(1.0));
		f.setPixel(0,1, new GrayPixel(0.25));
		f.setPixel(1,1, new GrayPixel(0.45));
		f.setPixel(2,1, new GrayPixel(0.25));
		f.setPixel(0,2, new GrayPixel(1.0));
		f.setPixel(1,2, new GrayPixel(0.15));
		f.setPixel(2,2, new GrayPixel(1.0));
		System.out.println(f.render());
	}

}
