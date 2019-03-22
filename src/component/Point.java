package component;

public class Point {
	public int x;
	public int y;
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public void increaseX() {
		this.x++;
	}
	
	public void increaseY() {
		System.out.println(this.y);
		this.y++;
		System.out.println(this.y);
	}
	
	public void decreaseX() {
		this.x--;
	}
	
	public void decreaseY() {
		this.y--;
	}
	
	public Point(){}

	
}

