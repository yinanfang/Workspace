package a4novice;

public class ColorFrame implements Frame {

	private static final Pixel DEFAULT_PIXEL_VALUE = new ColorPixel(0.5, 0.5, 0.5);
	

	private Pixel pixels[][];
	private int width;
	private int height;
	private String title;
	private static final String DEFAULT_TITLE = "untitled";
	
	public ColorFrame(int width, int height, Pixel init_color, String title) {
		if (width < 1 || height < 1) {
			throw new RuntimeException("Illegal dimensions.");
		}
		
		if (title.equals(null)){
			throw new RuntimeException("Title cannot be null.");
		}

		this.width = width;
		this.height = height;
		this.title = title;

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
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Pixel getPixel(int x, int y) {
		if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
			throw new RuntimeException("x,y coordinates out of bounds");
		}

		return pixels[y][x];
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
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String new_title){
		if (new_title.equals(null)){
			throw new RuntimeException("Title cannot be null");
		}
		title = new_title;
	}
	
	public boolean equals(Frame f){
		if (!(getWidth()==f.getWidth()&&getHeight()==f.getHeight())){
			return false;
		}
		for (int i=0;i<f.getWidth();i++){
			for (int j=0;j<f.getHeight();j++){
				if (!(Math.abs(f.getPixel(i, j).getBrightness()-getPixel(i,j).getBrightness())<0.01)){
					return false;
				}
			}
		}
		return true;
	}
	
	public String toString(){
		String strg = "Frame: "+title+" ("+getWidth()+"x"+getHeight()+" )";
		return strg;
	}
}
