package a4adept;

public class FrameImpl implements Frame {

	protected Pixel pixels[][];
	protected int width;
	protected int height;
	protected String title;
	protected static final String DEFAULT_TITLE = "untitled";

	public FrameImpl(int width, int height, String title) {
		
		if (width < 1 || height < 1) {
			throw new RuntimeException("Illegal dimensions.");
		}
		
		if (title.equals(null)){
			throw new RuntimeException("Title cannot be null.");
		}

		this.width = width;
		this.height = height;
		this.title = title;
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

	@Override
	public void setPixel(int x, int y, Pixel p) {
		// TODO Auto-generated method stub
		
	}

}
