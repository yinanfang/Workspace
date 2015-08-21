package a5jedi;

public abstract class DirectFrame extends FrameImpl{
	
	
	protected String title;
	protected Pixel pixels[][];
	protected static final String DEFAULT_TITLE = "untitled";
	protected Pixel init_color;
	
	public DirectFrame(int width, int height, Pixel init_color, String title) {
		super(width, height);
		if (width < 1 || height < 1) {
			throw new RuntimeException("Illegal dimensions.");
		}
		
		if (title.equals(null)){
			throw new RuntimeException("Title cannot be null.");
		}

		
		this.init_color = init_color;
		this.title = title;
		
		
	}
		
	
	
	
	public Pixel getPixel(int x, int y) {
		if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
			throw new RuntimeException("x,y coordinates out of bounds");
		}

		return pixels[y][x];
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
	
	
	
	
	public String toString(){
		String strg = "Frame: "+title+" ("+getWidth()+" x "+getHeight()+")";
		return strg;
	}

	public void setPixel(int x, int y, Pixel p) {
	}
	
	public abstract GrayFrame[] separate();
	
	public IndirectFrame crop(int x, int y, int width, int height) {
	
		IndirectFrame cropFrame = new IndirectFrame(this,x,y,width,height);
		return cropFrame;
	}
}
