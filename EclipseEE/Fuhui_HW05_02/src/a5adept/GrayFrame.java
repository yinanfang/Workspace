package a5adept;



public class GrayFrame extends FrameImpl{
	
	
	private static final Pixel DEFAULT_PIXEL_VALUE = new GrayPixel(0.5);
	
	public GrayFrame(int width, int height, Pixel init_color, String title) {
		super(width, height, init_color, title);
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
	public GrayFrame(int width, int height){
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
	
	public GrayFrame[] separate() {
		GrayFrame[] grayArray = new GrayFrame[1];
		grayArray[0] = new GrayFrame(width, height);
		for (int i=0;i<getWidth();i++){
			for (int j=0;j<getHeight();j++){
					grayArray[0].setPixel(i,j,this.getPixel(i,j));
			}
		}
			return grayArray;
	}
	
	
	
	
}