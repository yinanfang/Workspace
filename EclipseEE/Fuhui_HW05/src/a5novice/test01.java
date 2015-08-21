package a5novice;

import static org.junit.Assert.assertEquals;

public class test01 {

	public static void main(String[] args) {
		System.out.println("=============================================================");
		System.out.println("Started test01");
		System.out.println("=============================================================");
		System.out.println("");
		
		test_asChar();
		

	}
	
	public void getAverageTest() {
		GrayFrame gf = new GrayFrame(10,10,new GrayPixel(1.0), "average test frame");
		
		GrayPixel zero_pixel = new GrayPixel(0.0);
		GrayPixel half_pixel = new GrayPixel(0.5);
		GrayPixel qtr_pixel = new GrayPixel(0.25);

		paint_row(gf, 0, zero_pixel);
		paint_row(gf, 1, zero_pixel);
		paint_row(gf, 2, half_pixel);
		paint_row(gf, 3, half_pixel);
		paint_row(gf, 4, qtr_pixel);
		paint_row(gf, 5, qtr_pixel);
		
		assertEquals(0.55, gf.getAverage().getBrightness(), 0.01);
		
		double pixel_sum = 0.0;
		for (int x=0; x<10; x++) {
			for (int y=0; y<10; y++) {
				double rnd_val = Math.random();
				GrayPixel random_gp = new GrayPixel(rnd_val);
	
				pixel_sum += rnd_val;				
				gf.setPixel(x, y, random_gp);
			}
		}
		
		assertEquals(pixel_sum/100.0, gf.getAverage().getBrightness(), 0.01);
	}
	
	private static void paint_row(Frame f, int ridx, Pixel p) {
		for (int x=0; x<f.getWidth(); x++) {
			f.setPixel(x, ridx, p);
		}
	}
	
	
	
	
	
	
	
	
	static void test_asChar() {
		System.out.println("==========================");
		System.out.println("Test for asChar()");
		System.out.println("==========================");
		
		ColorPixel cp = new ColorPixel(0.05, 0.05, 0.05);
		System.out.println("Correct output should be: ' '");
		System.out.printf("cp output is:             '%s'\n", cp.asChar());
		
		cp = new ColorPixel(0.15, 0.15, 0.15);
		System.out.println("Correct output should be: '.'");
		System.out.printf("cp output is:             '%s'\n", cp.asChar());
		
		cp = new ColorPixel(0.25, 0.25, 0.25);
		System.out.println("Correct output should be: ':'");
		System.out.printf("cp output is:             '%s'\n", cp.asChar());

		cp = new ColorPixel(0.35, 0.35, 0.35);
		System.out.println("Correct output should be: '*'");
		System.out.printf("cp output is:             '%s'\n", cp.asChar());

		cp = new ColorPixel(0.45, 0.45, 0.45);
		System.out.println("Correct output should be: '+'");
		System.out.printf("cp output is:             '%s'\n", cp.asChar());
		
		cp = new ColorPixel(0.55, 0.55, 0.55);
		System.out.println("Correct output should be: '?'");
		System.out.printf("cp output is:             '%s'\n", cp.asChar());

		cp = new ColorPixel(0.65, 0.65, 0.65);
		System.out.println("Correct output should be: 'X'");
		System.out.printf("cp output is:             '%s'\n", cp.asChar());

		cp = new ColorPixel(0.75, 0.75, 0.75);
		System.out.println("Correct output should be: '%'");
		System.out.printf("cp output is:             '%s'\n", cp.asChar());

		cp = new ColorPixel(0.85, 0.85, 0.85);
		System.out.println("Correct output should be: '#'");
		System.out.printf("cp output is:             '%s'\n", cp.asChar());

		cp = new ColorPixel(0.95, 0.95, 0.95);
		System.out.println("Correct output should be: '@'");
		System.out.printf("cp output is:             '%s'\n", cp.asChar());
	}
}
