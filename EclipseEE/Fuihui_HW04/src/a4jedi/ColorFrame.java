package a4jedi;

public class ColorFrame extends FrameImpl  {

	private static final Pixel DEFAULT_PIXEL_VALUE = new ColorPixel(0.5, 0.5, 0.5);
	
	public ColorFrame(int width, int height, Pixel init_color, String title) {
		
		super(width, height, title);
		
		if (init_color == null) {
			throw new RuntimeException("Illegal initial pixel: null");
		}
				
		pixels = new ColorPixel[height][width];
		for (int y = 0; y<height; y++) {
			for (int x = 0; x<width; x++) {
				pixels[y][x] = init_color;
			}
		}
	}
	
	public ColorFrame(int width, int height) {
		this(width, height, ColorFrame.DEFAULT_PIXEL_VALUE, ColorFrame.DEFAULT_TITLE);
	}

	public void setPixel(int x, int y, Pixel p) {
		if (!(p instanceof ColorPixel)) {
			throw new RuntimeException("ColorFrame can only accept ColorPixel");
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
