package a5jedi;

public interface Frame {
	int getWidth();
	int getHeight();
	Pixel getPixel(int x, int y);
	void setPixel(int x, int y, Pixel p);
	String getTitle();
	void setTitle(String new_title);
	boolean equals(Frame f);
	GrayFrame[] separate();
	ColorPixel getAverage();
	String render();
	String toString();
	IndirectFrame crop(int x, int y, int width, int height);
	IndirectFrame[][] makeTiles(int num_across, int num_down);
}
