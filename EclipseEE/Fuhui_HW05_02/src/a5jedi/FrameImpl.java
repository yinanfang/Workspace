package a5jedi;

import java.util.ArrayList;

public class FrameImpl implements Frame{
	protected int width;
	protected int height;
	/**
	 * @param args
	 */
	public FrameImpl(int width, int height){
		this.width = width;
		this.height = height;

	}

	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public Pixel getPixel(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPixel(int x, int y, Pixel p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTitle(String new_title) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean equals(Frame f) {
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

	@Override
	public GrayFrame[] separate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ColorPixel getAverage() {
		double totalRed = 0.0;
		double totalGreen = 0.0;
		double totalBlue = 0.0;
		int area = getWidth()*getHeight();
		for (int i=0;i<getWidth();i++){
			for (int j=0;j<getHeight();j++){
				totalRed += this.getPixel(i, j).getRed();
				totalGreen += this.getPixel(i, j).getGreen();
				totalBlue += this.getPixel(i, j).getBlue();
			}
		}
		ColorPixel averagePixel = new ColorPixel(totalRed/area,totalGreen/area,totalBlue/area);
		return averagePixel;
	}

	public String render() {
		String charString = "";
		for (int i=0;i<getHeight();i++){
			for (int j=0;j<getWidth();j++){
				charString += this.getPixel(j,i).asChar();
			}
			if (i<getHeight()-1){
				charString += '\n';
			}
		}
		return charString;
	}

	@Override
	public IndirectFrame crop(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IndirectFrame[][] makeTiles(int num_across, int num_down) {
		System.out.println("input frame width is: " + this.getWidth());
		System.out.println("input frame height is: " + this.getHeight());
		
		System.out.println("num_across is: " + num_across);
		System.out.println("num_down is: " + num_down);
		
		int remainder_across = width%num_across;
		int reminder_down = height%num_down;
		int length_across = width/num_across;
		int length_down = width/num_down;
		System.out.println("remainder_across is: " + remainder_across);
		System.out.println("reminder_down is: " + reminder_down);
		System.out.println("length_across is: " + length_across);
		System.out.println("length_down is: " + length_down);
		
		
		System.out.println("======================");
		System.out.println("Start makeTiles");
		System.out.println("======================");
		System.out.println("got here01");
		IndirectFrame[][] tiles = new IndirectFrame[num_across][num_down];
		
		int[] crop_width = new int[num_across];
		for (int i = 0; i < num_across; i++) {
			if (i < remainder_across){
				crop_width[i] = length_across + 1;
			}else {
				crop_width[i] = length_across;
			}
			System.out.println(crop_width[i]);
		}
		
		int[] crop_height = new int[num_down];
		for (int i = 0; i < num_down; i++) {
			if (i < reminder_down){
				crop_height[i] = length_down + 1;
			}else {
				crop_height[i] = length_down;
			}
			System.out.println(crop_height[i]);
		}
		
		for (int i = 0; i < num_across; i++) {
			for (int j = 0; j < num_down; j++) {
				int x = 0;
				for (int m = 0; m <= i; m++){
					x += crop_width[m];
				}
				x -= crop_width[i];
				
				int y = 0;
				for (int n = 0; n <= i; n++){
					y += crop_height[n];
				}
				y -= crop_height[i];
				
				int crop_inner_width = crop_width[i];
				int crop_inner_height = crop_height[j];
				
				System.out.println("=========");
				
				System.out.println("Here i = "+i+" and j = "+j);
				
				System.out.println("So the parameter is:");
				System.out.println("x: " + x + ";y: " + y +"crop_inner_width: " + crop_inner_width +"crop_inner_height: " + crop_inner_height);
				
				System.out.println("=========");
				
				
			}
		}
		
		
		
//		ArrayList<int[]> crop_width = new ArrayList<int[]>();
//		ArrayList<int[]> crop_height = new ArrayList<int[]>();
//		
//		for (int i = num_across; i < num_across; i++) {
//			crop_width.add
//		}
//		
//		int crop_width
//		for (int i=0;i<remainder_across;i++){
		

//		for (int i=0;i<remainder_across;i++){
//			System.out.println("In");
//			for (int j=0;j<reminder_down;j++){
//				System.out.println("(length_across+1)*i: " + (length_across+1)*i);
//				System.out.println("(length_down+1)*j: " + (length_down+1)*j);
//				System.out.println("length_across+1: " + (length_across+1));
//				System.out.println("length_down+1: " + (length_down+1));
//				tiles[i][j] = this.crop((length_across+1)*i,(length_down+1)*j,length_across+1,length_down+1);
////				System.out.println("length_down+1: " + length_down+1);
//				System.out.println("finished");
//			}
//		}
//		
//		System.out.println("got here02");
//		for (int i=remainder_across;i<num_across;i++){
//			for(int j=reminder_down;j<num_down;j++){
//				tiles[remainder_across+i][reminder_down+j] = this.crop(length_across*i, length_down*j, length_across, length_down);
//			}
//		}
//		System.out.println("got here03");
		return tiles;
	}

}
