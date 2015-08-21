package a5jedi;

public class IndirectFrame extends FrameImpl{
	
	private Frame source;
	private int x_offset;
	private int y_offset;

	public IndirectFrame(Frame source, int x_offset, int y_offset, int width, int height){
		super(width, height);
		if (!(x_offset<source.getWidth()&&x_offset>=0)){
			throw new IllegalArgumentException("x_offset is illegal");
		}else if(!((y_offset<source.getHeight()&&y_offset>=0))){
			throw new IllegalArgumentException("y_offest is illegal");
		}else if (!((x_offset+width>0)&&x_offset+width<source.getWidth())){
			throw new IllegalArgumentException("width goes out of boundary");
		}else if (!((y_offset+height>0)&&y_offset+height<source.getHeight())){
			throw new IllegalArgumentException("height goes out of boundary");
		}
		
		this.source = source;
		this.x_offset = x_offset;
		this.y_offset = y_offset;


		
	}

	
	@Override
	public Pixel getPixel(int x, int y) {
		return source.getPixel(x+x_offset, y+y_offset);
	}

	@Override
	public void setPixel(int x, int y, Pixel p) {
		source.setPixel(x+x_offset, y+y_offset,p);
	}

	@Override
	public String getTitle() {
		return source.getTitle();
	}

	@Override
	public void setTitle(String new_title) {
		source.setTitle(new_title);
	}

	
	public GrayFrame[] separate() {
		Frame indirectFrame = null;
		for (int i=0;i<width;i++){
			for (int j=0;j<height;j++){
				indirectFrame.setPixel(i, j, source.getPixel(x_offset+i, y_offset+j));
		}
	}
		
		return indirectFrame.separate();
	}

	
	
	
	public String toString(){
		String strg = "Indirect Frame: "+getTitle()+" ("+getWidth()+" x "+getHeight()+") at ("+x_offset+", "+y_offset+")";
		return strg;
	}

	@Override
	public IndirectFrame crop(int x, int y, int width, int height) {
		IndirectFrame cropFrame = new IndirectFrame(source,x+x_offset,y+y_offset,width,height);
		return cropFrame;
	}

}
