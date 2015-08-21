package a4jedi;

public class GrayFrame extends FrameImpl {

	private static final Pixel DEFAULT_PIXEL_VALUE = new GrayPixel(0.5);
	
	public GrayFrame(int width, int height, Pixel init_color, String title) {
		
		super(width, height, title);

		if (init_color == null) {
			throw new RuntimeException("Illegal initial pixel: null");
		}
				
		pixels = new GrayPixel[height][width];
		for (int y = 0; y<height; y++) {
			for (int x = 0; x<width; x++) {
				pixels[y][x] = init_color;
			}
		}
	}
	
	public GrayFrame(int width, int height) {
		this(width, height, DEFAULT_PIXEL_VALUE, DEFAULT_TITLE);
	}

	public void setPixel(int x, int y, Pixel p) {
		if (!(p instanceof GrayPixel)) {
			throw new RuntimeException("GrayFrame can only accept GrayPixel");
		}

		if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
			throw new RuntimeException("x,y coordinates out of bounds");
		}
		
		pixels[y][x] = p;
	}

	@Override
	public GrayFrame[] separate() {
		// TODO Auto-generated method stub
		return null;
	}
}
