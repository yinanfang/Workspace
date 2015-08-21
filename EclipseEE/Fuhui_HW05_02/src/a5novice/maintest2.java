package a5novice;

public class maintest2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
ColorFrame cf = new ColorFrame(4,4);
		
		cf.setPixel(0, 0, new ColorPixel(1.0, 0.7, 0.2));
		cf.setPixel(1, 0, new ColorPixel(0.6, 0.23, 0.8));
		cf.setPixel(2, 0, new ColorPixel(0.0, 0.3, 1.0));
		cf.setPixel(3, 0, new ColorPixel(1.0, 0.8, 0.9));
		cf.setPixel(0, 1, new ColorPixel(1.0, 0.7, 0.2));
		cf.setPixel(1, 1, new ColorPixel(0.6, 0.23, 0.8));
		cf.setPixel(2, 1, new ColorPixel(0.0, 0.3, 1.0));
		cf.setPixel(3, 1, new ColorPixel(1.0, 0.8, 0.9));
		cf.setPixel(0, 2, new ColorPixel(1.0, 0.7, 0.2));
		cf.setPixel(1, 2, new ColorPixel(0.6, 0.23, 0.8));
		cf.setPixel(2, 2, new ColorPixel(0.0, 0.3, 1.0));
		cf.setPixel(3, 2, new ColorPixel(1.0, 0.8, 0.9));
		cf.setPixel(0, 3, new ColorPixel(1.0, 0.7, 0.2));
		cf.setPixel(1, 3, new ColorPixel(0.6, 0.23, 0.8));
		cf.setPixel(2, 3, new ColorPixel(0.0, 0.3, 1.0));
		cf.setPixel(3, 3, new ColorPixel(1.0, 0.8, 0.9));
		System.out.println(cf.render());
	}

}
